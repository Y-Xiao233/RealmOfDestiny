package net.yxiao233.realmofdestiny.compact.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.compact.jei.category.PedestalGeneratorCategory;
import net.yxiao233.realmofdestiny.registry.ModItems;
import net.yxiao233.realmofdestiny.util.JEIRegistryHelper;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private JEIRegistryHelper helper = new JEIRegistryHelper();
    private void registries(IGuiHelper guiHelper){
        helper.add(new PedestalGeneratorCategory(guiHelper), ModItems.PEDESTAL_ITEM);
    }
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(RealmOfDestiny.MODID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registries(registration.getJeiHelpers().getGuiHelper());

        helper.getRecipeCategories().forEach(registration::addRecipeCategories);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();

        helper.addRecipes(manager,registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        helper.addRecipeCatalyst(registration);
    }
}
