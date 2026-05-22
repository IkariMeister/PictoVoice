# Task Backlog (Synced from GitHub)

Generated: 2026-05-22

Source: https://github.com/IkariMeister/PictoVoice/issues

## Status Legend

- OPEN = not completed
- CLOSED = completed

## US1

- [ ] `T025` Issue #8 - [US1][T025] Add unit tests for `AddPictogram` and `SpeakSentence` handlers in `pictovoice/shared/domain/src/commonTest/kotlin/com/pictovoice/domain/usecase/SpeakSentenceHandlerTest.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/8))
- [ ] `T026` Issue #9 - [US1][T026] Add MVI reducer/state tests for `CommunicationViewModel` in `pictovoice/shared/presentation/src/commonTest/kotlin/com/pictovoice/presentation/communication/CommunicationViewModelTest.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/9))
- [ ] `T027` Issue #10 - [US1][T027] Add Compose UI smoke test for sentence build + speak button flow in `pictovoice/composeApp/src/commonTest/kotlin/com/pictovoice/ui/CommunicationScreenTest.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/10))
- [ ] `T028` Issue #11 - [US1][T028] Add `CommunicationUiState`, `CommunicationEvent`, `CommunicationEffect` in `pictovoice/shared/presentation/src/commonMain/kotlin/com/pictovoice/presentation/communication/CommunicationContract.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/11))
- [ ] `T029` Issue #12 - [US1][T029] Implement `CommunicationViewModel` (MVI store) in `pictovoice/shared/presentation/src/commonMain/kotlin/com/pictovoice/presentation/communication/CommunicationViewModel.kt` wiring `CommandHandler` + `VocabularyRepository` + `TextToSpeech` ([link](https://github.com/IkariMeister/PictoVoice/issues/12))
- [ ] `T030` Issue #13 - [US1][T030] Implement `SpeakSentence` and `AddPictogram` handlers in `pictovoice/shared/domain/src/commonMain/kotlin/com/pictovoice/domain/usecase/` consuming repository + TTS port ([link](https://github.com/IkariMeister/PictoVoice/issues/13))
- [ ] `T031` Issue #14 - [US1][T031] Add high-contrast theme (`PictoVoiceColors`, `PictoVoiceTypography`) in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/theme/Theme.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/14))
- [ ] `T032` Issue #15 - [US1][T032] Implement `SentenceBuilderBar` composable in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/SentenceBuilderBar.kt` showing ordered pictograms ([link](https://github.com/IkariMeister/PictoVoice/issues/15))
- [ ] `T033` Issue #16 - [US1][T033] Implement `PictogramGrid` composable in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/PictogramGrid.kt` with minimum touch target ≥ 48.dp (FR-002) ([link](https://github.com/IkariMeister/PictoVoice/issues/16))
- [ ] `T034` Issue #17 - [US1][T034] Implement primary `SpeakButton` composable in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/SpeakButton.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/17))
- [ ] `T035` Issue #18 - [US1][T035] Compose `CommunicationScreen` in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/CommunicationScreen.kt` binding ViewModel state/events ([link](https://github.com/IkariMeister/PictoVoice/issues/18))
- [ ] `T036` Issue #19 - [US1][T036] Set `CommunicationScreen` as start destination in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/App.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/19))
- [ ] `T037` Issue #20 - [US1][T037] On pictogram tap, call `SentryBreadcrumbs.record("pictogram_selected", mapOf("id" to pictogramId))` in `CommunicationViewModel` ([link](https://github.com/IkariMeister/PictoVoice/issues/20))
- [ ] `T038` Issue #21 - [US1][T038] Handle empty Speak per spec edge case (non-blocking prompt or no-op) in `SpeakSentence` handler in `pictovoice/shared/domain/src/commonMain/kotlin/com/pictovoice/domain/usecase/SpeakSentenceHandler.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/21))

## US2

