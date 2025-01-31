package net.yxiao233.realmofdestiny.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AddonItem extends AbstractBaseItemWithTooltip {
    public final Type type;
    public final int value;
    public AddonItem(Type type, int value, Rarity rarity) {
        super(new Item.Properties().rarity(rarity).stacksTo(4));
        this.type = type;
        this.value = value;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
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
        Type(String id){
            this.id = id;
        }
        public static Type getById(String id){
            return switch (id){
                case "speed" -> SPEED;
                case "input_chance" -> INPUT_CHANCE;
                case "output_chance" -> OUTPUT_CHANCE;
                default -> EMPTY;
            };
        }
    }
}
