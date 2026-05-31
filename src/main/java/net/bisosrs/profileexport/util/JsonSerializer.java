package net.bisosrs.profileexport.util;

import com.google.gson.Gson;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.bisosrs.profileexport.BisOsrsProfile;

@Singleton
public final class JsonSerializer
{
	private final Gson gson;

	@Inject
	JsonSerializer(Gson gson)
	{
		this.gson = gson.newBuilder().setPrettyPrinting().create();
	}

	public String toJson(BisOsrsProfile profile)
	{
		return gson.toJson(profile);
	}
}
