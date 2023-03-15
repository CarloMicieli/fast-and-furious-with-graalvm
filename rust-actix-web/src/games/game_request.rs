use crate::games::genre::Genre;
use crate::games::mode::Mode;
use crate::games::rating::Rating;
use serde_derive::{Deserialize, Serialize};
use slug::slugify;
use urn::{Urn, UrnBuilder};
use validator::Validate;

#[derive(Debug, PartialEq, Eq, Clone, Serialize, Deserialize, Validate)]
pub struct GameRequest {
    pub title: String,
    pub genres: Vec<Genre>,
    pub platform: String,
    pub modes: Vec<Mode>,
    pub series: Option<String>,
    pub developer: String,
    pub publisher: String,
    pub rating: Option<Rating>,
    pub plot: Option<String>,
    pub year: u32,
}

impl TryFrom<&GameRequest> for Urn {
    type Error = urn::Error;

    fn try_from(value: &GameRequest) -> Result<Self, Self::Error> {
        let platform_slug = slugify(&value.platform);
        let title_slug = slugify(&value.title);
        let game_slug = format!("{}:{}", platform_slug, title_slug);
        UrnBuilder::new("game", &game_slug).build()
    }
}
