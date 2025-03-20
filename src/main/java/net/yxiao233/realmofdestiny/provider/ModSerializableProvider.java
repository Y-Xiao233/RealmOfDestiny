package net.yxiao233.realmofdestiny.provider;

import com.hrznstudio.titanium.recipe.generator.IJSONGenerator;
import com.hrznstudio.titanium.recipe.generator.IJsonFile;
import com.hrznstudio.titanium.recipe.generator.TitaniumSerializableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.recipe.PedestalGeneratorRecipe;

import java.util.List;
import java.util.Map;

public class ModSerializableProvider extends TitaniumSerializableProvider {
    private static final String modId = RealmOfDestiny.MODID;
    public ModSerializableProvider(DataGenerator generatorIn) {
        super(generatorIn,modId);
    }

    @Override
    public void add(Map<IJsonFile, IJSONGenerator> map) {
        //Pedestal_Generator
        new PedestalGeneratorRecipe(new ResourceLocation(modId,"cobblestone"),
                0,tagValue(Tags.Items.COBBLESTONE),
                new ItemStack(Items.COBBLESTONE,2),
                0.8,2000,20
        );

        new PedestalGeneratorRecipe(new ResourceLocation(modId,"dirt"),
                0.4,itemValue(Items.DIRT.getDefaultInstance()),
                new ItemStack(Items.DIRT,2),
                1,2000,20
        );

        PedestalGeneratorRecipe.RECIPES.forEach(pedestalGeneratorRecipe -> map.put(pedestalGeneratorRecipe,pedestalGeneratorRecipe));
    }

    public Ingredient.TagValue tagValue(TagKey<Item> tagKey){
        return new Ingredient.TagValue(tagKey);
    }
    public Ingredient.ItemValue itemValue(ItemStack itemStack){
        return new Ingredient.ItemValue(itemStack);
    }
}
