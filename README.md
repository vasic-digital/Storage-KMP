# Storage-KMP

Cross-platform Kotlin Multiplatform library providing network storage service interfaces and model types for cloud/network storage protocols.

## Overview

Storage-KMP defines the core abstractions for network storage operations:

- **NetworkStorageService** interface for connect/disconnect, file CRUD, search, and quota operations
- **StorageDocument** model with file type detection and path utilities
- **StorageOperation** model with progress tracking for uploads/downloads
- **StorageInfo** and **StorageQuota** models with formatted display
- **PlatformFileIO** interface for platform-specific file operations
- **ProtocolInfo** for describing protocol implementation tiers

## Supported Platforms

| Platform | Target |
|----------|--------|
| Android | android |
| Desktop | jvm |
| iOS | iosArm64, iosSimulatorArm64, iosX64 |
| Web | wasmJs |

## Installation

Add as a composite build in your project's `settings.gradle.kts`:

```kotlin
includeBuild("Storage-KMP")
```

Then add the dependency:

```kotlin
implementation("digital.vasic.storage:shared")
```

## Quick Start

```kotlin
import digital.vasic.storage.*

// Implement the interface for your protocol
class MyStorageService : NetworkStorageService {
    override val isOnline: Boolean get() = true
    override suspend fun connect(): Result<Unit> = Result.success(Unit)
    // ...
}

// Use storage models
val doc = StorageDocument(
    id = "1",
    name = "notes.md",
    path = "/docs/notes.md",
    sizeBytes = 1024L,
    isDirectory = false
)
println(doc.formattedSize) // "1.0 KB"
println(doc.isTextFile)    // true
```

## Building

```bash
./gradlew desktopTest    # Run tests
./gradlew build          # Build all targets
```

## License

Apache-2.0
