# BIS OSRS Profile Export (RuneLite)

Plugin Hub–compatible RuneLite side panel that **reads** your logged-in player profile and exports it as **BIS OSRS v1 JSON** for manual import on `/recommend`.

## Read-only export

This plugin is a **read-only data export tool**. It:

- reads game state already exposed by the RuneLite client (stats, quest states, varbits, equipped items)
- shows a summary in the side panel
- lets you **copy** or **save** JSON locally (clipboard / file chooser)

It does **not**:

- send data over the network (no HTTP, WebSocket, or remote calls)
- automate gameplay or inject input (no mouse/keyboard simulation)
- modify right-click menus or add in-world menu entries
- control the client beyond its own sidebar panel buttons

Import the exported file yourself in the BIS OSRS web app — the plugin never contacts BIS OSRS.

## V1 scope

- Combat **stats** (real levels)
- **Completed quests** mapped to BIS catalog ids
- **Completed diary tiers** (BIS-catalog regions only, threshold-based varbits)
- **Equipped untradeables only** (equipment tab)
- `completedUnlockIds` always `[]`
- Optional extras: `accountType`, `schemaVersionLabel` (ignored by BIS v1 import)

Not included: unlock heuristics, bank/inventory scanning, localhost POST, browser automation, or any recommender logic.

## Important v1 behaviour

- **`completedUnlockIds` is always `[]`.** BIS OSRS import will replace unlocks with an empty list — set unlocks manually in the web app after import.
- **Owned items = equipped untradeables only.** Items in inventory or bank are not exported.
- **Diaries are threshold-based.** Only fully completed tiers in BIS-catalog regions (`karamja`, `kourend_kebos`, `western_provinces`) are exported; partial progress is skipped rather than guessed.

## Development

Requirements: **JDK 11**, IntelliJ IDEA Community (recommended).

```bash
./gradlew test
./gradlew build
./gradlew run
```

Enable **BIS OSRS Profile Export** in the RuneLite plugin list, open the sidebar panel, click **Refresh preview**, then **Copy BIS OSRS JSON** or **Save BIS OSRS JSON**.

Import the file on BIS OSRS `/recommend` → **Import profile JSON**.

### Gradle TLS errors

If `./gradlew` fails with `PKIX path building failed` when downloading Gradle or RuneLite dependencies, fix Java trust normally — e.g. install your corporate root CA into the JDK trust store. **Do not** disable TLS globally (`NODE_TLS_REJECT_UNAUTHORIZED=0`).

## BIS compatibility

Fixtures in `src/test/resources/fixtures/` are validated by the main BIS OSRS repo:

```bash
cd ..
npm run test:plugin-fixtures
```

## Package

- Java package: `net.bisosrs.profileexport`
- Plugin Hub id: `bis-osrs-profile-export`
- Main plugin class: `net.bisosrs.profileexport.BisOsrsPlugin`

## License

BSD 2-Clause (same as RuneLite). See [LICENSE](LICENSE).

---

## Plugin Hub submission checklist

Use this before opening a PR to [runelite/plugin-hub](https://github.com/runelite/plugin-hub).

### Repository (required)

- [ ] **Dedicated public GitHub repository** at repo root (generate from [runelite/example-plugin](https://github.com/runelite/example-plugin/generate) or mirror this project as its own repo — Plugin Hub points at a repo URL, not a monorepo subfolder)
- [ ] Repository is **public**
- [ ] [LICENSE](LICENSE) committed — **BSD 2-Clause**
- [ ] [runelite-plugin.properties](runelite-plugin.properties) at repo root with `plugins=net.bisosrs.profileexport.BisOsrsPlugin` and `build=standard`
- [ ] Gradle wrapper committed (`gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.jar`, `gradle/wrapper/gradle-wrapper.properties`)
- [ ] `./gradlew test` passes on a clean clone
- [ ] Optional: `icon.png` at repo root (≤ 48×72 px) for Plugin Hub listing; plugin also loads `/icon.png` from resources if present

### Plugin metadata (required)

- [ ] `displayName`, `author`, `description`, and `tags` in `runelite-plugin.properties` match `@PluginDescriptor` on `BisOsrsPlugin`
- [ ] Description states **read-only export** (no automation, no network)
- [ ] `runeLiteVersion = 'latest.release'` in `build.gradle`

### Policy / review (required)

- [ ] No external network calls in plugin code
- [ ] No gameplay automation, simulated input, or menu manipulation
- [ ] No `@Subscribe` handlers that alter game behaviour — export runs only when the user clicks panel buttons
- [ ] README clearly documents v1 limitations (`completedUnlockIds`, equipped-only owned items, diary regions)

### Submit to Plugin Hub

1. Fork [runelite/plugin-hub](https://github.com/runelite/plugin-hub).
2. Create branch; add `plugins/bis-osrs-profile-export` (filename = plugin id) containing:
   ```
   repository=https://github.com/YOUR_USER/bis-osrs-profile-export.git
   commit=<full 40-char commit hash>
   ```
3. Open PR against `runelite/plugin-hub` with a short description of the read-only export behaviour.
4. Fix any CI / “Changes are needed” review comments; update `commit=` hash after each fix.

---

## Release checklist

Before tagging a release or updating the Plugin Hub manifest `commit=` hash:

1. [ ] `./gradlew test` — all unit tests pass
2. [ ] `./gradlew build` — main sources compile; JAR assembles
3. [ ] **Manual export/import test**
   - [ ] `./gradlew run` — enable plugin in dev client
   - [ ] Log in, open side panel, **Refresh preview** — summary looks correct
   - [ ] **Copy** or **Save** JSON
   - [ ] Import JSON on BIS OSRS `/recommend` — stats, quests, diaries, owned items load; unlocks remain manual
4. [ ] Push to the public plugin repository; copy new commit hash
5. [ ] Open or update PR on [runelite/plugin-hub](https://github.com/runelite/plugin-hub) with the new `commit=`
