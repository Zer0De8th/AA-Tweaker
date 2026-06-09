# Known Issues

## Build-Blocking Issues (App Won't Compile)

### ~~BLD-001~~ — ✅ FIXED: jcenter() replaced with mavenCentral()
**Fixed in commit `39970df`**
Both `build.gradle` files updated. AGP and all app repositories now resolve from `google()` and `mavenCentral()`. JitPack retained for GitHub-hosted libraries.

> **Remaining**: `com.rm:rmswitch:1.2.2` source repo is unknown — coordinate kept as-is with a TODO comment. If resolution fails, replace with `SwitchCompat` from AppCompat as part of the AndroidX migration (Roadmap Phase 2).

---

### ~~BLD-002~~ — ✅ FIXED: Signing config is now conditional
**Fixed in commit `39970df`**
`app/build.gradle` now checks for `local.properties` existence before loading signing keys. Debug builds work on a fresh clone without any `local.properties`. `PASTEBIN_API_KEY` defaults to empty string when not provided.

---

### ~~BLD-003~~ — ✅ FIXED: Build toolchain upgraded
**Fixed in commit `39970df`**
- AGP `4.0.1` → `7.4.2`
- Gradle `6.1.1` → `7.6.4`
- `compileSdkVersion 29` → `34`
- `targetSdkVersion 29` → `34`
- `versionCode 34` → `35`, `versionName` → `5.3.0`

---

### ~~BLD-004~~ — ✅ FIXED: SNAPSHOT dependencies pinned
**Fixed in commit `39970df`**

| Library | Before | After |
|---------|--------|-------|
| `jPastebin` | `master-SNAPSHOT` | `1.0` |
| `BottomDialogs` | `master-SNAPSHOT` | `v1.0.0` |
| `pageindicatorview` | `com.romandanylyk:…` (jcenter) | `com.github.romandanylyk:PageIndicatorView:v1.0.3` (JitPack) |
| `android-issue-reporter` | jcenter | `com.github.HeinrichReimer:android-issue-reporter:1.3.1` (JitPack) |

---

### ~~BLD-005~~ — ✅ FIXED: BuildConfig generation disabled by default in AGP 8.x
**Fixed in commit `d4f770e`**
AGP 8.x disables `BuildConfig` class generation by default. The app uses `BuildConfig.VERSION_NAME` in `SplashActivity` and defines a `PASTEBIN_API_KEY` `buildConfigField` in both build types. Added `buildFeatures { buildConfig true }` to `app/build.gradle`.

---

### ~~CQ-003~~ — ✅ FIXED: CI/CD pipeline added
**Fixed in commit `a93f691`**
`.github/workflows/build.yml` now builds a debug APK on every push to `master` or `claude/**` branches and on PRs. APK artifact uploaded with 14-day retention.

---

## Runtime Issues

### ~~RT-001~~ — ✅ FIXED: 32-bit sqlite3 binary replaced with ARM64 static build
**Fixed in commit `152eaea`**
Bundled sqlite3 replaced with a statically linked ARM64 binary built from SQLite 3.45.1. No external runtime dependencies.

> **ABI scope**: ARM64 only. Legacy 32-bit ARM devices not supported. Multi-ABI support tracked in Roadmap Phase 3.

---

### ~~RT-002~~ — ✅ FIXED: Coolwalk flag name typo
**Fixed in commit `39970df`**
`"Coolwalk__opt_in _default"` → `"Coolwalk__opt_in_default"` corrected in both the activate and deactivate blocks of `MainActivity.java` (lines 2497 and 2684).

---

### ~~RT-003~~ — ✅ FIXED: SELinux restore condition was inverted
**Fixed in commit `39970df`**
The condition guarding `setenforce 1` was `equals("permissive")` — meaning SELinux was only restored when the device was **already** permissive, and left in permissive mode on all normal (enforcing) devices after every operation.

Changed to `!equals("permissive")` across all 24 occurrences in `MainActivity.java`. SELinux is now correctly restored to enforcing after every tweak operation on a standard device.

---

### RT-004 — Feature flags stale against modern Android Auto
**Severity**: High  
**Detail**: Last upstream sync was May 2023 (AA ~8.x). All flags should be audited against current AA versions. See Roadmap Phase 3.

---

### ~~RT-005~~ — ✅ FIXED: Version check URL updated
**Fixed in commit `39970df`**
`SplashActivity.BASE_URL` now points to `headymonster/aa-tweaker` instead of `shmykelsa/AA-Tweaker`.

---

### RT-006 — SharedPreferences state reset on every launch
**Severity**: Low (by design)
All 26 tweak keys are reset to `false` on each launch; state is read from live DB queries. Expected behavior.

---

## Code Quality Issues

### CQ-001 — Monolithic MainActivity (~250KB)
**Severity**: Medium — Refactor tracked in Roadmap Phase 4.

### CQ-002 — Android Support libraries, not AndroidX
**Severity**: High — Migration tracked in Roadmap Phase 2.

### CQ-003 — No CI/CD pipeline
**Severity**: Medium — Tracked in Roadmap Phase 5.

### CQ-004 — No user-facing error detail on failed tweak
**Severity**: Medium — `NotSuccessfulDialog` shows no actionable information when a tweak fails.
