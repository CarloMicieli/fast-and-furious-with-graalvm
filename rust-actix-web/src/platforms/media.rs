use serde_derive::{Deserialize, Serialize};
use strum::{Display, EnumString};

#[derive(Debug, PartialEq, Eq, Clone, Serialize, Deserialize, sqlx::Type, EnumString, Display)]
#[serde(rename_all = "SCREAMING_SNAKE_CASE")]
#[strum(serialize_all = "SCREAMING_SNAKE_CASE")]
#[strum(ascii_case_insensitive)]
#[sqlx(type_name = "VARCHAR")]
pub enum Media {
    CdRom,
    GameCubeGameDisc,
    GdRom,
    HuCard,
    MiniCd,
    RomCartridge,
}
