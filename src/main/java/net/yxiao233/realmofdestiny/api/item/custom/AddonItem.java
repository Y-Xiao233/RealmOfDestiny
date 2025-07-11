package net.yxiao233.realmofdestiny.api.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.yxiao233.realmofdestiny.api.item.AbstractBaseItemWithTooltip;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AddonItem extends AbstractBaseItemWithTooltip {
    public final Type type;
    public final int value;
    public AddonItem(Type type, int value, Rarity rarity, int stackSize) {
        super(new Item.Properties().rarity(rarity).stacksTo(stackSize));
        this.type = type;
        this.value = value;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
        tooltips.add(Component.translatable("addon_item.realmofdestiny.max_stack_size",new Object[]{this.getMaxStackSize(this.getDefaultInstance())}).withStyle(ChatFormatting.GREEN));
        if(this.type == Type.INPUT_CHANCE){
            tooltips.add(Component.translatable("addon_item.realmofdestiny.input_chance",(this.value+"%")).withStyle(ChatFormatting.GOLD));
        }else if(this.type == Type.OUTPUT_CHANCE){
            tooltips.add(Component.translatable("addon_item.realmofdestiny.output_chance",(this.value+"%")).withStyle(ChatFormatting.GOLD));
        }else if(this.type == Type.SPEED){
            tooltips.add(Component.translatable("addon_item.realmofdestiny.speed",(this.value+"%")).withStyle(ChatFormatting.GOLD));
        }
    }
    public enum Type {
        SPEED("speed"),
        INPUT_CHANCE("input_chance"),
        OUTPUT_CHANCE("output_chance"),
        EMPTY("empty");
        public final String id;
        public static final String SPEED_ID = "speed";
        public static final String INPUT_CHANCE_ID = "input_chance";
        public static final String OUTPUT_CHANCE_ID = "output_chance";
        Type(String id){
            this.id = id;
        }
        public static Type getById(String id){
            return switch (id){
                case SPEED_ID -> SPEED;
                case INPUT_CHANCE_ID -> INPUT_CHANCE;
                case OUTPUT_CHANCE_ID -> OUTPUT_CHANCE;
                default -> EMPTY;
            };
        }
    }
}
