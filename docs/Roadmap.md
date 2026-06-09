# Roadmap

## Phase 1 — Fix the Build ✅ Complete

- [x] **Replace `jcenter()` with `mavenCentral()`** — done in `39970df`
- [x] **Upgrade AGP** `4.0.1` → `7.4.2` — done in `39970df`
- [x] **Upgrade Gradle** `6.1.1` → `7.6.4` — done in `39970df`
- [x] **Upgrade `compileSdkVersion`** `29` → `34` — done in `39970df`
- [x] **Upgrade `targetSdkVersion`** `29` → `34` — done in `39970df`
- [x] **Make signing config optional** — done in `39970df`
- [x] **Replace SNAPSHOT dependencies** — jPastebin and BottomDialogs pinned; pageindicatorview and android-issue-reporter migrated to JitPack — done in `39970df`
- [ ] **Resolve `com.rm:rmswitch`** — source repo unknown; needs locating or replacing with `SwitchCompat` (can be deferred to Phase 2 AndroidX migration)

---

## Phase 2 — AndroidX Migration

- [ ] Run **Migrate to AndroidX** in Android Studio after confirming Phase 1 builds
- [ ] Replace `android.support.*` imports throughout all Java files
- [ ] Replace `com.rm:rmswitch` with `androidx.appcompat.widget.SwitchCompat`
- [ ] Replace Support `Fragment`, `AppCompatActivity`, etc. with AndroidX equivalents
- [ ] Verify `ConstraintLayout`, `RecyclerView`, and `ViewPager` work with AndroidX versions

---

## Phase 3 — Flag Audit + Runtime Fixes (Core Functionality)

- [x] **Replace 32-bit sqlite3 binary with ARM64** — done in `152eaea`
- [x] **Fix Coolwalk flag typo** `Coolwalk__opt_in _default` → `Coolwalk__opt_in_default` — done in `39970df`
- [x] **Fix inverted SELinux restore condition** — 24 occurrences fixed in `39970df`
- [x] **Fix version check URL** — now points to `headymonster/aa-tweaker` — done in `39970df`
- [ ] **Multi-ABI sqlite3 support** — bundle ARM32 + ARM64 and select at runtime via `Build.SUPPORTED_ABIS`
- [ ] **Audit all feature flags** against Android Auto 10.x+ (see `docs/Tweaks-Reference.md` — all currently marked Unknown)
- [ ] **Remove or deprecate** flags that no longer exist in current AA
- [ ] **Research new flags** introduced in AA 9.x–11.x
- [ ] **Fix Coolwalk activate/deactivate logic** — Coolwalk is now the default UI in recent AA; logic may need reversal
- [ ] **Audit Material You and Vertical Bar flags** — high risk of being stale

---

## Phase 4 — Code Quality / Kotlin Migration

- [ ] **Refactor `MainActivity.java`** (250KB monolith) into per-tweak classes
- [ ] **Kotlin migration** — convert files one at a time, starting with smaller utilities
- [ ] **Add ViewModel/Repository pattern** for tweak state management
- [ ] **Fix `NotSuccessfulDialog`** to surface actual error stream content

---

## Phase 5 — CI/CD

- [ ] **Add GitHub Actions workflow** for automated builds on push/PR
- [ ] **Generate debug APK artifacts** on each main branch push
- [ ] **Add lint check** as PR gate
- [ ] **Add release workflow** that signs and publishes APK to GitHub Releases

---

## Phase 6 — UX Improvements

- [ ] **Replace 5-second splash countdown** with instant load + async version check
- [ ] **Add per-tweak Android Auto version compatibility indicator**
- [ ] **Add log export** feature for easier bug reports
- [ ] **Dark mode** improvements

---

## Ideas / Backlog

- Investigate whether `content://` provider access or `Settings.Global` can replace some sqlite3 commands on newer Android versions
- Explore Shizuku as an alternative to full root for some tweaks
- Per-account flag scoping (currently uses empty string `''` for user field)
