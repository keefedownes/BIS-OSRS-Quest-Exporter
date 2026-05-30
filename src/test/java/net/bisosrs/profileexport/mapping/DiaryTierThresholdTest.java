package net.bisosrs.profileexport.mapping;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DiaryTierThresholdTest
{
	@Test
	public void karamjaEasyCompleteAtFullBitmask()
	{
		DiaryTierThreshold threshold = DiaryTierThreshold.ENTRIES[0];
		assertTrue(threshold.isComplete(1023));
		assertFalse(threshold.isComplete(512));
		assertFalse(threshold.isComplete(0));
	}

	@Test
	public void rejectsInvalidTaskCounts()
	{
		DiaryTierThreshold invalid = DiaryTierThreshold.of(
			"test",
			"easy",
			1,
			0,
			DiaryTierThreshold.ThresholdConfidence.LOW
		);
		assertFalse(invalid.isComplete(1));
	}
}
