from rich.console import Console
import typer
from components import get_todos_table, get_project_details
from tui import TodoTui

app = typer.Typer()


def start_todo_tui():
    """
    Start the todo TUI.
    """
    tui = TodoTui()
    tui.run()


@app.command("project")
def project_infos():
    """
    Show the GitLab project infos.
    """
    console = Console()
    console.print(get_project_details())


@app.command("todos")
def todos_list():
    """
    List the todo items in the terminal.
    """
    table = get_todos_table()
    console = Console()
    console.print(table)


@app.command("tui")
def todos_tui():
    start_todo_tui()


if __name__ == "__main__":
    app()
