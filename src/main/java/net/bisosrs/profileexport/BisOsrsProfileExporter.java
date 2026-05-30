package net.bisosrs.profileexport;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.bisosrs.profileexport.mapping.AccountTypeMapper;
import net.bisosrs.profileexport.mapping.DiaryCollector;
import net.bisosrs.profileexport.mapping.EquippedUntradeablesCollector;
import net.bisosrs.profileexport.mapping.EquippedUntradeablesCollector.OwnedItemExport;
import net.bisosrs.profileexport.mapping.QuestCollector;
import net.bisosrs.profileexport.mapping.StatsCollector;
import net.bisosrs.profileexport.util.JsonSerializer;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.client.game.ItemManager;

@Singleton
public class BisOsrsProfileExporter
{
	@Inject
	private Client client;

	@Inject
	private ItemManager itemManager;

	public ExportResult exportProfile()
	{
		if (client.getGameState() != GameState.LOGGED_IN)
		{
			return ExportResult.failure("Log in to export your BIS OSRS profile.");
		}

		String accountType = AccountTypeMapper.map(client);
		BisOsrsStats stats = StatsCollector.collect(client);
		List<String> quests = QuestCollector.collectCompletedQuestIds(client);
		List<BisOsrsDiaryTier> diaries = DiaryCollector.collectCompletedDiaryTiers(client);
		OwnedItemExport owned = EquippedUntradeablesCollector.collect(client, itemManager);

		List<String> notes = new ArrayList<>();
		notes.add("Exported from RuneLite (BIS OSRS Quest Exporter v1).");
		notes.add("Unlocks are not exported in v1 — set them manually in BIS OSRS.");
		notes.add("Owned items include equipped untradeables only (not bank or inventory).");
		if (!diaries.isEmpty())
		{
			notes.add("Diary tiers use threshold-based varbit completion for BIS-catalog regions only.");
		}

		BisOsrsProfile profile = BisOsrsProfile.builder()
			.version(BisOsrsProfile.EXPORT_VERSION)
			.source(BisOsrsProfile.EXPORT_SOURCE)
			.exportedAt(Instant.now().toString())
			.notes(String.join(" ", notes))
			.stats(stats)
			.completedQuestIds(quests)
			.completedDiaryTiers(diaries)
			.completedUnlockIds(Collections.emptyList())
			.ownedItemIds(owned.getIds())
			.ownedItemNames(owned.getNames())
			.ownedItemLines(owned.getLines())
			.accountType(accountType)
			.schemaVersionLabel(BisOsrsProfile.SCHEMA_VERSION_LABEL)
			.build();

		ExportSummary summary = ExportSummary.builder()
			.statsExported(StatsCollector.exportedStatCount(stats))
			.questsExported(quests.size())
			.diariesExported(diaries.size())
			.ownedItemsExported(owned.getIds().size())
			.accountTypeLabel(AccountTypeMapper.displayLabel(accountType))
			.statusMessage("Ready to copy or save.")
			.build();

		return ExportResult.success(profile, summary, JsonSerializer.toJson(profile));
	}

	@lombok.Value
	public static class ExportResult
	{
		boolean ok;
		String message;
		BisOsrsProfile profile;
		ExportSummary summary;
		String json;

		static ExportResult success(BisOsrsProfile profile, ExportSummary summary, String json)
		{
			return new ExportResult(true, summary.getStatusMessage(), profile, summary, json);
		}

		static ExportResult failure(String message)
		{
			return new ExportResult(false, message, null, null, null);
		}
	}
}
