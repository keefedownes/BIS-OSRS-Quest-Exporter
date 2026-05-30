package net.bisosrs.profileexport;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
	name = "BIS OSRS Profile Export",
	description = "Read-only export of stats, quests, diaries, and equipped untradeables as JSON for BIS OSRS",
	tags = {"bis", "gear", "export", "profile", "quests", "read-only"}
)
public class BisOsrsPlugin extends Plugin
{
	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private BisOsrsPanel panel;

	private NavigationButton navButton;

	@Override
	protected void startUp()
	{
		BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/icon.png");
		if (icon == null)
		{
			icon = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		}

		navButton = NavigationButton.builder()
			.tooltip("BIS OSRS Export")
			.icon(icon)
			.priority(8)
			.panel(panel)
			.build();

		clientToolbar.addNavigation(navButton);
		log.debug("BIS OSRS Profile Export started");
	}

	@Override
	protected void shutDown()
	{
		clientToolbar.removeNavigation(navButton);
		log.debug("BIS OSRS Profile Export stopped");
	}

	@Provides
	BisOsrsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BisOsrsConfig.class);
	}
}
