package net.yxiao233.realmofdestiny.Items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.yxiao233.realmofdestiny.modAbstracts.item.AbstractBaseItemWithTooltip;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StructureViewerItem extends AbstractBaseItemWithTooltip {
    public StructureViewerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
        addTooltip(tooltips,stack, ChatFormatting.GOLD);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }
}
