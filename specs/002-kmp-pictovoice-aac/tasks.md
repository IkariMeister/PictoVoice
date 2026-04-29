---
description: "Task list for PictoVoice AAC (KMP) — generated from plan + spec"
---

# Tasks: PictoVoice AAC (KMP + Compose)

**Input**: Design documents from `/Users/ikari/Projects/PictoVoice/specs/002-kmp-pictovoice-aac/`  
**Prerequisites**: [plan.md](./plan.md), [spec.md](./spec.md), [research.md](./research.md), [data-model.md](./data-model.md), [contracts/openapi.yaml](./contracts/openapi.yaml)

**Tests**: Included — explicit unit, integration, contract, and UI smoke tasks are defined per user story.

**Organization**: Phases follow user story priority (US1 P1 → US2/US3 P2 → US4 P3).

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Parallelizable (different files, no ordering dependency within the same checkpoint)
- **[USn]**: User story label (only in user-story phases)

## Path conventions

Root layout per [plan.md](./plan.md): `pictovoice/shared/`, `pictovoice/composeApp/`, `pictovoice/androidApp/`, `pictovoice/iosApp/`, `pictovoice/caregiver-web/`, `pictovoice/server/`, `pictovoice/gradle/`.

---

## Phase 1: Setup (shared infrastructure)

**Purpose**: Monorepo skeleton and tooling so shared code compiles for Android + iOS.

- [ ] T001 Create `pictovoice/` directory tree per plan.md (empty `shared/domain`, `shared/data`, `shared/presentation`, `composeApp`, `androidApp`, `iosApp`, `caregiver-web`, `server`, `gradle`)
- [ ] T002 Add root `pictovoice/settings.gradle.kts` including all KMP modules and version catalog reference
- [ ] T003 Add `pictovoice/gradle/libs.versions.toml` with Kotlin, Compose Multiplatform, Coroutines, Ktor Client, SQLDelight (or chosen local DB), Sentry KMP, Android/iOS minimum SDKs
- [ ] T004 [P] Configure `pictovoice/shared/domain/build.gradle.kts` as `kotlin { jvm(); androidTarget(); iosArm64(); iosSimulatorArm64() }` with `commonMain` only dependencies appropriate for pure Kotlin
- [ ] T005 [P] Configure `pictovoice/shared/data/build.gradle.kts` with SQLDelight (or chosen) and Ktor Client in commonMain + platform drivers
- [ ] T006 [P] Configure `pictovoice/shared/presentation/build.gradle.kts` with Compose runtime and ViewModel-friendly deps (no Android-only UI imports in commonMain)
- [ ] T007 Configure `pictovoice/composeApp/build.gradle.kts` as Compose Multiplatform application module depending on `shared/presentation`, `shared/data`, `shared/domain`
- [ ] T008 [P] Wire `pictovoice/androidApp/build.gradle.kts` and `pictovoice/iosApp` entry targets to embed `composeApp` and set application IDs / bundle IDs

---

## Phase 2: Foundational (blocking prerequisites)

**Purpose**: Domain contracts, persistence shell, platform bridges, observability — **required before any user story UI work**.

**⚠️ CRITICAL**: No user story phase starts until this checkpoint passes.

