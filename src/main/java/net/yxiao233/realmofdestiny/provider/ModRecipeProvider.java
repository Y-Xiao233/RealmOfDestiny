package net.yxiao233.realmofdestiny.provider;

import com.hrznstudio.titanium.recipe.generator.TitaniumRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.yxiao233.realmofdestiny.RealmOfDestiny;

import java.util.function.Consumer;

public class ModRecipeProvider extends TitaniumRecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }
    public static final String modId = RealmOfDestiny.MODID;

    @Override
    public void register(Consumer<FinishedRecipe> consumer) {
//        TitaniumShapelessRecipeBuilder.shapelessRecipe(ModContents.LASER_LENS_SCULK.get())
//                .requires(Ingredient.of(Arrays.stream(ModuleCore.LASER_LENS).map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList()).stream()))
//                .requires(Items.SCULK,4)
//                .save(consumer);
    }
}
