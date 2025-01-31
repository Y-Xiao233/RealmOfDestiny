package net.yxiao233.realmofdestiny.compact.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.yxiao233.realmofdestiny.compact.kubejs.items.AddonItemBuilder;
import net.yxiao233.realmofdestiny.compact.kubejs.schemas.PedestalGeneratorSchema;

public class ModKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void init() {
        RegistryInfo.ITEM.addType("industrialforegoing:speed_addon", AddonItemBuilder.class, AddonItemBuilder::new);
    }

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        event.namespace("realmofdestiny")
                .register("pedestal_generator", PedestalGeneratorSchema.SCHEMA);
    }
}
