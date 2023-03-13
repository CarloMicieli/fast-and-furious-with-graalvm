use serde_derive::{Deserialize, Serialize};

#[derive(Debug, PartialEq, Eq, Clone, Copy, Serialize, Deserialize)]
#[serde(rename_all = "SCREAMING_SNAKE_CASE")]
pub enum Rating {
    CeroCAges15Plus,
    EsrbAdultsOnly,
    EsrbEarlyChildhood,
    EsrbEveryone,
    EsrbEveryone10Plus,
    EsrbKidsToAdults,
    EsrbMature,
    EsrbRatingPending,
    EsrbTeen,
    Hsrs17Plus,
    HsrsAdult,
    HsrsParentalGuidance,
    OtherNotRated,
}
