use super::{
    database::{find_all_platform, find_platform_by_urn, update_platform},
    platform_request::PlatformRequest,
};
use crate::{
    games::database::find_all_games_by_platform, platforms::database::insert_platform,
    responders::ServerResponseError,
};
use actix_web::{web, HttpResponse};
use anyhow::Context;
use sqlx::PgPool;
use urn::Urn;

pub const PLATFORMS_ROOT_API: &str = "/platforms";

pub fn configure_platform_routes(cfg: &mut web::ServiceConfig) {
    #[rustfmt::skip]
    cfg.service(
        web::scope(PLATFORMS_ROOT_API)
            .service(
                web::resource("")
                    .route(web::post().to(post_platform))
                    .route(web::get().to(get_all_platforms))
            )
            .service(
                web::scope("/{platform}")
                    .service(
                    web::resource("")
                        .route(web::get().to(get_platform_by_urn))
                        .route(web::put().to(put_platform)))
                    .service(
                        web::resource("/games")
                        .route(web::get().to(get_all_games_by_platform))
                    )
            )
    );
}

pub async fn get_all_games_by_platform(
    platform_urn: web::Path<Urn>,
    db_pool: web::Data<PgPool>,
) -> Result<HttpResponse, ServerResponseError> {
    let mut transaction = db_pool
        .begin()
        .await
        .context("Unable to begin a database transaction")?;

    let results = find_all_games_by_platform(&platform_urn, &mut transaction).await?;

    transaction
        .commit()
        .await
        .context("Unable to commit a database transaction")?;

    Ok(HttpResponse::Ok().json(results))
}

pub async fn get_all_platforms(
    db_pool: web::Data<PgPool>,
) -> Result<HttpResponse, ServerResponseError> {
    let mut transaction = db_pool
        .begin()
        .await
        .context("Unable to begin a database transaction")?;

    let results = find_all_platform(&mut transaction).await?;

    transaction
        .commit()
        .await
        .context("Unable to commit a database transaction")?;

    Ok(HttpResponse::Ok().json(results))
}

pub async fn post_platform(
    request: web::Json<PlatformRequest>,
    db_pool: web::Data<PgPool>,
) -> Result<HttpResponse, ServerResponseError> {
    let new_platform = &request.0;
    let mut transaction = db_pool
        .begin()
        .await
        .context("Unable to begin a database transaction")?;

    let platform_urn = insert_platform(new_platform, &mut transaction).await?;

    transaction
        .commit()
        .await
        .context("Unable to commit a database transaction")?;

    let location = format!("/platforms/{}", &platform_urn);
    Ok(HttpResponse::Created()
        .insert_header(("Location", location))
        .finish())
}

pub async fn get_platform_by_urn(
    platform_urn: web::Path<Urn>,
    db_pool: web::Data<PgPool>,
) -> Result<HttpResponse, ServerResponseError> {
    let platform_urn: &Urn = platform_urn.as_ref();

    let mut transaction = db_pool
        .begin()
        .await
        .context("Unable to begin a database transaction")?;

    let result = find_platform_by_urn(platform_urn, &mut transaction).await?;

    transaction
        .commit()
        .await
        .context("Unable to commit the database transaction")?;

    result
        .map(|platform| Ok(HttpResponse::Ok().json(platform)))
        .unwrap_or_else(|| Ok(HttpResponse::NotFound().finish()))
}

pub async fn put_platform(
    platform_urn: web::Path<Urn>,
    request: web::Json<PlatformRequest>,
    db_pool: web::Data<PgPool>,
) -> Result<HttpResponse, ServerResponseError> {
    let platform_urn = platform_urn.as_ref();
    let platform_update = &request.0;

    let mut transaction = db_pool
        .begin()
        .await
        .context("Unable to begin a database transaction")?;

    let existing_platform = find_platform_by_urn(platform_urn, &mut transaction).await?;

    match existing_platform {
        Some(existing_platform) => {
            update_platform(
                &existing_platform.platform_id,
                platform_update,
                existing_platform.metadata,
                &mut transaction,
            )
            .await?
        }
        None => {
            let _ = insert_platform(platform_update, &mut transaction).await?;
        }
    };

    transaction
        .commit()
        .await
        .context("Unable to commit the database transaction")?;

    Ok(HttpResponse::NoContent().finish())
}
