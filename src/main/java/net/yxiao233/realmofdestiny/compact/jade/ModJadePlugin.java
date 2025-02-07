package net.yxiao233.realmofdestiny.compact.jade;

import net.yxiao233.realmofdestiny.block.PedestalBlock;
import net.yxiao233.realmofdestiny.block.entity.PedestalBlockEntity;
import net.yxiao233.realmofdestiny.compact.jade.providers.client.PedestalComponentProvider;
import net.yxiao233.realmofdestiny.compact.jade.providers.server.PedestalServerComponentProvider;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ModJadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(PedestalServerComponentProvider.INSTANCE, PedestalBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(PedestalComponentProvider.INSTANCE, PedestalBlock.class);
    }
}
