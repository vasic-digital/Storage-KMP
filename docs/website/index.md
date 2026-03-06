# Storage-KMP

## Cross-Platform Storage Interfaces for Kotlin Multiplatform

Storage-KMP provides a unified interface for network storage operations across Android, Desktop, iOS, and Web platforms.

### Features

- **Protocol-Agnostic Interface** — One interface for WebDAV, FTP, SFTP, Dropbox, Google Drive, OneDrive, Git, and S3
- **Progress Tracking** — Flow-based upload/download with real-time progress
- **Type-Safe Models** — StorageDocument, StorageOperation, StorageQuota with computed properties
- **Result-Based Errors** — Explicit error handling without exceptions
- **Minimal Dependencies** — Only kotlinx-coroutines-core beyond Kotlin stdlib
- **Full KMP Support** — Android, Desktop (JVM), iOS, Web (Wasm)

### Getting Started

```kotlin
// settings.gradle.kts
includeBuild("Storage-KMP")

// build.gradle.kts
dependencies {
    implementation("digital.vasic.storage:shared")
}
```

### Documentation

- [User Guide](docs/user-guide.md)
- [API Reference](docs/api-reference.md)
- [Architecture](docs/architecture.md)

### Related Libraries

| Library | Purpose |
|---------|---------|
| [Config-KMP](https://github.com/vasic-digital/Config-KMP) | Storage configuration types |
| [Database-KMP](https://github.com/vasic-digital/Database-KMP) | Storage metadata database |
| [Document-KMP](https://github.com/vasic-digital/Document-KMP) | Document model types |

### License

Apache-2.0
