package com.almostreliable.merequester.requester;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import appeng.api.orientation.IOrientationStrategy;
import appeng.block.AEBaseEntityBlock;
import appeng.menu.MenuOpener;
import appeng.menu.locator.MenuLocators;
import appeng.util.InteractionUtil;

public class RequesterBlock extends AEBaseEntityBlock<RequesterBlockEntity> {

    private static final IOrientationStrategy ORIENTATION_STRATEGY = new FacingWithVerticalSpin();
    private static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public RequesterBlock(Properties p) {
        super(metalProps(p));
        registerDefaultState(defaultBlockState().setValue(ACTIVE, false));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        var entity = getBlockEntity(level, pos);
        if (entity == null || InteractionUtil.isInAlternateUseMode(player)) return InteractionResult.PASS;

        if (!level.isClientSide()) {
            MenuOpener.open(RequesterMenu.TYPE, player, MenuLocators.forBlockEntity(entity));
        }
        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
    }

    @Override
    protected BlockState updateBlockStateFromBlockEntity(BlockState currentState, RequesterBlockEntity be) {
        return currentState.setValue(ACTIVE, be.isActive());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ACTIVE);
    }

    @Override
    public IOrientationStrategy getOrientationStrategy() {
        return ORIENTATION_STRATEGY;
    }
}
