use std::str::FromStr;

use anyhow::Context;
use chrono::{DateTime, NaiveDate, Utc};
use rust_decimal::Decimal;
use slug::slugify;
use sqlx::{Postgres, Transaction};
use urn::{Urn, UrnBuilder};
use uuid::Uuid;

use super::{
    media::Media, metadata::PlatformMetadata, platform::Platform,
    platform_request::PlatformRequest, platform_type::PlatformType, release::Release,
    tech_specs::TechSpecs,
};

pub async fn platform_exists<'db>(
    name: &str,
    transaction: &mut Transaction<'db, Postgres>,
) -> Result<bool, anyhow::Error> {
    let platform_urn = UrnBuilder::new("platform", &slugify(name)).build().unwrap();
    let result = sqlx::query!(
        r#"select platform_id from platforms where platform_urn = $1"#,
        &platform_urn.to_string()
    )
    .fetch_optional(transaction)
    .await
    .context("A database failure was encountered while trying to check the platform existence.")?;

    Ok(result.is_some())
}

pub async fn find_platform_id_by_name<'db>(
    name: &str,
    transaction: &mut Transaction<'db, Postgres>,
) -> Result<Option<Uuid>, anyhow::Error> {
    let platform_urn = UrnBuilder::new("platform", &slugify(name)).build().unwrap();
    let result = sqlx::query!(
        r#"select platform_id from platforms where platform_urn = $1"#,
        &platform_urn.to_string()
    )
    .fetch_optional(transaction)
    .await
    .context("A database failure was encountered while trying to read the platform id.")?;

    Ok(result.map(|row| row.platform_id))
}

pub async fn find_all_platform<'db>(
    transaction: &mut Transaction<'db, Postgres>,
) -> Result<Vec<Platform>, anyhow::Error> {
    let results: Vec<PlatformRow> = sqlx::query_as!(
        PlatformRow,
        r#"
        select
            platform_id, platform_urn,
            name, manufacturer, generation,
            type as "platform_type!: String",
            year, release_eu, release_jp, release_na, discontinued_year, discontinued,
            introductory_price, units_sold, media, cpu, memory,
            display, sound, created_date, last_modified_date, version
        from platforms
        order by name;"#
    )
    .fetch_all(transaction)
    .await
    .context("A database failure was encountered while trying to read a platform.")?;

    let platforms = results
        .into_iter()
        .map(|row| Platform::try_from(row).unwrap())
        .collect();
    Ok(platforms)
}

pub async fn find_platform_by_urn<'db>(
    platform_urn: &Urn,
    transaction: &mut Transaction<'db, Postgres>,
) -> Result<Option<Platform>, anyhow::Error> {
    let result: Option<PlatformRow> = sqlx::query_as!(
        PlatformRow,
        r#"
        select
            platform_id, platform_urn,
            name, manufacturer, generation,
            type as "platform_type!: String",
            year, release_eu, release_jp, release_na, discontinued_year, discontinued,
            introductory_price, units_sold, media, cpu, memory,
            display, sound, created_date, last_modified_date, version
        from platforms
        where platform_urn = $1;"#,
        &platform_urn.to_string()
    )
    .fetch_optional(transaction)
    .await
    .context("A database failure was encountered while trying to read a platform.")?;

    Ok(result.map(|row| Platform::try_from(row).unwrap()))
}

