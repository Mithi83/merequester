package com.almostreliable.merequester.wireless;

import appeng.menu.locator.ItemMenuHostLocator;
import de.mari_023.ae2wtlib.api.terminal.ItemWT;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
//import net.minecraft.world.item.ItemStack;

public class ItemWR extends ItemWT {

    @Override
    public MenuType<?> getMenuType(ItemMenuHostLocator itemMenuHostLocator, Player player) {
        return WRMenu.TYPE;
    }
}