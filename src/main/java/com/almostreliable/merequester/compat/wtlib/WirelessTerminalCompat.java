package com.almostreliable.merequester.compat.wtlib;

import com.almostreliable.merequester.Utils;

import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import appeng.api.features.GridLinkables;
import appeng.init.client.InitScreens;
import appeng.items.tools.powered.WirelessTerminalItem;
import appeng.items.tools.powered.powersink.PoweredItemCapabilities;
import de.mari_023.ae2wtlib.api.gui.Icon;
import de.mari_023.ae2wtlib.api.registration.AddTerminalEvent;

import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings({"NonConstantFieldWithUpperCaseName", "StaticVariableMayNotBeInitialized"})
public final class WirelessTerminalCompat {

    public static final WirelessTerminalCompat INSTANCE = new WirelessTerminalCompat();
    static final String TERMINAL_ID = "wireless_requester_terminal";

    public void init(DeferredRegister<MenuType<?>> menuRegistry) {
        if (isLoaded()) {
            Guard.init(menuRegistry);
        }
    }

    public void initClient(RegisterMenuScreensEvent event) {
        if (isLoaded()) {
            GuardClient.init(event);
        }
    }

    public void registerWirelessTerminal(Registry<Item> registry) {
        if (isLoaded()) {
            Guard.registerWirelessTerminal(registry);
        }
    }

    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        if (isLoaded()) {
            Guard.registerCapabilities(event);
        }
    }

    public Iterable<ItemLike> collectItems() {
        if (!isLoaded()) {
            return List.of();
        }

        return Guard.collectItems();
    }

    private boolean isLoaded() {
        return LoadingModList.get().getModFileById("ae2wtlib") != null;
    }

    private WirelessTerminalCompat() {}

    @SuppressWarnings("StaticVariableUsedBeforeInitialization")
    static final class Guard {

        @Nullable
        static ReqWirelessTerminalItem WIRELESS_REQUESTER_TERMINAL;
        @Nullable
        static DeferredHolder<MenuType<?>, MenuType<ReqWirelessTerminalMenu>> WIRELESS_REQUESTER_TERMINAL_MENU;

        private static void init(DeferredRegister<MenuType<?>> menuRegistry) {
            WIRELESS_REQUESTER_TERMINAL_MENU = menuRegistry.register(
                TERMINAL_ID,
                () -> ReqWirelessTerminalMenu.TYPE
            );
        }

        public static void registerWirelessTerminal(Registry<Item> registry) {
            WIRELESS_REQUESTER_TERMINAL = new ReqWirelessTerminalItem();
            Registry.register(registry, Utils.getRL(TERMINAL_ID), WIRELESS_REQUESTER_TERMINAL);
            AddTerminalEvent.register(event -> event.builder(
                    "requester", ReqWirelessTerminalMenuHost::new, ReqWirelessTerminalMenu.TYPE, WIRELESS_REQUESTER_TERMINAL,
                    Icon.PATTERN_ACCESS
                )
                .addTerminal());
        }

        private static void registerCapabilities(RegisterCapabilitiesEvent event) {
            assert WIRELESS_REQUESTER_TERMINAL != null;
            GridLinkables.register(WIRELESS_REQUESTER_TERMINAL, WirelessTerminalItem.LINKABLE_HANDLER);
            event.registerItem(
                Capabilities.EnergyStorage.ITEM,
                (stack, context) -> new PoweredItemCapabilities(stack, WIRELESS_REQUESTER_TERMINAL),
                WIRELESS_REQUESTER_TERMINAL
            );
        }

        private static Iterable<ItemLike> collectItems() {
            assert WIRELESS_REQUESTER_TERMINAL != null;
            return List.of(WIRELESS_REQUESTER_TERMINAL);
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
