package com.almostreliable.merequester.requester;

import net.minecraft.world.item.ItemStack;

import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

import appeng.api.inventories.InternalInventory;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.GenericStack;
import com.google.common.primitives.Ints;

import org.jspecify.annotations.Nullable;

final class RequesterConfigInventory implements InternalInventory {

    private final RequestManager manager;

    RequesterConfigInventory(RequestManager manager) {
        this.manager = manager;
    }

    @Override
    public ResourceHandler<ItemResource> toResourceHandler() {
        throw new UnsupportedOperationException("requester config inventory is not resource storage");
    }

    @Override
    public int size() {
        return manager.size();
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return stack.isEmpty() || convertToSuitableStack(stack) != null;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        var genericStack = manager.getStack(slot);
        if (genericStack != null && genericStack.what() instanceof AEItemKey itemKey) {
            return itemKey.toStack();
        }
        return GenericStack.wrapInItemStack(genericStack);
    }

    @Override
    public void setItemDirect(int slot, ItemStack stack) {
        if (stack.isEmpty()) {
            manager.setStack(slot, null);
        } else {
            var converted = convertToSuitableStack(stack);
            if (converted != null) manager.setStack(slot, converted);
        }
    }

    @Nullable
    private GenericStack convertToSuitableStack(ItemStack stack) {
        if (stack.isEmpty()) return null;

        var unwrappedStack = GenericStack.unwrapItemStack(stack);
        ItemStack returnStack = stack;
        if (unwrappedStack != null) {
            if (unwrappedStack.what() instanceof AEItemKey itemKey) {
                returnStack = itemKey.toStack(Math.max(1, Ints.saturatedCast(unwrappedStack.amount())));
            } else {
                return unwrappedStack;
            }
        }

        var itemKey = AEItemKey.of(returnStack);
        return itemKey != null ? new GenericStack(itemKey, returnStack.getCount()) : null;
    }
}
