# composeApp Agent Guide

## Purpose

- Own Compose app-shell UI composition and app-level screen wiring for mobile experiences.
- Keep feature behavior driven by shared modules, not re-implemented in UI layer.

## Proposed Architecture

- `composeApp` should stay a thin presentation shell.
- UI components and screen wiring live here; business rules live in `shared`.
- Dependency direction: `composeApp` -> `shared/*` only.
- Platform-specific launch/container concerns belong to platform hosts, not feature logic.

## Out of Bounds

- Do not add backend route logic, auth middleware, or server data persistence here.
- Do not duplicate domain logic that already belongs in `shared` modules.

## Integration Contracts

- Consume contracts and state from `shared/feature-*` and `shared/core-*`.
- Integrate with platform host app surfaces through stable interfaces only.
- Any new API dependency must be reviewed against `shared` ownership first.

## Worktree execution (mandatory)

- Perform implementation and verification from a linked worktree only.
- Include worktree path and branch in PR verification evidence.

## Verification Required Before PR

- Whole project build must pass.
- If `composeApp` is modified, deploy and verify the touched app platform.
- Prefer physical device when available; otherwise use tablet simulator/emulator.
- Capture screenshots for touched flows.

## Evidence To Include In PR

- Full build command and result.
- Worktree path and branch used.
- Device/simulator details.
- Screenshot attachments.
- Skipped verification with explicit reason.
