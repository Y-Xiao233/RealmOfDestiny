package net.yxiao233.realmofdestiny.Items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.yxiao233.realmofdestiny.Items.Abstract.AbstractBaseBlockItemWithTooltip;
import net.yxiao233.realmofdestiny.Items.Abstract.KeyType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class BaseFluidTankItem extends AbstractBaseBlockItemWithTooltip {
    public BaseFluidTankItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> tooltips, TooltipFlag flag) {
        addTooltipWhileKeyDown(KeyType.SHIFT,tooltips,stack,ChatFormatting.GOLD,ChatFormatting.WHITE);
    }
}
