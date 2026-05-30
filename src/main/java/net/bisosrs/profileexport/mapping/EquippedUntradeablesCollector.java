package net.bisosrs.profileexport.mapping;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.ItemContainer;
import net.runelite.client.game.ItemManager;

public final class EquippedUntradeablesCollector
{
	public static final class OwnedItemExport
	{
		private final List<Integer> ids = new ArrayList<>();
		private final List<String> names = new ArrayList<>();

		public List<Integer> getIds()
		{
			return ids;
		}

		public List<String> getNames()
		{
			return names;
		}

		public List<String> getLines()
		{
			List<String> lines = new ArrayList<>();
			for (int i = 0; i < ids.size(); i++)
			{
				lines.add(String.valueOf(ids.get(i)));
				lines.add(names.get(i));
			}
			return lines;
		}
	}

	private EquippedUntradeablesCollector()
	{
	}

	public static OwnedItemExport collect(Client client, ItemManager itemManager)
	{
		OwnedItemExport export = new OwnedItemExport();
		if (client == null || itemManager == null || client.getGameState() != GameState.LOGGED_IN)
		{
			return export;
		}

		ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);
		if (equipment == null)
		{
			return export;
		}

		Set<Integer> seen = new LinkedHashSet<>();
		for (Item item : equipment.getItems())
		{
			if (item == null || item.getId() <= 0)
			{
				continue;
			}

			int itemId = item.getId();
			if (!seen.add(itemId))
			{
				continue;
			}

			ItemComposition composition = itemManager.getItemComposition(itemId);
			if (composition == null || composition.isTradeable())
			{
				continue;
			}

			export.ids.add(itemId);
			export.names.add(composition.getName());
		}

		return export;
	}
}
