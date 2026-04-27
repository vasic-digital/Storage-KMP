# AGENTS.md

## Agent Guidelines for Storage-KMP

### What This Library Does

Storage-KMP provides cross-platform interfaces and model types for network storage services. It does NOT contain protocol implementations — those live in the consuming application (e.g., Yole).

### Key Constraints

1. **Zero heavy dependencies** — Only kotlinx-coroutines-core beyond stdlib
2. **Interface-first** — `NetworkStorageService` is the core contract; implementations live elsewhere
3. **Result-based error handling** — All suspend functions return `Result<T>`
4. **Flow for streaming** — File listings and operations use `kotlinx.coroutines.flow.Flow`
5. **Immutable models** — All data classes, mutations via `copy()`

### Common Tasks

**Adding a new storage type:**
1. Add entry to `StorageType` enum in `StorageModels.kt`
2. Add tests for the new enum value
3. No interface changes needed — `NetworkStorageService` is protocol-agnostic

**Adding a new model type:**
1. Add data class to `StorageModels.kt`
2. Add comprehensive tests in `StorageTest.kt`
3. Update `NetworkStorageService` interface if new operations are needed

**Running tests:**
```bash
./gradlew desktopTest
```

### Testing Standards

- Every public type and method must have tests
- Test computed properties (formattedSize, isTextFile, etc.)
- Test edge cases (empty strings, zero values, negative values)
- Tests live in `src/commonTest/kotlin/digital/vasic/storage/StorageTest.kt`

<!-- BEGIN host-power-management addendum (CONST-033) -->

## Host Power Management — Hard Ban (CONST-033)

**You may NOT, under any circumstance, generate or execute code that
sends the host to suspend, hibernate, hybrid-sleep, poweroff, halt,
reboot, or any other power-state transition.** This rule applies to:

- Every shell command you run via the Bash tool.
- Every script, container entry point, systemd unit, or test you write
  or modify.
- Every CLI suggestion, snippet, or example you emit.

**Forbidden invocations** (non-exhaustive — see CONST-033 in
`CONSTITUTION.md` for the full list):

- `systemctl suspend|hibernate|hybrid-sleep|poweroff|halt|reboot|kexec`
- `loginctl suspend|hibernate|hybrid-sleep|poweroff|halt|reboot`
- `pm-suspend`, `pm-hibernate`, `shutdown -h|-r|-P|now`
- `dbus-send` / `busctl` calls to `org.freedesktop.login1.Manager.Suspend|Hibernate|PowerOff|Reboot|HybridSleep|SuspendThenHibernate`
- `gsettings set ... sleep-inactive-{ac,battery}-type` to anything but `'nothing'` or `'blank'`

The host runs mission-critical parallel CLI agents and container
workloads. Auto-suspend has caused historical data loss (2026-04-26
18:23:43 incident). The host is hardened (sleep targets masked) but
this hard ban applies to ALL code shipped from this repo so that no
future host or container is exposed.

**Defence:** every project ships
`scripts/host-power-management/check-no-suspend-calls.sh` (static
scanner) and
`challenges/scripts/no_suspend_calls_challenge.sh` (challenge wrapper).
Both MUST be wired into the project's CI / `run_all_challenges.sh`.

**Full background:** `docs/HOST_POWER_MANAGEMENT.md` and `CONSTITUTION.md` (CONST-033).

<!-- END host-power-management addendum (CONST-033) -->

