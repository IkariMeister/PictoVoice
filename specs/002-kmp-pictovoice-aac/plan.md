# Implementation Plan: PictoVoice AAC (KMP + Compose)

**Branch**: `002-kmp-pictovoice-aac` | **Date**: 2026-04-10 | **Spec**: [spec.md](./spec.md)  
**Input**: Feature specification `specs/002-kmp-pictovoice-aac/spec.md` + stack: Kotlin Multiplatform, Compose Multiplatform, Clean Architecture, MVI, repository/data sources, domain commands, Sentry, caregiver web vocabulary admin, push-based vocabulary updates.

## Summary

Deliver an AAC pictogram app for motor disability: high-contrast grid, sentence builder, offline Speak, touch feedback, on-device predictions, caregiver-protected customization, dual mobile platforms (see spec clarifications). **Implementation** uses **Kotlin Multiplatform** with **Compose Multiplatform** UI, **Clean Architecture** layers, **MVI** screens, **repositories + data sources** for persistence and network, **command-style domain operations** (explicit use-case/command objects with handlers), **Sentry** for crashes and structured interaction breadcrumbs, a **caregiver web** app to edit vocabulary/grid definitions, and a **push notification** path to prompt devices to pull fresh vocabulary (silent or user-visible per product choice).

## Technical Context

**Language/Version**: Kotlin 2.0+ (KMP), Gradle Kotlin DSL  
**Primary Dependencies**: Compose Multiplatform, Kotlin Coroutines/Flow, Ktor Client (or OkHttp expect/actual) for REST, SQLDelight or Room (Android) + platform storage expect/actual for local catalog, Firebase Cloud Messaging (Android), Apple Push Notification service (iOS), Sentry Kotlin Multiplatform SDK  
**Storage**: On-device vocabulary cache (versioned manifest + symbol assets/metadata); optional encrypted local store for caregiver session tokens  
**Testing**: `kotlin.test` + Turbine for Flow/MVI; Android instrumented UI tests; iOS XCTest where applicable; contract tests against OpenAPI stubs  
**Target Platform**: Android + iOS (v1 comparable support per spec FR-012)  
**Project Type**: Multiplatform mobile app + separate caregiver web client + backend services for vocabulary API and push dispatch  
**Performance Goals**: Speak starts within spec success criteria; prediction ranking on-device under typical grid sizes (<100ms target for rank step on mid devices, to be validated in profiling)  
**Constraints**: Offline-first Speak; no cloud processing of sentence/selection data for predictions (FR-009); caregiver auth before persisting layout changes (FR-013); single primary language v1 (FR-011)  
**Scale/Scope**: Single communicator per device v1; bounded catalog; caregiver web concurrent editors low tens; push fan-out per household/device tokens

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

Repository `.specify/memory/constitution.md` is still **template placeholders** (not ratified on `master`). Until ratified, enforce intent from the product spec and standard engineering practice:

- **Code quality**: Layer boundaries (domain without Android/iOS imports), small use-cases/commands, reviews for concurrency and cancellation.
- **Testing**: Unit tests for domain commands and MVI reducers; integration tests for repository + API client against contract stubs; critical path UI smoke on both platforms.
- **UX / accessibility**: WCAG-oriented caregiver web; mobile flows honor FR-002, FR-007, FR-008 and platform accessibility services.
- **Performance**: Meet spec SC-002/SC-003; profile TTS and prediction hot paths.
- **Observability**: Sentry for crashes + non-PII breadcrumbs for key interactions (symbol select, speak, sync); no communication content in Sentry payloads.

**Post-design**: Architecture aligns with dual-platform delivery, offline Speak, on-device prediction, and explicit vocabulary sync boundary—no unjustified complexity beyond the three delivery surfaces (mobile KMP, caregiver web, backend).

## Project Structure

### Documentation (this feature)

```text
specs/002-kmp-pictovoice-aac/
├── plan.md              # This file
├── research.md          # Phase 0
├── data-model.md        # Phase 1
├── quickstart.md        # Phase 1
├── contracts/           # Phase 1
└── tasks.md             # /speckit.tasks (later)
```

### Source Code (repository root) — target layout

```text
pictovoice/
├── shared/
│   ├── domain/                 # entities, command interfaces, use-case handlers (command pattern)
│   ├── data/                   # repositories, dto mappers, local + remote data sources
│   └── presentation/           # MVI: State, Event, Effect, ViewModel/Store per screen
├── composeApp/                 # Compose Multiplatform app entry, navigation, theming
├── androidApp/                 # Android manifest, FCM service, Sentry init, TTS expect/actual
├── iosApp/                     # iOS entry, APNs registration, Sentry init, TTS expect/actual
├── caregiver-web/              # Web app for vocabulary/grid management (framework TBD in research)
├── server/                     # Vocabulary API + auth + push sender (stack TBD in research)
└── gradle/                     # Convention plugins, version catalog
```

**Structure Decision**: **KMP shared core** for domain + data contracts + presentation MVI; **platform apps** for push, TTS, Sentry DSN wiring; **caregiver-web** and **server** as separate deployables in the monorepo to satisfy web editing and push-triggered sync without coupling vocabulary editing into the mobile binaries.

## Complexity Tracking

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| Three deployables (mobile, web, server) | Caregiver must edit vocabulary on web; devices need authenticated API + push | Mobile-only admin would violate “web for caregiver” and complicate large-grid editing accessibility |
| Push + pull sync | Spec requires reliable vocabulary updates; push reduces polling and battery | Pull-only increases latency and misses “push mechanism” requirement |

## Phase 0 & Phase 1 Outputs

See `research.md` (decisions), `data-model.md` (entities), `contracts/openapi.yaml` (vocabulary & device API), `quickstart.md` (local dev).

**Agent context**: Run `.specify/scripts/bash/update-agent-context.sh cursor-agent` after plan commit.
