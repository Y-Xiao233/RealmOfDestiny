package net.yxiao233.realmofdestiny.Entities.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.realmofdestiny.Entities.PedestalBlockEntity;
import net.yxiao233.realmofdestiny.helper.recipe.KeyToItemStackHelper;
import net.yxiao233.realmofdestiny.recipes.PedestalGeneratorRecipe;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PedestalRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
    private float rotationAngle = 0.0f;
    public PedestalRenderer(BlockEntityRendererProvider.Context context){

    }
    @Override
    public void render(PedestalBlockEntity blockEntity, float v, PoseStack poseStack, MultiBufferSource buffer, int i, int i1) {
        ItemStack itemStack = blockEntity.getRenderStack();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        rotationAngle += 0.5f;
        if (rotationAngle >= 360.0f) {
            rotationAngle = 0.0f;
        }

        poseStack.pushPose();
        poseStack.translate(0.5f,0.8f,0.5f);
        poseStack.scale(0.2f,0.2f,0.2f);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle));

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED,getLightLevel(blockEntity.getLevel(),blockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY,poseStack,buffer,blockEntity.getLevel(),1);
        poseStack.popPose();



        renderWhileNeeded(blockEntity,poseStack,buffer,i,i1);
    }

    private void renderWhileNeeded(PedestalBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        if(blockEntity.isPressed() && blockEntity.getStructureId() != -1){
            List<PedestalGeneratorRecipe> recipeList = getRecipeList(blockEntity);
            PedestalGeneratorRecipe recipe = recipeList.get(blockEntity.getStructureId());

            poseStack.pushPose();
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(770, 771);
            Minecraft.getInstance().getTextureManager().bindForSetup(TextureAtlas.LOCATION_BLOCKS);
            {
                renderStructure(recipe,poseStack,buffer,light,overlay);
            }
            RenderSystem.disableBlend();
            poseStack.popPose();
        }
    }

    private void renderStructure(PedestalGeneratorRecipe recipe,PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay){
        char[][][] patternsList = recipe.getPatternsList();
        int[] pedestal = findPedestal(patternsList);
        poseStack.translate(pedestal[0],pedestal[1],pedestal[2]);
        for (int y = 0; y < patternsList.length; y++) {
            for (int x = 0; x < patternsList[y].length; x++) {
                for (int z = 0; z < patternsList[y][x].length; z++) {
                    poseStack.translate(-x,-y,-z);
                    renderBlock(recipe,patternsList[y][x][z],poseStack,bufferSource,light,overlay);
                    poseStack.translate(x,y,z);
                }
            }
        }
    }

    private void renderBlock(PedestalGeneratorRecipe recipe, char key, PoseStack poseStack, MultiBufferSource bufferSource,int light, int overlay){
        BlockRenderDispatcher blockRender = Minecraft.getInstance().getBlockRenderer();
        ItemStack itemStack = recipe.getKeyItemStack().getCurrentItemStack(String.valueOf(key));
        if(itemStack != null){
            BlockState blockState = Block.byItem(itemStack.getItem()).defaultBlockState();
            blockRender.renderSingleBlock(blockState,poseStack,bufferSource,light,overlay);
        }
    }

    private int[] findPedestal(char[][][] patternsList) {
        for (int y = 0; y < patternsList.length; y++) {
            for (int x = 0; x < patternsList[y].length; x++) {
                for (int z = 0; z < patternsList[y][x].length; z++) {
                    if(String.valueOf(patternsList[y][x][z]).equals("P")){
                        return new int[]{x,y,z};
                    }
                }
            }
        }
        return new int[]{0,0,0};
    }

    private int getLightLevel(Level level, BlockPos blockPos){
        int bLight = level.getBrightness(LightLayer.BLOCK,blockPos);
        int sLight = level.getBrightness(LightLayer.SKY,blockPos);
        return LightTexture.pack(bLight,sLight);
    }

    private List<PedestalGeneratorRecipe> getRecipeList(PedestalBlockEntity entity){
        return entity.getPedestalGeneratorRecipeList();
    }
}
