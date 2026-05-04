# Agent Working Agreement

## Git workflow (mandatory)

- Never push commits directly to `master`.
- Never push commits directly to `develop`.
- Always create a feature branch for any change.
- Feature branch names must follow: `feature/<github_issue_number>_<github_issue_title>` (lowercase words; use underscores for spaces or special characters).
- Example: `feature/123_add_offline_sync`.
- Always open a Pull Request for review and merge through the PR flow.

## Worktrees

- When using a linked git worktree, do implementation work there and run Gradle or Android builds from that worktree root so the branch and paths match the PR under development.

## Enforcement note

If the current branch is `master` or `develop`, stop and create a new branch before making commits.
