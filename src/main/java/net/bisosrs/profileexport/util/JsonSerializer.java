package net.bisosrs.profileexport.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.bisosrs.profileexport.BisOsrsProfile;

public final class JsonSerializer
{
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private JsonSerializer()
	{
	}

	public static String toJson(BisOsrsProfile profile)
	{
		return GSON.toJson(profile);
	}
}
