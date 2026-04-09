# Quickstart: PictoVoice development

## Prerequisites

- JDK 17+ (Temurin recommended)
- Android Studio with Kotlin Multiplatform plugin
- Xcode 15+ (for iOS targets on macOS)
- CocoaPods or SPM per KMP template choice when iOS project is generated

## Repository setup (after implementation lands)

1. Clone the repo and open the root in Android Studio.
2. Copy `local.properties` / `gradle.properties` templates (to be added in implementation) for SDK paths.
3. Set `SENTRY_DSN` in non-committed env or `local.properties` for debug builds.
4. Point `server/` config to a local Postgres (or SQLite for dev) once backend exists.

## Run mobile

```bash
./gradlew :composeApp:assembleDebug          # Android
./gradlew :iosApp:iosSimulatorArm64Test      # adjust per template
```

## Run caregiver web

```bash
cd caregiver-web && npm install && npm run dev   # or pnpm; exact stack from research
```

## Run server

```bash
cd server && ./gradlew run    # or equivalent once Ktor/Spring app exists
```

## Contract checks

```bash
# Example: spectral or openapi-diff against contracts/openapi.yaml (add in CI)
```

## Useful links

- Spec: [spec.md](./spec.md)
- Plan: [plan.md](./plan.md)
- API contract: [contracts/openapi.yaml](./contracts/openapi.yaml)
