# AGENTS.md

## Agent Guidelines for Storage-KMP

### What This Library Does

Storage-KMP provides cross-platform interfaces and model types for network storage services. It does NOT contain protocol implementations — those live in the consuming application (e.g., Yole).

### Key Constraints

1. **Zero heavy dependencies** — Only kotlinx-coroutines-core beyond stdlib
2. **Interface-first** — `NetworkStorageService` is the core contract; implementations live elsewhere
3. **Result-based error handling** — All suspend functions return `Result<T>`
4. **Flow for streaming** — File listings and operations use `kotlinx.coroutines.flow.Flow`
5. **Immutable models** — All data classes, mutations via `copy()`

### Common Tasks

**Adding a new storage type:**
1. Add entry to `StorageType` enum in `StorageModels.kt`
2. Add tests for the new enum value
3. No interface changes needed — `NetworkStorageService` is protocol-agnostic

**Adding a new model type:**
1. Add data class to `StorageModels.kt`
2. Add comprehensive tests in `StorageTest.kt`
3. Update `NetworkStorageService` interface if new operations are needed

**Running tests:**
```bash
./gradlew desktopTest
```

### Testing Standards

- Every public type and method must have tests
- Test computed properties (formattedSize, isTextFile, etc.)
- Test edge cases (empty strings, zero values, negative values)
- Tests live in `src/commonTest/kotlin/digital/vasic/storage/StorageTest.kt`
