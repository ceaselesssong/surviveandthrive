package galena.oreganized.content.capabilities.block.engraveable;

import galena.oreganized.api.TextureDeconstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import static galena.oreganized.network.OreganizedNetwork.CHANNEL;

public class EngraveableBlock implements IEngraveableBlock, INBTSerializable<CompoundTag> {

    public HashSet<BlockPos> engravedBlocks = new HashSet<>();
    public HashMap<BlockPos, HashMap<Face, String>> engravedFaces = new HashMap <>( 12 );
    public HashMap<BlockPos, Integer> engravedColors = new HashMap<>();

    private boolean isEditable = true;
    @Nullable
    private UUID playerWhoMayEdit;
    @Nullable
    private FormattedCharSequence[] renderMessages;
    private boolean renderMessagedFiltered;

    public boolean isEngraved(BlockPos pos) {
        return engravedFaces.containsKey(pos) && engravedBlocks.contains(pos);
    }

    @Override
    public void setFaceString(BlockPos pos, Face face , String string ){

    }

    @Override
    public void removeEngravedBlock(BlockPos pos){
        if(isEngraved( pos )){
            engravedFaces.remove( pos );
            engravedBlocks.remove( pos );
            engravedColors.remove( pos );
        }
    }

    @Override
    public String[] getStringArray(BlockPos pos, Face face) {
        return new String[0];
    }

    @Override
    public String getString(BlockPos pos, Face face) {
        return null;
    }


    @Override
    public int getColor(BlockPos pos){
        return engravedColors.get( pos ) != null ? engravedColors.get( pos ) : 0;
    }

    @Override
    public HashSet <BlockPos> getEngravedBlocks(){
        return engravedBlocks;
    }

    @Override
    public HashMap <BlockPos, HashMap <Face, String>> getEngravedFaces(){
        return engravedFaces;
    }

    @Override
    public HashMap <BlockPos, Integer> getEngravedColors(){
        return engravedColors;
    }

    @Override
    public void setEngravedBlocks( HashSet <BlockPos> engravedBlocks ){
        this.engravedBlocks = engravedBlocks;
    }

    @Override
    public void setEngravedFaces(HashMap<BlockPos, HashMap<Face, String>> engravedFaces) {

    }

    @Override
    public void setEngravedColors( HashMap <BlockPos, Integer> engravedColors ){
        this.engravedColors = engravedColors;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();



        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }

}
