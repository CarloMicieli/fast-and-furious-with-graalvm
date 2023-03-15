use serde_derive::{Deserialize, Serialize};
use sqlx::Type;
use strum::{Display, EnumString};

#[derive(Debug, PartialEq, Eq, Clone, Copy, Serialize, Deserialize, Display, EnumString, Type)]
#[serde(rename_all = "SCREAMING_SNAKE_CASE")]
#[strum(serialize_all = "SCREAMING_SNAKE_CASE")]
#[strum(ascii_case_insensitive)]
#[sqlx(type_name = "VARCHAR")]
#[sqlx(rename_all = "SCREAMING_SNAKE_CASE")]
pub enum Rating {
    #[serde(rename = "CERO_C_AGES_15_PLUS")]
    CeroCAges15Plus,
    EsrbAdultsOnly,
    EsrbEarlyChildhood,
    EsrbEveryone,
    #[serde(rename = "ESRB_EVERYONE_10_PLUS")]
    EsrbEveryone10Plus,
    EsrbKidsToAdults,
    EsrbMature,
    EsrbRatingPending,
    EsrbTeen,
    #[serde(rename = "HSRS_17_PLUS")]
    Hsrs17Plus,
    HsrsAdult,
    HsrsParentalGuidance,
    OtherNotRated,
}
