from textual.app import App, ComposeResult
from textual.widgets import Footer, TabbedContent, TabPane, Static
from components import get_todos_table, get_project_list_view


class TodoTui(App):
    BINDINGS = [
        ("p", "show_tab('project')", "Project"),
        ("t", "show_tab('todos')", "Todos List"),
    ]

    def on_mount(self) -> None:
        self.theme = "textual-light"

    def compose(self) -> ComposeResult:
        """Compose app with tabbed content."""
        # Footer to show keys
        yield Footer()

        # Add the TabbedContent widget
        with TabbedContent(initial="project"):
            with TabPane("Project", id="project"):  # First tab
                yield get_project_list_view()  # Tab content
            with TabPane("Todos list", id="todos"):  # Second tab
                yield Static(get_todos_table())  # Tab content

    def action_show_tab(self, tab: str) -> None:
        """Switch to a new tab."""
        self.get_child_by_type(TabbedContent).active = tab
