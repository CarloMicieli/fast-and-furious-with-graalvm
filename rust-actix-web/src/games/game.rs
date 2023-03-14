use crate::games::genre::Genre;
use crate::games::metadata::GameMetadata;
use crate::games::mode::Mode;
use crate::games::rating::Rating;
use serde_derive::{Deserialize, Serialize};
use urn::Urn;
use uuid::Uuid;
use validator::Validate;

#[derive(Debug, PartialEq, Eq, Clone, Serialize, Deserialize, Validate)]
pub struct Game {
    pub game_id: Uuid,
    pub game_urn: Urn,
    pub title: String,
    pub genres: Vec<Genre>,
    pub platform: String,
    pub modes: Vec<Mode>,
    pub series: Option<String>,
    pub developer: Option<String>,
    pub publisher: Option<String>,
    pub rating: Option<Rating>,
    pub plot: Option<String>,
    pub year: i32,
    pub metadata: GameMetadata,
}
