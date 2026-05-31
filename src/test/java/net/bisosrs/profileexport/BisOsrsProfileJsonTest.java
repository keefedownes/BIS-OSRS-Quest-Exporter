package net.bisosrs.profileexport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import java.util.Collections;
import net.bisosrs.profileexport.util.JsonSerializer;
import net.runelite.http.api.RuneLiteAPI;
import org.junit.Test;

public class BisOsrsProfileJsonTest
{
	@Test
	public void serializesRequiredV1Fields()
	{
		JsonSerializer jsonSerializer = Guice.createInjector(new AbstractModule()
		{
			@Override
			protected void configure()
			{
				bind(Gson.class).toInstance(RuneLiteAPI.GSON);
				bind(JsonSerializer.class);
			}
		}).getInstance(JsonSerializer.class);

		BisOsrsProfile profile = BisOsrsProfile.builder()
			.version(BisOsrsProfile.EXPORT_VERSION)
			.source(BisOsrsProfile.EXPORT_SOURCE)
			.exportedAt("2026-05-30T18:00:00.000Z")
			.notes("test export")
			.stats(BisOsrsStats.builder()
				.attack(75)
				.strength(75)
				.defence(70)
				.ranged(75)
				.magic(75)
				.prayer(69)
				.hitpoints(80)
				.build())
			.completedQuestIds(Collections.singletonList("dragon_slayer_i"))
			.completedDiaryTiers(Collections.singletonList(new BisOsrsDiaryTier("karamja", "hard")))
			.completedUnlockIds(Collections.emptyList())
			.ownedItemIds(Collections.singletonList(6570))
			.ownedItemNames(Collections.singletonList("Fire cape"))
			.ownedItemLines(java.util.Arrays.asList("6570", "Fire cape"))
			.accountType("ironman")
			.schemaVersionLabel(BisOsrsProfile.SCHEMA_VERSION_LABEL)
			.build();

		JsonObject json = JsonParser.parseString(jsonSerializer.toJson(profile)).getAsJsonObject();
		assertEquals(1, json.get("version").getAsInt());
		assertEquals("runelite-plugin", json.get("source").getAsString());
		assertTrue(json.get("completedUnlockIds").getAsJsonArray().isEmpty());
		assertEquals("ironman", json.get("accountType").getAsString());
		assertEquals("2026-05", json.get("schemaVersionLabel").getAsString());
	}
}
