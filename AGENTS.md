# Agent Working Agreement

This root file is the primary contract. Nested `AGENTS.md` files add local rules for their submodule. If rules conflict, this root file wins.

## Git workflow (mandatory)

- Never push commits directly to `master`.
- Never push commits directly to `develop`.
- Always use an issue-backed feature branch.
- Feature branch names must follow: `feature/<github_issue_number>_<github_issue_title>` (lowercase words; use underscores for spaces or special characters).
- Always open a Pull Request for review and merge through the PR flow.

## Worktrees (mandatory)

- All implementation work must be done from a linked git worktree, never from the main checkout.
- Required flow: create/switch worktree, create/switch feature branch inside that worktree, and run build/test/deploy from that worktree root.
- Do not run release-impacting validation from the main checkout.
- PR evidence must include worktree path and branch name used for verification.

## Architecture map

- `pictovoice/composeApp`: shared mobile UI shell and app-level Compose wiring.
- `pictovoice/iosApp`: iOS host app and Apple platform integration.
- `pictovoice/shared`: KMP shared domain, model, telemetry, and feature logic.
- `pictovoice/server`: backend APIs, auth, device registration, sync, and push.
- `pictovoice/caregiver-web` (when present): caregiver web UI and browser flows.

Read the nearest nested `AGENTS.md` before changing code under that path.

## Chore and issue hygiene

- Search open and closed issues before creating new chore issues.
- Do not duplicate existing chore or task issues.
- Keep parent User Story issue checklists synchronized with child task issue status.
- Link any new chore issue to the correct milestone and state the reason (tooling, unblocker, planning reconciliation, or cleanup).

## Verification policy (mandatory for PR)

- The whole project must build successfully before opening a PR.
- If app code changes, deploy and verify the touched platform:
- Prefer physical devices when available; otherwise use tablet simulator/emulator.
- Include screenshots in the PR.
- If backend code changes, deploy/start backend using the documented command:
- Include deployment evidence (command, endpoint/process, smoke result, logs or screenshot).
- If web code changes, open the web app in a browser and verify touched flows:
- Include screenshots in the PR.
- Every PR must include verification evidence:
- Full-build result.
- Worktree path and branch.
- Deployment/browser/device details.
- Screenshots.
- Any skipped checks with explicit reason.
