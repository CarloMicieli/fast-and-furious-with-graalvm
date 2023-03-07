use chrono::{DateTime, Utc};

#[derive(Debug, PartialEq, Eq, Clone, Serialize, Deserialize)]
pub struct Release {
    pub europe: Option<DateTime<Utc>>,
    pub japan: Option<DateTime<Utc>>,
    pub north_america: Option<DateTime<Utc>>,
}
