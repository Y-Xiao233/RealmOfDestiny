package net.yxiao233.realmofdestiny.compact.jei;

import com.hrznstudio.titanium.util.RecipeUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.compact.jei.category.PedestalGeneratorCategory;
import net.yxiao233.realmofdestiny.recipe.PedestalGeneratorRecipe;
import net.yxiao233.realmofdestiny.registry.ModItems;
import net.yxiao233.realmofdestiny.registry.ModRecipes;
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
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new PedestalGeneratorCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Level level = Minecraft.getInstance().level;
        registration.addRecipes(ModRecipeType.PEDESTAL_GENERATOR, RecipeUtil.getRecipes(level,(RecipeType<PedestalGeneratorRecipe>) ModRecipes.PEDESTAL_TYPE.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModItems.PEDESTAL_ITEM.get().getDefaultInstance(),ModRecipeType.PEDESTAL_GENERATOR);
    }
}
