package digital.vasic.storage

import kotlin.test.*

class StorageTypeTest {
    @Test fun testAllTypes() { assertEquals(8, StorageType.entries.size) }
    @Test fun testDisplayNames() {
        assertEquals("WebDAV", StorageType.WEBDAV.displayName)
        assertEquals("Google Drive", StorageType.GOOGLE_DRIVE.displayName)
        assertEquals("Git", StorageType.GIT.displayName)
    }
    @Test fun testDefaultPorts() {
        assertEquals(443, StorageType.WEBDAV.defaultPort)
        assertEquals(21, StorageType.FTP.defaultPort)
        assertEquals(22, StorageType.SFTP.defaultPort)
        assertEquals(445, StorageType.SMB.defaultPort)
        assertEquals(22, StorageType.GIT.defaultPort)
    }
}

class StorageDocumentTest {
    @Test fun testCreation() {
        val doc = StorageDocument(id = "d1", name = "test.txt", path = "/docs/test.txt", size = 1024)
        assertEquals("d1", doc.id)
        assertEquals("txt", doc.extension)
        assertEquals("/docs", doc.parentPath)
        assertFalse(doc.isFolder)
    }
    @Test fun testFolder() {
        val doc = StorageDocument(id = "d1", name = "docs", path = "/docs", isFolder = true)
        assertTrue(doc.isFolder)
        assertEquals("—", doc.formattedSize)
    }
    @Test fun testFormattedSize() {
        assertEquals("512B", StorageDocument(id = "1", name = "a", size = 512).formattedSize)
        assertEquals("1KB", StorageDocument(id = "1", name = "a", size = 1024).formattedSize)
        assertEquals("1MB", StorageDocument(id = "1", name = "a", size = 1024 * 1024).formattedSize)
        assertEquals("1GB", StorageDocument(id = "1", name = "a", size = 1024L * 1024 * 1024).formattedSize)
    }
    @Test fun testIsInPath() {
        val doc = StorageDocument(id = "d1", name = "test.txt", path = "/docs/sub/test.txt")
        assertTrue(doc.isInPath("/docs"))
        assertTrue(doc.isInPath("/docs/sub"))
        assertFalse(doc.isInPath("/other"))
    }
    @Test fun testIsDirectChildOf() {
        val doc = StorageDocument(id = "d1", name = "test.txt", path = "/docs/test.txt")
        assertTrue(doc.isDirectChildOf("/docs"))
        assertFalse(doc.isDirectChildOf("/docs/sub"))
    }
    @Test fun testMetadata() {
        val doc = StorageDocument(id = "d1", name = "a", metadata = mapOf("k" to "v"))
        assertEquals("v", doc.metadata["k"])
    }
    @Test fun testExtensionEmpty() {
        val doc = StorageDocument(id = "d1", name = "Makefile")
        assertEquals("", doc.extension)
    }
}

class OperationStatusTest {
    @Test fun testAllStatuses() { assertEquals(6, OperationStatus.entries.size) }
}

class OperationTypeTest {
    @Test fun testAllTypes() { assertEquals(8, OperationType.entries.size) }
}

class StorageOperationTest {
    @Test fun testCreation() {
        val op = StorageOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t.txt", createdAt = 1000)
        assertEquals(OperationStatus.PENDING, op.status)
        assertFalse(op.isRunning)
        assertFalse(op.isCompleted)
    }
    @Test fun testIsRunning() {
        val op = StorageOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", status = OperationStatus.IN_PROGRESS)
        assertTrue(op.isRunning)
    }
    @Test fun testCanRetry() {
        val op = StorageOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", status = OperationStatus.FAILED, retryCount = 1)
        assertTrue(op.canRetry)
    }
    @Test fun testCannotRetryExhausted() {
        val op = StorageOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", status = OperationStatus.FAILED, retryCount = 3)
        assertFalse(op.canRetry)
    }
    @Test fun testProgressPercentage() {
        val op = StorageOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", progress = 0.75)
        assertEquals(75, op.progressPercentage)
    }
}

class StorageInfoTest {
    @Test fun testCreation() {
        val info = StorageInfo(id = "1", name = "Test", type = StorageType.WEBDAV, location = "https://dav.test.com")
        assertEquals("1", info.id)
        assertFalse(info.isOnline)
        assertTrue(info.isEnabled)
    }
}

class StorageQuotaTest {
    @Test fun testCreation() {
        val quota = StorageQuota(totalSpace = 1000, usedSpace = 300, availableSpace = 700, usagePercentage = 0.3, isFull = false, isLowOnSpace = false)
        assertEquals(1000L, quota.totalSpace)
        assertEquals(700L, quota.availableSpace)
    }
    @Test fun testFormattedSpace() {
        val quota = StorageQuota(totalSpace = 1024L * 1024 * 1024, usedSpace = 512L * 1024 * 1024, availableSpace = 512L * 1024 * 1024, usagePercentage = 0.5, isFull = false, isLowOnSpace = false)
        assertEquals("1GB", quota.formattedTotalSpace)
        assertEquals("512MB", quota.formattedUsedSpace)
    }
    @Test fun testUsagePercentageString() {
        val quota = StorageQuota(totalSpace = 1000, usedSpace = 750, availableSpace = 250, usagePercentage = 0.75, isFull = false, isLowOnSpace = false)
        assertEquals("75%", quota.usagePercentageString)
    }
    @Test fun testIsFull() {
        val quota = StorageQuota(totalSpace = 100, usedSpace = 100, availableSpace = 0, usagePercentage = 1.0, isFull = true, isLowOnSpace = true)
        assertTrue(quota.isFull)
        assertTrue(quota.isLowOnSpace)
    }
}

class ImplementationTierTest {
    @Test fun testAllTiers() { assertEquals(4, ImplementationTier.entries.size) }
}

class ProtocolInfoTest {
    @Test fun testCreation() {
        val info = ProtocolInfo(
            protocolName = "WebDAV",
            tier = ImplementationTier.FULLY_IMPLEMENTED,
            serviceClass = "digital.vasic.storage.WebDavService",
            usesHttpClient = true,
            authMechanism = "Basic",
            notes = "Full CRUD"
        )
        assertEquals("WebDAV", info.protocolName)
        assertEquals(ImplementationTier.FULLY_IMPLEMENTED, info.tier)
        assertTrue(info.usesHttpClient)
    }
}
