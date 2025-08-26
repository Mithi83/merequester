package com.almostreliable.merequester.compat.wtlib;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;

import appeng.menu.locator.ItemMenuHostLocator;
import de.mari_023.ae2wtlib.api.terminal.ItemWT;

class ReqWirelessTerminalItem extends ItemWT {

    @Override
    public MenuType<?> getMenuType(ItemMenuHostLocator itemMenuHostLocator, Player player) {
        return ReqWirelessTerminalMenu.TYPE;
    }
}