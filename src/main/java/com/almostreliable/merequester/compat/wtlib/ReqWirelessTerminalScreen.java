package com.almostreliable.merequester.compat.wtlib;

import com.almostreliable.merequester.client.RequesterTerminalScreen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import appeng.client.gui.style.ScreenStyle;
import appeng.client.gui.widgets.ToolboxPanel;
import de.mari_023.ae2wtlib.api.gui.ScrollingUpgradesPanel;
import de.mari_023.ae2wtlib.api.terminal.IUniversalTerminalCapable;
import de.mari_023.ae2wtlib.api.terminal.WTMenuHost;

class ReqWirelessTerminalScreen extends RequesterTerminalScreen<ReqWirelessTerminalMenu> implements IUniversalTerminalCapable {

    private final ScrollingUpgradesPanel upgradesPanel;

    ReqWirelessTerminalScreen(ReqWirelessTerminalMenu menu, Inventory playerInventory, Component name, ScreenStyle style) {
        super(menu, playerInventory, name, style);
        if (getMenu().isWUT()) {
            addToLeftToolbar(cycleTerminalButton());
        }

        upgradesPanel = addUpgradePanel(widgets, getMenu());
        if (getMenu().getToolbox().isPresent()) {
            widgets.add("toolbox", new ToolboxPanel(style, getMenu().getToolbox().getName()));
        }
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

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int keyPressed) {
        return super.keyPressed(keyCode, scanCode, keyPressed) || checkForTerminalKeys(keyCode, scanCode);
    }
}