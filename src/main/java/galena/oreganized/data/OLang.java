package galena.oreganized.data;

import galena.oreganized.Oreganized;
import galena.oreganized.data.provider.OLangProvider;
import galena.oreganized.registry.OBlocks;
import galena.oreganized.registry.OEffects;
import galena.oreganized.registry.OItems;
import galena.oreganized.registry.OPotions;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

public class OLang extends OLangProvider {

    public OLang(DataGenerator gen) {
        super(gen, Oreganized.MOD_ID, "en_us");
    }
    @Override
    protected void addTranslations() {
        addDisc(OItems.MUSIC_DISC_PILLAGED, "JamiesName", "Pillaged");
        addDisc(OItems.MUSIC_DISC_18, "JamiesName", "18");
        addDisc(OItems.MUSIC_DISC_SHULK, "JamiesName", "Shulk");

        addBlock(OBlocks.RAW_SILVER_BLOCK, "Block of Raw Silver");
        addBlock(OBlocks.RAW_LEAD_BLOCK, "Block of Raw Lead");
        addBlock(OBlocks.SILVER_BLOCK, "Block of Silver");
        addBlock(OBlocks.LEAD_BLOCK, "Block of Lead");
        addBlock(OBlocks.ELECTRUM_BLOCK, "Block of Electrum");

        addEffect(OEffects.STUNNING, "Stunning");
        addPotion(OPotions.STUNNING, "Stunning");

        // JEED compat
        add("effect.oreganized.stunning.description", "Paralyzes the victim periodically with random intervals");

        addSubtitle("entity", "shrapnel_bomb.primed", "Shrapnel Bomb fizzes");

        /*
            Automatically create translations for blocks and items based on their registry name.

            This must be at the very bottom to avoid overwriting errors. These functions ignore objects
            that have already been translated above.
         */
        for (int i = 0; OBlocks.BLOCKS.getEntries().size() > i; i++) {
            tryBlock(OBlocks.BLOCKS.getEntries().stream().toList().get(i));
        }
        for (int i = 0; OItems.ITEMS.getEntries().size() > i; i++) {
            tryItem(OItems.ITEMS.getEntries().stream().toList().get(i));
        }
    }
}
