use serde_derive::{Deserialize, Serialize};

#[derive(Debug, PartialEq, Eq, Clone, Copy, Serialize, Deserialize)]
#[serde(rename_all = "SCREAMING_SNAKE_CASE")]
pub enum Genre {
    Action,
    ADVENTURE,
    BallAndPaddle,
    BeatEmUp,
    BoardGames,
    BREAKOUT,
    CARDS,
    CardBattle,
    CASINO,
    CLIMBING,
    COMPILATION,
    DRIVING,
    EDUCATIONAL,
    FIGHTER,
    FIGHTING,
    FirstPersonShooter,
    FLYING,
    GAMBLING,
    GameShow,
    GUN,
    HackAndSlash,
    MAHJONG,
    MATURE,
    MAZE,
    MiniGames,
    MISCELLANEOUS,
    MULTIMEDIA,
    MultiPlay,
    OTHER,
    PARTY,
    PINBALL,
    PLATFORM,
    PoolAndDart,
    PUZZLE,
    QUIZ,
    RACING,
    RacingDriving,
    RacingMotorbike,
    RacingMotorcycle,
    RunAndGun,
    RHYTHM,
    RolePlaying,
    SHOOTER,
    ShootEmUp,
    SIMULATION,
    SLOTS,
    SPORTS,
    SportsBaseball,
    SportsBasketball,
    SportsBiking,
    SportsBowling,
    SportsBoxing,
    SportsCricket,
    SportsFishing,
    SportsFootball,
    SportsFuturistic,
    SportsGolf,
    SportsHandball,
    SportsHockey,
    SportsHorseRacing,
    SportsHunting,
    SportsOlympic,
    SportsPool,
    SportsPoolAndDart,
    SportsRacing,
    SportsRugby,
    SportsSkateboarding,
    SportsSkating,
    SportsSkiing,
    SportsSnowboarding,
    SportsSoccer,
    SportsSurfing,
    SportsTennis,
    SportsTrackAndField,
    SportsVolleyball,
    SportsWrestling,
    STRATEGY,
    TABLETOP,
    UTILITY,
    VirtualLife,
    WATER,
}
