# Agent Working Agreement

## Git workflow (mandatory)

- Never push commits directly to `master`.
- Never push commits directly to `develop`.
- Always create a feature branch for any change.
- Feature branch names must follow: `feature/<git_issue_number>_<git_issue_title>`.
- Example: `feature/123_add_offline_sync`.
- Always open a Pull Request for review and merge through the PR flow.

## Enforcement note

If the current branch is `master` or `develop`, stop and create a new branch before making commits.
