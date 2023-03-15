use config::{Config, Environment, File};
use log::LevelFilter;
use secrecy::{ExposeSecret, Secret};
use serde::Deserialize;
use sqlx::postgres::{PgConnectOptions, PgPoolOptions, PgSslMode};
use sqlx::{ConnectOptions, PgPool};

/// Application settings
#[derive(Deserialize, Debug)]
pub struct Settings {
    pub database: DatabaseSettings,
    pub server: ServerSettings,
}

impl Settings {
    /// Returns the server address (host and port)
    pub fn address(&self) -> String {
        format!("{}:{}", self.server.host, self.server.port)
    }

    /// Returns the number of actix workers
    pub fn workers(&self) -> usize {
        self.server.workers
    }

    /// Returns the postgres connection options
    pub fn pg_connection_options(&self) -> PgConnectOptions {
        self.database.pg_connection_options()
    }

    /// Load the settings from the configuration file (config/application.yaml)
    /// and environment variables.
    pub fn load() -> Result<Settings, config::ConfigError> {
        let s = Config::builder()
            .add_source(File::with_name("src/config/application").required(false))
            .add_source(Environment::default().separator("_").ignore_empty(true))
            .build()?;
        s.try_deserialize()
    }
}

/// It contains the server configuration
#[derive(Deserialize, Clone, Debug)]
pub struct ServerSettings {
    /// the server host name
    pub host: String,
    /// the server port number
    pub port: u16,
    /// the number of actix workers
    pub workers: usize,
}

/// It contains the database connection settings
#[derive(Deserialize, Clone, Debug)]
pub struct DatabaseSettings {
    /// the username
    pub username: String,
    /// the password
    pub password: Secret<String>,
    /// the host name
    pub host: String,
    /// the port number
    pub port: u16,
    /// the database name
    pub name: String,
    /// the SSL mode for the connection
    pub require_ssl: bool,
}

impl DatabaseSettings {
    pub fn new(
        username: &str,
        password: &str,
        host: &str,
        port: u16,
        name: &str,
    ) -> DatabaseSettings {
        DatabaseSettings {
            username: username.to_owned(),
            password: Secret::new(password.to_owned()),
            host: host.to_owned(),
            port,
            name: name.to_owned(),
            require_ssl: false,
        }
    }

    /// Creates a new postgres connection pool using the database connection settings.
    pub fn get_connection_pool(&self) -> PgPool {
        PgPoolOptions::new()
            .acquire_timeout(std::time::Duration::from_secs(2))
            .connect_lazy_with(self.pg_connection_options())
    }

    /// Returns the postgres connection options
    pub fn pg_connection_options(&self) -> PgConnectOptions {
        let ssl_mode = if self.require_ssl {
            PgSslMode::Require
        } else {
            PgSslMode::Prefer
        };

        let mut options = PgConnectOptions::new()
            .application_name("rust-actix-web")
            .host(&self.host)
            .database(&self.name)
            .username(&self.username)
            .password(self.password.expose_secret())
            .port(self.port)
            .ssl_mode(ssl_mode);

        options.log_statements(LevelFilter::Off);

        options
    }
}
