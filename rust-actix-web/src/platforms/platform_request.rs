use crate::platforms::media::Media;
use crate::platforms::platform_type::PlatformType;
use crate::platforms::release::Release;
use crate::platforms::tech_specs::TechSpecs;
use rust_decimal::Decimal;
use serde_derive::{Deserialize, Serialize};
use validator::Validate;

#[derive(Debug, PartialEq, Eq, Clone, Serialize, Deserialize, Validate)]
pub struct PlatformRequest {
    pub name: String,
    pub manufacturer: String,
    pub generation: i8,
    #[serde(rename = "type")]
    pub platform_type: PlatformType,
    pub year: i32,
    pub release: Release,
    pub discontinued_year: Option<i32>,
    pub discontinued: bool,
    pub introductory_price: Decimal,
    pub units_sold: Decimal,
    pub media: Vec<Media>,
    pub tech_specs: TechSpecs,
}
