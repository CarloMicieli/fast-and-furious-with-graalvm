use chrono::{DateTime, Utc};
use serde_derive::{Deserialize, Serialize};

#[derive(Debug, PartialEq, Eq, Clone, Serialize, Deserialize)]
pub struct GameMetadata {
    pub created_date: DateTime<Utc>,
    pub last_modified_date: DateTime<Utc>,
    pub version: i32,
}
