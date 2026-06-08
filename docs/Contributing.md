# Contributing

## Development Setup

### Prerequisites

- Android Studio (Giraffe or newer)
- JDK 11 or JDK 17
- A rooted Android device or rooted emulator for testing
- Android Auto installed on the test device

### Getting the Build to Work

The project **cannot build on a fresh clone** due to missing `local.properties`. Create this file in the project root:

```properties
# Required for signing (leave empty for debug builds after disabling signingConfig)
STORELOCATION=
STOREPASSWORD=
KEYPASSWORD=

# Required for Pastebin log upload feature (can be a dummy value for debug)
PASTEBIN_API_KEY=dummy_key
```

Then in `app/build.gradle`, comment out the `signingConfig signingConfigs.'new'` line inside `defaultConfig` to allow unsigned debug builds.

> **Note**: Until [BLD-001](Known-Issues.md#bld-001--jcenter-is-shut-down) is fixed, the build will still fail due to jcenter. See the [Roadmap](Roadmap.md) Phase 1 for the fix.

### Branch Strategy

| Branch | Purpose |
|--------|---------|
| `master` | Stable releases only |
| `claude/android-auto-onboarding-4sxwtm` | Current development branch |

All new work should be submitted as PRs against `master`.

---

## Testing a Tweak

1. Build and install a debug APK on your rooted test device
2. Grant root access when prompted
3. Enable the tweak you want to test
4. Tap **Apply**
5. Reboot the device
6. Launch Android Auto and verify the behavior change

### Verifying Flag Was Written

You can manually verify via ADB:
```bash
adb shell su -c "sqlite3 /data/data/com.google.android.gms/databases/phenotype.db \
  \"SELECT name, intVal, stringVal FROM Flags WHERE packageName='com.google.android.apps.auto'\""
```

### Finding Flag Names

Flag names can be discovered by:
1. Extracting and decompiling the Android Auto APK with jadx/apktool
2. Searching for `phenotype` or `Phenotype` in the decompiled source
3. Checking XDA Developers forum threads for the target AA version
4. Diffing `phenotype.db` before/after enabling features through the AA developer menu (requires enabling developer options)

---

## Code Style

- Java files follow the existing style (no strict formatter enforced yet)
- When adding a new tweak:
  1. Add the SharedPreferences key to `SplashActivity.java` (reset block)
  2. Add the UI button/switch in the appropriate tab layout XML
  3. Implement the tweak logic in `MainActivity.java` (currently monolithic)
  4. Add a string resource for the button label and description
  5. Document the new flag in `docs/Tweaks-Reference.md`

---

## Submitting Changes

1. Fork or branch from `master`
2. Make changes
3. Test on a real device with Android Auto
4. Open a PR with:
   - Which Android Auto version you tested on
   - What the tweak does / what changed
   - Any flags you added/modified with the exact flag name