- [ ] `T039` Issue #22 - [US2][T039] Add repository integration tests for offline-first invariants in `pictovoice/shared/data/src/commonTest/kotlin/com/pictovoice/data/repo/DefaultVocabularyRepositoryOfflineTest.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/22))
- [ ] `T040` Issue #23 - [US2][T040] Add platform smoke test for Speak in airplane/offline mode in `pictovoice/androidApp/src/androidTest/kotlin/com/pictovoice/OfflineSpeakTest.kt` and `pictovoice/iosApp/.../OfflineSpeakTest.swift` ([link](https://github.com/IkariMeister/PictoVoice/issues/23))
- [ ] `T041` Issue #24 - [US2][T041] Audit `CommunicationViewModel` and repository paths in `pictovoice/shared/presentation/.../CommunicationViewModel.kt` to ensure no `RemoteVocabularyDataSource` call before Speak ([link](https://github.com/IkariMeister/PictoVoice/issues/24))
- [ ] `T042` Issue #25 - [US2][T042] Document offline guarantee in `pictovoice/shared/data/src/commonMain/kotlin/com/pictovoice/data/repo/DefaultVocabularyRepository.kt` KDoc (local-first invariant) ([link](https://github.com/IkariMeister/PictoVoice/issues/25))
- [ ] `T043` Issue #26 - [US2][T043] Add `NetworkMonitor` `expect`/`actual` stubs in `pictovoice/shared/data/src/commonMain/kotlin/com/pictovoice/data/network/NetworkMonitor.kt` (optional UI badge deferred) ensuring sync jobs no-op when offline without blocking UI thread ([link](https://github.com/IkariMeister/PictoVoice/issues/26))
- [ ] `T044` Issue #27 - [US2][T044] Verify seed assets in T017 include all pictograms needed for acceptance demo; expand `pictovoice/shared/data/src/commonMain/resources/seed/` if gaps found ([link](https://github.com/IkariMeister/PictoVoice/issues/27))

## US3

- [x] `T045` Issue #28 - [US3][T045] Add unit tests for `RemovePictogramAt` / `ClearSentence` handlers in `pictovoice/shared/domain/src/commonTest/kotlin/com/pictovoice/domain/usecase/EditSentenceHandlersTest.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/28))
- [ ] `T046` Issue #29 - [US3][T046] Add Compose UI tests for remove/clear + visual pressed states in `pictovoice/composeApp/src/commonTest/kotlin/com/pictovoice/ui/EditSentenceUiTest.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/29))
- [x] `T047` Issue #30 - [US3][T047] Implement `RemovePictogramAt` and `ClearSentence` handlers in `pictovoice/shared/domain/src/commonMain/kotlin/com/pictovoice/domain/usecase/EditSentenceHandlers.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/30))
- [x] `T048` Issue #31 - [US3][T048] Expose remove/clear events from `CommunicationViewModel` in `pictovoice/shared/presentation/src/commonMain/kotlin/com/pictovoice/presentation/communication/CommunicationViewModel.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/31))
- [ ] `T049` Issue #32 - [US3][T049] Add per-cell `Modifier` for press indication (border/color) in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/PictogramGrid.kt` and `SpeakButton.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/32))
- [x] `T050` Issue #33 - [US3][T050] Add swipe/remove affordance on `SentenceBuilderBar` items in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/SentenceBuilderBar.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/33))
- [x] `T051` Issue #34 - [US3][T051] Add `ClearSentence` control composable in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/ClearSentenceButton.kt` meeting FR-002 size ([link](https://github.com/IkariMeister/PictoVoice/issues/34))
- [ ] `T052` Issue #35 - [US3][T052] Map platform assistive touch settings (no app-level blocking of long-press) — verify no custom gesture steal in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/` composables ([link](https://github.com/IkariMeister/PictoVoice/issues/35))

## US4

