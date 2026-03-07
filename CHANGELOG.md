<!-- SPDX-FileCopyrightText: 2025 Milos Vasic -->
<!-- SPDX-License-Identifier: Apache-2.0 -->

# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-03-06

### Added
- Initial release extracted from Yole monolith
- `NetworkStorageService` - Interface for connect/disconnect, file CRUD, search, and quota operations
- `StorageDocument` - Model with file type detection, path utilities, and formatted size display
- `StorageOperation` - Model with progress tracking for uploads and downloads
- `StorageInfo` and `StorageQuota` - Models with formatted display for storage metadata
- `PlatformFileIO` - Interface for platform-specific file operations via expect/actual
- `ProtocolInfo` - Data class for describing protocol implementation tiers and capabilities
- Kotlin Multiplatform support (Android, Desktop/JVM, iOS, Wasm/JS)
- Comprehensive test suite
- CI/CD via GitHub Actions

### Infrastructure
- Gradle build with version catalog
- Kover code coverage
- SPDX license headers (Apache-2.0)
