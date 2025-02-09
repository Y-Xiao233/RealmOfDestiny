package net.yxiao233.realmofdestiny.compact.jade.providers.server;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.yxiao233.realmofdestiny.block.entity.PedestalBlockEntity;
import net.yxiao233.realmofdestiny.compact.jade.ModJadeIdentifiers;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum PedestalServerComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        CompoundTag tag = blockAccessor.getServerData();
        if(tag.contains("progress") && tag.contains("max_progress") && tag.contains("has_recipe") && tag.contains("has_container") && tag.contains("can_increase")){
            boolean hasRecipe = tag.getBoolean("has_recipe");
            boolean hasContainer = tag.getBoolean("has_container");
            boolean canIncrease = tag.getBoolean("can_increase");
            if(hasRecipe){
                if(!hasContainer){
                    tooltip.add(Component.translatable("jade.realmofdestiny.pedestal.no_container").withStyle(ChatFormatting.RED));
                }else if(!canIncrease){
                    tooltip.add(Component.translatable("jade.realmofdestiny.pedestal.not_enough_energy").withStyle(ChatFormatting.RED));
                }else{
                    IElementHelper helper = tooltip.getElementHelper();
                    int progress = tag.getInt("progress");
                    int maxProgress = tag.getInt("max_progress");
                    float ratio = (float) progress / maxProgress;
                    IElement element = helper.progress(ratio, Component.translatable("jade.realmofdestiny.pedestal.progress", ChatFormatting.WHITE + String.valueOf(progress), maxProgress).withStyle(ChatFormatting.GRAY), helper.progressStyle(), BoxStyle.DEFAULT,true);
                    tooltip.add(element);
                }
            }else{
                tooltip.add(Component.translatable("jade.realmofdestiny.pedestal.no_recipe").withStyle(ChatFormatting.RED));
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor blockAccessor) {
        PedestalBlockEntity pedestal = (PedestalBlockEntity) blockAccessor.getBlockEntity();
        data.putInt("progress", pedestal.progress);
        data.putInt("max_progress", pedestal.maxProgress);
        data.putBoolean("has_recipe",pedestal.checkForRecipe(blockAccessor.getLevel()));
        data.putBoolean("has_container",pedestal.checkForContainer(blockAccessor.getLevel(),blockAccessor.getPosition()));
        data.putBoolean("can_increase",pedestal.canIncrease());
    }

    @Override
    public ResourceLocation getUid() {
        return ModJadeIdentifiers.PEDESTAL;
    }
}
