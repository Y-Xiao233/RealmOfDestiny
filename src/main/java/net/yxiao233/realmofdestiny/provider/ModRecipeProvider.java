package net.yxiao233.realmofdestiny.provider;

import com.hrznstudio.titanium.recipe.generator.TitaniumRecipeProvider;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.registry.ModItems;

import java.util.function.Consumer;

public class ModRecipeProvider extends TitaniumRecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }
    public static final String modId = RealmOfDestiny.MODID;

    @Override
    public void register(Consumer<FinishedRecipe> consumer) {
        TitaniumShapedRecipeBuilder.shapedRecipe(ModItems.INPUT_AUGMENT_1.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Tags.Items.INGOTS_COPPER)
                .define('B', Blocks.REDSTONE_BLOCK)
                .save(consumer);

        TitaniumShapedRecipeBuilder.shapedRecipe(ModItems.OUTPUT_AUGMENT_1.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Tags.Items.INGOTS_COPPER)
                .define('B', Blocks.EMERALD_BLOCK)
                .save(consumer);


        TitaniumShapedRecipeBuilder.shapedRecipe(ModItems.SPEED_AUGMENT_1.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Tags.Items.INGOTS_COPPER)
                .define('B', Blocks.DIAMOND_BLOCK)
                .save(consumer);
    }
}
