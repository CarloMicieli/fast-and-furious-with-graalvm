use chrono::Utc;
use colored::Colorize;
use rust_actix_web::app;
use rust_actix_web::configuration::Settings;
use std::net::TcpListener;

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    let start_time = Utc::now().time();
    let settings = Settings::load().expect("Failed to read configuration");
    let listener = TcpListener::bind(settings.address()).expect("Failed to bind port");

    println!("{}", &BANNER_TEXT.red());
    println!("(Version {})", "4.3.1".bold());
    app::run(listener, &settings)
        .map(|res| {
            let end_time = Utc::now().time();
            let diff = end_time - start_time;
            println!("Starting the server ({})...", settings.address().bold());
            println!("Total time taken to run is {} ms", diff.num_milliseconds());
            res
        })?
        .await
}

const BANNER_TEXT: &str = r#"
   _____          __  .__                  __      __      ___.
  /  _  \   _____/  |_|__|__  ___         /  \    /  \ ____\_ |__
 /  /_\  \_/ ___\   __\  \  \/  /  ______ \   \/\/   // __ \| __ \
/    |    \  \___|  | |  |>    <  /_____/  \        /\  ___/| \_\ \
\____|__  /\___  >__| |__/__/\_ \           \__/\  /  \___  >___  /
        \/     \/              \/                \/       \/    \/

"#;
