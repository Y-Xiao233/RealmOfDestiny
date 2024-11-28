package net.yxiao233.realmofdestiny.compact.JEI;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.yxiao233.realmofdestiny.ModRegistry.ModItems;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.compact.JEI.Category.*;
import net.yxiao233.realmofdestiny.helper.jei.JEIRegistryHelper;
import net.yxiao233.realmofdestiny.screen.VoidPlantMachineScreen;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private JEIRegistryHelper helper = new JEIRegistryHelper();

    public void registries(IGuiHelper guiHelper){
        helper.add(new ChangeStoneCategory(guiHelper),ModItems.CHANGE_STONE.get());
        helper.add(new PedestalGeneratorCategory(guiHelper),ModItems.PEDESTAL_ITEM.get());
        helper.add(new PedestalLightingCategory(guiHelper),ModItems.PEDESTAL_ITEM.get());
        helper.add(new VoidPlantMachineCategory(guiHelper),ModItems.VOID_PLANT_MACHINE_ITEM.get());
    }
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(RealmOfDestiny.MODID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registries(guiHelper);
        helper.getRecipeCategories().forEach(registration::addRecipeCategories);
    }

    @Override
    public  void registerRecipes(IRecipeRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        registries(guiHelper);

        helper.addRecipes(manager,registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registries(guiHelper);
        helper.addRecipeCatalyst(registration);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(VoidPlantMachineScreen.class,70,30,20,30,
                VoidPlantMachineCategory.VOID_PLANT_TYPE);
    }
}
