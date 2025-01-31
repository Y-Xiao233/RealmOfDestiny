package net.yxiao233.realmofdestiny.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class TooltipCallBackHelper {
    private String translatableKey;
    private ChatFormatting style;
    private Component component;
    public TooltipCallBackHelper(String translatableKey, ChatFormatting style){
        this.translatableKey = translatableKey;
        this.style = style;
        this.component = Component.translatable(translatableKey).withStyle(style);
    }

    public TooltipCallBackHelper(String translatableKey, ChatFormatting style, Object... obj){
        this.translatableKey = translatableKey;
        this.style = style;
        this.component = Component.translatable(translatableKey,obj).withStyle(style);
    }

    public Component getComponent() {
        return component;
    }
}
