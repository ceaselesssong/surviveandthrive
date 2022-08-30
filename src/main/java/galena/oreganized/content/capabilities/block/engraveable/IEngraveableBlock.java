package galena.oreganized.content.capabilities.block.engraveable;

//import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import net.minecraft.core.BlockPos;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.HashMap;
import java.util.HashSet;

@AutoRegisterCapability
public interface IEngraveableBlock {
    void setFaceString(BlockPos pos, Face face, String string);
    void removeEngravedBlock( BlockPos pos );
    String[] getStringArray( BlockPos pos, Face face);
    String getString( BlockPos pos, Face face);
    HashSet<BlockPos> getEngravedBlocks();
    HashMap <BlockPos, HashMap<Face, String>> getEngravedFaces();
    HashMap<BlockPos, Integer> getEngravedColors();
    void setEngravedBlocks(HashSet <BlockPos> engravedBlocks);
    void setEngravedFaces(HashMap <BlockPos, HashMap<Face, String>> engravedFaces);
    void setEngravedColors(HashMap <BlockPos, Integer> engravedColors);
    boolean isEngraved(BlockPos pos);
    int getColor(BlockPos pos);
}
