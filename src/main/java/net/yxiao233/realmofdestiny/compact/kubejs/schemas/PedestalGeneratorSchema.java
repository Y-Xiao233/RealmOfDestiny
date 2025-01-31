package net.yxiao233.realmofdestiny.compact.kubejs.schemas;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.TimeComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.yxiao233.realmofdestiny.config.machine.PedestalConfig;

public interface PedestalGeneratorSchema {
    RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("previewItem");
    RecipeKey<Double> INPUT_CONSUME_CHANCE = NumberComponent.doubleRange(0,1).key("inputConsumeChance");
    RecipeKey<OutputItem[]> OUTPUT = ItemComponents.OUTPUT_ARRAY.key("output");
    RecipeKey<Double> OUTPUT_CHANCE = NumberComponent.doubleRange(0,1).key("outputChance");
    RecipeKey<Long> TIME = TimeComponent.TICKS.key("processingTime");
    RecipeKey<Integer> ENERGY = NumberComponent.intRange(0, PedestalConfig.maxStoredPower).key("energy");
    RecipeSchema SCHEMA = new RecipeSchema(INPUT,INPUT_CONSUME_CHANCE,OUTPUT,OUTPUT_CHANCE,TIME,ENERGY);
}
