use serde_derive::{Deserialize, Serialize};

#[derive(Debug, PartialEq, Eq, Clone, Serialize, Deserialize)]
pub struct TechSpecs {
    pub cpu: Option<String>,
    pub memory: Option<String>,
    pub display: Option<String>,
    pub sound: Option<String>,
}
