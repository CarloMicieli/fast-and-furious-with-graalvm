use actix_web::{http::StatusCode, HttpResponse, HttpResponseBuilder, ResponseError};
use log::error;
use thiserror::Error;
use url::Url;
use urn::{Urn, UrnBuilder};
use uuid::Uuid;
use serde::Serialize;

#[derive(Debug, Error)]
pub enum ServerResponseError {
    #[error("An error has occurred ({0})")]
    GenericError(#[from] anyhow::Error),

    #[error("{0}")]
    Conflict(String),

    #[error("{0}")]
    NotFound(String),

    #[error("{0}")]
    AlreadyExists(String),
}

impl ResponseError for ServerResponseError {
    fn status_code(&self) -> StatusCode {
        match self {
            ServerResponseError::GenericError(_) => StatusCode::INTERNAL_SERVER_ERROR,
            ServerResponseError::NotFound(_) => StatusCode::NOT_FOUND,
            ServerResponseError::Conflict(_) => StatusCode::CONFLICT,
            ServerResponseError::AlreadyExists(_) => StatusCode::CONFLICT,
        }
    }

    fn error_response(&self) -> HttpResponse {
        let id = Uuid::new_v4();
        error!("problem_id: {}, reason: {:?}", id, self);

        match self {
            ServerResponseError::GenericError(why) =>
                ProblemDetail::error(id, &why.to_string()).to_response(),
            ServerResponseError::NotFound(why) =>
                ProblemDetail::not_found(id, why).to_response(),
            ServerResponseError::Conflict(why) =>
                ProblemDetail::unprocessable_entity(id, why).to_response(),
            ServerResponseError::AlreadyExists(why) =>
                ProblemDetail::resource_already_exists(id, why).to_response(),
        }
    }
}

#[derive(Debug, Serialize, Clone)]
pub struct ProblemDetail {
    #[serde(rename(serialize = "type"))]
    pub problem_type: Url,
    pub title: String,
    pub detail: String,
    pub status: u16,
    pub instance: Urn,
}

static PROBLEM_DETAIL_CONTENT_TYPE: &str = "application/problem+json";

impl ProblemDetail {
    /// Creates a new problem detail from an error
    pub fn error(id: Uuid, error: &str) -> ProblemDetail {
        let type_url = Url::parse("https://httpstatuses.com/500").unwrap();
        ProblemDetail {
            problem_type: type_url,
            title: String::from("Error: Internal Server Error"),
            detail: error.to_owned(),
            status: StatusCode::INTERNAL_SERVER_ERROR.as_u16(),
            instance: UrnBuilder::new("problem-detail", &id.to_string()).build().unwrap()
        }
    }

    /// Creates a new problem detail for unprocessable entity
    pub fn unprocessable_entity(id: Uuid, detail: &str) -> ProblemDetail {
        let type_url = Url::parse("https://httpstatuses.com/422").unwrap();
        ProblemDetail {
            problem_type: type_url,
            title: String::from("Unprocessable entity"),
            detail: detail.to_owned(),
            status: StatusCode::UNPROCESSABLE_ENTITY.as_u16(),
            instance: UrnBuilder::new("problem-detail", &id.to_string()).build().unwrap()
        }
    }

    /// Creates a new problem detail for bad requests
    pub fn bad_request(id: Uuid, detail: &str) -> ProblemDetail {
        let type_url = Url::parse("https://httpstatuses.com/400").unwrap();
        ProblemDetail {
            problem_type: type_url,
            title: String::from("Bad request"),
            detail: detail.to_owned(),
            status: StatusCode::BAD_REQUEST.as_u16(),
            instance: UrnBuilder::new("problem-detail", &id.to_string()).build().unwrap()
        }
    }

    /// Creates a new problem detail for a resource which exists already
    pub fn resource_already_exists(id: Uuid, detail: &str) -> ProblemDetail {
        let type_url = Url::parse("https://httpstatuses.com/409").unwrap();
        ProblemDetail {
            problem_type: type_url,
            title: String::from("The resource already exists"),
            detail: detail.to_owned(),
            status: StatusCode::CONFLICT.as_u16(),
            instance: UrnBuilder::new("problem-detail", &id.to_string()).build().unwrap()
        }
    }

    /// Creates a new problem detail for a resource not found
    pub fn not_found(id: Uuid, detail: &str) -> ProblemDetail {
        let type_url = Url::parse("https://httpstatuses.com/404").unwrap();
        ProblemDetail {
            problem_type: type_url,
            title: String::from("The resource was not found"),
            detail: detail.to_owned(),
            status: StatusCode::NOT_FOUND.as_u16(),
            instance: UrnBuilder::new("problem-detail", &id.to_string()).build().unwrap()
        }
    }

    pub fn to_response(self) -> HttpResponse {
        let status_code = StatusCode::from_u16(self.status).expect("invalid http status code");
        HttpResponseBuilder::new(status_code)
            .content_type(PROBLEM_DETAIL_CONTENT_TYPE)
            .json(self)
    }
}
