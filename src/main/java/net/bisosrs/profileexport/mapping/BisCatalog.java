package net.bisosrs.profileexport.mapping;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Quest;

@Singleton
public final class BisCatalog
{
	private final Map<Quest, String> questIdsByQuest;
	private final List<String> diaryIds;

	@Inject
	BisCatalog(Gson gson)
	{
		InputStream stream = BisCatalog.class.getResourceAsStream("/bis_catalog.json");
		if (stream == null)
		{
			throw new IllegalStateException("Missing bis_catalog.json resource");
		}

		CatalogFile file = gson.fromJson(
			new InputStreamReader(stream, StandardCharsets.UTF_8),
			CatalogFile.class
		);

		Map<Quest, String> quests = new LinkedHashMap<>();
		for (CatalogQuest entry : file.quests)
		{
			try
			{
				Quest quest = Quest.valueOf(entry.runeliteQuest);
				quests.put(quest, entry.id);
			}
			catch (IllegalArgumentException ignored)
			{
				// Unknown quest enum — skip rather than fail export.
			}
		}

		List<String> diaries = file.diaries == null
			? Collections.emptyList()
			: file.diaries.stream().map(d -> d.id).collect(java.util.stream.Collectors.toList());

		this.questIdsByQuest = Collections.unmodifiableMap(quests);
		this.diaryIds = List.copyOf(diaries);
	}

	public Map<Quest, String> questIdsByQuest()
	{
		return questIdsByQuest;
	}

	public List<String> diaryIds()
	{
		return diaryIds;
	}

	private static final class CatalogFile
	{
		List<CatalogQuest> quests;
		List<CatalogDiary> diaries;
	}

	private static final class CatalogQuest
	{
		String id;

		@SerializedName("runeliteQuest")
		String runeliteQuest;
	}

	private static final class CatalogDiary
	{
		String id;
	}
}
