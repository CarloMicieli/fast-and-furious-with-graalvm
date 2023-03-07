use crate::games::game_request::GameRequest;
use actix_web::{web, HttpResponse, Responder};
use sqlx::PgPool;
use urn::Urn;

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
                    .route(web::get().to(get_brand_by_id))
                    .route(web::put().to(put_brand))
            )
    );
}

pub async fn post_game(
    _request: web::Json<GameRequest>,
    _db_pool: web::Data<PgPool>,
) -> impl Responder {
    HttpResponse::Ok()
}

pub async fn delete_game(_game_urn: web::Path<Urn>, _db_pool: web::Data<PgPool>) -> impl Responder {
    HttpResponse::Ok()
}

pub async fn get_brand_by_id(
    _game_urn: web::Path<Urn>,
    _db_pool: web::Data<PgPool>,
) -> impl Responder {
    HttpResponse::Ok()
}

pub async fn put_brand(
    _game_urn: web::Path<Urn>,
    _request: web::Json<GameRequest>,
    _db_pool: web::Data<PgPool>,
) -> impl Responder {
    HttpResponse::Ok()
}
