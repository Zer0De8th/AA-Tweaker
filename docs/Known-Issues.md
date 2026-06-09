# Known Issues

## Build-Blocking Issues (App Won't Compile)

### BLD-001 — jcenter() is shut down
**Severity**: Critical — build will fail  
**File**: `build.gradle` (root + `app/build.gradle`)  
**Detail**: JFrog shut down JCenter in May 2021. All dependencies that resolve only from jcenter will produce 404 errors during dependency resolution. This includes `pageindicatorview`, `rmswitch`, and `android-issue-reporter`.  
**Fix**: Migrate to `mavenCentral()` and find replacement libraries or updated coordinates.

### BLD-002 — `local.properties` required for signing
**Severity**: Critical — build will fail without it  
**File**: `app/build.gradle`  
**Detail**: The `signingConfigs` block reads `STORELOCATION`, `STOREPASSWORD`, `KEYPASSWORD`, and `PASTEBIN_API_KEY` from `local.properties`. Any fresh clone will fail to build.  
**Fix**: Add a fallback so debug builds don't require signing config. Move API keys to GitHub Secrets for CI.

### BLD-003 — Gradle 4.0.1 / AGP 4.0.1 incompatible with modern JDK
**Severity**: High  
**File**: `build.gradle`  
**Detail**: AGP 4.0.1 requires JDK 8 and is incompatible with JDK 17+ which is the default on most modern dev machines. Android Studio Giraffe+ ships with JDK 17.  
**Fix**: Upgrade to AGP 8.x, Gradle 8.x, JDK 17.

### BLD-004 — Unstable SNAPSHOT dependencies
**Severity**: High  
**File**: `app/build.gradle`  
**Detail**: `jPastebin:master-SNAPSHOT` and `BottomDialogs:master-SNAPSHOT` resolve to whatever the HEAD of those repos is at build time. They can break without warning and JitPack may return stale or missing builds.  
**Fix**: Pin to specific versions or replace with alternatives.

---

## Runtime Issues

### ~~RT-001~~ — ✅ FIXED: 32-bit sqlite3 binary replaced with ARM64 static build
**Status**: Fixed in commit `152eaea`  
**Fix applied**: The 32-bit ARM (armv7, 397KB) binary was replaced with an ARM64 (aarch64) statically linked binary built from the SQLite 3.45.1 amalgamation using `aarch64-linux-gnu-gcc`. No external runtime dependencies.

**Confirmed error from runtime log** (now resolved):
```
/system/bin/sh: /data/user/0/sksa.aa.tweaker/sqlite3: not executable: 32-bit ELF file
```

> **ABI scope note**: The replacement binary is ARM64 only. It will not run on legacy 32-bit ARM devices. If 32-bit support is needed in the future, bundle both ABIs and select at runtime via `Build.SUPPORTED_ABIS`. For the current target (Android 15, modern hardware), ARM64 is correct.

---

### RT-002 — Typo in Coolwalk flag name causes silent insert failure
**Severity**: Medium  
**File**: `MainActivity.java` (Coolwalk activate/deactivate sections)  
**Confirmed from runtime log** — the following flag name contains a stray space before `_default`:
```sql
"Coolwalk__opt_in _default"
```
Should be:
```sql
"Coolwalk__opt_in_default"
```
SQLite inserts the row with the malformed name; Android Auto will not recognize it. This bug exists in both the activate and deactivate Coolwalk blocks and in both the direct INSERT and the trigger definition.

---

### RT-003 — SELinux not restored after third operation
**Severity**: Low  
**Confirmed from runtime log**: The log shows three tweak operations. The first two include `setenforce 0` (permissive) and `setenforce 1` (enforcing) pairs. The third operation sets `setenforce 0` but the log ends before `setenforce 1` is called — SELinux is left in permissive mode until the next reboot.  
**Fix**: Ensure `setenforce 1` is always called in a `finally`-equivalent block regardless of command success/failure.

---

### RT-004 — Feature flags stale against modern Android Auto
**Severity**: High  
**Detail**: The last commit was May 2023. Android Auto has shipped versions 9.x, 10.x, and 11.x since then. Google regularly renames, removes, or changes phenotype flag types between major releases. Flags that worked in AA 8.x may silently do nothing or cause unexpected behavior on current versions.  
**Affected tweaks**: All flags should be audited. High-risk ones: Coolwalk, Vertical Bar, Material You, UX Prototype.

---

### RT-005 — Version check points to upstream repo
**Severity**: Low  
**File**: `SplashActivity.java:28`  
**Detail**: `BASE_URL = "https://api.github.com/repos/shmykelsa/AA-Tweaker/releases/latest"` — this always checks the upstream author's releases, not this fork. Users will be incorrectly prompted to update to the upstream version.  
**Fix**: Update `BASE_URL` to point to `headymonster/aa-tweaker`.

---

### RT-006 — SharedPreferences state reset on every launch
**Severity**: Low (by design, but potentially confusing)  
**File**: `SplashActivity.java`  
**Detail**: All 26 tweak preference keys are force-set to `false` on every app launch. The UI derives status from live DB queries, not saved preferences.

---

## Code Quality Issues

### CQ-001 — Monolithic MainActivity (250KB, ~7000+ lines)
**Severity**: Medium  
**File**: `MainActivity.java`  
**Detail**: All 26+ tweaks, all UI logic, and all shell execution are in a single file. This makes it extremely hard to maintain, test, or extend.  
**Fix**: Refactor into separate feature modules (ideally Kotlin, with ViewModel/Repository pattern).

### CQ-002 — Android Support libraries, not AndroidX
**Severity**: High  
**Detail**: All imports use `android.support.*`. Google stopped updating support libs after 2018. AndroidX has been the standard for 6+ years.  
**Fix**: Run Android Studio's `Migrate to AndroidX` refactor tool after upgrading AGP.

### CQ-003 — No CI/CD pipeline
**Severity**: Medium  
**Detail**: No GitHub Actions workflows exist. There's no automated build verification, no lint checks, and no APK artifact generation on push.  
**Fix**: Add a `.github/workflows/build.yml` using AGP's assemble task.

### CQ-004 — No user-facing error detail on failed tweak
**Severity**: Medium  
**Detail**: When a tweak command fails (DB locked, permissions issue), the app shows `NotSuccessfulDialog` but provides no actionable detail.  
**Fix**: Surface the `ErrorStream` content in the failure dialog.
