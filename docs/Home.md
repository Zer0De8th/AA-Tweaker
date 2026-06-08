# AA-AIO-Tweaker Wiki

## Overview

**AA-AIO-Tweaker** (Android Auto All-In-One Tweaker) removes stock Android Auto restrictions by writing SQLite phenotype flag overrides directly into Google Play Services. It requires a rooted device.

This repository is a fork of [shmykelsa/AA-Tweaker](https://github.com/shmykelsa/AA-Tweaker), last synced at **v5.2.2 (May 2023)**. The codebase is significantly outdated and needs modernization before it will build cleanly or work reliably on Android Auto versions released after mid-2023.

---

## Quick Links

| Page | Description |
|------|-------------|
| [Architecture](Architecture.md) | How the app works internally |
| [Tweaks Reference](Tweaks-Reference.md) | All available tweaks and their flag status |
| [Known Issues](Known-Issues.md) | Current bugs and broken functionality |
| [Roadmap](Roadmap.md) | Planned improvements and modernization |
| [Contributing](Contributing.md) | Dev setup and contribution guide |

---

## How It Works (30-second version)

1. App checks for root access on launch
2. Copies a bundled `sqlite3` binary to app data dir
3. User selects tweaks and taps Apply
4. App runs root shell → `sqlite3` queries against Google Play Services `phenotype.db`
5. Flags are written as phenotype overrides
6. Device reboots, Android Auto reads new flags

---

## Current Status

| Area | Status |
|------|--------|
| Build system | Broken — `jcenter()` shutdown, AGP 4.0.1 |
| Android SDK | Outdated — compileSdk 29, Support libs (not AndroidX) |
| Feature flags | Partially stale — not audited against AA 10.x+ |
| Last upstream sync | May 2023 (v5.2.2) |
| Kotlin migration | Not started |

---

## Requirements

- Rooted Android device (Magisk or similar)
- Android Auto installed
- Root shell access granted to the app
- Reboot after applying tweaks
