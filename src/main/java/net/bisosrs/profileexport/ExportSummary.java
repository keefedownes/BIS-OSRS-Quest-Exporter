package net.bisosrs.profileexport;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExportSummary
{
	int statsExported;
	int questsExported;
	int diariesExported;
	int ownedItemsExported;
	String accountTypeLabel;
	String statusMessage;

	public String statsLine()
	{
		return "Stats: " + statsExported + " exported";
	}

	public String questsLine()
	{
		return "Quests: " + questsExported + " exported";
	}

	public String diariesLine()
	{
		return "Diaries: " + diariesExported + " exported";
	}

	public String ownedItemsLine()
	{
		return "Owned items: " + ownedItemsExported + " exported";
	}

	public String accountTypeLine()
	{
		return "Account type: " + accountTypeLabel;
	}
}