- [ ] `T053` Issue #36 - [US4][T053] Add server contract tests for `/v1/vocabulary/*`, `/v1/devices/register`, `/v1/caregiver/layout` in `pictovoice/server/src/test/kotlin/com/pictovoice/server/contract/VocabularyApiContractTest.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/36))
- [ ] `T054` Issue #37 - [US4][T054] Add integration test for revision bump + push dispatch trigger in `pictovoice/server/src/test/kotlin/com/pictovoice/server/integration/VocabularyPublishFlowTest.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/37))
- [ ] `T055` Issue #38 - [US4][T055] Add repository sync tests (manifest + delta apply atomicity) in `pictovoice/shared/data/src/commonTest/kotlin/com/pictovoice/data/repo/VocabularySyncRepositoryTest.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/38))
- [ ] `T056` Issue #39 - [US4][T056] Add prediction engine tests validating no network dependency in `pictovoice/shared/domain/src/commonTest/kotlin/com/pictovoice/domain/prediction/OnDevicePredictionEngineTest.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/39))
- [ ] `T057` Issue #40 - [US4][T057] Add caregiver web e2e test for auth-before-save flow in `pictovoice/caregiver-web/tests/e2e/caregiver-save-auth.spec.ts` ([link](https://github.com/IkariMeister/PictoVoice/issues/40))
- [ ] `T058` Issue #41 - [US4][T058] Bootstrap Ktor (or Spring) project in `pictovoice/server/src/main/kotlin/com/pictovoice/server/Application.kt` serving OpenAPI-aligned routes ([link](https://github.com/IkariMeister/PictoVoice/issues/41))
- [ ] `T059` Issue #42 - [US4][T059] Implement `GET /v1/vocabulary/manifest` and `GET /v1/vocabulary/delta` per contract in `pictovoice/server/src/main/kotlin/com/pictovoice/server/routes/VocabularyRoutes.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/42))
- [ ] `T060` Issue #43 - [US4][T060] Implement `POST /v1/devices/register` storing FCM/APNs tokens in `pictovoice/server/src/main/kotlin/com/pictovoice/server/device/DeviceRegistry.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/43))
- [ ] `T061` Issue #44 - [US4][T061] Implement caregiver JWT auth middleware in `pictovoice/server/src/main/kotlin/com/pictovoice/server/auth/CaregiverAuth.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/44))
- [ ] `T062` Issue #45 - [US4][T062] Implement `PUT /v1/caregiver/layout` persisting layout + bumping `vocabulary_revision` in `pictovoice/server/src/main/kotlin/com/pictovoice/server/routes/CaregiverRoutes.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/45))
- [ ] `T063` Issue #46 - [US4][T063] Implement push dispatcher sending revision-only payload via FCM HTTP v1 + APNs in `pictovoice/server/src/main/kotlin/com/pictovoice/server/push/PushDispatcher.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/46))
- [ ] `T064` Issue #47 - [US4][T064] Implement DTO mappers + `RemoteVocabularyDataSource` using Ktor in `pictovoice/shared/data/src/commonMain/kotlin/com/pictovoice/data/remote/RemoteVocabularyDataSource.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/47))
- [ ] `T065` Issue #48 - [US4][T065] Extend `DefaultVocabularyRepository` in `pictovoice/shared/data/.../DefaultVocabularyRepository.kt` to merge remote deltas atomically after manifest compare ([link](https://github.com/IkariMeister/PictoVoice/issues/48))
- [ ] `T066` Issue #49 - [US4][T066] Implement `SyncVocabularyCommand` handler invoking repository pull in `pictovoice/shared/domain/src/commonMain/kotlin/com/pictovoice/domain/usecase/SyncVocabularyHandler.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/49))
- [ ] `T067` Issue #50 - [US4][T067] Add `FirebaseMessagingService` subclass registering device token + handling data messages in `pictovoice/androidApp/src/main/kotlin/com/pictovoice/push/PictoVoiceFirebaseMessagingService.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/50))
- [ ] `T068` Issue #51 - [US4][T068] Register for APNs, forward token to server, handle silent push in `pictovoice/iosApp/` (Swift or KN bridge files as per template) ([link](https://github.com/IkariMeister/PictoVoice/issues/51))
- [ ] `T069` Issue #52 - [US4][T069] Implement `OnDevicePredictionEngine` in `pictovoice/shared/domain/src/commonMain/kotlin/com/pictovoice/domain/prediction/OnDevicePredictionEngine.kt` using time-of-day + session history only (no network) ([link](https://github.com/IkariMeister/PictoVoice/issues/52))
- [ ] `T070` Issue #53 - [US4][T070] Add `PredictionStrip` composable in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/PredictionStrip.kt` and wire to `CommunicationViewModel` ([link](https://github.com/IkariMeister/PictoVoice/issues/53))
- [ ] `T071` Issue #54 - [US4][T071] Implement append-on-select behavior for prediction taps in `pictovoice/shared/presentation/.../CommunicationViewModel.kt` ([link](https://github.com/IkariMeister/PictoVoice/issues/54))
- [ ] `T072` Issue #55 - [US4][T072] Scaffold `pictovoice/caregiver-web/` (package.json + build) with login view calling server auth ([link](https://github.com/IkariMeister/PictoVoice/issues/55))
- [ ] `T073` Issue #56 - [US4][T073] Implement grid editor page posting `PUT /v1/caregiver/layout` in `pictovoice/caregiver-web/src/pages/LayoutEditor.tsx` (or `.vue` / `.svelte` per chosen stack) ([link](https://github.com/IkariMeister/PictoVoice/issues/56))
- [ ] `T074` Issue #57 - [US4][T074] Enforce re-auth before save (browser session + server validates JWT) aligned with FR-013 in `pictovoice/caregiver-web/src/components/ConfirmSaveDialog.tsx` ([link](https://github.com/IkariMeister/PictoVoice/issues/57))
- [ ] `T075` Issue #58 - [US4][T075] If mobile exposes “edit mode”, add `CaregiverAuthDialog` composable gating writes in `pictovoice/composeApp/src/commonMain/kotlin/com/pictovoice/ui/CaregiverAuthDialog.kt` (optional if all edits are web-only — then document in README that FR-013 is enforced on web + server only) ([link](https://github.com/IkariMeister/PictoVoice/issues/58))

## CHORE

- [ ] `T084` Issue #69 - [CHORE][T084] Add Kotlin linting tooling and CI checks ([link](https://github.com/IkariMeister/PictoVoice/issues/69))

## Sync Notes

- US2 parent issue #3 is CLOSED while US2 tasks remain OPEN.
- CHORE issue #69 is OPEN and currently has no milestone set.

