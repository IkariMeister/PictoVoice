# Research: PictoVoice KMP AAC

## 1. Compose Multiplatform + KMP for dual mobile release

- **Decision**: Use **Compose Multiplatform** for shared UI where feasible; expect/actual for TTS, push token registration, and Sentry native bridges.
- **Rationale**: One codebase for grid, sentence builder, MVI wiring; matches FR-012 dual-platform requirement; mature CMP path for iOS+Android with platform-specific shells.
- **Alternatives considered**: Fully native UIs per platform (higher duplication); Flutter/Dart (off-stack for Kotlin teams).

## 2. MVI + Clean Architecture

- **Decision**: **Presentation**: unidirectional data flow (State, Intent/Event, Side effects) with a single store/ViewModel per screen; **Domain**: command objects (`SpeakSentence`, `ApplyVocabularySync`, `SelectPictogram`) handled by use-case classes; **Data**: Repository facades over `LocalVocabularyDataSource` + `RemoteVocabularyDataSource`.
- **Rationale**: Testable reducers, clear separation, aligns with constitution-style quality gates.
- **Alternatives considered**: MVVM without explicit command types (weaker audit trail for domain operations).

## 3. Vocabulary updates: caregiver web + push

- **Decision**: **Caregiver web** talks to **backend REST API** (authenticated) to mutate catalog and grid templates stored server-side (or authorized object storage + DB metadata). **Devices** poll manifest with `If-None-Match` / `sinceVersion` and apply deltas; **push** (FCM + APNs) carries only **non-sensitive** payload: e.g. `{ "vocabularyRevision": "2026-04-10T12:00:00Z", "scope": "default" }` to trigger sync—**no pictogram text or user messages in push**.
- **Rationale**: Satisfies “web for caregiver” and “push mechanism”; keeps PHI/communication content off push payloads; compatible with FR-009 (no cloud prediction on user sentences).
- **Alternatives considered**: Full vocabulary in push payload (too large, privacy risk); WebSocket-only (higher ops burden for v1).

## 4. Sentry (KMP)

- **Decision**: **Sentry Kotlin Multiplatform** for crash reporting; add **breadcrumbs** for interaction events (e.g. `pictogram_selected`, `speak_triggered`, `sync_started`) with **symbol IDs only**, not free-form user text.
- **Rationale**: Meets observability for crashes and funnel debugging without logging AAC message content.
- **Alternatives considered**: Firebase Crashlytics (less unified across KMP); custom logging only (no crash symbolication depth).

## 5. Backend stack (concise default)

- **Decision**: **Kotlin/JVM** (Ktor or Spring Boot) for vocabulary API + JWT/session for caregivers + FCM HTTP v1 + APNs provider API—**unless** team standardizes on existing Node/Go stack (document in ADR if changed).
- **Rationale**: Shared Kotlin types/DTOs optional via OpenAPI generation; single language across mobile + server reduces friction.
- **Alternatives considered**: Serverless BaaS (less control for push + versioning policies).

## 6. Caregiver web stack

- **Decision**: **TypeScript + React** or **SvelteKit** for caregiver SPA with OIDC/password flow against same backend—pick one in first implementation sprint based on team skill.
- **Rationale**: Fast iteration for grid editors and bulk import; independent deploy from mobile.
- **Alternatives considered**: CMP for Web (experimental for complex admin grids).
