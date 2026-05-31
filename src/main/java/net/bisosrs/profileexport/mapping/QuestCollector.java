package net.bisosrs.profileexport.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.runelite.api.Client;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;

public final class QuestCollector
{
	private QuestCollector()
	{
	}

	public static List<String> collectCompletedQuestIds(Client client, BisCatalog catalog)
	{
		List<String> completed = new ArrayList<>();
		Map<Quest, String> questCatalog = catalog.questIdsByQuest();

		for (Map.Entry<Quest, String> entry : questCatalog.entrySet())
		{
			Quest quest = entry.getKey();
			if (quest.getState(client) == QuestState.FINISHED)
			{
				completed.add(entry.getValue());
			}
		}

		completed.sort(String::compareTo);
		return completed;
	}
}
