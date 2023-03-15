use crate::{
    games::game_request::GameRequest, platforms::database::find_platform_id_by_name,
    responders::ServerResponseError,
};
use actix_web::{web, HttpResponse};
use anyhow::Context;
use sqlx::PgPool;
use urn::Urn;

use super::database::{delete_game_by_urn, find_game_by_urn, insert_game, update_game};

pub const GAMES_ROOT_API: &str = "/games";

pub fn configure_game_routes(cfg: &mut web::ServiceConfig) {
    #[rustfmt::skip]
    cfg.service(
        web::scope(GAMES_ROOT_API)
            .service(
                web::resource("")
                    .route(web::post().to(post_game))
            )
            .service(
                web::resource("/{game}")
                    .route(web::delete().to(delete_game))
                    .route(web::get().to(get_game_by_urn))
                    .route(web::put().to(put_game))
            )
    );
}

pub async fn post_game(
    request: web::Json<GameRequest>,
    db_pool: web::Data<PgPool>,
) -> Result<HttpResponse, ServerResponseError> {
    let mut transaction = db_pool
        .begin()
        .await
        .context("Unable to begin a database transaction")?;

    let new_game = &request.0;
    let game_urn: Urn = new_game.try_into().unwrap();

    let platform_id = find_platform_id_by_name(&request.platform, &mut transaction).await?;
    if platform_id.is_none() {
        return Err(ServerResponseError::Conflict(format!(
            "platform {} not found",
            new_game.platform
        )));
    }

    insert_game(new_game, &game_urn, platform_id.unwrap(), &mut transaction).await?;

    transaction
        .commit()
        .await
        .context("Unable to commit a database transaction")?;

    let location = format!("/games/{}", &game_urn);
    Ok(HttpResponse::Created()
        .insert_header(("Location", location))
        .finish())
}

pub async fn delete_game(
    game_urn: web::Path<Urn>,
    db_pool: web::Data<PgPool>,
) -> Result<HttpResponse, ServerResponseError> {
    let mut transaction = db_pool
        .begin()
        .await
        .context("Unable to begin a database transaction")?;

    let _ = delete_game_by_urn(&game_urn, &mut transaction).await?;

    transaction
        .commit()
        .await
        .context("Unable to commit a database transaction")?;

    Ok(HttpResponse::NoContent().finish())
}

pub async fn get_game_by_urn(
    game_urn: web::Path<Urn>,
    db_pool: web::Data<PgPool>,
) -> Result<HttpResponse, ServerResponseError> {
    let mut transaction = db_pool
        .begin()
        .await
        .context("Unable to begin a database transaction")?;

    let result = find_game_by_urn(&game_urn, &mut transaction).await?;

    transaction
        .commit()
        .await
        .context("Unable to commit a database transaction")?;

    result
        .map(|game| Ok(HttpResponse::Ok().json(game)))
        .unwrap_or_else(|| {
            Err(ServerResponseError::NotFound(format!(
                "game {} not found",
                game_urn
            )))
        })
}

pub async fn put_game(
    game_urn: web::Path<Urn>,
    request: web::Json<GameRequest>,
    db_pool: web::Data<PgPool>,
) -> Result<HttpResponse, ServerResponseError> {
    let game_update = &request.0;

    let mut transaction = db_pool
        .begin()
        .await
        .context("Unable to begin a database transaction")?;

    let platform_id = find_platform_id_by_name(&game_update.platform, &mut transaction).await?;
    if platform_id.is_none() {
        return Err(ServerResponseError::Conflict(format!(
            "platform {} not found",
            game_update.platform
        )));
    }

    let platform_id = platform_id.unwrap();

    let existing_game = find_game_by_urn(&game_urn, &mut transaction).await?;

    match existing_game {
        Some(existing_game) => {
            update_game(
                existing_game.game_id,
                game_update,
                platform_id,
                existing_game.metadata,
                &mut transaction,
            )
            .await?
        }
        None => {
            insert_game(game_update, &game_urn, platform_id, &mut transaction).await?;
        }
    }

    transaction
        .commit()
        .await
        .context("Unable to commit a database transaction")?;

    Ok(HttpResponse::NoContent().finish())
}
