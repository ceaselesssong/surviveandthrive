package galena.oreganized.data;

import galena.oreganized.Oreganized;
import galena.oreganized.index.OPaintingVariants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class OPaintingVariantTags extends PaintingVariantTagsProvider {

    public OPaintingVariantTags(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, Oreganized.MOD_ID, helper);
    }

    @Override
    public void addTags() {
        for (Supplier<? extends PaintingVariant> variant : OPaintingVariants.PAINTING_VARIANTS.getEntries()) {
            this.tag(PaintingVariantTags.PLACEABLE).add(variant.get());
        }
    }
}
