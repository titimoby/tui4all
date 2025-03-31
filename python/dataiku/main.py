import typer
from rich.console import Console

from components import get_users_table
from tui import AdminTui

app = typer.Typer()


def start_admin_tui():
    """
    Start the DSS admin TUI.
    """
    tui = AdminTui()
    tui.run()


@app.command("users")
def project_infos():
    """
    Show the list of users from a Dataiku Data Science Studio instance.
    """
    console = Console()
    table = get_users_table()
    console.print(table)


@app.command("tui")
def todos_tui():
    start_admin_tui()


if __name__ == "__main__":
    app()
