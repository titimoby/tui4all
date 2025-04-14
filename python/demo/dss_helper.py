import dataikuapi

import config


def get_users():
    dss_client = dataikuapi.DSSClient(config.host, config.apiKey)
    users = dss_client.list_users(as_objects=True)
    return users


def get_projects_metadata():
    dss_client = dataikuapi.DSSClient(config.host, config.apiKey)
    projects = dss_client.list_projects()
    return [dss_client.get_project(project['projectKey']).get_metadata() for project in projects]