- [ ] T009 Define `Pictogram`, `Sentence`, `GridLayout` immutable models in `pictovoice/shared/domain/src/commonMain/kotlin/com/pictovoice/domain/model/`
- [ ] T010 Define domain command types (e.g. `AddPictogram`, `RemovePictogramAt`, `ClearSentence`, `SpeakSentence`, `SyncVocabulary`) in `pictovoice/shared/domain/src/commonMain/kotlin/com/pictovoice/domain/command/`
- [ ] T011 Define `CommandHandler` / dispatcher interface routing commands to use cases in `pictovoice/shared/domain/src/commonMain/kotlin/com/pictovoice/domain/dispatcher/`
- [ ] T012 Create `TextToSpeech` `expect` interface in `pictovoice/shared/domain/src/commonMain/kotlin/com/pictovoice/domain/tts/TextToSpeech.kt` and document contract (queue utterances, language fixed v1)
- [ ] T013 Implement `TextToSpeech` `actual` in `pictovoice/androidApp/src/main/kotlin/com/pictovoice/tts/AndroidTextToSpeech.kt` using Android `TextToSpeech` API
- [ ] T014 Implement `TextToSpeech` `actual` in `pictovoice/iosApp/iosApp/.../IosTextToSpeech.swift` or Kotlin/Native bridge per chosen template under `pictovoice/iosApp/`
- [ ] T015 Add SQLDelight schema for `pictogram`, `layout_cell`, `vocabulary_meta` matching [data-model.md](./data-model.md) in `pictovoice/shared/data/src/commonMain/sqldelight/com/pictovoice/data/Vocabulary.sq`
- [ ] T016 Implement `LocalVocabularyDataSource` in `pictovoice/shared/data/src/commonMain/kotlin/com/pictovoice/data/local/LocalVocabularyDataSource.kt` wrapping SQLDelight driver
- [ ] T017 Add bundled seed vocabulary JSON/SQL under `pictovoice/shared/data/src/commonMain/resources/` and loader into local DB on first launch
- [ ] T018 Define `VocabularyRepository` interface in `pictovoice/shared/data/src/commonMain/kotlin/com/pictovoice/data/repo/VocabularyRepository.kt` (local-first; remote optional later)
- [ ] T019 Implement `VocabularyRepository` default in `pictovoice/shared/data/src/commonMain/kotlin/com/pictovoice/data/repo/DefaultVocabularyRepository.kt` using only `LocalVocabularyDataSource` initially
- [ ] T020 Initialize Sentry KMP in `pictovoice/androidApp/src/main/kotlin/com/pictovoice/PictoVoiceApp.kt` with DSN from build config (no sentence text in breadcrumbs)
- [ ] T021 Initialize Sentry KMP in `pictovoice/iosApp` entry point analogous to T020
- [ ] T022 Add `SentryBreadcrumbs` helper in `pictovoice/shared/presentation/src/commonMain/kotlin/com/pictovoice/presentation/telemetry/SentryBreadcrumbs.kt` wrapping safe non-PII events (`symbol_id`, `event_name` only)
- [ ] T023 [P] Add contract test scaffolding for `contracts/openapi.yaml` in `pictovoice/server/src/test/kotlin/com/pictovoice/server/contract/OpenApiContractTest.kt`
- [ ] T024 [P] Add shared test fixtures module for sample pictograms/sentences in `pictovoice/shared/domain/src/commonTest/kotlin/com/pictovoice/domain/fixtures/`

**Checkpoint**: Domain models compile; local DB + seed load; TTS no-ops or speaks on both platforms; Sentry captures test crash.

---

## Phase 3: User Story 1 — Build a message and speak it (Priority: P1) 🎯 MVP

**Goal**: Grid + sentence builder + Speak using natural device TTS; high-contrast presentation.

**Independent Test**: Select ≥2 pictograms, order matches sentence strip, Speak output matches sequence (per spec).

### Tests for User Story 1

- [ ] T025 [P] [US1] Add unit tests for `AddPictogram` and `SpeakSentence` handlers in `pictovoice/shared/domain/src/commonTest/kotlin/com/pictovoice/domain/usecase/SpeakSentenceHandlerTest.kt`
- [ ] T026 [P] [US1] Add MVI reducer/state tests for `CommunicationViewModel` in `pictovoice/shared/presentation/src/commonTest/kotlin/com/pictovoice/presentation/communication/CommunicationViewModelTest.kt`
- [ ] T027 [P] [US1] Add Compose UI smoke test for sentence build + speak button flow in `pictovoice/composeApp/src/commonTest/kotlin/com/pictovoice/ui/CommunicationScreenTest.kt`

### Implementation for User Story 1

- [ ] T028 [US1] Add `CommunicationUiState`, `CommunicationEvent`, `CommunicationEffect` in `pictovoice/shared/presentation/src/commonMain/kotlin/com/pictovoice/presentation/communication/CommunicationContract.kt`
- [ ] T029 [US1] Implement `CommunicationViewModel` (MVI store) in `pictovoice/shared/presentation/src/commonMain/kotlin/com/pictovoice/presentation/communication/CommunicationViewModel.kt` wiring `CommandHandler` + `VocabularyRepository` + `TextToSpeech`
- [ ] T030 [US1] Implement `SpeakSentence` and `AddPictogram` handlers in `pictovoice/shared/domain/src/commonMain/kotlin/com/pictovoice/domain/usecase/` consuming repository + TTS port
- [ ] T031 [US1] Add high-contrast theme (`PictoVoiceColors`, `PictoVoiceTypography`) in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/theme/Theme.kt`
- [ ] T032 [US1] Implement `SentenceBuilderBar` composable in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/SentenceBuilderBar.kt` showing ordered pictograms
- [ ] T033 [US1] Implement `PictogramGrid` composable in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/PictogramGrid.kt` with minimum touch target ≥ 48.dp (FR-002)
- [ ] T034 [US1] Implement primary `SpeakButton` composable in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/SpeakButton.kt`
- [ ] T035 [US1] Compose `CommunicationScreen` in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/CommunicationScreen.kt` binding ViewModel state/events
- [ ] T036 [US1] Set `CommunicationScreen` as start destination in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/App.kt`
- [ ] T037 [US1] On pictogram tap, call `SentryBreadcrumbs.record("pictogram_selected", mapOf("id" to pictogramId))` in `CommunicationViewModel`
- [ ] T038 [US1] Handle empty Speak per spec edge case (non-blocking prompt or no-op) in `SpeakSentence` handler in `pictovoice/shared/domain/src/commonMain/kotlin/com/pictovoice/domain/usecase/SpeakSentenceHandler.kt`

