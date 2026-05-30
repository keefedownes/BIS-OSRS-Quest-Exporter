package net.bisosrs.profileexport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BisOsrsProfile
{
	public static final int EXPORT_VERSION = 1;
	public static final String EXPORT_SOURCE = "runelite-plugin";
	public static final String SCHEMA_VERSION_LABEL = "2026-05";

	int version;
	String source;
	String exportedAt;
	String notes;
	BisOsrsStats stats;
	List<String> completedQuestIds;
	List<BisOsrsDiaryTier> completedDiaryTiers;
	List<String> completedUnlockIds;
	List<Integer> ownedItemIds;
	List<String> ownedItemNames;
	List<String> ownedItemLines;
	String accountType;
	String schemaVersionLabel;

	public static BisOsrsProfile emptyShell()
	{
		return BisOsrsProfile.builder()
			.version(EXPORT_VERSION)
			.source(EXPORT_SOURCE)
			.exportedAt("")
			.notes("")
			.stats(BisOsrsStats.empty())
			.completedQuestIds(Collections.emptyList())
			.completedDiaryTiers(Collections.emptyList())
			.completedUnlockIds(Collections.emptyList())
			.ownedItemIds(Collections.emptyList())
			.ownedItemNames(Collections.emptyList())
			.ownedItemLines(Collections.emptyList())
			.accountType("unknown")
			.schemaVersionLabel(SCHEMA_VERSION_LABEL)
			.build();
	}

	public BisOsrsProfile withMutableCollections()
	{
		return BisOsrsProfile.builder()
			.version(version)
			.source(source)
			.exportedAt(exportedAt)
			.notes(notes)
			.stats(stats)
			.completedQuestIds(new ArrayList<>(completedQuestIds))
			.completedDiaryTiers(new ArrayList<>(completedDiaryTiers))
			.completedUnlockIds(new ArrayList<>(completedUnlockIds))
			.ownedItemIds(new ArrayList<>(ownedItemIds))
			.ownedItemNames(new ArrayList<>(ownedItemNames))
			.ownedItemLines(new ArrayList<>(ownedItemLines))
			.accountType(accountType)
			.schemaVersionLabel(schemaVersionLabel)
			.build();
	}
}
