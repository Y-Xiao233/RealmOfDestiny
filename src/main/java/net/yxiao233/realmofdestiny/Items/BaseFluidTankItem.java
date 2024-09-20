package net.yxiao233.realmofdestiny.Items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.yxiao233.realmofdestiny.modAbstracts.item.AbstractBaseBlockItemWithTooltip;
import net.yxiao233.realmofdestiny.modAbstracts.item.KeyType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseFluidTankItem extends AbstractBaseBlockItemWithTooltip {
    public BaseFluidTankItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> tooltips, TooltipFlag flag) {
        addTooltipWhileKeyDown(KeyType.SHIFT,tooltips,stack,ChatFormatting.GOLD,ChatFormatting.WHITE);
    }
}
