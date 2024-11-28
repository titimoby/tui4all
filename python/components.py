from gitlab_helper import get_gitlab_todos, get_gitlab_project_details
from rich.table import Table
from textual.widgets import Label, ListItem, ListView


def build_todo_table(todos):
    table = Table(title="Todo List")

    table.add_column("Creation", justify="center", style="cyan", no_wrap=True)
    table.add_column("Title", style="magenta")
    table.add_column("Description", justify="right", style="green")

    for item in todos:
        table.add_row(
            item["target"]["created_at"][:10],
            item["target"]["title"],
            item["target"]["description"],
        )

    return table


def get_todos_table():
    todos = get_gitlab_todos()
    table = build_todo_table(todos)
    return table


def get_project_details():
    project = get_gitlab_project_details()

    table = Table(title="Project")

    table.add_column("Name", justify="center", style="cyan", no_wrap=True)
    table.add_column("Url", style="magenta")
    table.add_column("Owner", justify="right", style="green")

    table.add_row(
        project["name_with_namespace"],
        project["web_url"],
        project["owner"]["username"],
    )

    return table


def get_project_list_view():
    project = get_gitlab_project_details()

    return ListView(
        ListItem(Label(project["name_with_namespace"])),
        ListItem(Label(project["web_url"])),
        ListItem(Label(project["owner"]["username"])),
    )
