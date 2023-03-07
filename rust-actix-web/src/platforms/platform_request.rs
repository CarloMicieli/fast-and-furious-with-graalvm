use crate::platforms::media::Media;
use crate::platforms::platform_type::PlatformType;
use crate::platforms::tech_specs::TechSpecs;
use rust_decimal::Decimal;
use crate::platforms::release::Release;

#[derive(Debug, PartialEq, Eq, Clone, Serialize, Deserialize, Validate)]
pub struct PlatformRequest {
    pub name: String,
    pub manufacturer: String,
    pub generation: u8,
    #[serde(rename_all = "type")]
    pub platform_type: PlatformType,
    pub release: Release,
    pub discontinued_year: Option<i32>,
    pub discontinued: bool,
    pub introductory_price: Decimal,
    pub units_sold: u32,
    pub media: Media,
    pub tech_specs: TechSpecs,
}
