use std::str::FromStr;

use anyhow::Context;
use chrono::{DateTime, Utc};
use slug::slugify;
use sqlx::{Postgres, Transaction};
use urn::{Urn, UrnBuilder};
use uuid::Uuid;

use super::{
    game::Game, game_request::GameRequest, genre::Genre, metadata::GameMetadata, mode::Mode,
    rating::Rating,
};

pub async fn find_all_games_by_platform<'db>(
    platform_urn: &Urn,
    transaction: &mut Transaction<'db, Postgres>,
) -> Result<Vec<Game>, anyhow::Error> {
    let results = sqlx::query_as!(
        GameRow,
        r#"select
         g.game_id,
         g.game_urn,
         g.title,
         g.genres,
         p.name as platform,
         g.modes,
         g.series,
         g.developer,
         g.publisher,
         g.rating as "rating?: Rating",
         g.plot,
         g.year,
         g.created_date,
         g.last_modified_date,
         g.version
        from games as g
        join platforms as p
          on g.platform_id = p.platform_id
        where p.platform_urn = $1
        order by g.title;"#,
        &platform_urn.to_string()
    )
    .fetch_all(transaction)
    .await
    .context("A database failure was encountered while trying to read games for a platform.")?;

    let games = results
        .into_iter()
        .map(|row| Game::try_from(row).unwrap())
        .collect();

    Ok(games)
}

pub async fn game_exists<'db>(
    game_urn: &Urn,
    transaction: &mut Transaction<'db, Postgres>,
) -> Result<bool, anyhow::Error> {

    let result = sqlx::query!(
        r#"select game_id from games where game_urn = $1"#,
        &game_urn.to_string()
    )
        .fetch_optional(transaction)
        .await
        .context("A database failure was encountered while trying to check the game existence.")?;

    Ok(result.is_some())
}

pub async fn find_game_by_urn<'db>(
    game_urn: &Urn,
    transaction: &mut Transaction<'db, Postgres>,
) -> Result<Option<Game>, anyhow::Error> {
    let results = sqlx::query_as!(
        GameRow,
        r#"select
         g.game_id,
         g.game_urn,
         g.title,
         g.genres,
         p.name as platform,
         g.modes,
         g.series,
         g.developer,
         g.publisher,
         g.rating as "rating?: Rating",
         g.plot,
         g.year,
         g.created_date,
         g.last_modified_date,
         g.version
        from games as g
        join platforms as p
          on g.platform_id = p.platform_id
        where g.game_urn = $1
        limit 1;"#,
        &game_urn.to_string()
    )
    .fetch_optional(transaction)
    .await
    .context("A database failure was encountered while trying to read games for a platform.")?;

    let game = results.map(|row| Game::try_from(row).unwrap());

    Ok(game)
}

pub async fn insert_game<'db>(
    new_game: &GameRequest,
    game_urn: &Urn,
    platform_id: Uuid,
    transaction: &mut Transaction<'db, Postgres>,
) -> Result<(), anyhow::Error> {
    let game_id = Uuid::new_v4();

    let genres: Vec<String> = new_game
        .genres
        .iter()
        .map(|genre| genre.to_string())
        .collect();

    let modes: Vec<String> = new_game.modes.iter().map(|mode| mode.to_string()).collect();

    sqlx::query!(r#"insert into games (
            game_id, game_urn, platform_id, title, genres, modes, series, developer, publisher, plot, rating,
            "year", created_date, last_modified_date, version)
        values (
            $1, $2, $3, $4, $5, $6,
            $7, $8, $9, $10, $11, $12,
            now(), now(), 0);"#,
            game_id,
            &game_urn.to_string(),
            platform_id,
            new_game.title,
            &genres,
            &modes,
            new_game.series,
            new_game.developer,
            new_game.publisher,
            new_game.plot,
            new_game.rating.as_ref() as Option<&Rating>,
            new_game.year as i32)
        .execute(transaction)
        .await
        .context("A database failure was encountered while trying to create a game.")?;

    Ok(())
}

pub async fn update_game<'db>(
    game_id: Uuid,
    game_update: &GameRequest,
    platform_id: Uuid,
    metadata: GameMetadata,
    transaction: &mut Transaction<'db, Postgres>,
) -> Result<(), anyhow::Error> {
    let platform_slug = slugify(&game_update.platform);
    let title_slug = slugify(&game_update.title);
    let game_slug = format!("{}:{}", platform_slug, title_slug);
    let game_urn = UrnBuilder::new("game", &game_slug).build().unwrap();

    let genres: Vec<String> = game_update
        .genres
        .iter()
        .map(|genre| genre.to_string())
        .collect();

    let modes: Vec<String> = game_update
        .modes
        .iter()
        .map(|mode| mode.to_string())
        .collect();

    sqlx::query!(
        r#"update games set
            game_urn = $1,
            platform_id = $2,
            title = $3,
            genres = $4,
            modes = $5,
            series = $6,
            developer = $7,
            publisher = $8,
            plot = $9,
            rating = $10,
            "year" = $11,
            last_modified_date = now(),
            version = $12
            where game_id = $13;"#,
        &game_urn.to_string(),
        platform_id,
        game_update.title,
        &genres,
        &modes,
        game_update.series,
        game_update.developer,
        game_update.publisher,
        game_update.plot,
        game_update.rating.as_ref() as Option<&Rating>,
        game_update.year as i32,
        metadata.version + 1,
        game_id
    )
    .execute(transaction)
    .await
    .context("A database failure was encountered while trying to update a game.")?;

    Ok(())
}

pub async fn delete_game_by_urn<'db>(
    game_urn: &Urn,
    transaction: &mut Transaction<'db, Postgres>,
) -> Result<bool, anyhow::Error> {
    let _ = sqlx::query!(
        r#"delete from games where game_urn = $1"#,
        &game_urn.to_string()
    )
    .execute(transaction)
    .await
    .context("A database failure was encountered while trying to delete a game.")?;

    Ok(true)
}

#[derive(Debug)]
struct GameRow {
    pub game_id: Uuid,
    pub game_urn: String,
    pub title: String,
    pub genres: Vec<String>,
    pub platform: String,
    pub modes: Vec<String>,
    pub series: Option<String>,
    pub developer: Option<String>,
    pub publisher: Option<String>,
    pub rating: Option<Rating>,
    pub year: i32,
    pub plot: Option<String>,
    pub created_date: DateTime<Utc>,
    pub last_modified_date: DateTime<Utc>,
    pub version: i32,
}

impl TryFrom<GameRow> for Game {
    type Error = ();

    fn try_from(value: GameRow) -> Result<Self, Self::Error> {
        let metadata = GameMetadata {
            created_date: value.created_date,
            last_modified_date: value.last_modified_date,
            version: value.version,
        };

        let genres = value
            .genres
            .into_iter()
            .map(|genre| {
                Genre::from_str(&genre).unwrap_or_else(|_| panic!("Wrong genre {}", &genre))
            })
            .collect();

        let modes = value
            .modes
            .into_iter()
            .map(|mode| Mode::from_str(&mode).unwrap_or_else(|_| panic!("Wrong mode {}", &mode)))
            .collect();

        Ok(Game {
            game_id: value.game_id,
            game_urn: Urn::from_str(&value.game_urn).unwrap(),
            title: value.title,
            genres,
            platform: value.platform,
            modes,
            series: value.series,
            developer: value.developer,
            publisher: value.publisher,
            plot: value.plot,
            rating: value.rating,
            year: value.year,
            metadata,
        })
    }
}
