package net.bisosrs.profileexport;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BisOsrsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BisOsrsPlugin.class);
		RuneLite.main(args);
	}
}
