# Self-Healing Policy

## Trigger
A self-healing cycle starts when any post-change verification check fails.

## Required Checks
- Compilation must succeed.
- Unit tests must pass.
- Regression tests must pass.
- Static analysis must not introduce new critical findings.

## Recovery Sequence
1. Stop further changes immediately.
2. Rollback the latest automated commit.
3. Capture failing checks, stack traces, and changed files.
4. Create an issue with failure evidence and rollback reference.
5. Mark the change as blocked for human review.

## Retry Rules
- Max retries for the same target method: 1 automated retry.
- Retry must use a smaller, more localized patch.
- If retry fails, escalation is mandatory and automation stops for that target.
