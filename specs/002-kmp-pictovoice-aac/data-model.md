# Data Model: PictoVoice AAC

## Entities

### Pictogram

| Field | Description |
|-------|-------------|
| `id` | Stable string UUID |
| `label` | Primary display text (single language v1) |
| `spokenText` | Text passed to TTS (may differ from label) |
| `imageRef` | URI or asset key for bitmap/vector |
| `category` | Optional grouping for grid editor |
| `sortOrder` | Integer within grid page |

**Validation**: Non-empty `id`, `spokenText` required for Speak; image optional if label-only mode allowed by product.

### GridLayout

| Field | Description |
|-------|-------------|
| `id` | Layout identifier |
| `cells` | Ordered list of `PictogramRef` or empty slots |
| `rows`, `columns` | Dimensions for communication grid |
| `version` | Monotonic revision for sync |

**Relationships**: References pictograms by `id`; one **active** layout per device for v1 communicator context.

### Sentence (runtime)

| Field | Description |
|-------|-------------|
| `items` | Ordered list of `Pictogram.id` |
| `revision` | Ephemeral; not persisted unless product adds history later |

**State transitions**: `empty` → `building` → `spoken` (optional) → `cleared` / partial delete.

### VocabularyManifest

| Field | Description |
|-------|-------------|
| `revision` | Server-authoritative string or long (ETag-friendly) |
| `generatedAt` | ISO-8601 |
| `pictogramHashes` | Map id → content hash for delta download |

**Use**: Client compares to local manifest; fetch deltas when push received or on periodic safety refresh.

### DeviceRegistration

| Field | Description |
|-------|-------------|
| `deviceId` | Anonymous install id |
| `fcmToken` / `apnsToken` | Platform push token |
| `communicatorProfileId` | v1: single implicit profile |

### CaregiverAccount

| Field | Description |
|-------|-------------|
| `id` | Subject from auth provider |
| `linkedCommunicatorIds` | Devices or profiles permitted to edit (v1 may be single-tenant) |

## Sync rules

- Server is source of truth for published vocabulary **revision**.
- Client applies updates **atomically** per revision (transaction in local DB).
- Failed sync leaves last **good** revision active (matches spec edge case for failed caregiver auth analogue).

## Privacy notes

- No storage of sentence content on server unless explicitly added in a future spec (currently out of scope).
