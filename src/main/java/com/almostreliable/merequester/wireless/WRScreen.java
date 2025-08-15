package com.almostreliable.merequester.wireless;

import appeng.client.gui.style.ScreenStyle;
import appeng.client.gui.widgets.BackgroundPanel;
import appeng.client.gui.widgets.ToolboxPanel;
import appeng.client.gui.widgets.UpgradesPanel;
import appeng.menu.SlotSemantics;

import com.almostreliable.merequester.client.RequesterTerminalScreen;

import de.mari_023.ae2wtlib.api.terminal.IUniversalTerminalCapable;
import de.mari_023.ae2wtlib.api.terminal.WTMenuHost;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class WRScreen extends RequesterTerminalScreen<WRMenu> implements IUniversalTerminalCapable {
private final ScrollingUpgradesPanel upgradesPanel;

    public WRScreen(WRMenu menu, Inventory playerInventory, Component name, ScreenStyle style) {
        super(menu, playerInventory, name, style);
        if (getMenu().isWUT())
            addToLeftToolbar(cycleTerminalButton());

        upgradesPanel = addUpgradePanel(widgets, getMenu());
        if (getMenu().getToolbox().isPresent())
            widgets.add("toolbox", new ToolboxPanel(style, getMenu().getToolbox().getName()));
    }

@Override
    public void init() {
        super.init();
        upgradesPanel.setMaxRows(Math.max(2, rowAmount));
    }

    @Override
    public WTMenuHost getHost() {
        return (WTMenuHost) getMenu().getHost();
    }

    @Override
    public void storeState() {}
}