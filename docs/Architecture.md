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

Android Auto behaviour is controlled by Google Play Services **phenotype flags** stored at:
```
/data/data/com.google.android.gms/databases/phenotype.db
```

The app inserts rows into the `Flags` table with a package scope of `com.google.android.apps.auto` or `com.android.car.media`. Example:

```sql
INSERT OR REPLACE INTO Flags
  (packageName, user, name, intVal, committed)
  VALUES ('com.google.android.apps.auto', '', 'some_flag_name', 1, 0);
```

### Root Shell Execution

`MainActivity.runSuWithCmd(String cmd)` is the primary utility:
- Opens a `su` shell via `Runtime.getRuntime().exec("su")`
- Writes the command to stdin
- Reads stdout and stderr into a `StreamLogs` object
- Called from background threads (post-ANR refactor)

### sqlite3 Binary

A pre-compiled `sqlite3` binary is bundled in `app/src/main/res/raw/sqlite3`. On first launch, `SplashActivity.copyAssets()` copies it to:
```
/data/data/sksa.aa.tweaker/sqlite3
```
and sets permissions to 777. This binary is then invoked via root shell for all database operations.

**Issue**: This binary is compiled for a specific ABI (likely ARM). Devices with different ABIs or ARM64-only environments may fail silently.

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
| `com.romandanylyk:pageindicatorview` | 1.0.1 | Tab dots | jcenter only |
| `com.rm:rmswitch` | 1.2.2 | Toggle switches | jcenter only |
| `com.heinrichreimersoftware:android-issue-reporter` | 1.3.1 | Crash reporter | jcenter only |
| `com.github.bravobit:jPastebin` | master-SNAPSHOT | Log upload | Unstable snapshot |
| `com.android.volley:volley` | 1.2.1 | HTTP requests | OK (mavenCentral) |
| `com.github.iGio90:BottomDialogs` | master-SNAPSHOT | Bottom sheets | Unstable snapshot |
