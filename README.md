# BIS OSRS Quest Exporter (RuneLite)

RuneLite side panel that **reads** your logged-in player profile and exports **BIS OSRS v1 JSON** for manual import on `/recommend`.

## What it does

- Reads game state from the RuneLite client: stats, completed quests, completed diary tiers (selected regions), and equipped untradeables
- Shows an export summary in the sidebar
- Exports **JSON only** — copy to clipboard or save to a local file

## What it does not do

- **No automation** — nothing runs without you clicking panel buttons
- **No gameplay input** — no mouse/keyboard simulation or client control
- **No external network calls** — no HTTP, WebSocket, or remote requests; the plugin never contacts BIS OSRS
- **No bank or inventory scanning** — owned items are equipped untradeables only (equipment tab)
- **No menu manipulation** — no in-world right-click menu entries
- **No recommender logic** — import the JSON yourself in the BIS OSRS web app

## V1 scope

- Combat **stats** (real levels)
- **Completed quests** mapped to BIS catalog ids
- **Completed diary tiers** (BIS-catalog regions only, threshold-based varbits)
- **Equipped untradeables only**
- `completedUnlockIds` always `[]`
- Optional extras: `accountType`, `schemaVersionLabel` (ignored by BIS v1 import)

## Important v1 behaviour

- **`completedUnlockIds` is always `[]`.** Set unlocks manually in BIS OSRS after import.
- **Owned items = equipped untradeables only.** Bank and inventory are not scanned.
- **Diaries are threshold-based.** Only fully completed tiers in BIS-catalog regions (`karamja`, `kourend_kebos`, `western_provinces`) are exported.

## Development

Requirements: **JDK 11**, IntelliJ IDEA Community (recommended).

```bash
./gradlew test
./gradlew build
./gradlew run
```

Enable **BIS OSRS Quest Exporter** in the RuneLite plugin list, open the sidebar panel, click **Refresh preview**, then **Copy BIS OSRS JSON** or **Save BIS OSRS JSON**.

Import the file on BIS OSRS `/recommend` → **Import profile JSON**.

### Gradle TLS errors

If `./gradlew` fails with `PKIX path building failed`, install your corporate root CA into the JDK trust store. Do not disable TLS globally.

## BIS compatibility

Fixtures in `src/test/resources/fixtures/` are validated by the main BIS OSRS repo:

```bash
cd ..
npm run test:plugin-fixtures
```

## Package

- Java package: `net.bisosrs.profileexport`
- Plugin Hub id: `bis-osrs-quest-exporter`
- Main plugin class: `net.bisosrs.profileexport.BisOsrsPlugin`

## License

BSD 2-Clause (same as RuneLite). See [LICENSE](LICENSE).

---

## Plugin Hub submission

1. **Fork** [runelite/plugin-hub](https://github.com/runelite/plugin-hub).
2. **Add a manifest entry** — create `plugins/bis-osrs-quest-exporter` in your fork (see exact contents below).
3. **Open a PR** against `runelite/plugin-hub` describing the read-only JSON export behaviour.
4. **Wait for review** — fix any CI failures or reviewer comments, push updates, and update the `commit=` hash in the manifest.

After merge, the plugin appears on the RuneLite Plugin Hub once the hub build runs.

### Manifest entry

Add file `plugins/bis-osrs-quest-exporter`:

```
repository=https://github.com/keefedownes/BIS-OSRS-Quest-Exporter.git
commit=<full 40-character commit hash after you push>
```

Replace `commit=` with the latest commit on `main` after your submission-ready changes are pushed.

---

## Release checklist

1. [ ] `./gradlew test`
2. [ ] `./gradlew build`
3. [ ] Manual export → import on BIS OSRS `/recommend`
4. [ ] Push to GitHub; update `commit=` in the Plugin Hub manifest
5. [ ] Open or update PR on [runelite/plugin-hub](https://github.com/runelite/plugin-hub)
