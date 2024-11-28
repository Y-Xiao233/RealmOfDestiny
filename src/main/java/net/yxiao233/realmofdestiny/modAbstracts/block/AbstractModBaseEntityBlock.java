package net.yxiao233.realmofdestiny.modAbstracts.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.realmofdestiny.Entities.ModBaseBlockEntity;
import net.yxiao233.realmofdestiny.modInterfaces.TickHelper;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractModBaseEntityBlock<T extends ModBaseBlockEntity> extends BaseEntityBlock implements TickHelper {
    public ModBaseBlockEntity entity;

    protected AbstractModBaseEntityBlock(Properties pProperties) {
        super(pProperties);
    }

    public abstract T setBlockEntity(BlockPos blockPos, BlockState blockState);

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        this.entity = setBlockEntity(blockPos,blockState);
        return entity;
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {

    }
}
