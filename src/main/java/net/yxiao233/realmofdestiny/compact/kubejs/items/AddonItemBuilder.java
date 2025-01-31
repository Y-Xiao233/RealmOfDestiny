package net.yxiao233.realmofdestiny.compact.kubejs.items;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.yxiao233.realmofdestiny.item.custom.AddonItem;

public class AddonItemBuilder extends ItemBuilder {
    private int value;
    private AddonItem.Type type;
    private Rarity rarity = Rarity.EPIC;
    public AddonItemBuilder(ResourceLocation i) {
        super(i);
    }

    public AddonItemBuilder setRarityById(String rarityName, int chatFormattingId){
        this.rarity = Rarity.create(rarityName, ChatFormatting.getById(chatFormattingId));
        return this;
    }

    public AddonItemBuilder setValue(int value){
        this.value = value;
        return this;
    }

    public AddonItemBuilder setType(String type){
        this.type = AddonItem.Type.getById(type);
        return this;
    }

    @Override
    public Item createObject() {
        return new AddonItem(type,value,rarity);
    }
}
