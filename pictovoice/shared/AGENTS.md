# shared Agent Guide

## Purpose

- Own cross-platform KMP domain, models, feature logic, and shared UI foundations.
- Provide reusable contracts for app layers without embedding platform host concerns.

## Proposed Architecture

- `shared` is organized by core and feature modules.
- Core modules (`core-model`, `core-ui`, `core-telemetry`) provide reusable foundations.
- Feature modules (`feature-communication`, `feature-vocabulary`) compose domain and presentation logic.
- Dependency direction: feature modules may depend on core modules; core modules must not depend on features.

## Out of Bounds

- Do not add platform-host lifecycle handling here.
- Do not add server route handlers or deployment code here.
- Do not leak platform-specific APIs into common contracts unless explicitly isolated.

## Integration Contracts

- Expose stable domain/presentation contracts consumed by app shells.
- Define interfaces for platform adapters where required.
- Coordinate API contract assumptions with `server` via explicit boundary types.

## Worktree execution (mandatory)

- Perform implementation and verification from a linked worktree only.
- Include worktree path and branch in PR verification evidence.

## Verification Required Before PR

- Whole project build must pass.
- If `shared` is modified, verify impacted platform path at minimum.
- Prefer physical device when available; otherwise use tablet simulator/emulator.
- Capture screenshots for any UI behavior changed by shared-layer updates.

## Evidence To Include In PR

- Full build command and result.
- Worktree path and branch used.
- Impacted platform verification details.
- Screenshot attachments where UI behavior is affected.
- Skipped verification with explicit reason.