**Checkpoint**: US1 demoable on Android + iOS simulators with seeded vocabulary.

---

## Phase 4: User Story 2 — Communicate when offline (Priority: P2)

**Goal**: Speak path never depends on network; local catalog is authoritative for speech.

**Independent Test**: Airplane mode on; build sentence and Speak succeeds.

### Tests for User Story 2

- [ ] T039 [P] [US2] Add repository integration tests for offline-first invariants in `pictovoice/shared/data/src/commonTest/kotlin/com/pictovoice/data/repo/DefaultVocabularyRepositoryOfflineTest.kt`
- [ ] T040 [P] [US2] Add platform smoke test for Speak in airplane/offline mode in `pictovoice/androidApp/src/androidTest/kotlin/com/pictovoice/OfflineSpeakTest.kt` and `pictovoice/iosApp/.../OfflineSpeakTest.swift`

### Implementation for User Story 2

- [ ] T041 [US2] Audit `CommunicationViewModel` and repository paths in `pictovoice/shared/presentation/.../CommunicationViewModel.kt` to ensure no `RemoteVocabularyDataSource` call before Speak
- [ ] T042 [US2] Document offline guarantee in `pictovoice/shared/data/src/commonMain/kotlin/com/pictovoice/data/repo/DefaultVocabularyRepository.kt` KDoc (local-first invariant)
- [ ] T043 [US2] Add `NetworkMonitor` `expect`/`actual` stubs in `pictovoice/shared/data/src/commonMain/kotlin/com/pictovoice/data/network/NetworkMonitor.kt` (optional UI badge deferred) ensuring sync jobs no-op when offline without blocking UI thread
- [ ] T044 [US2] Verify seed assets in T017 include all pictograms needed for acceptance demo; expand `pictovoice/shared/data/src/commonMain/resources/seed/` if gaps found

**Checkpoint**: Manual US2 independent test passes on both platforms.

---

## Phase 5: User Story 3 — Edit sentence + immediate touch feedback (Priority: P2)

**Goal**: Remove one / clear all + immediate high-contrast pressed state on controls.

**Independent Test**: Remove middle item order preserved; clear empties strip; visual feedback on each tap.

### Tests for User Story 3

- [ ] T045 [P] [US3] Add unit tests for `RemovePictogramAt` / `ClearSentence` handlers in `pictovoice/shared/domain/src/commonTest/kotlin/com/pictovoice/domain/usecase/EditSentenceHandlersTest.kt`
- [ ] T046 [P] [US3] Add Compose UI tests for remove/clear + visual pressed states in `pictovoice/composeApp/src/commonTest/kotlin/com/pictovoice/ui/EditSentenceUiTest.kt`

### Implementation for User Story 3

- [ ] T047 [US3] Implement `RemovePictogramAt` and `ClearSentence` handlers in `pictovoice/shared/domain/src/commonMain/kotlin/com/pictovoice/domain/usecase/EditSentenceHandlers.kt`
- [ ] T048 [US3] Expose remove/clear events from `CommunicationViewModel` in `pictovoice/shared/presentation/src/commonMain/kotlin/com/pictovoice/presentation/communication/CommunicationViewModel.kt`
- [ ] T049 [US3] Add per-cell `Modifier` for press indication (border/color) in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/PictogramGrid.kt` and `SpeakButton.kt`
- [ ] T050 [US3] Add swipe/remove affordance on `SentenceBuilderBar` items in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/SentenceBuilderBar.kt`
- [ ] T051 [US3] Add `ClearSentence` control composable in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/ClearSentenceButton.kt` meeting FR-002 size
- [ ] T052 [US3] Map platform assistive touch settings (no app-level blocking of long-press) — verify no custom gesture steal in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/` composables

**Checkpoint**: US3 behaviors verified without regressing US1–US2.

