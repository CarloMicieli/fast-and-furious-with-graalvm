use chrono::NaiveDate;
use serde_derive::{Deserialize, Serialize};

#[derive(Debug, PartialEq, Eq, Clone, Serialize, Deserialize)]
pub struct Release {
    pub europe: Option<NaiveDate>,
    pub japan: Option<NaiveDate>,
    pub north_america: Option<NaiveDate>,
}
