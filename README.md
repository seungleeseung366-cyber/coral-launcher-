# CORAL Launcher

Minecraft Java launcher foundation for Android.

## Batch 1

Included:

- Android/Kotlin project
- Jetpack Compose UI
- Renderer Manager
- Renderer provider architecture
- Renderer entries for:
  - Auto
  - GL4ES
  - MobileGlues
  - Zink
  - ANGLE
  - Vulkan
  - VirGL
- GitHub Actions APK build

## Important

The renderer entries in Batch 1 are placeholders. Native renderer binaries/source are not bundled yet.

The next stage can integrate the actual native graphics components and Minecraft Java launcher core.

## Build

This repository expects a Gradle wrapper.

If you have Gradle installed:

```bash
gradle wrapper --gradle-version 8.11.1
./gradlew assembleDebug
```

The GitHub Actions workflow builds `assembleDebug` and uploads the APK as an artifact.
