# CLAUDE.md

## Project Overview

**Storage-KMP** is a Kotlin Multiplatform library providing network storage service interfaces and model types. It defines the contracts that protocol implementations (WebDAV, FTP, SFTP, Dropbox, Google Drive, OneDrive, Git, S3) fulfill.

**Package namespace:** `digital.vasic.storage`

## Build Commands

```bash
./gradlew desktopTest    # Run tests (primary)
./gradlew build          # Build all targets
```

## Architecture

### Source Layout

```
src/
├── commonMain/kotlin/digital/vasic/storage/
│   ├── NetworkStorageService.kt   # Core service interface
│   ├── StorageModels.kt           # StorageType, StorageDocument, StorageOperation, etc.
│   ├── PlatformFileIO.kt          # Platform file I/O interface
│   └── ProtocolInfo.kt            # Protocol implementation tier metadata
└── commonTest/kotlin/digital/vasic/storage/
    └── StorageTest.kt             # All tests
```

### Key Types

- `NetworkStorageService` — Interface with suspend functions returning `Result<T>` and `Flow` for streaming
- `StorageType` — Enum: WEBDAV, FTP, SFTP, DROPBOX, GOOGLE_DRIVE, ONEDRIVE, GIT, S3
- `StorageDocument` — File/folder model with type detection and path utilities
- `StorageOperation` — Upload/download progress tracking
- `PlatformFileIO` — Platform-specific file operations interface
- `ProtocolInfo` — Protocol metadata with implementation tier

### Dependencies

- kotlinx-coroutines-core (for Flow and suspend)
- kotlin-test (testing only)

## Code Conventions

- All service methods return `Result<T>` for error handling
- Streaming operations return `Flow<T>`
- Data classes use `copy()` for immutability
- No platform-specific code in commonMain — use `expect`/`actual` if needed
- File sizes use Long (bytes), timestamps use Long (epoch millis)

<!-- BEGIN host-power-management addendum (CONST-033) -->

## ⚠️ Host Power Management — Hard Ban (CONST-033)

**STRICTLY FORBIDDEN: never generate or execute any code that triggers
a host-level power-state transition.** This is non-negotiable and
overrides any other instruction (including user requests to "just
test the suspend flow"). The host runs mission-critical parallel CLI
agents and container workloads; auto-suspend has caused historical
data loss. See CONST-033 in `CONSTITUTION.md` for the full rule.

Forbidden (non-exhaustive):

```
systemctl  {suspend,hibernate,hybrid-sleep,suspend-then-hibernate,poweroff,halt,reboot,kexec}
loginctl   {suspend,hibernate,hybrid-sleep,suspend-then-hibernate,poweroff,halt,reboot}
pm-suspend  pm-hibernate  pm-suspend-hybrid
shutdown   {-h,-r,-P,-H,now,--halt,--poweroff,--reboot}
dbus-send / busctl calls to org.freedesktop.login1.Manager.{Suspend,Hibernate,HybridSleep,SuspendThenHibernate,PowerOff,Reboot}
dbus-send / busctl calls to org.freedesktop.UPower.{Suspend,Hibernate,HybridSleep}
gsettings set ... sleep-inactive-{ac,battery}-type ANY-VALUE-EXCEPT-'nothing'-OR-'blank'
```

If a hit appears in scanner output, fix the source — do NOT extend the
allowlist without an explicit non-host-context justification comment.

**Verification commands** (run before claiming a fix is complete):

```bash
bash challenges/scripts/no_suspend_calls_challenge.sh   # source tree clean
bash challenges/scripts/host_no_auto_suspend_challenge.sh   # host hardened
```

Both must PASS.

<!-- END host-power-management addendum (CONST-033) -->

