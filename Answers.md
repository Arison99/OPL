# Software Evolution & Architecture - Answers

## Part 2: Impact Analysis & Refactoring

### 1. Dependency Mapping

`OrderProcessor` has these explicit dependencies:

1. `DatabaseConnection` via direct field instantiation: `private DatabaseConnection db = new DatabaseConnection();`
2. `Order` via the method parameter: `public void process(Order order)`
3. `EmailSender` via the static method call: `EmailSender.send(...)`
4. `String` through `order.getCustomerEmail()` and the email message literal

### 2. Impact Prediction

If `DatabaseConnection` changes to require a `TenantID` string in its constructor, the directly broken line in `OrderProcessor` is:

```java
private DatabaseConnection db = new DatabaseConnection();
```

That line no longer matches the constructor signature. As a result, the later calls to `db.saveHighValueOrder(order);` and `db.saveRegularOrder(order);` also become unusable until the field initialization is corrected.

### 3. Refactoring Using DIP

```java
public interface IOrderRepository {
    void saveHighValueOrder(Order order);
    void saveRegularOrder(Order order);
}

public class DatabaseOrderRepository implements IOrderRepository {
    private final DatabaseConnection db;

    public DatabaseOrderRepository(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public void saveHighValueOrder(Order order) {
        db.saveHighValueOrder(order);
    }

    @Override
    public void saveRegularOrder(Order order) {
        db.saveRegularOrder(order);
    }
}

public class OrderProcessor {
    private final IOrderRepository repository;

    public OrderProcessor(IOrderRepository repository) {
        this.repository = repository;
    }

    public void process(Order order) {
        if (order.getAmount() > 1000) {
            repository.saveHighValueOrder(order);
        } else {
            repository.saveRegularOrder(order);
        }

        EmailSender.send(order.getCustomerEmail(), "Order Processed");
    }
}
```

### 4. Post-Refactoring Impact Analysis

After refactoring, `OrderProcessor` depends only on the abstraction `IOrderRepository` instead of the concrete `DatabaseConnection` class. Future database constructor or implementation changes are isolated inside `DatabaseOrderRepository` and composition code, which reduces the blast radius from business logic classes to a narrow infrastructure layer.

## Part 3: Intelligent Agents in Software Evolution

### 1. Agent Control Loop

**Percepts**

1. Git commit history and pull request diffs
2. Build status and compiler errors
3. Unit, integration, and regression test results
4. Static analysis alerts and code smell reports
5. Test coverage reports
6. Dependency vulnerability alerts
7. Architecture rules or API compatibility checks

**Actions**

1. Create or modify source files
2. Refactor methods, classes, and imports
3. Update build files and dependency versions
4. Run formatters, linters, builds, and tests
5. Create branches, commits, and pull requests
6. Revert or amend an automated change when validation fails
7. Open issues or request human review for risky changes

**Example control loop**

1. Observe repository state, quality signals, and recent changes.
2. Detect a candidate evolution task such as duplication, deprecation, or a static-analysis finding.
3. Plan a minimal safe refactoring constrained by API compatibility rules.
4. Apply the change on a branch.
5. Run verification checks.
6. If all checks pass, prepare a pull request; if not, rollback or revise and retry within a bounded limit.
7. Escalate to a human when confidence is low or failures persist.

### 2. System Prompt for Safe Refactoring

```text
You are a software evolution agent operating on a production codebase.

Your task is to refactor a long, complex method into smaller private helper methods while preserving behavior exactly.

Hard constraints:
- Do not introduce breaking changes.
- Do not change any public class names, public method names, public method signatures, return types, exceptions, or package structure.
- Preserve all existing public APIs exactly as they are.
- Do not remove validations, side effects, logging, or error handling unless they are moved intact into helper methods.
- Prefer minimal diffs and keep changes localized.
- If behavior is ambiguous, stop and flag the ambiguity instead of guessing.

Required workflow:
- Read the target method and identify logical subsections.
- Extract only cohesive blocks into private helper methods with clear names.
- Keep call order, branching logic, and data flow unchanged.
- After refactoring, run or propose the narrowest available tests, then broader regression checks.
- If any test fails, revert the risky change or produce a smaller alternative.

Success condition:
The code is easier to read, all existing public APIs remain unchanged, and all validation checks pass.
```

### 3. Self-Healing Evaluation

The agent should run a validation pipeline immediately after each automated refactoring: compile, execute unit tests, execute regression tests, and compare baseline results against post-change results. If a regression test fails, the agent should mark the change as unsafe, revert the last refactoring commit or restore the last known-good baseline, analyze the failing diff, and either attempt a smaller bounded repair or escalate the failure to a human reviewer with the test evidence attached.

## Part 4: Integrated Case Study

### 1. CM Baselines and Production Safety

Automated configuration management baselines protect production by defining a known-good combination of code, dependency versions, build settings, and test results that must be matched before release. If the logging dependency upgrade causes deprecated API usage in 45 classes, the agent can compare the proposed change against the last approved baseline, detect compatibility drift during CI, and block promotion until all impacted classes are fixed and the new baseline is formally approved.

### 2. 3-Step Hybrid Human + Agent Workflow

1. The intelligent agent scans the project after the dependency update, identifies all 45 usages of the deprecated logging method, groups them by call pattern, and produces an impact report with candidate replacements.
2. A human developer reviews the report, confirms the correct replacement strategy for each usage pattern, and approves any edge-case handling or API semantics that the agent should preserve.
3. The agent applies the approved refactorings across the 45 classes on a branch, runs the full validation suite, and opens a pull request for final human review before the updated dependency becomes the new CM baseline.