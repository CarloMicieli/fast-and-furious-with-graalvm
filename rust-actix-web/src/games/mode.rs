#[derive(Debug, PartialEq, Eq, Clone, Copy, Serialize, Deserialize)]
pub enum Mode {
    SINGLE_PLAYER,
    TWO_PLAYERS,
    MULTIPLAYER,
}
