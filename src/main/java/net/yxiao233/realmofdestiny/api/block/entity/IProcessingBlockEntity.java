package net.yxiao233.realmofdestiny.api.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IProcessingBlockEntity {
     void tick(Level level, BlockPos blockPos, BlockState blockState);
}
