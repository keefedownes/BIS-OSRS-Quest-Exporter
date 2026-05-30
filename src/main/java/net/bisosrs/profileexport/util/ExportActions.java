package net.bisosrs.profileexport.util;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.runelite.client.util.Text;

public final class ExportActions
{
	private ExportActions()
	{
	}

	public static void copyToClipboard(String json)
	{
		StringSelection selection = new StringSelection(json);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
	}

	public static File saveToFile(String json, String usernameHint) throws IOException
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Save BIS OSRS profile JSON");
		chooser.setSelectedFile(new File(defaultFilename(usernameHint)));
		chooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));

		int result = chooser.showSaveDialog(null);
		if (result != JFileChooser.APPROVE_OPTION)
		{
			return null;
		}

		File file = chooser.getSelectedFile();
		if (!file.getName().toLowerCase().endsWith(".json"))
		{
			file = new File(file.getAbsolutePath() + ".json");
		}

		Files.writeString(file.toPath(), json, StandardCharsets.UTF_8);
		return file;
	}

	public static String defaultFilename(String usernameHint)
	{
		String safe = Text.standardize(usernameHint == null ? "" : usernameHint)
			.replace(' ', '-')
			.replaceAll("[^a-zA-Z0-9\\-_]", "");
		if (safe.isEmpty())
		{
			return "bisosrs-profile.json";
		}
		return "bisosrs-profile-" + safe + ".json";
	}
}
