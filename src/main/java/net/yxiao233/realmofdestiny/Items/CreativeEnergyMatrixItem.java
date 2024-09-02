package net.yxiao233.realmofdestiny.Items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.yxiao233.realmofdestiny.Items.Abstract.AbstractBaseBlockItemWithTooltip;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CreativeEnergyMatrixItem extends AbstractBaseBlockItemWithTooltip {
    public CreativeEnergyMatrixItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        addTooltip(pTooltip,pStack,ChatFormatting.GOLD);
    }
}
