package net.yxiao233.realmofdestiny.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.yxiao233.realmofdestiny.api.item.AbstractBaseBlockItemWithTooltip;
import net.yxiao233.realmofdestiny.api.item.KeyType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PedestalItem extends AbstractBaseBlockItemWithTooltip {
    public PedestalItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
        addTooltipWhileKeyDown(KeyType.SHIFT,tooltips,stack,ChatFormatting.GOLD,0);
        addTooltipWhileKeyDown(KeyType.ALT,tooltips,stack,ChatFormatting.GOLD,1);
        addTooltipsWhileKeyDown(KeyType.CONTROL,tooltips,stack,ChatFormatting.GOLD,new int[]{2,3,4});
    }
}
