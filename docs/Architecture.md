# Architecture

## Overview

AA-AIO-Tweaker is a single-module Android app (`sksa.aa.tweaker`) written in Java targeting API 23–29. It has one main dependency: root shell access to execute SQLite commands against Google Play Services.

## Package Structure

```
sksa.aa.tweaker/
├── SplashActivity.java          # Entry point — root check, sqlite3 copy, update check
├── MainActivity.java            # All tweak implementations (~250KB, monolithic)
├── AppsList.java                # Activity to select apps for whitelist patching
├── AppInfo.java                 # Data model for installed app
├── MyAdapter.java               # RecyclerView adapter for app list
├── CommonPageAdapter.java       # ViewPager adapter for tab pages
├── AboutDialog.java             # About dialog fragment
├── NoRootDialog.java            # Shown when root is unavailable
├── RebootDialog.java            # Prompts user to reboot after applying tweaks
├── NotSuccessfulDialog.java     # Shown when a tweak command fails
├── StreamLogs.java              # Captures stdin/stdout/stderr from shell commands
├── AccountsChooseActivity/
│   └── AccountsChooser.java    # Activity to pick a Google account for operations
├── CarRemoverActivity/
│   └── CarRemover.java         # Activity to remove car from Android Auto device list
└── Utils/
    ├── BottomDialog.java        # Bottom sheet dialog utility
    ├── RecyclerItemClickListener.java  # Click listener for RecyclerView
    ├── UtilsLibrary.java        # UI/drawable helpers
    └── Version.java             # Semantic version comparison utility
```

## Core Mechanism

### SQLite Flag Injection

Android Auto behaviour is controlled by **phenotype flag overrides** stored in the Google Play Services database at:
```
/data/data/com.google.android.gms/databases/phenotype.db
```

The app inserts rows into the **`FlagOverrides`** table with the Android Auto package name `com.google.android.projection.gearhead`. Example from the actual runtime log:

```sql
INSERT OR REPLACE INTO FlagOverrides
  (packageName, flagType, name, user, boolVal, committed)
  VALUES ("com.google.android.projection.gearhead", 0, "Coolwalk__enabled", "", 0, 0);
```

> **Note**: The package name is `com.google.android.projection.gearhead` (the projected/phone-screen AA client), **not** `com.google.android.apps.auto`. The table is `FlagOverrides`, not `Flags`. Both distinctions matter when querying the DB manually.

Some tweaks also create SQLite **TRIGGER**s on `FlagOverrides` so the flags are re-inserted automatically if Google Play Services deletes them:

```sql
CREATE TRIGGER aa_deactivate_coolwalk AFTER DELETE ON FlagOverrides
BEGIN
  INSERT OR REPLACE INTO FlagOverrides (...) VALUES (...);
  ...
END;
```

### Root Shell Execution

The full operation sequence for each tweak (visible in runtime logs):

1. `am kill all com.google.android.gms` — force-stop GMS so DB isn't locked
2. `chown root <phenotype.db>` — take ownership of the database
3. `setenforce 0` — set SELinux to permissive
4. `<sqlite3 binary> -batch <phenotype.db> '<SQL>'` — run the flag inserts
5. `pm enable com.google.android.gms` — re-enable GMS
6. `chown u0_a133 <phenotype.db>` — restore original ownership
7. `setenforce 1` — restore SELinux enforcing mode

`MainActivity.runSuWithCmd(String cmd)` is the primary utility:
- Opens a `su` shell via `Runtime.getRuntime().exec("su")`
- Writes the command to stdin
- Reads stdout and stderr into a `StreamLogs` object
- Called from background threads (post-ANR refactor)

### sqlite3 Binary — Current Status: BROKEN on 64-bit Devices

A pre-compiled `sqlite3` binary is bundled in `app/src/main/res/raw/sqlite3`. On first launch, `SplashActivity.copyAssets()` copies it to:
```
/data/user/0/sksa.aa.tweaker/sqlite3
```
and sets permissions to 777.

**This binary is 32-bit ARM (armv7) only.** On any 64-bit-only Android device it produces:
```
not executable: 32-bit ELF file
```
and every SQL operation is skipped. See [Known Issues RT-001](Known-Issues.md#rt-001----32-bit-sqlite3-binary-fails-on-all-modern-64-bit-only-devices--all-tweaks-broken) for the fix.

## Activity Flow

```
SplashActivity
  ├── Check root (runSuWithCmd "echo 1")
  ├── Copy sqlite3 binary if not present
  ├── Check for app updates (Volley → GitHub API)
  ├── 5-second countdown
  └── → MainActivity (on button press, if rooted)
        ├── Tab 1: Tweaks
        ├── Tab 2: Apps Whitelist → AppsList
        ├── Tab 3: Accounts → AccountsChooser
        └── Tab 4: Car Remover → CarRemover
```

## SharedPreferences State

`SplashActivity` resets all tweak state to `false` on every launch (in `SharedPreferences "MainActivity"`). This means the UI always shows current _applied_ state, not a persistent user preference — the app queries actual DB state on load to show current status.

## Dependencies

| Library | Version | Purpose | Status |
|---------|---------|---------|--------|
| `com.android.support:appcompat-v7` | 28.0.0 | Base UI | Deprecated (use AndroidX) |
| `com.android.support.constraint:constraint-layout` | 2.0.4 | Layouts | Deprecated |
| `com.android.support:design` | 28.0.0 | Material components | Deprecated |
| `com.romandanylyk:pageindicatorview` | 1.0.1 | Tab dots | jcenter only — broken |
| `com.rm:rmswitch` | 1.2.2 | Toggle switches | jcenter only — broken |
| `com.heinrichreimersoftware:android-issue-reporter` | 1.3.1 | Crash reporter | jcenter only — broken |
| `com.github.bravobit:jPastebin` | master-SNAPSHOT | Log upload | Unstable snapshot |
| `com.android.volley:volley` | 1.2.1 | HTTP requests | OK (mavenCentral) |
| `com.github.iGio90:BottomDialogs` | master-SNAPSHOT | Bottom sheets | Unstable snapshot |
