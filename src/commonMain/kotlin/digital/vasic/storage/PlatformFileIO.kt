package digital.vasic.storage

/**
 * Platform-abstracted file I/O for network protocol services.
 */
interface PlatformFileIO {
    suspend fun readFileBytes(path: String): Result<ByteArray>
    suspend fun writeFileBytes(path: String, bytes: ByteArray): Result<Unit>
    suspend fun fileExists(path: String): Boolean
    suspend fun fileSize(path: String): Long
    suspend fun ensureParentDirectories(path: String): Result<Unit>
}
