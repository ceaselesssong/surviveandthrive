package galena.oreganized.data;

import galena.oreganized.Oreganized;
import galena.oreganized.index.OTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class OMobEffectTags extends TagsProvider<MobEffect> {

    public OMobEffectTags(PackOutput output, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper helper) {
        super(output, Registries.MOB_EFFECT, future, Oreganized.MOD_ID, helper);
    }

    @Override
    public @NotNull String getName() {
        return "Oreganized MobEffect Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(OTags.Effects.VIGIL_CANDLE_CLEARS).add(ResourceKey.create(Registries.MOB_EFFECT, Oreganized.modLoc("fog")));
    }
}
