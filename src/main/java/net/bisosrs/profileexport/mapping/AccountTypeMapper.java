package net.bisosrs.profileexport.mapping;

import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Varbits;

public final class AccountTypeMapper
{
	private AccountTypeMapper()
	{
	}

	public static String map(Client client)
	{
		if (client == null || client.getGameState() != GameState.LOGGED_IN)
		{
			return "unknown";
		}

		int value = client.getVarbitValue(Varbits.ACCOUNT_TYPE);
		switch (value)
		{
			case 0:
				return "normal";
			case 1:
				return "ironman";
			case 2:
				return "ultimate_ironman";
			case 3:
				return "hardcore_ironman";
			case 4:
			case 5:
			case 6:
				return "group_ironman";
			default:
				return "unknown";
		}
	}

	public static String displayLabel(String accountType)
	{
		switch (accountType)
		{
			case "normal":
				return "Normal";
			case "ironman":
				return "Ironman";
			case "ultimate_ironman":
				return "Ultimate Ironman";
			case "hardcore_ironman":
				return "Hardcore Ironman";
			case "group_ironman":
				return "Group Ironman";
			default:
				return "Unknown";
		}
	}
}
