use chrono::{DateTime, Utc};

#[derive(Debug, PartialEq, Eq, Clone, Serialize, Deserialize)]
pub struct GameMetadata {
    pub created_date: DateTime<Utc>,
    pub last_modified_date: DateTime<Utc>,
    pub version: i32,
}
