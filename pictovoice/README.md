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
