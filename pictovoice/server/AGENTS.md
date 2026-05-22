# server Agent Guide

## Purpose

- Own backend API behavior, auth boundaries, device registration, vocabulary sync orchestration, and push dispatch integration.
- Keep server concerns isolated from app presentation concerns.

## Proposed Architecture

- Route/controller layer handles HTTP contracts and request validation.
- Domain/service layer handles business orchestration and invariants.
- Integration layer handles persistence, push providers, and external adapters.
- Dependency direction: routes -> services -> integrations.

## Out of Bounds

- Do not implement app UI behavior or platform-host concerns here.
- Do not place mobile interaction logic in server routes.

## Integration Contracts

- Provide stable API contracts consumed by app/web clients.
- Expose auth and device registration endpoints through explicit route boundaries.
- Coordinate contract changes with shared/client consumers before merge.

## Worktree execution (mandatory)

- Perform implementation and verification from a linked worktree only.
- Include worktree path and branch in PR verification evidence.

## Verification Required Before PR

- Whole project build must pass.
- If `server` is modified, deploy/start backend using documented command and run smoke verification.
- Capture logs or screenshots demonstrating successful startup and endpoint health.

## Evidence To Include In PR

- Full build command and result.
- Worktree path and branch used.
- Backend deploy/start command and target endpoint/process.
- Smoke-check result plus logs/screenshot evidence.
- Skipped verification with explicit reason.
