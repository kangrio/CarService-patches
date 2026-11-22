# CarService-patches

Application compatibility patches for [CarService-AOSP](https://github.com/kangrio/CarService-AOSP).

## Overview

CarService-patches provides patches for Android applications that depend on Android Automotive APIs (`android.car.*`).

These patches are designed to work alongside CarService-AOSP, bringing automotive application compatibility to standard AOSP devices without requiring a full Android Automotive OS installation.

## Related Project

**[CarService-AOSP](https://github.com/kangrio/CarService-AOSP)**

CarService-AOSP implements the Android Automotive Car API on standard AOSP, allowing supported automotive applications, such as Google Maps Automotive, to run on devices that do not include the original Android Automotive framework.

## Features

- Application-specific compatibility patches.
- Support for CarService-AOSP integration.
- Patches for supported automotive applications.
- Designed for standard AOSP-based devices.

## Requirements

- Android device running a compatible AOSP build.
- [CarService-AOSP](https://github.com/kangrio/CarService-AOSP) installed and configured.
- Morphe Patcher.
- Compatible application APK with a minimum SDK version of 29 or lower.

**Note:** APKs with `minSdkVersion` higher than 29 are not supported.

## Compatibility

Compatibility depends on:

- Android version and API level.
- Application version.
- CarService-AOSP implementation.
- Individual patch support.

Not all applications or versions are guaranteed to work.

## Related Links

- [CarService-AOSP](https://github.com/kangrio/CarService-AOSP)
- [GStore](https://github.com/GStore0/GStore)
- [GStore Community](https://t.me/GStore000)

## Disclaimer

This is an independent community project. It is not affiliated with, endorsed by, or sponsored by Google LLC or any application developers.

This software is provided for development and research purposes and is not certified for production vehicles or safety-critical systems.

## License

Copyright (C) 2026 KangRio

Licensed under the GNU General Public License v3.0 or later. See [LICENSE](LICENSE) for details.
