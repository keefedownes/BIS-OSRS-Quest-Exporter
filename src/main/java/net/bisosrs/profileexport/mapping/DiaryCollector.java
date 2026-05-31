package net.bisosrs.profileexport.mapping;

import java.util.ArrayList;
import java.util.List;
import net.bisosrs.profileexport.BisOsrsDiaryTier;
import net.runelite.api.Client;
import net.runelite.api.GameState;

public final class DiaryCollector
{
	private DiaryCollector()
	{
	}

	public static List<BisOsrsDiaryTier> collectCompletedDiaryTiers(Client client, BisCatalog catalog)
	{
		List<BisOsrsDiaryTier> completed = new ArrayList<>();
		if (client == null || client.getGameState() != GameState.LOGGED_IN)
		{
			return completed;
		}

		for (DiaryTierThreshold threshold : DiaryTierThreshold.ENTRIES)
		{
			if (threshold.getConfidence() != DiaryTierThreshold.ThresholdConfidence.HIGH)
			{
				continue;
			}

			if (!catalog.diaryIds().contains(threshold.getDiaryId()))
			{
				continue;
			}

			int value = client.getVarbitValue(threshold.getVarbitId());
			if (threshold.isComplete(value))
			{
				completed.add(new BisOsrsDiaryTier(threshold.getDiaryId(), threshold.getTier()));
			}
		}

		return completed;
	}
}
