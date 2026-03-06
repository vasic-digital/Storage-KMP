# Storage-KMP Architecture

## Design Philosophy

Storage-KMP follows an **interface-first** design. It defines contracts and models that protocol implementations fulfill, without coupling to any specific HTTP client, authentication library, or storage SDK.

## Layer Architecture

```
┌─────────────────────────────────────────────┐
│           Consuming Application              │
│  (e.g., Yole - provides implementations)     │
├─────────────────────────────────────────────┤
│           Storage-KMP (this library)         │
│  ┌─────────────────┐  ┌──────────────────┐  │
│  │ NetworkStorage   │  │ StorageModels    │  │
│  │ Service          │  │ (types, enums)   │  │
│  │ (interface)      │  │                  │  │
│  └─────────────────┘  └──────────────────┘  │
│  ┌─────────────────┐  ┌──────────────────┐  │
│  │ PlatformFileIO  │  │ ProtocolInfo     │  │
│  │ (interface)      │  │ (metadata)       │  │
│  └─────────────────┘  └──────────────────┘  │
├─────────────────────────────────────────────┤
│       kotlinx-coroutines-core                │
│       (Flow, suspend, Result)                │
└─────────────────────────────────────────────┘
```

## Key Design Decisions

### Result-Based Error Handling

All operations return `Result<T>` instead of throwing exceptions. This makes error handling explicit and composable across platforms.

### Flow for Streaming

File listings use `Flow<Result<List<StorageDocument>>>` to support incremental loading and real-time updates. Upload/download operations emit `Flow<StorageOperation>` for progress tracking.

### No Authentication in Scope

Authentication (OAuth2, basic auth, API keys) is deliberately excluded. Each consuming application handles auth according to its own requirements and security model.

### Self-Contained Enums

`StorageType` is defined locally rather than imported from Config-KMP. This keeps the module independent — consumers can map between enum types at integration boundaries.

### Epoch Millis for Timestamps

Timestamps use `Long` (epoch milliseconds) rather than `kotlinx.datetime.Instant` to avoid adding dependencies beyond coroutines.

## Module Dependencies

```
Storage-KMP
  └── kotlinx-coroutines-core (Flow, suspend)
```

No dependency on Config-KMP, Database-KMP, or any HTTP client library.
