# iosApp Agent Guide

## Purpose

- Own iOS host application setup, Apple platform lifecycle, and integration of shared KMP framework into iOS runtime.
- Keep feature/domain behavior in shared modules rather than duplicating it in Swift host glue.

## Proposed Architecture

- `iosApp` is the iOS platform host around shared KMP features.
- iOS-specific app lifecycle, permissions, and device integrations live here.
- Dependency direction: `iosApp` -> shared KMP framework boundaries.
- Shared business logic remains in `pictovoice/shared`.

## Out of Bounds

- Do not implement backend routes, persistence, or server-side auth here.
- Do not fork business logic that already exists in shared feature modules.

## Integration Contracts

- Consume exposed shared contracts/state/events from shared modules.
- Keep iOS bridge code thin and deterministic.
- Introduce platform adapters only for iOS-only capabilities.

## Worktree execution (mandatory)

- Perform implementation and verification from a linked worktree only.
- Include worktree path and branch in PR verification evidence.

## Verification Required Before PR

- Whole project build must pass.
- If `iosApp` is modified, deploy and verify on touched iOS target.
- Prefer physical iPad/iPhone when available; otherwise use tablet-class simulator.
- Capture screenshots for touched flows.

## Evidence To Include In PR

- Full build command and result.
- Worktree path and branch used.
- Device/simulator details.
- Screenshot attachments.
- Skipped verification with explicit reason.
