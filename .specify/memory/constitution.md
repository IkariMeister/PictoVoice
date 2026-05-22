

# PictoVoice Constitution

## Core Principles

### I. Code Quality

Delivered code MUST be readable, cohesive, and maintainable. Changes MUST follow
project style and lint rules where defined; where tooling is absent, reviewers
MUST still verify naming clarity, reasonable module boundaries, and absence of
avoidable duplication. Complexity MUST be justified in review when it increases
cognitive load or coupling. Rationale: sustainable velocity and safer change
depend on code that the team can understand and modify with confidence.

### II. Testing Standards

Material behavior or contract changes MUST include automated tests at the
appropriate level (unit, integration, contract, or end-to-end) unless the
feature specification documents a time-bounded, reviewed exception with a
compensating manual verification plan. Tests MUST fail meaningfully before new
behavior exists (red-green-refactor for new logic). Regressions in protected
behavior MUST be caught by expanding tests rather than relying on ad hoc checks
alone. Rationale: repeatable verification reduces defects and documents intent
for future contributors.

### III. User Experience Consistency

User-facing flows MUST align with established interaction, visual, and content
patterns documented for the product (design system, copy guidelines, or
precedent screens). New patterns MUST be introduced deliberately, documented,
and reviewed for consistency with adjacent experiences. Error and empty states
MUST be understandable and actionable without jargon. Rationale: consistency
lowers learning cost, builds trust, and reduces fragmented one-off UI decisions.

### IV. Accessibility

Interactive and informational user interfaces MUST conform to WCAG 2.1 Level AA
for the applicable content types unless the specification records a scoped
exception with owner approval and a remediation timeline. Focus order, labels,
contrast, keyboard operation, and assistive technology compatibility MUST be
verified for new or changed UI. Rationale: accessibility is a product
requirement, not an optional polish pass.

### V. Performance

Features MUST meet performance expectations stated in the specification or plan
(e.g., perceived responsiveness, throughput, memory, or frame rate) for the
target context. When budgets are not yet written, plans MUST propose measurable
targets before implementation proceeds. Regressions against agreed budgets
MUST be resolved or explicitly accepted with rationale. Rationale: performance
directly affects usability, cost, and reliability; it must be defined and
verified like other requirements.

## Cross-Cutting Delivery Standards

Specifications and implementation plans MUST call out user-facing
non-functional expectations when relevant: UX consistency references,
accessibility scope, and performance budgets. Success criteria MUST remain
verifiable without naming implementation technologies. Complexity Tracking in
plans MUST be used when a delivery choice intentionally relaxes a principle,
with justification and a simpler alternative noted.

## Review & Verification

Pull requests MUST be reviewed for adherence to these principles. Reviewers
SHOULD confirm tests, UX consistency, accessibility checks (where UI changed),
and performance expectations for the change set. The constitution supersedes
informal habits when they conflict. Amendments MUST update this document,
bump version per semantic rules below, and refresh dependent templates when
gates or mandatory guidance change.

## Governance

**Authority**: This constitution is the source of truth for engineering quality
expectations on PictoVoice.

**Amendments**: Proposed changes MUST be submitted as edits to this file with
version bump rationale. Material principle changes require team agreement;
clarifications and non-semantic wording fixes may proceed with a PATCH bump.

**Versioning**: MAJOR for incompatible removals or redefinitions of principles;
MINOR for new principles or materially expanded guidance; PATCH for clarifications
and typos.

**Compliance**: Feature planning (`/speckit.plan`) MUST pass the Constitution
Check gates. Periodic spot reviews SHOULD sample recent work against principles.

**Version**: 1.0.0 | **Ratified**: 2026-04-09 | **Last Amended**: 2026-04-09