use crate::games::genre::Genre;
use crate::games::metadata::GameMetadata;
use crate::games::mode::Mode;
use crate::games::rating::Rating;
use urn::Urn;

#[derive(Debug, PartialEq, Eq, Clone, Serialize, Deserialize, Validate)]
pub struct Game {
    pub game_urn: Urn,
    pub title: String,
    pub genres: List<Genre>,
    pub platform: String,
    pub modes: List<Mode>,
    pub series: Option<String>,
    pub developer: String,
    pub publisher: String,
    pub rating: Rating,
    pub year: u32,
    pub metadata: GameMetadata,
}
