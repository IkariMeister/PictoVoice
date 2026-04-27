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
