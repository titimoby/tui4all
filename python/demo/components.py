from rich.table import Table

import dss_helper


def build_users_table(users: list) -> Table:
    table = Table(title="Users List")

    table.add_column("Login", justify="center", style="cyan", no_wrap=True)
    table.add_column("Name", style="magenta")
    table.add_column("Email", justify="right", style="green")
    table.add_column("connected", justify="center")

    for user in users:
        settings = user.get_settings().settings
        connected = ""
        if settings.get("activeWebSocketSesssions", None):
            connected = "✅"
        table.add_row(
            user.login, settings.get("displayName"), settings.get("email"), connected
        )

    return table


def get_users_table():
    users = dss_helper.get_users()
    table = build_users_table(users)
    return table


def build_projects_table(metadatas):
    table = Table(title="Project List")

    table.add_column("Label", justify="center", style="cyan", no_wrap=True)
    table.add_column("Short Description", style="magenta")

    for metadata in metadatas:
        table.add_row(metadata["label"], metadata["shortDesc"])

    return table


def get_projects_table():
    metadatas = dss_helper.get_projects_metadata()
    table = build_projects_table(metadatas)
    return table