pub async fn update_platform<'db>(
    platform_id: &Uuid,
    platform_update: &PlatformRequest,
    metadata: PlatformMetadata,
    transaction: &mut Transaction<'db, Postgres>,
) -> Result<(), anyhow::Error> {
    let medias: &Vec<String> = &platform_update
        .media
        .iter()
        .map(|media| media.to_string())
        .collect();

    let name_slug = slugify(&platform_update.name);
    let platform_urn = UrnBuilder::new("platform", &name_slug).build().unwrap();

    sqlx::query!(
        r#"update platforms set
            platform_urn = $1,
            name = $2,
            manufacturer = $3,
            generation = $4,
            type = $5,
            year = $6,
            release_eu = $7,
            release_jp = $8,
            release_na = $9,
            discontinued_year = $10,
            discontinued = $11,
            introductory_price = $12,
            units_sold = $13,
            media = $14,
            cpu = $15,
            memory = $16,
            display = $17,
            sound = $18,
            last_modified_date = now(),
            version = $19
        where platform_id = $20;"#,
        &platform_urn.to_string(),
        &platform_update.name,
        &platform_update.manufacturer,
        platform_update.generation as i32,
        &platform_update.platform_type as &PlatformType,
        platform_update.year as i32,
        platform_update.release.europe,
        platform_update.release.japan,
        platform_update.release.north_america,
        &platform_update.discontinued_year as &Option<i32>,
        &platform_update.discontinued,
        &platform_update.introductory_price,
        &platform_update.units_sold,
        &medias,
        platform_update.tech_specs.cpu.as_ref(),
        platform_update.tech_specs.memory.as_ref(),
        platform_update.tech_specs.display.as_ref(),
        platform_update.tech_specs.sound.as_ref(),
        metadata.version + 1,
        platform_id
    )
    .execute(transaction)
    .await
    .context("A database failure was encountered while trying to update a platform.")?;

    Ok(())
}

pub async fn insert_platform<'db>(
    new_platform: &PlatformRequest,
    platform_urn: &Urn,
    transaction: &mut Transaction<'db, Postgres>,
) -> Result<(), anyhow::Error> {
    let platform_id = Uuid::new_v4();

    let medias: &Vec<String> = &new_platform
        .media
        .iter()
        .map(|media| media.to_string())
        .collect();

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
        new_platform.generation as i32,
        &new_platform.platform_type as &PlatformType,
        new_platform.year as i32,
        new_platform.release.europe,
        new_platform.release.japan,
        new_platform.release.north_america,
        &new_platform.discontinued_year as &Option<i32>,
        &new_platform.discontinued,
        &new_platform.introductory_price,
        &new_platform.units_sold,
        &medias,
        new_platform.tech_specs.cpu.as_ref(),
        new_platform.tech_specs.memory.as_ref(),
        new_platform.tech_specs.display.as_ref(),
        new_platform.tech_specs.sound.as_ref()
    )
    .execute(transaction)
    .await
    .context("A database failure was encountered while trying to store a platform.")?;

    Ok(())
}

#[derive(Debug)]
struct PlatformRow {
    pub platform_id: Uuid,
    pub platform_urn: String,
    pub name: String,
    pub manufacturer: String,
    pub generation: i32,
    pub platform_type: String,
    pub year: i32,
    pub release_eu: Option<NaiveDate>,
    pub release_jp: Option<NaiveDate>,
    pub release_na: Option<NaiveDate>,
    pub discontinued_year: Option<i32>,
    pub discontinued: bool,
    pub introductory_price: Option<Decimal>,
    pub units_sold: Option<Decimal>,
    pub media: Vec<String>,
    pub cpu: Option<String>,
    pub memory: Option<String>,
    pub display: Option<String>,
    pub sound: Option<String>,
    pub created_date: DateTime<Utc>,
    pub last_modified_date: DateTime<Utc>,
    pub version: i32,
}

impl TryFrom<PlatformRow> for Platform {
    type Error = ();

    fn try_from(value: PlatformRow) -> Result<Self, Self::Error> {
        let release = Release {
            europe: value.release_eu,
            japan: value.release_jp,
            north_america: value.release_na,
        };

        let metadata = PlatformMetadata {
            created_date: value.created_date,
            last_modified_date: value.last_modified_date,
            version: value.version,
        };

        let tech_specs = TechSpecs {
            cpu: value.cpu,
            memory: value.memory,
            display: value.display,
            sound: value.sound,
        };

        let media: Vec<Media> = value
            .media
            .iter()
            .map(|it| Media::from_str(it).unwrap())
            .collect();

        Ok(Platform {
            platform_id: value.platform_id,
            platform_urn: Urn::from_str(&value.platform_urn).unwrap(),
            name: value.name,
            manufacturer: value.manufacturer,
            generation: value.generation,
            platform_type: PlatformType::from_str(&value.platform_type)
                .unwrap_or_else(|_| panic!("Wrong platform type {}", &value.platform_type)),
            year: value.year,
            release,
            discontinued_year: value.discontinued_year,
            discontinued: value.discontinued,
            introductory_price: value.introductory_price,
            units_sold: value.units_sold,
            media,
            tech_specs,
            metadata,
        })
    }
}
