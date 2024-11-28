package net.yxiao233.realmofdestiny.Entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.yxiao233.realmofdestiny.modInterfaces.TickHelper;

public class ModBaseBlockEntity extends BlockEntity implements TickHelper {
    public ModBaseBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {

    }
}
