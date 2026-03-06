package digital.vasic.storage

/**
 * Implementation tier for protocol services.
 */
enum class ImplementationTier {
    STUBBED,
    PARTIALLY_IMPLEMENTED,
    SUBSTANTIALLY_IMPLEMENTED,
    FULLY_IMPLEMENTED
}

/**
 * Metadata about a protocol service implementation.
 */
data class ProtocolInfo(
    val protocolName: String,
    val tier: ImplementationTier,
    val serviceClass: String,
    val usesHttpClient: Boolean,
    val authMechanism: String,
    val notes: String
)
