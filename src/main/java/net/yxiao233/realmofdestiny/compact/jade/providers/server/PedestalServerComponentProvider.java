package net.yxiao233.realmofdestiny.compact.jade.providers.server;

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

public enum PedestalServerComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
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
    public void appendServerData(CompoundTag data, BlockAccessor blockAccessor) {
        PedestalBlockEntity pedestal = (PedestalBlockEntity) blockAccessor.getBlockEntity();
        data.putInt("progress", pedestal.progress);
        data.putInt("max_progress", pedestal.maxProgress);
    }

    @Override
    public ResourceLocation getUid() {
        return ModJadeIdentifiers.PEDESTAL;
    }
}