---

## Phase 6: User Story 4 — Caregiver customization, push sync, predictions (Priority: P3)

**Goal**: Caregiver web + server APIs per [contracts/openapi.yaml](./contracts/openapi.yaml); device registers push; on-device prediction strip; FR-009/FR-013 satisfied.

**Independent Test**: Authenticate caregiver, publish layout, device receives push (or manual trigger), grid updates; suggestions append per assumptions; failed auth does not corrupt DB.

### Server & API

### Tests for User Story 4

- [ ] T053 [P] [US4] Add server contract tests for `/v1/vocabulary/*`, `/v1/devices/register`, `/v1/caregiver/layout` in `pictovoice/server/src/test/kotlin/com/pictovoice/server/contract/VocabularyApiContractTest.kt`
- [ ] T054 [P] [US4] Add integration test for revision bump + push dispatch trigger in `pictovoice/server/src/test/kotlin/com/pictovoice/server/integration/VocabularyPublishFlowTest.kt`
- [ ] T055 [P] [US4] Add repository sync tests (manifest + delta apply atomicity) in `pictovoice/shared/data/src/commonTest/kotlin/com/pictovoice/data/repo/VocabularySyncRepositoryTest.kt`
- [ ] T056 [P] [US4] Add prediction engine tests validating no network dependency in `pictovoice/shared/domain/src/commonTest/kotlin/com/pictovoice/domain/prediction/OnDevicePredictionEngineTest.kt`
- [ ] T057 [P] [US4] Add caregiver web e2e test for auth-before-save flow in `pictovoice/caregiver-web/tests/e2e/caregiver-save-auth.spec.ts`

### Implementation for User Story 4

- [ ] T058 [P] [US4] Bootstrap Ktor (or Spring) project in `pictovoice/server/src/main/kotlin/com/pictovoice/server/Application.kt` serving OpenAPI-aligned routes
- [ ] T059 [US4] Implement `GET /v1/vocabulary/manifest` and `GET /v1/vocabulary/delta` per contract in `pictovoice/server/src/main/kotlin/com/pictovoice/server/routes/VocabularyRoutes.kt`
- [ ] T060 [US4] Implement `POST /v1/devices/register` storing FCM/APNs tokens in `pictovoice/server/src/main/kotlin/com/pictovoice/server/device/DeviceRegistry.kt`
- [ ] T061 [US4] Implement caregiver JWT auth middleware in `pictovoice/server/src/main/kotlin/com/pictovoice/server/auth/CaregiverAuth.kt`
- [ ] T062 [US4] Implement `PUT /v1/caregiver/layout` persisting layout + bumping `vocabulary_revision` in `pictovoice/server/src/main/kotlin/com/pictovoice/server/routes/CaregiverRoutes.kt`
- [ ] T063 [US4] Implement push dispatcher sending revision-only payload via FCM HTTP v1 + APNs in `pictovoice/server/src/main/kotlin/com/pictovoice/server/push/PushDispatcher.kt`

### Remote data & sync (mobile)

- [ ] T064 [US4] Implement DTO mappers + `RemoteVocabularyDataSource` using Ktor in `pictovoice/shared/data/src/commonMain/kotlin/com/pictovoice/data/remote/RemoteVocabularyDataSource.kt`
- [ ] T065 [US4] Extend `DefaultVocabularyRepository` in `pictovoice/shared/data/.../DefaultVocabularyRepository.kt` to merge remote deltas atomically after manifest compare
- [ ] T066 [US4] Implement `SyncVocabularyCommand` handler invoking repository pull in `pictovoice/shared/domain/src/commonMain/kotlin/com/pictovoice/domain/usecase/SyncVocabularyHandler.kt`

### Push receivers (mobile)

- [ ] T067 [US4] Add `FirebaseMessagingService` subclass registering device token + handling data messages in `pictovoice/androidApp/src/main/kotlin/com/pictovoice/push/PictoVoiceFirebaseMessagingService.kt`
- [ ] T068 [US4] Register for APNs, forward token to server, handle silent push in `pictovoice/iosApp/` (Swift or KN bridge files as per template)

### On-device prediction (FR-009)

