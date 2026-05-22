# PictoVoice (Refactor Baseline)

This is a clean restart scaffold using a **feature-first shared architecture**.

## Shared modules

- `shared/core-model`
- `shared/core-ui`
- `shared/core-telemetry`
- `shared/feature-vocabulary`
- `shared/feature-communication`

Architecture layers are now delimited by package inside each feature module:

- `domain`
- `data`
- `presentation`

Specifications and issue tracking remain in the repository (`specs/`) and GitHub issues.

## UI snapshot testing (Compose)

Snapshot tests are implemented in:

- `composeApp/src/androidInstrumentedTest/kotlin/com/pictovoice/ui/CommunicationSnapshotTest.kt`

### How it works

- The test captures rendered UI pixels with Compose test APIs.
- A deterministic SHA-256 hash is computed from the captured bitmap.
- The hash is compared with in-file golden constants:
  - `GOLDEN_DEFAULT_SCREEN`
  - `GOLDEN_WITH_SENTENCE`

### Initialize/refresh golden hashes

1. Run the Android instrumented snapshot tests on a stable emulator/device:
   - same API level
   - same display scale/font settings
2. Temporarily print/log the computed `snapshotHash` values in the test.
3. Replace:
   - `GOLDEN_DEFAULT_SCREEN`
   - `GOLDEN_WITH_SENTENCE`
   with the observed hashes.
4. Remove temporary logging and commit the updated constants.

### Notes

- Tests are currently guarded with `Assume.assumeTrue(...)` while goldens are uninitialized.
- Once constants are set, snapshot drift will fail tests and require intentional golden updates.

## Assistive touch compatibility (US3)

To preserve platform accessibility behavior, interactive controls in `composeApp/src/commonMain/kotlin/com/pictovoice/ui/`
must avoid custom low-level gesture handlers that can interfere with assistive touch systems.

- Use high-level Compose interactions (`Button`, `clickable`) for tap actions.
- Avoid custom pointer gesture interception for core controls (`pointerInput`, gesture detectors) unless strictly required.
- Keep minimum touch targets at or above the current 48-56dp controls used by the communication screen.

Current verification:

- No `pointerInput`, `detectTapGestures`, or similar custom gesture handlers are used in the shared communication UI controls.

## US3 testing quickstart

Run from `pictovoice/` with Android SDK configured and an emulator/device connected for instrumented tests.

### Shared logic tests (JVM/common)

```bash
./gradlew :shared:feature-communication:allTests
./gradlew :composeApp:allTests
```

Expected US3-related coverage includes:

- `CommunicationViewModelTest`
- `EditSentenceUiLogicTest`

### Android instrumented interaction tests

```bash
./gradlew :composeApp:connectedDebugAndroidTest
```

US3 interaction suite:

- `EditSentenceUiTest`
  - sentence item tap removes item
  - clear action empties sentence strip
  - speak/clear accessibility labels exist
  - speak button pressed state toggles via semantics

## Kotlin linting (Issue #69)

Detekt is configured at workspace level with:

- config: `pictovoice/config/detekt/detekt.yml`
- baselines: `pictovoice/config/detekt/*-baseline.xml` (one file per Gradle module)

Run from `pictovoice/`:

```bash
./gradlew detekt
```

To regenerate baseline (only when intentionally updating accepted debt):

```bash
./gradlew detektBaseline
```
