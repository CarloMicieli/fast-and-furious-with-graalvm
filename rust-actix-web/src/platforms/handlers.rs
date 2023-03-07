use crate::games::game_request::GameRequest;
use crate::platforms::platform::PlatformRequest;
use actix_web::{web, HttpResponse, Responder};
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
                web::resource("/{platform}")
                    .route(web::get().to(get_platform_by_id))
                    .route(web::put().to(put_platform))
            )
    );
}

pub async fn get_all_platforms(_db_pool: web::Data<PgPool>) -> impl Responder {
    HttpResponse::Ok()
}

pub async fn post_platform(
    _request: web::Json<PlatformRequest>,
    _db_pool: web::Data<PgPool>,
) -> impl Responder {
    HttpResponse::Ok()
}

pub async fn get_platform_by_id(
    _platform_urn: web::Path<Urn>,
    _db_pool: web::Data<PgPool>,
) -> impl Responder {
    HttpResponse::Ok()
}

pub async fn put_platform(
    _platform_urn: web::Path<Urn>,
    _request: web::Json<PlatformRequest>,
    _db_pool: web::Data<PgPool>,
) -> impl Responder {
    HttpResponse::Ok()
}
