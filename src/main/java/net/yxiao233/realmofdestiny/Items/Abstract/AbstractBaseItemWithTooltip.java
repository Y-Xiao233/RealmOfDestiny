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
    public static final int SHIFT_KEY = 0;
    public static final int ALT_KEY = 1;
    public static final int CONTROL_KEY = 2;
    public static final String SHIFT_VALUE = "shift";
    public static final String ALT_VALUE = "alt";
    public static final String CONTROL_VALUE = "control";
    public AbstractBaseItemWithTooltip(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public abstract void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag);

    public void addTooltip(List<Component> tooltips, ItemStack itemStack){
        tooltips.add(Component.translatable(itemIdToKey(itemStack)));
    }

    public void addTooltip(List<Component> tooltips, ItemStack itemStack, ChatFormatting style, Object... obj){
        tooltips.add(Component.translatable(itemIdToKey(itemStack),obj).withStyle(style));
    }

    public void addTooltip(List<Component> tooltips, ItemStack itemStack, ChatFormatting style){
        tooltips.add(Component.translatable(itemIdToKey(itemStack)).withStyle(style));
    }

    public String itemIdToKey(ItemStack itemStack){
        String rawKey = itemStack.getDescriptionId();
        return "tooltip" + rawKey.substring(rawKey.indexOf("."));
    }

    public void addTooltipWhileKeyDown(int keyType, List<Component> tooltips, ItemStack itemStack){
        if(getKeyType(keyType)){
            addTooltip(tooltips,itemStack);
        }else{
            tooltips.add(Component.translatable("tooltip.realmofdestiny.held." + getKeyValue(keyType)).withStyle(ChatFormatting.GRAY));
        }
    }

    public void addTooltipWhileKeyDown(int keyType, List<Component> tooltips, ItemStack itemStack, ChatFormatting style, Object... obj){
        if(getKeyType(keyType)){
            addTooltip(tooltips,itemStack,style,obj);
        }else{
            tooltips.add(Component.translatable("tooltip.realmofdestiny.held." + getKeyValue(keyType)).withStyle(ChatFormatting.GRAY));
        }
    }

    public void addTooltipWhileKeyDown(int keyType, List<Component> tooltips, ItemStack itemStack, ChatFormatting style){
        if(getKeyType(keyType)){
            addTooltip(tooltips,itemStack,style);
        }else{
            tooltips.add(Component.translatable("tooltip.realmofdestiny.held." + getKeyValue(keyType)).withStyle(ChatFormatting.GRAY));
        }
    }

    public boolean getKeyType(int keyType){
        return switch (keyType){
            case 0 -> Screen.hasShiftDown();
            case 1 -> Screen.hasAltDown();
            case 2 -> Screen.hasControlDown();
            default -> Screen.hasShiftDown();
        };
    }

    public String getKeyValue(int keyType){
        return switch (keyType){
            case 0 -> SHIFT_VALUE;
            case 1 -> ALT_VALUE;
            case 2 -> CONTROL_VALUE;
            default -> SHIFT_VALUE;
        };
    }
}
