use crate::games::genre::Genre;
use crate::games::mode::Mode;
use crate::games::rating::Rating;
use serde_derive::{Deserialize, Serialize};
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
