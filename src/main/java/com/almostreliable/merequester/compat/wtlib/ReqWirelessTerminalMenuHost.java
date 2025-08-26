package com.almostreliable.merequester.compat.wtlib;

import com.almostreliable.merequester.terminal.RequesterTerminalHost;

import net.minecraft.world.entity.player.Player;

import appeng.api.storage.ITerminalHost;
import appeng.menu.ISubMenu;
import appeng.menu.locator.ItemMenuHostLocator;
import de.mari_023.ae2wtlib.api.terminal.ItemWT;
import de.mari_023.ae2wtlib.api.terminal.WTMenuHost;

import java.util.function.BiConsumer;

public class ReqWirelessTerminalMenuHost extends WTMenuHost implements ITerminalHost, RequesterTerminalHost {

    public ReqWirelessTerminalMenuHost(
        ItemWT item, Player player, ItemMenuHostLocator locator, BiConsumer<Player, ISubMenu> returnToMainMenu) {
        super(item, player, locator, returnToMainMenu);
    }
}