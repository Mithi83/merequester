package com.almostreliable.merequester.requester;

import com.almostreliable.merequester.MERequester;
import com.almostreliable.merequester.Utils;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RequesterBlockItem extends BlockItem {

    public RequesterBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(
        ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag
    ) {
        super.appendHoverText(stack, context, display, builder, tooltipFlag);

        if (Minecraft.getInstance().hasShiftDown()) {
            builder.accept(Component.literal(" "));
            builder.accept(Utils.translate("tooltip", String.format("%s_desc", MERequester.REQUESTER_ID)).withStyle(ChatFormatting.AQUA));
        } else {
            List<Component> tooltip = new ArrayList<>();
            Utils.addShiftInfoTooltip(tooltip);
            tooltip.forEach(builder);
        }
    }
}
