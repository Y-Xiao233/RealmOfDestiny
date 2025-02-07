package net.yxiao233.realmofdestiny.compact.jade.providers.client;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.yxiao233.realmofdestiny.compact.jade.ModJadeIdentifiers;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum PedestalComponentProvider implements IBlockComponentProvider {
    INSTANCE;
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig pluginConfig) {
        CompoundTag tag = blockAccessor.getServerData();
        if(tag.contains("progress") && tag.contains("max_progress")){
            tooltip.add(
                    Component.translatable("jade.realmofdestiny.pedestal.progress",
                            (tag.getInt("progress") + "/" + tag.getInt("max_progress"))
                    )
            );
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ModJadeIdentifiers.PEDESTAL;
    }
}
