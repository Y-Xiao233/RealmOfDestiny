package net.yxiao233.realmofdestiny.Items.Abstract;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.yxiao233.realmofdestiny.Entities.PedestalBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractBaseBlockItemWithTooltip extends BlockItem {

    public AbstractBaseBlockItemWithTooltip(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public abstract void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag);
    public boolean getKeyType(KeyType keyType) {
        return switch (keyType) {
            case SHIFT -> Screen.hasShiftDown();
            case ALT -> Screen.hasAltDown();
            case CONTROL -> Screen.hasControlDown();
        };
    }

    public void addTooltipWhileHasFluidTag(List<Component> tooltips, ItemStack stack, ChatFormatting fluidStyle, ChatFormatting extraStyle){
        if(stack.hasTag()){
            CompoundTag tag =  stack.getTag();

            String fluid = tag.getString("FluidName");
            int amount =  Integer.parseInt(tag.get("Amount").getAsString());
            String key = "block." + fluid.replace(':','.');
            tooltips.add(Component.translatable(key).withStyle(fluidStyle).append(Component.literal(": " + amount + "mb").withStyle(extraStyle)));
        }else{
            tooltips.add(Component.translatable("tooltip.realmofdestiny.empty"));
        }
    }

    public void addTooltipWhileKeyDown(KeyType keyType, List<Component> tooltips, ItemStack itemStack, ChatFormatting fluidStyle, ChatFormatting extraStyle) {
        if (getKeyType(keyType)) {
            addTooltipWhileHasFluidTag(tooltips,itemStack,fluidStyle,extraStyle);
        } else {
            tooltips.add(Component.translatable("tooltip.realmofdestiny.held." + keyType.getValue()).withStyle(ChatFormatting.GRAY));
        }
    }

    //Multiple
    public void addTooltip(List<Component> tooltips, ItemStack itemStack, int index) {
        tooltips.add(Component.translatable(itemIdToKey(itemStack, index)));
    }

    public void addTooltip(List<Component> tooltips, ItemStack itemStack, ChatFormatting style, int index, Object... obj) {
        tooltips.add(Component.translatable(itemIdToKey(itemStack, index), obj).withStyle(style));
    }

    public void addTooltip(List<Component> tooltips, ItemStack itemStack, ChatFormatting style, int index) {
        tooltips.add(Component.translatable(itemIdToKey(itemStack, index)).withStyle(style));
    }

    public String itemIdToKey(ItemStack itemStack, int index) {
        String rawKey = itemStack.getDescriptionId();
        return "tooltip" + rawKey.substring(rawKey.indexOf(".")) + index;
    }

    public void addTooltipWhileKeyDown(KeyType keyType, List<Component> tooltips, ItemStack itemStack, int index) {
        if (getKeyType(keyType)) {
            addTooltip(tooltips, itemStack, index);
        } else {
            tooltips.add(Component.translatable("tooltip.realmofdestiny.held." + keyType.getValue()).withStyle(ChatFormatting.GRAY));
        }
    }

    public void addTooltipWhileKeyDown(KeyType keyType, List<Component> tooltips, ItemStack itemStack, ChatFormatting style, int index, Object... obj) {
        if (getKeyType(keyType)) {
            addTooltip(tooltips, itemStack, style, index, obj);
        } else {
            tooltips.add(Component.translatable("tooltip.realmofdestiny.held." + keyType.getValue()).withStyle(ChatFormatting.GRAY));
        }
    }

    public void addTooltipWhileKeyDown(KeyType keyType, List<Component> tooltips, ItemStack itemStack, ChatFormatting style, int index) {
        if (getKeyType(keyType)) {
            addTooltip(tooltips, itemStack, style, index);
        } else {
            tooltips.add(Component.translatable("tooltip.realmofdestiny.held." + keyType.getValue()).withStyle(ChatFormatting.GRAY));
        }
    }

    //Single
    public void addTooltip(List<Component> tooltips, ItemStack itemStack) {
        tooltips.add(Component.translatable(itemIdToKey(itemStack)));
    }

    public void addTooltip(List<Component> tooltips, ItemStack itemStack, ChatFormatting style, Object... obj) {
        tooltips.add(Component.translatable(itemIdToKey(itemStack), obj).withStyle(style));
    }

    public void addTooltip(List<Component> tooltips, ItemStack itemStack, ChatFormatting style) {
        tooltips.add(Component.translatable(itemIdToKey(itemStack)).withStyle(style));
    }

    public String itemIdToKey(ItemStack itemStack) {
        String rawKey = itemStack.getDescriptionId();
        return "tooltip" + rawKey.substring(rawKey.indexOf("."));
    }

    public void addTooltipWhileKeyDown(KeyType keyType, List<Component> tooltips, ItemStack itemStack) {
        if (getKeyType(keyType)) {
            addTooltip(tooltips, itemStack);
        } else {
            tooltips.add(Component.translatable("tooltip.realmofdestiny.held." + keyType.getValue()).withStyle(ChatFormatting.GRAY));
        }
    }

    public void addTooltipWhileKeyDown(KeyType keyType, List<Component> tooltips, ItemStack itemStack, ChatFormatting style, Object... obj) {
        if (getKeyType(keyType)) {
            addTooltip(tooltips, itemStack, style, obj);
        } else {
            tooltips.add(Component.translatable("tooltip.realmofdestiny.held." + keyType.getValue()).withStyle(ChatFormatting.GRAY));
        }
    }

    public void addTooltipWhileKeyDown(KeyType keyType, List<Component> tooltips, ItemStack itemStack, ChatFormatting style) {
        if (getKeyType(keyType)) {
            addTooltip(tooltips, itemStack, style);
        } else {
            tooltips.add(Component.translatable("tooltip.realmofdestiny.held." + keyType.getValue()).withStyle(ChatFormatting.GRAY));
        }
    }
}