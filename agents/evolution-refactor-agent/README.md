# Evolution Refactor Agent

This folder contains LLM-ready agent artifacts for Assignment Task 3.

## Files
- `agent.yaml`: Agent manifest (percepts, actions, constraints, validation, failure strategy)
- `prompts/system.prompt.md`: System prompt for safe non-breaking refactoring
- `control-loop.md`: Perception-decision-action-verification loop
- `policies/self-healing-policy.md`: Regression response and rollback policy

## How To Use
1. Load `prompts/system.prompt.md` as the system instruction in your LLM wrapper.
2. Follow `control-loop.md` to execute one bounded refactor cycle.
3. Enforce `policies/self-healing-policy.md` after every automated patch.
4. Keep all evidence in PR/issue comments for auditability.