- [ ] T069 [US4] Implement `OnDevicePredictionEngine` in `pictovoice/shared/domain/src/commonMain/kotlin/com/pictovoice/domain/prediction/OnDevicePredictionEngine.kt` using time-of-day + session history only (no network)
- [ ] T070 [US4] Add `PredictionStrip` composable in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/PredictionStrip.kt` and wire to `CommunicationViewModel`
- [ ] T071 [US4] Implement append-on-select behavior for prediction taps in `pictovoice/shared/presentation/.../CommunicationViewModel.kt`

### Caregiver web

- [ ] T072 [US4] Scaffold `pictovoice/caregiver-web/` (package.json + build) with login view calling server auth
- [ ] T073 [US4] Implement grid editor page posting `PUT /v1/caregiver/layout` in `pictovoice/caregiver-web/src/pages/LayoutEditor.tsx` (or `.vue` / `.svelte` per chosen stack)
- [ ] T074 [US4] Enforce re-auth before save (browser session + server validates JWT) aligned with FR-013 in `pictovoice/caregiver-web/src/components/ConfirmSaveDialog.tsx`

### Caregiver auth gate (mobile local edits if any)

- [ ] T075 [US4] If mobile exposes “edit mode”, add `CaregiverAuthDialog` composable gating writes in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/CaregiverAuthDialog.kt` (optional if all edits are web-only — then document in README that FR-013 is enforced on web + server only)

**Checkpoint**: End-to-end: web publish → push → device sync → grid refresh; predictions visible offline.

---

## Phase 7: Polish & cross-cutting concerns

**Purpose**: Dual-platform parity, docs, CI, spec success criteria alignment.

- [ ] T076 [P] Add `pictovoice/README.md` linking to `specs/002-kmp-pictovoice-aac/quickstart.md` and environment variables (`SENTRY_DSN`, server base URL)
- [ ] T077 Run `bash /Users/ikari/Projects/PictoVoice/.specify/scripts/bash/update-agent-context.sh cursor-agent` after major stack changes
- [ ] T078 [P] Add GitHub Action workflow `.github/workflows/pictovoice-ci.yml` running domain/data/presentation tests + Android assemble + server tests
- [ ] T079 [P] Add CI step validating OpenAPI contract compatibility against `specs/002-kmp-pictovoice-aac/contracts/openapi.yaml` in `.github/workflows/pictovoice-ci.yml`
- [ ] T080 Verify FR-012 checklist (feature parity Android vs iOS) in `specs/002-kmp-pictovoice-aac/plan.md` appendix note or `pictovoice/docs/platform-parity.md`
- [ ] T081 Add ProGuard/R8 consumer rules file `pictovoice/androidApp/proguard-rules.pro` for Ktor/Sentry if minify enabled
- [ ] T082 [P] Add privacy manifest / usage descriptions for microphone if ever used (likely not) and network usage strings in `pictovoice/iosApp/Info.plist`
- [ ] T083 Review Sentry events to confirm no sentence text leaves device in `pictovoice/shared/presentation/.../SentryBreadcrumbs.kt`
- [ ] T084 [P] Add Kotlin linting as a chore (detekt or ktlint/spotless), configure baseline/rules, and wire CI check task in `pictovoice/build.gradle.kts` and `.github/workflows/pictovoice-ci.yml`

---

## Dependencies & execution order

| Phase | Depends on |
|-------|------------|
| 1 Setup | — |
| 2 Foundational | Phase 1 |
| 3 US1 | Phase 2 |
| 4 US2 | Phase 3 (incremental hardening) |
| 5 US3 | Phase 3 |
| 6 US4 | Phase 2 + 3 (sync/prediction needs running communication UI) |
| 7 Polish | Phases 3–6 per scope |

US4 can start server tasks (T058–T063) in parallel with late US1 polish once Phase 2 is done.

---

## Parallel example (after Phase 2)

```bash
# Developer A: US1 UI — T032, T033, T034 in parallel after T028–T030 exist
# Developer B: US4 server — T058, T059 in parallel
# Developer C: iOS TTS — T014 while Android T013 completes
```

---

## Implementation strategy

1. Complete Phases **1–2**, then deliver **Phase 3 (US1)** as MVP demo.
2. Layer **US2** hardening and **US3** UX.
3. Execute **US4** server + web + mobile sync as a milestone branch; feature-flag remote sync if needed.
4. **Polish** before store / pilot.

**MVP scope**: Phase 1 + 2 + **Phase 3 (US1)** only.

---

## Task counts

| Area | Tasks |
|------|-------|
| Phase 1 Setup | 8 |
| Phase 2 Foundational | 14 |
| Phase 3 US1 | 14 |
| Phase 4 US2 | 6 |
| Phase 5 US3 | 8 |
| Phase 6 US4 | 23 |
| Phase 7 Polish | 9 |
| **Total** | **84** |

All tasks use checklist format with sequential IDs and file paths as required.
