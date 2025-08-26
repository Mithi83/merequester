package com.almostreliable.merequester;

import com.almostreliable.merequester.client.RequesterScreen;
import com.almostreliable.merequester.client.RequesterTerminalScreen;
import com.almostreliable.merequester.compat.wtlib.WirelessTerminalCompat;
import com.almostreliable.merequester.core.Registration;
import com.almostreliable.merequester.requester.RequesterMenu;
import com.almostreliable.merequester.terminal.RequesterTerminalMenu;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import appeng.api.util.AEColor;
import appeng.client.render.StaticItemColor;
import appeng.init.client.InitScreens;

@Mod(value = ModConstants.MOD_ID, dist = Dist.CLIENT)
public final class MERequesterClient {

    public MERequesterClient(IEventBus modEventBus) {
        modEventBus.addListener(this::registerScreens);
        modEventBus.addListener(this::registerColors);
    }

    @SuppressWarnings("RedundantTypeArguments")
    private void registerScreens(RegisterMenuScreensEvent event) {
        InitScreens.register(event, RequesterMenu.TYPE, RequesterScreen::new, String.format("/screens/%s.json", MERequester.REQUESTER_ID));
        InitScreens.<RequesterTerminalMenu, RequesterTerminalScreen<RequesterTerminalMenu>> register(
            event,
            RequesterTerminalMenu.TYPE,
            RequesterTerminalScreen::new,
            String.format("/screens/%s.json", MERequester.TERMINAL_ID)
        );
        WirelessTerminalCompat.INSTANCE.initClient(event);
    }

    private void registerColors(RegisterColorHandlersEvent.Item event) {
        event.register(makeOpaque(new StaticItemColor(AEColor.TRANSPARENT)), Registration.REQUESTER_TERMINAL);
    }

    private static ItemColor makeOpaque(ItemColor itemColor) {
        return (stack, tintIndex) -> FastColor.ARGB32.opaque(itemColor.getColor(stack, tintIndex));
    }
}
