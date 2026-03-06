package digital.vasic.storage

import kotlinx.coroutines.flow.Flow

/**
 * Main interface for network storage operations.
 * Provides a unified API for working with different network storage types.
 */
interface NetworkStorageService {

    val isOnline: Boolean
    val rootPath: String get() = "/"

    suspend fun connect(): Result<Unit>
    suspend fun disconnect(): Result<Unit>
    suspend fun testConnection(): Result<Boolean>

    // File operations
    fun listFiles(path: String = "/"): Flow<Result<List<StorageDocument>>>
    suspend fun downloadFile(remotePath: String, localPath: String): Flow<StorageOperation>
    suspend fun uploadFile(localPath: String, remotePath: String): Flow<StorageOperation>
    suspend fun deleteFile(remotePath: String): Result<Unit>
    suspend fun createFolder(remotePath: String): Result<StorageDocument>
    suspend fun renameFile(remotePath: String, newName: String): Result<Unit>
    suspend fun moveFile(sourcePath: String, destinationPath: String): Result<StorageDocument>
    suspend fun copyFile(sourcePath: String, destinationPath: String): Result<Unit>
    suspend fun getFileInfo(remotePath: String): Result<StorageDocument>
    suspend fun exists(remotePath: String): Result<Boolean>

    // Operations management
    fun getActiveOperations(): Flow<List<StorageOperation>>
    suspend fun cancelOperation(operationId: Long): Result<Unit>
    suspend fun pauseOperation(operationId: Long): Result<Unit>
    suspend fun resumeOperation(operationId: Long): Result<Unit>

    // Storage info
    suspend fun getStorageInfo(): StorageInfo
    suspend fun getQuotaInfo(): Result<StorageQuota>

    // Search
    fun searchFiles(query: String, path: String? = null, includeContent: Boolean = false): Flow<Result<List<StorageDocument>>>

    // Path utilities
    fun getParentPath(remotePath: String): String?
    fun validatePath(remotePath: String): Result<Unit>
}
