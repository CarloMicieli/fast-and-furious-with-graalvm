use serde_derive::{Deserialize, Serialize};

#[derive(Debug, PartialEq, Eq, Clone, Copy, Serialize, Deserialize)]
#[serde(rename_all = "SCREAMING_SNAKE_CASE")]
pub enum Mode {
    SinglePlayer,
    TwoPlayers,
    Multiplayer,
}
