package net.bisosrs.profileexport;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BisOsrsStats
{
	int attack;
	int strength;
	int defence;
	int ranged;
	int magic;
	int prayer;
	int hitpoints;

	public static BisOsrsStats empty()
	{
		return BisOsrsStats.builder().build();
	}
}
