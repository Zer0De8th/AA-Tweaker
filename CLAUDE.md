# AA-AIO-Tweaker — AI Session Guide

> **The `docs/` directory is the authoritative source of truth for this project.**
> Always read from there first. Update it when you discover anything new. Do not
> duplicate information here that already lives in the wiki.

## Start Here

Before doing anything, read these wiki pages in order:

1. **[docs/Home.md](docs/Home.md)** — What the project is and its current status
2. **[docs/Architecture.md](docs/Architecture.md)** — How the app works, key files, and dependencies
3. **[docs/Known-Issues.md](docs/Known-Issues.md)** — Build blockers and runtime bugs (read before touching code)
4. **[docs/Roadmap.md](docs/Roadmap.md)** — Planned work and modernization phases
5. **[docs/Tweaks-Reference.md](docs/Tweaks-Reference.md)** — Every tweak flag, its key, and current status
6. **[docs/Contributing.md](docs/Contributing.md)** — Dev setup, testing instructions, how to add a tweak

## Quick Facts (see wiki for full details)

- **Language**: Java (Kotlin migration planned — see Roadmap Phase 4)
- **Root required**: Yes — all tweaks run via `su` shell + bundled `sqlite3` binary
- **Mechanism**: SQLite overrides in `/data/data/com.google.android.gms/databases/phenotype.db`
- **Last upstream sync**: v5.2.2, May 2023 — many flags likely stale on AA 10.x+
- **Build status**: Currently broken — see [Known Issues BLD-001 through BLD-004](docs/Known-Issues.md)

## Key Files

| File | Purpose |
|------|---------|
| `app/src/main/java/sksa/aa/tweaker/MainActivity.java` | All tweak logic (~250KB monolith) |
| `app/src/main/java/sksa/aa/tweaker/SplashActivity.java` | Entry point — root check, sqlite3 copy, update check |
| `app/src/main/java/sksa/aa/tweaker/StreamLogs.java` | Captures stdout/stderr from root shell commands |
| `app/src/main/res/raw/sqlite3` | Bundled sqlite3 binary (single ABI — see Known Issues RT-002) |
| `app/build.gradle` | Build config — must be fixed before anything compiles |

## Keeping the Wiki Current

If you discover a new issue, fix a flag, or complete a roadmap item, **update the relevant `docs/` page** as part of the same PR. The wiki is only useful if it stays accurate.

- New bug found → add to `docs/Known-Issues.md`
- Tweak verified or broken → update status in `docs/Tweaks-Reference.md`
- Work completed → check off item in `docs/Roadmap.md`
- Architecture changed → update `docs/Architecture.md`
