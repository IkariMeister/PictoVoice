# Feature Specification: AAC Pictogram App for Motor Disability


## User Scenarios & Testing *(mandatory)*

### User Story 1 - Build a message and speak it (Priority: P1)

**Tracked as**: [#2 — US1 / P1](https://github.com/IkariMeister/PictoVoice/issues/2)

A person with motor disability selects communication symbols from a grid; each selection adds the symbol to an ordered sentence area at the top. They use a primary Speak action to hear the full sequence read aloud in natural-sounding speech.

**Why this priority**: Without selection, ordering, and speech, the product is not an AAC tool; this is the minimum viable communication loop.

**Independent Test**: Observe a user (or test participant) select two or more pictograms, confirm order in the sentence area, trigger Speak, and verify the spoken output matches the sequence intent.

**Acceptance Scenarios**:

1. **Given** the grid shows available pictograms and the sentence area is empty, **When** the user selects pictograms in order, **Then** each appears in the sentence builder in the same order without losing prior selections.
2. **Given** the sentence builder contains at least one pictogram, **When** the user activates Speak, **Then** the app produces continuous speech that reflects the full current sequence.
3. **Given** the user is on the main communication screen, **When** they scan the grid, **Then** pictograms are presented with high contrast between symbol and background for readability.

---

### User Story 2 - Communicate when offline (Priority: P2)

**Tracked as**: [#3 — US2 / P2](https://github.com/IkariMeister/PictoVoice/issues/3)

The user relies on speech output even without internet (e.g., clinic basement, travel, outage). Speech for the current sequence is generated and played entirely on the device.

**Why this priority**: AAC users need a dependable voice; network dependency creates unacceptable failure modes.

**Independent Test**: Disable all network connectivity, build a short sentence, use Speak, and confirm audible output completes successfully.

**Acceptance Scenarios**:

1. **Given** the device has no network connectivity, **When** the user completes a sentence and activates Speak, **Then** speech plays successfully without requiring a connection.
2. **Given** the app was used online earlier, **When** the user goes offline and opens the app, **Then** core selection and Speak behavior remains available (subject to Assumptions about pre-installed voices).

---

### User Story 3 - Edit the sentence and get immediate touch feedback (Priority: P2)

**Tracked as**: [#4 — US3 / P2](https://github.com/IkariMeister/PictoVoice/issues/4)

The user clears one pictogram or the whole sentence to correct mistakes quickly. Every interactive control gives obvious visual confirmation as soon as a touch is recognized.

**Why this priority**: Motor imprecision and tremor make errors frequent; fast correction and confidence that a tap registered reduce frustration and fatigue.

**Independent Test**: Add several pictograms, remove one from the middle, clear all, and repeat while observing that each tap or activation produces an immediate high-contrast visual change (border, color shift, or equivalent).

**Acceptance Scenarios**:

1. **Given** multiple pictograms in the sentence builder, **When** the user requests removal of a single item, **Then** only that item is removed and order of the others is preserved.
2. **Given** a non-empty sentence, **When** the user clears the entire workspace, **Then** the sentence area is empty and ready for a new message.
3. **Given** any interactive control on the communication screen, **When** the user activates it, **Then** a high-contrast visual response appears within a perceptually immediate timeframe (see Success Criteria).

---

### User Story 4 - Customize the grid and get next-symbol help (Priority: P3)

**Tracked as**: [#5 — US4 / P3](https://github.com/IkariMeister/PictoVoice/issues/5)

A caregiver or the user adjusts which pictograms appear and how the grid is arranged. A prediction strip suggests likely next pictograms based on context (e.g., time of day) and recent selections to reduce how many touches are needed.

**Why this priority**: Personalization and prediction improve speed and relevance but are not required for the first usable communication session.

**Independent Test**: Change grid content or layout, return to communication, confirm new symbols appear; build a partial sentence and confirm the suggestion bar updates and that selecting a suggestion adds the symbol correctly.

**Acceptance Scenarios**:

1. **Given** customization mode (or settings), **When** the user saves changes to the pictogram set or layout, **Then** the main grid reflects those changes on next use.
2. **Given** an active sentence or context and the prediction area is visible, **When** the user selects a suggested pictogram, **Then** it is inserted or appended according to a consistent, documented rule (see Assumptions).
3. **Given** the user navigates only with touch, **When** they operate the grid, sentence area, Speak, clear, and prediction controls, **Then** all targets meet the minimum size defined in Functional Requirements.

---

### Edge Cases

- User activates Speak with an empty sentence: app provides a clear, non-destructive response (e.g., short prompt or no audio) without error state that blocks further use.
- Speech output is temporarily unavailable (e.g., device muted, voice data missing): user sees or hears an understandable recovery path, not a silent failure.
- Rapid repeated touches (tremor or accidental double-tap): behavior aligns with system assistive settings where applicable; app does not trap the user in unintended repeated actions beyond platform norms.
- Prediction returns no or low-confidence suggestions: bar shows an empty or neutral state without breaking the grid or sentence builder.
- Very long sentences: sentence area remains usable (scroll or truncation with clear indication) without hiding Speak or essential controls.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The system MUST present a customizable grid of high-contrast communication pictograms suitable for users with low vision or contrast sensitivity in typical indoor lighting.
- **FR-002**: Every interactive element on primary communication flows (grid cells, sentence builder items, Speak, clear actions, prediction chips) MUST meet a minimum touch target of 44×44 density-independent units on platforms that use that measure, or the platform’s equivalent accessibility minimum where different.
- **FR-003**: The system MUST provide a dedicated sentence builder region that shows selected pictograms in order before speech is produced.
- **FR-004**: The system MUST provide a primary Speak action that converts the current pictogram sequence into spoken audio that sounds natural and intelligible to a typical listener.
- **FR-005**: Speech for the current sequence MUST be producible and playable with no active internet connection, using voices and engines available on the device.
- **FR-006**: Users MUST be able to remove a single pictogram from the sequence and clear the entire sequence through explicit actions.
- **FR-007**: On touch or equivalent activation, interactive controls MUST show immediate high-contrast visual feedback (e.g., border or color change) so the user can tell the input was recognized.
- **FR-008**: The interface MUST be fully operable via direct touch and MUST not interfere with system-level assistive behaviors such as extended touch duration or repeat-touch filtering when those features are enabled on the device.
- **FR-009**: The system MUST provide an AI-driven prediction strip suggesting likely next pictograms informed by contextual signals including time-of-day and prior selections in the current session (and MAY use additional context defined in planning).
- **FR-010**: Pictogram sets and grid layout MUST be user- or caregiver-configurable within product limits (e.g., visible cell count), without requiring developer intervention.

### Key Entities

- **Pictogram**: A communication symbol with display form (image, label, or both), spoken label or phrase, and placement in the grid.
- **Sentence (sequence)**: Ordered list of pictograms in the sentence builder, including empty state.
- **Grid layout**: Configuration of which pictograms appear, their positions, and any grouping relevant to the user.
- **Suggestion**: A candidate next pictogram offered by the prediction strip with an associated selection action.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: In moderated testing, at least 90% of participants with motor disability or representative proxy tasks complete “add three pictograms and Speak” on first attempt without facilitator intervention beyond initial orientation.
- **SC-002**: With network disabled, Speak succeeds for a five-pictogram sentence in at least 95% of trials across supported devices in acceptance testing.
- **SC-003**: Visual feedback after a successful touch is noticeable to observers within one quarter of a second under normal use (subjective timing verified in usability sessions or recorded video review).
- **SC-004**: For primary controls, measured touch targets meet or exceed the minimum specified in FR-002 on a sample of representative screens (verified by design review or measurement checklist).
- **SC-005**: When predictions are enabled, median number of touches to complete a fixed benchmark phrase is lower with predictions on than off in lab comparison, or prediction use reduces touches for at least 70% of benchmark runs.
- **SC-006**: Qualitative: at least 80% of assistive-technology users in pilot feedback report confidence that the app “responds when I touch it” on a 5-point Likert scale (agree or strongly agree).

## Assumptions

- Primary device class is mobile or tablet with platform-standard accessibility APIs; desktop is out of scope unless later specified.
- At least one high-quality on-device voice is installed or bundled so offline speech meets intelligibility expectations; voice installation flows may be documented separately.
- “Natural-sounding” is judged by stakeholder listening tests, not a specific vendor engine.
- Insertion behavior for prediction taps defaults to appending to the sentence unless product research favors another single consistent rule documented in planning.
- Customizable grid uses a bounded catalog of pictograms supplied with the app or approved imports; open-ended internet image search is out of scope.
- AI prediction may use on-device models, rule-based context, or both; planning will choose approaches that respect privacy and offline requirements for core flows.
