use actix_web::{http::StatusCode, HttpResponse, ResponseError};
use thiserror::Error;

#[derive(Debug, Error)]
pub enum ServerResponseError {
    #[error("an error has occorrued")]
    GenericError(#[from] anyhow::Error),
}

impl ResponseError for ServerResponseError {
    fn status_code(&self) -> StatusCode {
        StatusCode::INTERNAL_SERVER_ERROR
    }

    fn error_response(&self) -> HttpResponse {
        HttpResponse::InternalServerError().body(self.to_string())
    }
}
