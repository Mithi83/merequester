package com.almostreliable.merequester.wireless;

import appeng.api.storage.ITerminalHost;
import appeng.menu.ISubMenu;
import appeng.menu.locator.ItemMenuHostLocator;

import de.mari_023.ae2wtlib.api.terminal.ItemWT;
import de.mari_023.ae2wtlib.api.terminal.WTMenuHost;

import net.minecraft.world.entity.player.Player;

import java.util.function.BiConsumer;

public class WRMenuHost extends WTMenuHost implements ITerminalHost {

    public WRMenuHost(ItemWT item, Player player, ItemMenuHostLocator locator, BiConsumer<Player, ISubMenu> returnToMainMenu) {
        super(item, player, locator, returnToMainMenu);
    }
}