# Roadmap

## Phase 1 — Fix the Build (Unblock Development)

These must be done before anything else. Nothing can be tested until the project compiles.

- [ ] **Replace `jcenter()` with `mavenCentral()`** in both `build.gradle` files
- [ ] **Upgrade AGP** from 4.0.1 → 8.x (requires updating `gradle/wrapper/gradle-wrapper.properties` too)
- [ ] **Upgrade `compileSdkVersion`** from 29 → 35 (Android 15)
- [ ] **Upgrade `targetSdkVersion`** from 29 → 34+ (Play Store requirement)
- [ ] **Make signing config optional** — wrap `signingConfigs` in a try/catch or environment check so unsigned debug builds work on fresh clones
- [ ] **Replace SNAPSHOT dependencies** — pin `jPastebin` and `BottomDialogs` to specific release versions or find modern equivalents
- [ ] **Find jcenter-only library replacements**:
  - `pageindicatorview` → maintained fork or alternative
  - `rmswitch` → `com.google.android.material` `SwitchMaterial`
  - `android-issue-reporter` → remove or replace with GitHub Issues deep-link

---

## Phase 2 — AndroidX Migration

- [ ] Run **Migrate to AndroidX** in Android Studio after AGP upgrade
- [ ] Replace `android.support.*` imports throughout all Java files
- [ ] Replace Support `Fragment`, `AppCompatActivity`, etc. with AndroidX equivalents
- [ ] Verify `ConstraintLayout`, `RecyclerView`, and `ViewPager` work with AndroidX versions

---

## Phase 3 — Flag Audit (Core Functionality)

This is the most important phase for users. Many flags are likely broken.

- [ ] **Document current Android Auto version compatibility** for each flag (see `docs/Tweaks-Reference.md`)
- [ ] **Test each tweak** on Android Auto 10.x+ to verify flag name and value still work
- [ ] **Remove or deprecate** flags that no longer exist in current AA versions
- [ ] **Research new flags** introduced in AA 9.x–11.x that could be valuable tweaks
- [ ] **Fix Coolwalk flags** — Coolwalk is now the default UI in recent AA; activate/deactivate logic likely needs reversal
- [ ] **Audit Material You and Vertical Bar flags** — introduced late in development, high risk of being stale

---

## Phase 4 — Code Quality / Kotlin Migration

- [ ] **Refactor `MainActivity.java`** (250KB monolith) into per-tweak classes
  - Suggested pattern: `TweakManager` interface, one implementation per tweak category
- [ ] **Kotlin migration** — convert files one at a time, starting with smaller utilities (`StreamLogs`, `Version`, `AppInfo`)
- [ ] **Add ViewModel/Repository pattern** for tweak state management
- [ ] **Fix version check URL** in `SplashActivity` to point to `headymonster/aa-tweaker`
- [ ] **Multi-ABI sqlite3 support** — bundle ARM32, ARM64, x86 binaries or use JNI

---

## Phase 5 — CI/CD

- [ ] **Add GitHub Actions workflow** for automated builds on push/PR
- [ ] **Generate debug APK artifacts** on each main branch push
- [ ] **Add lint check** as PR gate
- [ ] **Add release workflow** that signs and publishes APK to GitHub Releases

---

## Phase 6 — UX Improvements

- [ ] **Replace 5-second splash countdown** with instant load + async version check
- [ ] **Improve error messages** — show which specific flag/command failed
- [ ] **Add per-tweak Android Auto version compatibility indicator**
- [ ] **Add log export** feature for easier bug reports
- [ ] **Dark mode** improvements

---

## Ideas / Backlog

- Investigate whether `content://` provider access or `Settings.Global` can replace some sqlite3 commands on newer Android versions
- Explore Shizuku as an alternative to full root for some tweaks
- Per-account flag scoping (currently uses empty string `''` for user field)
