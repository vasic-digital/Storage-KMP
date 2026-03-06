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
