use serde_derive::{Deserialize, Serialize};
use strum::{Display, EnumString};

#[derive(Debug, PartialEq, Eq, Clone, Serialize, Deserialize, sqlx::Type, Display, EnumString)]
#[serde(rename_all = "SCREAMING_SNAKE_CASE")]
#[strum(serialize_all = "SCREAMING_SNAKE_CASE")]
#[strum(ascii_case_insensitive)]
#[sqlx(type_name = "VARCHAR")]
#[sqlx(rename_all = "SCREAMING_SNAKE_CASE")]
pub enum PlatformType {
    ArcadeSystemBoard,
    HandheldGameConsole,
    HomeVideoGameConsole,
    VideoGameConsolePeripheral,
}
