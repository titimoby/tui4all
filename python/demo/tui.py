from textual.app import App, ComposeResult
from textual.widgets import Footer, TabbedContent, TabPane, Static, DataTable

import dss_helper
from components import get_users_table, get_projects_table


class AdminTui(App):
    BINDINGS = [
        ("u", "show_tab('users')", "Users"),
        ("p", "show_tab('projects')", "Projects List"),
        ("t", "show_tab('table')", "Projects Table"),
    ]

    def on_mount(self) -> None:
        self.theme = "textual-light"

        metadatas = dss_helper.get_projects_metadata()
        table = self.query_one(DataTable)

        table.add_column("Label")
        table.add_column("Short Description")

        for metadata in metadatas:
            table.add_row(metadata["label"], metadata["shortDesc"])

    def compose(self) -> ComposeResult:
        """Compose app with tabbed content."""
        # Footer to show keys
        yield Footer()

        # Add the TabbedContent widget
        with TabbedContent(initial="users"):
            with TabPane("Users", id="users"):  # First tab
                yield Static(get_users_table())  # Tab content
            with TabPane("Projects list", id="projects"):  # Second tab
                yield Static(get_projects_table())  # Tab content
            with TabPane("Projects Table", id="table"):  # Third tab
                yield DataTable()  # Tab content

    def action_show_tab(self, tab: str) -> None:
        """Switch to a new tab."""
        self.get_child_by_type(TabbedContent).active = tab


if __name__ == "__main__":
    AdminTui()
