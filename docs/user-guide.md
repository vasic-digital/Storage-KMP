# Storage-KMP User Guide

## Getting Started

Storage-KMP provides the interfaces and model types you need to build network storage integrations in Kotlin Multiplatform projects.

### Adding to Your Project

In `settings.gradle.kts`:

```kotlin
includeBuild("Storage-KMP")
```

In your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("digital.vasic.storage:shared")
}
```

## Core Concepts

### NetworkStorageService

The central interface defining all storage operations:

```kotlin
interface NetworkStorageService {
    val isOnline: Boolean
    val rootPath: String get() = "/"

    suspend fun connect(): Result<Unit>
    suspend fun disconnect(): Result<Unit>
    suspend fun testConnection(): Result<Boolean>

    fun listFiles(path: String = "/"): Flow<Result<List<StorageDocument>>>
    suspend fun downloadFile(remotePath: String, localPath: String): Flow<StorageOperation>
    suspend fun uploadFile(localPath: String, remotePath: String): Flow<StorageOperation>
    suspend fun deleteFile(remotePath: String): Result<Unit>
    suspend fun createFolder(remotePath: String): Result<StorageDocument>
    suspend fun moveFile(fromPath: String, toPath: String): Result<StorageDocument>
    suspend fun copyFile(fromPath: String, toPath: String): Result<StorageDocument>

    suspend fun getFileInfo(remotePath: String): Result<StorageDocument>
    suspend fun getQuota(): Result<StorageQuota>
    suspend fun search(query: String, path: String = "/"): Result<List<StorageDocument>>

    fun normalizePath(path: String): String
    fun joinPath(base: String, child: String): String
    fun parentPath(path: String): String
}
```

### StorageDocument

Represents a file or folder with metadata:

```kotlin
val doc = StorageDocument(
    id = "abc123",
    name = "report.pdf",
    path = "/docs/report.pdf",
    sizeBytes = 2_500_000L,
    isDirectory = false,
    mimeType = "application/pdf",
    lastModifiedMillis = System.currentTimeMillis()
)

doc.formattedSize   // "2.4 MB"
doc.extension       // "pdf"
doc.isTextFile      // false
doc.isImageFile     // false
doc.isInPath("/docs") // true
```

### StorageOperation

Tracks upload/download progress:

```kotlin
service.downloadFile("/remote/file.txt", "/local/file.txt")
    .collect { operation ->
        println("${operation.type}: ${operation.progressPercent}%")
        when (operation.status) {
            OperationStatus.COMPLETED -> println("Done!")
            OperationStatus.FAILED -> println("Error: ${operation.error}")
            else -> {} // IN_PROGRESS
        }
    }
```

### StorageType

Enum of supported protocols:

| Type | Display Name | Default Port |
|------|-------------|-------------|
| WEBDAV | WebDAV | 443 |
| FTP | FTP | 21 |
| SFTP | SFTP | 22 |
| DROPBOX | Dropbox | 443 |
| GOOGLE_DRIVE | Google Drive | 443 |
| ONEDRIVE | OneDrive | 443 |
| GIT | Git | 443 |
| S3 | S3 | 443 |

### PlatformFileIO

Interface for platform-specific file operations that implementations must provide:

```kotlin
interface PlatformFileIO {
    suspend fun readFileBytes(path: String): ByteArray
    suspend fun writeFileBytes(path: String, data: ByteArray)
    suspend fun fileExists(path: String): Boolean
    suspend fun fileSize(path: String): Long
    suspend fun ensureParentDirectories(path: String)
}
```

## Implementing a Storage Service

Create a class implementing `NetworkStorageService`:

```kotlin
class WebDavStorageService(
    private val config: WebDavConfig,
    private val fileIO: PlatformFileIO
) : NetworkStorageService {

    private var connected = false

    override val isOnline: Boolean get() = connected

    override suspend fun connect(): Result<Unit> = runCatching {
        // Establish connection using config
        connected = true
    }

    // Implement remaining methods...
}
```

## Error Handling

All operations return `Result<T>`. Use Kotlin's Result API:

```kotlin
service.connect()
    .onSuccess { println("Connected") }
    .onFailure { println("Failed: ${it.message}") }

val files = service.listFiles("/docs").first()
    .getOrElse { emptyList() }
```
