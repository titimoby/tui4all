use color_eyre::Result;
use crossterm::event::{self, Event, KeyCode, KeyEvent, KeyEventKind, KeyModifiers};
use ratatui::prelude::*;
use ratatui::{
    style::Stylize,
    text::Line,
    widgets::{Block, Paragraph},
    DefaultTerminal, Frame,
};

use reqwest::blocking::get;
use serde::Deserialize;

#[derive(Deserialize)]
struct Quote {
    q: String,
    a: String,
}

#[derive(Debug, Default)]
pub struct App {
    /// Is the application running?
    running: bool,
    quote: String,
}

impl App {
    /// Construct a new instance of [`App`].
    pub fn new() -> Self {
        Self::default()
    }

    /// Run the application's main loop.
    pub fn run(mut self, mut terminal: DefaultTerminal) -> Result<()> {
        self.running = true;
        let quote_requested =
            Self::fetch_quote().unwrap_or_else(|_| "Failed to fetch quote".to_string());

        self.quote = quote_requested.to_string();
        while self.running {
            terminal.draw(|frame| self.draw(frame))?;
            self.handle_crossterm_events()?;
        }
        Ok(())
    }

    /// Renders the user interface.
    ///
    /// This is where you add new widgets. See the following resources for more information:
    /// - <https://docs.rs/ratatui/latest/ratatui/widgets/index.html>
    /// - <https://github.com/ratatui/ratatui/tree/master/examples>
    fn draw(&mut self, frame: &mut Frame) {
        let layout = Layout::default()
            .direction(Direction::Vertical)
            .constraints(vec![Constraint::Percentage(50), Constraint::Percentage(50)])
            .split(frame.area());

        let title = Line::from("From Ratatui Simple Template et j'assume ;)")
            .bold()
            .blue()
            .centered();
        let text = "\nHello !\n\n\
        Tout le monde est bien assis ?\n\n\
        Press `Esc`, `Ctrl-C` or `q` to stop running."
            .to_string();
        frame.render_widget(
            Paragraph::new(text)
                .block(Block::bordered().title(title))
                .centered(),
            layout[0],
        );

        let quote_title = Line::from("Qui n'aime pas une citation au hasard ?")
            .bold()
            .red()
            .centered();
        let quote = self.quote.clone();
        frame.render_widget(
            Paragraph::new(quote)
                .block(Block::bordered().title(quote_title))
                .centered(),
            layout[1],
        )
    }

    fn fetch_quote() -> Result<String, Box<dyn std::error::Error>> {
        let response = get("https://zenquotes.io/api/random")?.text()?;
        let quotes: Vec<Quote> = serde_json::from_str(&response)?;
        if let Some(quote) = quotes.first() {
            Ok(format!("{}\n- {}", quote.q, quote.a))
        } else {
            Err("No quote found".into())
        }
    }

    /// Reads the crossterm events and updates the state of [`App`].
    ///
    /// If your application needs to perform work in between handling events, you can use the
    /// [`event::poll`] function to check if there are any events available with a timeout.
    fn handle_crossterm_events(&mut self) -> Result<()> {
        match event::read()? {
            // it's important to check KeyEventKind::Press to avoid handling key release events
            Event::Key(key) if key.kind == KeyEventKind::Press => self.on_key_event(key),
            Event::Mouse(_) => {}
            Event::Resize(_, _) => {}
            _ => {}
        }
        Ok(())
    }

    /// Handles the key events and updates the state of [`App`].
    fn on_key_event(&mut self, key: KeyEvent) {
        match (key.modifiers, key.code) {
            (_, KeyCode::Esc | KeyCode::Char('q'))
            | (KeyModifiers::CONTROL, KeyCode::Char('c') | KeyCode::Char('C')) => self.quit(),
            // Add other key handlers here.
            _ => {}
        }
    }

    /// Set running to false to quit the application.
    fn quit(&mut self) {
        self.running = false;
    }
}
