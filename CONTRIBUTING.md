<!-- SPDX-FileCopyrightText: 2025 Milos Vasic -->
<!-- SPDX-License-Identifier: Apache-2.0 -->

# Contributing to Storage-KMP

Thank you for your interest in contributing to Storage-KMP!

## Getting Started

1. Fork the repository
2. Clone your fork
3. Create a feature branch: `git checkout -b feature/my-feature`
4. Make your changes
5. Run tests: `./gradlew test`
6. Commit: `git commit -m "feat: add my feature"`
7. Push: `git push origin feature/my-feature`
8. Open a Pull Request

## Development Setup

### Prerequisites
- JDK 11+
- Gradle 8.7+

### Building
```bash
./gradlew build
```

### Testing
```bash
./gradlew desktopTest      # Run desktop tests
./gradlew test             # Run all platform tests
./gradlew koverHtmlReport  # Coverage report
```

## Code Style

- Kotlin primary language
- Follow existing code conventions
- SPDX license headers required on all new files:
```kotlin
/*#######################################################
 *
 * SPDX-FileCopyrightText: 2025 Milos Vasic
 * SPDX-License-Identifier: Apache-2.0
 *
 * Brief description
 *
 *########################################################*/
```

## Module-Specific Notes

- `NetworkStorageService` is the core interface; new protocol implementations should implement all methods
- All service methods are `suspend` functions; handle `CancellationException` correctly (always rethrow)
- `PlatformFileIO` uses expect/actual; any new file operations need implementations for all 4 targets
- `StorageDocument` and `StorageOperation` are immutable model classes
- Progress tracking in `StorageOperation` should report values between 0.0 and 1.0
- Path normalization must prevent directory traversal attacks (no `..` escaping root)

## Pull Request Process

1. Update tests for any new functionality
2. Ensure all tests pass
3. Update README.md if API changes
4. Update CHANGELOG.md with your changes

## License

By contributing, you agree that your contributions will be licensed under the Apache-2.0 License.
