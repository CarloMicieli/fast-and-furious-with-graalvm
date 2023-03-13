use serde_derive::{Deserialize, Serialize};

#[derive(Debug, PartialEq, Eq, Clone, Serialize, Deserialize)]
pub struct TechSpecs {
    pub cpu: String,
    pub memory: String,
    pub display: String,
    pub sound: String,
}
