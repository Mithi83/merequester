package com.almostreliable.merequester.compat.wtlib;

import net.minecraft.world.entity.player.Player;

import appeng.menu.ISubMenu;
import appeng.menu.locator.ItemMenuHostLocator;
import de.mari_023.ae2wtlib.api.terminal.ItemWT;
import de.mari_023.ae2wtlib.api.terminal.WTMenuHost;

import java.util.function.BiConsumer;

class ReqWirelessTerminalMenuHost extends WTMenuHost {

    ReqWirelessTerminalMenuHost(
        ItemWT item, Player player, ItemMenuHostLocator locator, BiConsumer<Player, ISubMenu> returnToMainMenu) {
        super(item, player, locator, returnToMainMenu);
    }
}