import config
import gitlab


# Function to get todos from GitLab
def get_gitlab_todos():
    # Initialize GitLab
    gl = gitlab.Gitlab(url=config.gitlab_server, private_token=config.gitlab_token)

    # Get todos
    todos = gl.todos.list(project_id=config.gitlab_project_id, all=True)
    return [todo.attributes for todo in todos]


# Function to get project details from GitLab
def get_gitlab_project_details():
    # Initialize GitLab
    gl = gitlab.Gitlab(url=config.gitlab_server, private_token=config.gitlab_token)
    # Get project details
    project = gl.projects.get(config.gitlab_project_id)
    return project.attributes


# function to list all issues from a GitLab project
def get_gitlab_issues():
    # Initialize GitLab
    gl = gitlab.Gitlab(url=config.gitlab_server, private_token=config.gitlab_token)

    # get issues assigned to the user Titimoby and open
    issues = gl.issues.list(
        project_id=config.gitlab_project_id,
        assignee_username=config.gitlab_user,
        get_all=True,
    )
    return [issue.attributes for issue in issues]
