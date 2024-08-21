package net.yxiao233.realmofdestiny.helper.blockBox;

import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.yxiao233.realmofdestiny.RealmOfDestiny;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class BlockBoxHelper {
    private static final Gson Gson = new Gson();
    private int size;
    private JsonArray jsonArray;
    public BlockBoxHelper(String fileName){
        try{
            ResourceLocation location = new ResourceLocation(RealmOfDestiny.MODID,"models/block/" + fileName + ".json");
            ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
            Optional<Resource> resource = resourceManager.getResource(location);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.get().open()));
            JsonObject jsonObject = Gson.fromJson(reader, JsonObject.class);
            this.jsonArray = jsonObject.getAsJsonArray("elements");
            this.size = this.jsonArray.size();
        }catch (IOException e){}
    }

    public VoxelShape getVoxelShapes(){
        VoxelShape[] shapes = new VoxelShape[this.size];
            for(int i = 0; i < jsonArray.size(); i ++){
                String from =  jsonArray.get(i).getAsJsonObject().get("from").toString();
                String to = jsonArray.get(i).getAsJsonObject().get("to").toString();
                int[] froms = getXYZ(from);
                int[] tos = getXYZ(to);
                shapes[i] = Block.box(froms[0],froms[1],froms[2],tos[0],tos[1],tos[2]);
            }
        return Shapes.or(shapes[0],shapes);
    }

    public int[] getXYZ(String FROMorTO){
        int[] xyz = new int[3];
        int leftBound = FROMorTO.indexOf("[");
        int rightBound = FROMorTO.indexOf("]");
        int firstIndex = FROMorTO.indexOf(",");
        int lastIndex = FROMorTO.lastIndexOf(",");
        xyz[0] = Integer.valueOf(FROMorTO.substring(leftBound+1,firstIndex)).intValue();
        xyz[1] = Integer.valueOf(FROMorTO.substring(firstIndex+1,lastIndex)).intValue();
        xyz[2] = Integer.valueOf(FROMorTO.substring(lastIndex+1,rightBound)).intValue();
        return xyz;
    }
}
