package com.almostreliable.merequester.wireless;

import appeng.api.storage.ITerminalHost;
import appeng.api.upgrades.IUpgradeInventory;
import appeng.menu.SlotSemantics;
import appeng.menu.ToolboxMenu;
import appeng.menu.implementations.MenuTypeBuilder;
import appeng.menu.slot.RestrictedInputSlot;

import com.almostreliable.merequester.MERequester;
import com.almostreliable.merequester.Utils;
import com.almostreliable.merequester.terminal.RequesterTerminalMenu;

import de.mari_023.ae2wtlib.api.terminal.ItemWUT;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class WRMenu extends RequesterTerminalMenu {

    public static final MenuType<WRMenu> TYPE = MenuTypeBuilder
        .create(WRMenu::new, WRMenuHost.class)
        .build(Utils.getRL(MERequester.WIRELESS_TERMINAL_ID));

    private final WRMenuHost wrMenuHost;
    private final ToolboxMenu toolboxMenu;

    public WRMenu(int id, Inventory playerInventory, WRMenuHost host) {
        super(TYPE, id, playerInventory, host);
        wrMenuHost = host;
        toolboxMenu = new ToolboxMenu(this);

        IUpgradeInventory upgrades = wrMenuHost.getUpgrades();
        for (int i = 0; i < upgrades.size(); i++) {
            var slot = new RestrictedInputSlot(RestrictedInputSlot.PlacableItemType.UPGRADES, upgrades, i);
            slot.setNotDraggable();
            addSlot(slot, SlotSemantics.UPGRADE);
        }
    }

    @Override
    public void broadcastChanges() {
        toolboxMenu.tick();
        super.broadcastChanges();
    }

    public boolean isWUT() {
        return wrMenuHost.getItemStack().getItem() instanceof ItemWUT;
    }

    public ITerminalHost getHost() {
        return wrMenuHost;
    }

    public ToolboxMenu getToolbox() {
        return toolboxMenu;
    }
}