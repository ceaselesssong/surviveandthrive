package galena.oreganized.data;

import galena.oreganized.Oreganized;
import galena.oreganized.registry.OTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class OEntityTags extends EntityTypeTagsProvider {

    public OEntityTags(DataGenerator gen, @Nullable ExistingFileHelper help) {
        super(gen, Oreganized.MOD_ID, help);
    }

    public String getName() {
        return "Oreganized Entity Type Tags";
    }

    protected void addTags() {
        // Oreganized
        tag(OTags.Entities.LIGHTER_THAN_LEAD).add(EntityType.IRON_GOLEM);

        // Vanilla
        //tag(EntityTypeTags.IMPACT_PROJECTILES).add(OEntityTypes.LEAD_BOLT.get());
    }
}
