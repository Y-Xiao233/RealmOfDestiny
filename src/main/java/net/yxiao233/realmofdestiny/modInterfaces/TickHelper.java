package net.yxiao233.realmofdestiny.modInterfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface TickHelper {
    void tick(Level level, BlockPos blockPos, BlockState blockState);
}
