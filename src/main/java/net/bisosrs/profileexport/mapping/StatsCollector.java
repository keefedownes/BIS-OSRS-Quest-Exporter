package net.bisosrs.profileexport.mapping;

import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Skill;
import net.bisosrs.profileexport.BisOsrsStats;

public final class StatsCollector
{
	private StatsCollector()
	{
	}

	public static BisOsrsStats collect(Client client)
	{
		if (client == null || client.getGameState() != GameState.LOGGED_IN)
		{
			return BisOsrsStats.empty();
		}

		return BisOsrsStats.builder()
			.attack(client.getRealSkillLevel(Skill.ATTACK))
			.strength(client.getRealSkillLevel(Skill.STRENGTH))
			.defence(client.getRealSkillLevel(Skill.DEFENCE))
			.ranged(client.getRealSkillLevel(Skill.RANGED))
			.magic(client.getRealSkillLevel(Skill.MAGIC))
			.prayer(client.getRealSkillLevel(Skill.PRAYER))
			.hitpoints(client.getRealSkillLevel(Skill.HITPOINTS))
			.build();
	}

	public static int exportedStatCount(BisOsrsStats stats)
	{
		return 7;
	}
}
