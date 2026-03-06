package digital.vasic.storage

/**
 * Storage protocol types.
 */
enum class StorageType {
    WEBDAV, FTP, SFTP, SMB, GOOGLE_DRIVE, DROPBOX, ONEDRIVE, GIT;

    val displayName: String get() = when (this) {
        WEBDAV -> "WebDAV"; FTP -> "FTP"; SFTP -> "SFTP"; SMB -> "SMB/CIFS"
        GOOGLE_DRIVE -> "Google Drive"; DROPBOX -> "Dropbox"; ONEDRIVE -> "OneDrive"; GIT -> "Git"
    }

    val defaultPort: Int get() = when (this) {
        WEBDAV -> 443; FTP -> 21; SFTP -> 22; SMB -> 445
        GOOGLE_DRIVE -> 443; DROPBOX -> 443; ONEDRIVE -> 443; GIT -> 22
    }
}

/**
 * Represents a file or folder on network storage.
 */
data class StorageDocument(
    val id: String,
    val name: String,
    val path: String = "",
    val isFolder: Boolean = false,
    val size: Long = 0L,
    val lastModified: Long? = null,
    val contentType: String? = null,
    val extension: String = name.substringAfterLast('.', "").ifEmpty { "" },
    val parentPath: String = path.substringBeforeLast('/', "").ifEmpty { "/" },
    val isReadOnly: Boolean = false,
    val metadata: Map<String, String> = emptyMap(),
    val storageId: String = ""
) {
    val formattedSize: String get() = when {
        isFolder -> "—"
        size < 1024 -> "${size}B"
        size < 1024 * 1024 -> "${size / 1024}KB"
        size < 1024 * 1024 * 1024 -> "${size / (1024 * 1024)}MB"
        else -> "${size / (1024 * 1024 * 1024)}GB"
    }

    fun isInPath(path: String): Boolean {
        val np = path.trimEnd('/')
        return this.path.trimEnd('/').startsWith("$np/")
    }

    fun isDirectChildOf(path: String): Boolean {
        val np = path.trimEnd('/')
        return this.path.trimEnd('/').substringBeforeLast('/', "") == np
    }
}

/**
 * Operation status.
 */
enum class OperationStatus {
    PENDING, IN_PROGRESS, COMPLETED, FAILED, PAUSED, CANCELLED
}

/**
 * Operation type.
 */
enum class OperationType {
    UPLOAD, DOWNLOAD, DELETE, CREATE_FOLDER, RENAME, MOVE, COPY, SYNC
}

/**
 * Represents a storage operation with progress tracking.
 */
data class StorageOperation(
    val id: Long,
    val type: OperationType,
    val remotePath: String,
    val localPath: String? = null,
    val status: OperationStatus = OperationStatus.PENDING,
    val progress: Double = 0.0,
    val totalSize: Long = 0L,
    val bytesTransferred: Long = 0L,
    val createdAt: Long = 0L,
    val startedAt: Long? = null,
    val completedAt: Long? = null,
    val error: String? = null,
    val retryCount: Int = 0,
    val maxRetries: Int = 3
) {
    val isRunning: Boolean get() = status == OperationStatus.IN_PROGRESS
    val isCompleted: Boolean get() = status == OperationStatus.COMPLETED
    val hasFailed: Boolean get() = status == OperationStatus.FAILED
    val canRetry: Boolean get() = hasFailed && retryCount < maxRetries
    val progressPercentage: Int get() = (progress * 100).toInt()
}

/**
 * Storage information.
 */
data class StorageInfo(
    val id: String,
    val name: String,
    val type: StorageType,
    val location: String,
    val isOnline: Boolean = false,
    val isEnabled: Boolean = true
)

/**
 * Storage quota information.
 */
data class StorageQuota(
    val totalSpace: Long,
    val usedSpace: Long,
    val availableSpace: Long,
    val usagePercentage: Double,
    val isFull: Boolean,
    val isLowOnSpace: Boolean,
    val metadata: Map<String, String> = emptyMap()
) {
    val formattedTotalSpace: String get() = formatBytes(totalSpace)
    val formattedUsedSpace: String get() = formatBytes(usedSpace)
    val formattedAvailableSpace: String get() = formatBytes(availableSpace)
    val usagePercentageString: String get() = "${(usagePercentage * 100).toInt()}%"

    private fun formatBytes(bytes: Long): String = when {
        bytes < 1024 -> "${bytes}B"
        bytes < 1024 * 1024 -> "${bytes / 1024}KB"
        bytes < 1024 * 1024 * 1024 -> "${bytes / (1024 * 1024)}MB"
        else -> "${bytes / (1024 * 1024 * 1024)}GB"
    }
}
