# AA-AIO-Tweaker — AI Onboarding Guide

## What This Project Is

**AA-AIO-Tweaker** (Android Auto All-In-One Tweaker) is a rooted-Android app that removes stock limitations from Android Auto by modifying Google Play Services SQLite phenotype flags at runtime. It requires root access and uses a bundled `sqlite3` binary copied to the app data directory.

This is a fork of the upstream project [shmykelsa/AA-Tweaker](https://github.com/shmykelsa/AA-Tweaker), last synced at v5.2.2 (May 2023).

## How It Works

1. On first launch, `SplashActivity` checks for root, copies a bundled `sqlite3` binary to `/data/data/sksa.aa.tweaker/sqlite3`, and chmod 777s it.
2. Root shell commands run SQLite queries against the Google Play Services database at `/data/data/com.google.android.gms/databases/phenotype.db`.
3. Feature flags are written as phenotype overrides; Android Auto reads them on startup.
4. A reboot is required after applying tweaks for them to take effect.

## Key Files

| File | Purpose |
|------|---------|
| `app/src/main/java/sksa/aa/tweaker/MainActivity.java` | All tweak implementations (~250KB, monolithic) |
| `app/src/main/java/sksa/aa/tweaker/SplashActivity.java` | Root check, sqlite3 copy, version check |
| `app/src/main/java/sksa/aa/tweaker/StreamLogs.java` | Captures stdout/stderr from root shell commands |
| `app/src/main/res/raw/sqlite3` | Bundled sqlite3 binary (ARM) |
| `app/build.gradle` | Build config — v5.2.2, compileSdk 29, support libs |

## Build Requirements

The build **will fail** without a `local.properties` file containing:
```
STORELOCATION=<path to keystore>
STOREPASSWORD=<password>
KEYPASSWORD=<key password>
PASTEBIN_API_KEY=<key>
```
Comment out `signingConfigs` block in `app/build.gradle` for unsigned debug builds.

## Critical Known Issues (as of June 2026)

1. **`jcenter()` is shut down** — all dependency resolution will fail. Must migrate to `mavenCentral()` and update library coordinates.
2. **Android Support libs (v28), not AndroidX** — migration required.
3. **`compileSdkVersion 29`** — 6 years behind; target SDK 34+ now required by Play Store.
4. **Gradle 4.0.1 / AGP 4.0.1** — current stable is AGP 8.x. Build toolchain needs full upgrade.
5. **`master-SNAPSHOT` JitPack deps** — `jPastebin` and `BottomDialogs` are unstable snapshots; JitPack availability not guaranteed.
6. **Version check URL hardcoded to upstream** — `SplashActivity.BASE_URL` points to `shmykelsa/AA-Tweaker`, not this fork.
7. **Many flags likely stale** — Android Auto has shipped many releases since May 2023; phenotype flag names change between versions.
8. **Monolithic `MainActivity.java`** — the entire tweak logic is one 250KB Java file. Any modernization should refactor this.

## Modernization Priorities

See `docs/Roadmap.md` for the full plan. In short:
1. Fix build toolchain (jcenter → mavenCentral, Gradle 8.x, AGP 8.x, AndroidX)
2. Audit all phenotype flags against current Android Auto versions
3. Kotlin migration
4. Add CI/CD with GitHub Actions

## Available Tweaks (SharedPreferences keys)

See `docs/Tweaks-Reference.md` for the full list with descriptions and flag status.

## Architecture

See `docs/Architecture.md`.
