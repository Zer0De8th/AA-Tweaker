# Tweaks Reference

All tweaks apply SQLite overrides to Google Play Services `phenotype.db`. A device reboot is required after applying changes.

**Status key:**
- `Working` — verified working as of last audit
- `Likely Working` — flag name unchanged but not recently verified
- `Unknown` — not audited against current AA versions
- `Stale` — flag name or behavior changed in modern AA; needs update
- `Deprecated` — removed from the app (code still present but UI hidden)

---

## Restrictions

| Pref Key | Display Name | Description | Last Known Status |
|----------|-------------|-------------|-------------------|
| `aa_speed_hack` | Disable Speed Restrictions | Removes the limit that hides message previews while driving | Unknown |
| `aa_six_tap` | Disable Six Tap Limit | Removes the banner after 6 screen taps asking driver to pay attention | Deprecated (removed from UI) |
| `aa_startup_policy` | Startup Policy | Modifies the startup behavior policy flags | Unknown |

---

## Apps & Custom Apps

| Pref Key | Display Name | Description | Last Known Status |
|----------|-------------|-------------|-------------------|
| `aa_patched_apps` | Custom App Whitelist | Patches Android Auto to allow third-party apps not officially whitelisted | Likely Working |

---

## Notifications

| Pref Key | Display Name | Description | Last Known Status |
|----------|-------------|-------------|-------------------|
| `aa_message_autoread` | Auto-Read Messages | Automatically reads incoming messages aloud | Unknown |
| `aa_hun_ms` | HUN Duration (0–20s) | Sets heads-up notification display duration in milliseconds | Unknown |
| `aa_media_hun` | Media Notification Duration (Navbar) | Sets how long media notifications persist in the nav bar (up to 15 min) | Unknown |
| `aa_activate_declinesms` | Decline SMS Button | Adds a decline SMS button on call notification | Unknown |

---

## Display & UI

| Pref Key | Display Name | Description | Last Known Status |
|----------|-------------|-------------|-------------------|
| `force_ws` | Force Widescreen | Forces widescreen layout regardless of head unit | Likely Working |
| `force_no_ws` | Force No Widescreen | Forces non-widescreen layout | Likely Working |
| `force_portrait` | Force Portrait | Forces portrait orientation | Likely Working |
| `aa_activate_coolwalk` | Enable Coolwalk UI | Enables the Coolwalk (split-screen) interface | Stale — Coolwalk is now default in AA 9+ |
| `aa_deactivate_coolwalk` | Disable Coolwalk UI | Disables Coolwalk, reverts to classic UI | Unknown |
| `coolwalk_daynight_tweak` | Coolwalk Day/Night | Adjusts day/night mode behavior in Coolwalk | Unknown |
| `aa_new_seekbar` | New Seekbar Style | Enables redesigned media seekbar | Unknown |
| `uxprototype_tweak` | UX Prototype | Enables experimental UX prototype features | Unknown |
| `aa_material_you` | Material You | Enables Material You theming in Android Auto | Unknown |
| `aa_vertical_bar` | Vertical Navigation Bar | Enables vertical navigation bar layout | Unknown |
| `aa_battery_outline` | Battery Outline | Shows battery outline indicator | Unknown |

---

## Connectivity

| Pref Key | Display Name | Description | Last Known Status |
|----------|-------------|-------------|-------------------|
| `aa_bluetooth_auto` | Disable BT Auto Connect | Disables automatic Bluetooth connection on startup | Unknown |
| `aa_bitrate_usb` | USB Bitrate | Sets custom USB connection bitrate | Unknown |
| `aa_bitrate_wireless` | Wireless Bitrate | Sets custom wireless Android Auto bitrate | Unknown |

---

## System

| Pref Key | Display Name | Description | Last Known Status |
|----------|-------------|-------------|-------------------|
| `kill_telemetry` | Disable Telemetry | Disables unnecessary telemetry/analytics reporting | Likely Working |
| `multi_display` | MultiDisplay / ClusterSim | Enables multi-display and cluster simulation features | Unknown |
| `battery_saver_warning` | Battery Saver Warning | Controls battery saver warning behavior | Unknown |
| `aa_inertial_scroll` | Inertial Scroll | Enables/disables inertial scrolling behavior | Unknown |

---

## Assistant

| Pref Key | Display Name | Description | Last Known Status |
|----------|-------------|-------------|-------------------|
| `aa_activate_assistant_tips` | Assistant Tips | Shows assistant usage tips | Unknown |

---

## Audit Log

| Date | AA Version Tested | Notes |
|------|-------------------|-------|
| May 2023 | ~8.x | Last upstream commit |
| — | — | No subsequent audits recorded |

**All tweaks should be re-audited against Android Auto 10.x+ before publishing a new release.**
