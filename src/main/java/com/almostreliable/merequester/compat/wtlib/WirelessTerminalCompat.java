package com.almostreliable.merequester.compat.wtlib;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import appeng.api.features.GridLinkables;
import appeng.init.client.InitScreens;
import de.mari_023.ae2wtlib.api.gui.Icon;
import de.mari_023.ae2wtlib.api.registration.AddTerminalEvent;

import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings({"NonConstantFieldWithUpperCaseName", "StaticVariableMayNotBeInitialized"})
public final class WirelessTerminalCompat {

    public static final WirelessTerminalCompat INSTANCE = new WirelessTerminalCompat();
    static final String TERMINAL_ID = "wireless_requester_terminal";

    public void init(DeferredRegister.Items itemRegistry, DeferredRegister<MenuType<?>> menuRegistry) {
        if (isLoaded()) {
            Guard.init(itemRegistry, menuRegistry);
        }
    }

    public void initClient(RegisterMenuScreensEvent event) {
        if (isLoaded()) {
            GuardClient.init(event);
        }
    }

    public void registerCapabilities() {
        if (isLoaded()) {
            Guard.registerCapabilities();
        }
    }

    public Iterable<ItemLike> collectItems() {
        if (!isLoaded()) {
            return List.of();
        }

        return Guard.collectItems();
    }

    private boolean isLoaded() {
        return ModList.get().isLoaded("ae2wtlib");
    }

    private WirelessTerminalCompat() {}

    @SuppressWarnings("StaticVariableUsedBeforeInitialization")
    static final class Guard {

        @Nullable
        static DeferredItem<ReqWirelessTerminalItem> WIRELESS_REQUESTER_TERMINAL;
        @Nullable
        static DeferredHolder<MenuType<?>, MenuType<ReqWirelessTerminalMenu>> WIRELESS_REQUESTER_TERMINAL_MENU;

        private static void init(DeferredRegister.Items itemRegistry, DeferredRegister<MenuType<?>> menuRegistry) {

            WIRELESS_REQUESTER_TERMINAL = itemRegistry.registerItem(
                TERMINAL_ID,
                properties -> new ReqWirelessTerminalItem()
            );
            WIRELESS_REQUESTER_TERMINAL_MENU = menuRegistry.register(
                TERMINAL_ID,
                () -> ReqWirelessTerminalMenu.TYPE
            );

            AddTerminalEvent.register(event -> event.builder(
                    "requester", ReqWirelessTerminalMenuHost::new, ReqWirelessTerminalMenu.TYPE, WIRELESS_REQUESTER_TERMINAL.get(),
                    Icon.PATTERN_ACCESS
                )
                .addTerminal());
        }

        private static void registerCapabilities() {
            assert WIRELESS_REQUESTER_TERMINAL != null;
            GridLinkables.register(WIRELESS_REQUESTER_TERMINAL, appeng.items.tools.powered.WirelessTerminalItem.LINKABLE_HANDLER);
        }

        private static Iterable<ItemLike> collectItems() {
            assert WIRELESS_REQUESTER_TERMINAL != null;
            return List.of(WIRELESS_REQUESTER_TERMINAL.get());
        }

        private Guard() {}
    }

    private static final class GuardClient {

        private static void init(RegisterMenuScreensEvent event) {
            InitScreens.register(
                event,
                ReqWirelessTerminalMenu.TYPE,
                ReqWirelessTerminalScreen::new,
                String.format("/screens/%s.json", TERMINAL_ID)
            );
        }
    }
}
