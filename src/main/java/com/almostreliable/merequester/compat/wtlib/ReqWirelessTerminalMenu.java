package com.almostreliable.merequester.compat.wtlib;

import com.almostreliable.merequester.Utils;
import com.almostreliable.merequester.terminal.RequesterTerminalMenu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

import appeng.api.storage.ITerminalHost;
import appeng.api.upgrades.IUpgradeInventory;
import appeng.menu.SlotSemantics;
import appeng.menu.ToolboxMenu;
import appeng.menu.implementations.MenuTypeBuilder;
import appeng.menu.slot.RestrictedInputSlot;
import de.mari_023.ae2wtlib.api.gui.AE2wtlibSlotSemantics;
import de.mari_023.ae2wtlib.api.terminal.ItemWUT;
import de.mari_023.ae2wtlib.api.terminal.WTMenuHost;

final class ReqWirelessTerminalMenu extends RequesterTerminalMenu {

    static final MenuType<ReqWirelessTerminalMenu> TYPE = MenuTypeBuilder
        .create(ReqWirelessTerminalMenu::new, ReqWirelessTerminalMenuHost.class)
        .buildUnregistered(Utils.getRL(WirelessTerminalCompat.TERMINAL_ID));

    private final ReqWirelessTerminalMenuHost wrMenuHost;
    private final ToolboxMenu toolboxMenu;

    private ReqWirelessTerminalMenu(int id, Inventory playerInventory, ReqWirelessTerminalMenuHost host) {
        super(TYPE, id, playerInventory, host);
        wrMenuHost = host;
        toolboxMenu = new ToolboxMenu(this);

        IUpgradeInventory upgrades = wrMenuHost.getUpgrades();
        for (int i = 0; i < upgrades.size(); i++) {
            var slot = new RestrictedInputSlot(RestrictedInputSlot.PlacableItemType.UPGRADES, upgrades, i);
            slot.setNotDraggable();
            addSlot(slot, SlotSemantics.UPGRADE);
        }
        addSlot(
            new RestrictedInputSlot(
                RestrictedInputSlot.PlacableItemType.QE_SINGULARITY,
                wrMenuHost.getSubInventory(WTMenuHost.INV_SINGULARITY),
                0
            ), AE2wtlibSlotSemantics.SINGULARITY
        );
    }

    @Override
    public void broadcastChanges() {
        toolboxMenu.tick();
        super.broadcastChanges();
    }

    boolean isWUT() {
        return wrMenuHost.getItemStack().getItem() instanceof ItemWUT;
    }

    ITerminalHost getHost() {
        return wrMenuHost;
    }

    ToolboxMenu getToolbox() {
        return toolboxMenu;
    }
}