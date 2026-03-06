# Storage-KMP API Reference

## Package: `digital.vasic.storage`

### Interfaces

#### `NetworkStorageService`

Core interface for network storage operations.

**Properties:**
| Property | Type | Description |
|----------|------|-------------|
| `isOnline` | `Boolean` | Whether the service is currently connected |
| `rootPath` | `String` | Root path for the storage service (default: `"/"`) |

**Connection Methods:**
| Method | Return Type | Description |
|--------|-------------|-------------|
| `connect()` | `Result<Unit>` | Establish connection to storage |
| `disconnect()` | `Result<Unit>` | Close connection |
| `testConnection()` | `Result<Boolean>` | Test if connection is possible |

**File Operations:**
| Method | Return Type | Description |
|--------|-------------|-------------|
| `listFiles(path)` | `Flow<Result<List<StorageDocument>>>` | List files in directory |
| `downloadFile(remotePath, localPath)` | `Flow<StorageOperation>` | Download with progress |
| `uploadFile(localPath, remotePath)` | `Flow<StorageOperation>` | Upload with progress |
| `deleteFile(remotePath)` | `Result<Unit>` | Delete file or folder |
| `createFolder(remotePath)` | `Result<StorageDocument>` | Create directory |
| `moveFile(from, to)` | `Result<StorageDocument>` | Move/rename |
| `copyFile(from, to)` | `Result<StorageDocument>` | Copy file |

**Query Methods:**
| Method | Return Type | Description |
|--------|-------------|-------------|
| `getFileInfo(remotePath)` | `Result<StorageDocument>` | Get file metadata |
| `getQuota()` | `Result<StorageQuota>` | Get storage quota |
| `search(query, path)` | `Result<List<StorageDocument>>` | Search for files |

**Path Utilities:**
| Method | Return Type | Description |
|--------|-------------|-------------|
| `normalizePath(path)` | `String` | Normalize path separators |
| `joinPath(base, child)` | `String` | Join path components |
| `parentPath(path)` | `String` | Get parent directory path |

---

#### `PlatformFileIO`

Platform-specific file I/O operations.

| Method | Return Type | Description |
|--------|-------------|-------------|
| `readFileBytes(path)` | `ByteArray` | Read file contents |
| `writeFileBytes(path, data)` | `Unit` | Write file contents |
| `fileExists(path)` | `Boolean` | Check file existence |
| `fileSize(path)` | `Long` | Get file size in bytes |
| `ensureParentDirectories(path)` | `Unit` | Create parent dirs |

---

### Enums

#### `StorageType`

| Value | `displayName` | `defaultPort` |
|-------|--------------|--------------|
| `WEBDAV` | "WebDAV" | 443 |
| `FTP` | "FTP" | 21 |
| `SFTP` | "SFTP" | 22 |
| `DROPBOX` | "Dropbox" | 443 |
| `GOOGLE_DRIVE` | "Google Drive" | 443 |
| `ONEDRIVE` | "OneDrive" | 443 |
| `GIT` | "Git" | 443 |
| `S3` | "S3" | 443 |

#### `OperationStatus`

`PENDING`, `IN_PROGRESS`, `COMPLETED`, `FAILED`, `CANCELLED`, `PAUSED`

#### `OperationType`

`UPLOAD`, `DOWNLOAD`, `DELETE`, `MOVE`, `COPY`, `CREATE_FOLDER`, `LIST`, `SYNC`

---

### Data Classes

#### `StorageDocument`

| Property | Type | Description |
|----------|------|-------------|
| `id` | `String` | Unique identifier |
| `name` | `String` | File name |
| `path` | `String` | Full remote path |
| `sizeBytes` | `Long` | Size in bytes |
| `isDirectory` | `Boolean` | Is folder |
| `mimeType` | `String?` | MIME type |
| `lastModifiedMillis` | `Long?` | Last modified timestamp |

**Computed Properties:** `formattedSize`, `extension`, `isTextFile`, `isImageFile`

**Methods:** `isInPath(path)`, `isDirectChildOf(path)`

#### `StorageOperation`

| Property | Type | Description |
|----------|------|-------------|
| `id` | `String` | Operation ID |
| `type` | `OperationType` | Operation type |
| `status` | `OperationStatus` | Current status |
| `remotePath` | `String` | Remote file path |
| `localPath` | `String?` | Local file path |
| `totalBytes` | `Long` | Total size |
| `transferredBytes` | `Long` | Bytes transferred |
| `error` | `String?` | Error message |

**Computed Properties:** `progressPercent`, `isComplete`, `isFailed`

#### `StorageInfo`

| Property | Type | Description |
|----------|------|-------------|
| `displayName` | `String` | Human-readable name |
| `storageType` | `StorageType` | Protocol type |
| `host` | `String` | Server host |
| `isConnected` | `Boolean` | Connection status |

#### `StorageQuota`

| Property | Type | Description |
|----------|------|-------------|
| `totalBytes` | `Long` | Total capacity |
| `usedBytes` | `Long` | Used space |

**Computed Properties:** `availableBytes`, `usagePercent`, `formattedTotal`, `formattedUsed`, `formattedAvailable`

#### `ProtocolInfo`

| Property | Type | Description |
|----------|------|-------------|
| `storageType` | `StorageType` | Protocol type |
| `displayName` | `String` | Human-readable name |
| `description` | `String` | Protocol description |
| `tier` | `ImplementationTier` | Implementation maturity |

#### `ImplementationTier`

`PRODUCTION`, `BETA`, `ALPHA`, `PLANNED`
