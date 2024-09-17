package net.yxiao233.realmofdestiny.Items.Abstract;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractBaseItemWithTooltip extends Item {
    public AbstractBaseItemWithTooltip(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public abstract void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag);
    public boolean getKeyType(KeyType keyType) {
        return switch (keyType) {
            case SHIFT -> Screen.hasShiftDown();
            case ALT -> Screen.hasAltDown();
            case CONTROL -> Screen.hasControlDown();
        };
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
