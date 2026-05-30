package net.bisosrs.profileexport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.inject.Inject;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import net.bisosrs.profileexport.BisOsrsProfileExporter.ExportResult;
import net.bisosrs.profileexport.util.ExportActions;
import net.runelite.api.Client;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

public class BisOsrsPanel extends PluginPanel
{
	private final BisOsrsProfileExporter exporter;
	private final Client client;

	private final JLabel statusLabel = new JLabel("Log in, then refresh export preview.", SwingConstants.LEFT);
	private final JLabel statsLabel = new JLabel("Stats: —", SwingConstants.LEFT);
	private final JLabel questsLabel = new JLabel("Quests: —", SwingConstants.LEFT);
	private final JLabel diariesLabel = new JLabel("Diaries: —", SwingConstants.LEFT);
	private final JLabel ownedLabel = new JLabel("Owned items: —", SwingConstants.LEFT);
	private final JLabel accountLabel = new JLabel("Account type: —", SwingConstants.LEFT);

	private ExportResult lastExport;

	@Inject
	BisOsrsPanel(BisOsrsProfileExporter exporter, Client client)
	{
		super(false);
		this.exporter = exporter;
		this.client = client;

		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARK_GRAY_COLOR);
		setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel title = new JLabel("BIS OSRS Export");
		title.setForeground(Color.WHITE);

		JPanel summaryPanel = new JPanel(new GridLayout(0, 1, 0, 4));
		summaryPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		summaryPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

		JLabel summaryHeading = new JLabel("Export summary");
		summaryHeading.setForeground(Color.LIGHT_GRAY);
		summaryPanel.add(summaryHeading);
		styleSummaryLabel(statsLabel);
		styleSummaryLabel(questsLabel);
		styleSummaryLabel(diariesLabel);
		styleSummaryLabel(ownedLabel);
		styleSummaryLabel(accountLabel);
		summaryPanel.add(statsLabel);
		summaryPanel.add(questsLabel);
		summaryPanel.add(diariesLabel);
		summaryPanel.add(ownedLabel);
		summaryPanel.add(accountLabel);

		JPanel buttons = new JPanel(new GridLayout(0, 1, 0, 6));
		buttons.setBackground(ColorScheme.DARK_GRAY_COLOR);

		JButton refreshButton = new JButton("Refresh preview");
		refreshButton.addActionListener(this::refreshPreview);

		JButton copyButton = new JButton("Copy BIS OSRS JSON");
		copyButton.addActionListener(this::copyJson);

		JButton saveButton = new JButton("Save BIS OSRS JSON");
		saveButton.addActionListener(this::saveJson);

		buttons.add(refreshButton);
		buttons.add(copyButton);
		buttons.add(saveButton);

		JPanel north = new JPanel(new BorderLayout(0, 8));
		north.setBackground(ColorScheme.DARK_GRAY_COLOR);
		north.add(title, BorderLayout.NORTH);
		north.add(summaryPanel, BorderLayout.CENTER);
		north.add(buttons, BorderLayout.SOUTH);

		statusLabel.setForeground(Color.LIGHT_GRAY);
		statusLabel.setPreferredSize(new Dimension(0, 40));

		add(north, BorderLayout.NORTH);
		add(statusLabel, BorderLayout.SOUTH);
	}

	private void styleSummaryLabel(JLabel label)
	{
		label.setForeground(Color.WHITE);
	}

	private void refreshPreview(ActionEvent event)
	{
		lastExport = exporter.exportProfile();
		if (!lastExport.isOk())
		{
			statusLabel.setText(lastExport.getMessage());
			statsLabel.setText("Stats: —");
			questsLabel.setText("Quests: —");
			diariesLabel.setText("Diaries: —");
			ownedLabel.setText("Owned items: —");
			accountLabel.setText("Account type: —");
			return;
		}

		ExportSummary summary = lastExport.getSummary();
		statsLabel.setText(summary.statsLine());
		questsLabel.setText(summary.questsLine());
		diariesLabel.setText(summary.diariesLine());
		ownedLabel.setText(summary.ownedItemsLine());
		accountLabel.setText(summary.accountTypeLine());
		statusLabel.setText(summary.getStatusMessage());
	}

	private void copyJson(ActionEvent event)
	{
		ExportResult result = ensureExport();
		if (!result.isOk())
		{
			statusLabel.setText(result.getMessage());
			return;
		}

		ExportActions.copyToClipboard(result.getJson());
		statusLabel.setText("JSON copied to clipboard.");
	}

	private void saveJson(ActionEvent event)
	{
		ExportResult result = ensureExport();
		if (!result.isOk())
		{
			statusLabel.setText(result.getMessage());
			return;
		}

		try
		{
			String username = client.getLocalPlayer() != null ? client.getLocalPlayer().getName() : "";
			java.io.File file = ExportActions.saveToFile(result.getJson(), username);
			if (file != null)
			{
				statusLabel.setText("Saved " + file.getName());
			}
		}
		catch (Exception e)
		{
			statusLabel.setText("Save failed: " + e.getMessage());
		}
	}

	private ExportResult ensureExport()
	{
		if (lastExport != null && lastExport.isOk())
		{
			return lastExport;
		}
		lastExport = exporter.exportProfile();
		if (lastExport.isOk())
		{
			refreshPreview(null);
		}
		return lastExport;
	}
}
