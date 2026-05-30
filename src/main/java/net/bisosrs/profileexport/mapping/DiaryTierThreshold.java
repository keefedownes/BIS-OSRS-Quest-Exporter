package net.bisosrs.profileexport.mapping;

import lombok.Value;
import net.runelite.api.Varbits;

@Value
public class DiaryTierThreshold
{
	String diaryId;
	String tier;
	int varbitId;
	int taskCount;
	ThresholdConfidence confidence;

	public boolean isComplete(int varbitValue)
	{
		if (taskCount <= 0 || taskCount > 30)
		{
			return false;
		}

		int fullMask = (1 << taskCount) - 1;
		return varbitValue > 0 && (varbitValue & fullMask) == fullMask;
	}

	public enum ThresholdConfidence
	{
		HIGH,
		LOW
	}

	static DiaryTierThreshold of(String diaryId, String tier, int varbitId, int taskCount, ThresholdConfidence confidence)
	{
		return new DiaryTierThreshold(diaryId, tier, varbitId, taskCount, confidence);
	}

	static final DiaryTierThreshold[] ENTRIES = {
		// Karamja — OSRS wiki task counts; bitmask completion (all bits set).
		of("karamja", "easy", Varbits.DIARY_KARAMJA_EASY, 10, ThresholdConfidence.HIGH),
		of("karamja", "medium", Varbits.DIARY_KARAMJA_MEDIUM, 19, ThresholdConfidence.HIGH),
		of("karamja", "hard", Varbits.DIARY_KARAMJA_HARD, 17, ThresholdConfidence.HIGH),
		of("karamja", "elite", Varbits.DIARY_KARAMJA_ELITE, 5, ThresholdConfidence.HIGH),

		// Kourend & Kebos — mapped to BIS id kourend_kebos.
		of("kourend_kebos", "easy", Varbits.DIARY_KOUREND_EASY, 12, ThresholdConfidence.HIGH),
		of("kourend_kebos", "medium", Varbits.DIARY_KOUREND_MEDIUM, 13, ThresholdConfidence.HIGH),
		of("kourend_kebos", "hard", Varbits.DIARY_KOUREND_HARD, 10, ThresholdConfidence.HIGH),
		of("kourend_kebos", "elite", Varbits.DIARY_KOUREND_ELITE, 8, ThresholdConfidence.HIGH),

		// Western Provinces.
		of("western_provinces", "easy", Varbits.DIARY_WESTERN_EASY, 11, ThresholdConfidence.HIGH),
		of("western_provinces", "medium", Varbits.DIARY_WESTERN_MEDIUM, 13, ThresholdConfidence.HIGH),
		of("western_provinces", "hard", Varbits.DIARY_WESTERN_HARD, 13, ThresholdConfidence.HIGH),
		of("western_provinces", "elite", Varbits.DIARY_WESTERN_ELITE, 7, ThresholdConfidence.HIGH),
	};
}
