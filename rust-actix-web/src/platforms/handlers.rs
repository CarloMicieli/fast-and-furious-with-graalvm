use super::{platform_request::PlatformRequest, platform_type::PlatformType};
use crate::responders::ServerResponseError;
use actix_web::{web, HttpResponse, Responder};
use anyhow::Context;
use slug::slugify;
use sqlx::PgPool;
use urn::{Urn, UrnBuilder};
use uuid::Uuid;

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
    request: web::Json<PlatformRequest>,
    db_pool: web::Data<PgPool>,
) -> Result<HttpResponse, ServerResponseError> {
    let platform_id = Uuid::new_v4();

    let medias: &Vec<String> = &request
        .0
        .media
        .iter()
        .map(|media| media.to_string())
        .collect();
    let new_platform = &request.0;

    let name_slug = slugify(&new_platform.name);
    let platform_urn = UrnBuilder::new("platform", &name_slug).build().unwrap();

    sqlx::query!(
        r#"
        insert into platforms (
            platform_id, platform_urn, name, manufacturer, generation, type,
            year, release_eu, release_jp, release_na, discontinued_year, discontinued,
            introductory_price, units_sold, media, cpu, memory,
            display, sound, created_date, last_modified_date, version)
        values (
            $1,$2,$3,$4,$5,$6,
            $7,$8,$9,$10,$11,$12,
            $13,$14,$15,$16,$17,$18,$19,
            now(),now(),0);"#,
        platform_id,
        &platform_urn.to_string(),
        &new_platform.name,
        &new_platform.manufacturer,
        &new_platform.generation as &i8,
        &new_platform.platform_type as &PlatformType,
        &new_platform.year as &i32,
        new_platform.release.europe,
        new_platform.release.japan,
        new_platform.release.north_america,
        &new_platform.discontinued_year as &Option<i32>,
        &new_platform.discontinued,
        &new_platform.introductory_price,
        &new_platform.units_sold,
        &medias,
        &new_platform.tech_specs.cpu,
        &new_platform.tech_specs.memory,
        &new_platform.tech_specs.display,
        &new_platform.tech_specs.sound
    )
    .execute(&**db_pool)
    .await
    .context("A database failure was encountered while trying to store a brand.")
    .map_err(|why| {
        println!("{why:?}");
        ServerResponseError::GenericError(why)
    })?;

    Ok(HttpResponse::Ok().finish())
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
