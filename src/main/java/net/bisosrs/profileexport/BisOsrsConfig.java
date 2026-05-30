package net.bisosrs.profileexport;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("bis-osrs-profile-export")
public interface BisOsrsConfig extends Config
{
	@ConfigItem(
		keyName = "showExportNotes",
		name = "Verbose export notes",
		description = "Include v1 limitation reminders in the exported JSON notes field."
	)
	default boolean showExportNotes()
	{
		return true;
	}
}
