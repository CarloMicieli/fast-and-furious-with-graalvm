use serde_derive::{Deserialize, Serialize};
use sqlx::Type;
use strum::{Display, EnumString};

#[derive(Debug, PartialEq, Eq, Clone, Copy, Serialize, Deserialize, Display, EnumString, Type)]
#[serde(rename_all = "SCREAMING_SNAKE_CASE")]
#[strum(serialize_all = "SCREAMING_SNAKE_CASE")]
#[strum(ascii_case_insensitive)]
#[sqlx(type_name = "VARCHAR")]
#[sqlx(rename_all = "SCREAMING_SNAKE_CASE")]
pub enum Mode {
    SinglePlayer,
    TwoPlayers,
    Multiplayer,
}
