# Evolution Agent Control Loop

1. Observe
- Collect percepts from commit history, coverage trend, static analysis, and test reports.
- Identify candidate methods for safe refactoring.

2. Decide
- Evaluate risk score and compatibility constraints.
- Select exactly one target method per cycle for bounded change size.

3. Act
- Create a dedicated branch.
- Apply refactor using the system prompt.
- Commit the patch.

4. Verify
- Run compile, unit, regression, and static-analysis checks.
- Compare outcomes with baseline.

5. Heal or Promote
- If all checks pass: open PR with evidence.
- If any check fails: rollback commit, open issue with evidence, and request human review.

6. Learn
- Record failure patterns and successful transformations for future cycles.
