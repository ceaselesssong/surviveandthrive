package galena.oreganized.data;

import galena.oreganized.Oreganized;
import galena.oreganized.data.provider.OLangProvider;
import galena.oreganized.index.OBlocks;
import galena.oreganized.index.OEffects;
import galena.oreganized.index.OEntityTypes;
import galena.oreganized.index.OFluids;
import galena.oreganized.index.OItems;
import galena.oreganized.index.OPotions;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class OLang extends OLangProvider {

    public OLang(PackOutput output) {
        super(output, Oreganized.MOD_ID, "en_us");
    }
    @Override
    protected void addTranslations() {
        addDisc(OItems.MUSIC_DISC_STRUCTURE, "Firch", "structure");

        addItem(OItems.SHRAPNEL_BOMB_MINECART, "Minecart with Shrapnel Bomb");

        addBlock(OBlocks.RAW_SILVER_BLOCK, "Block of Raw Silver");
        addBlock(OBlocks.RAW_LEAD_BLOCK, "Block of Raw Lead");
        addBlock(OBlocks.SILVER_BLOCK, "Block of Silver");
        addBlock(OBlocks.LEAD_BLOCK, "Block of Lead");
        addBlock(OBlocks.LEAD_BRICKS, "Lead Bricks");
        addBlock(OBlocks.LEAD_PILLAR, "Lead Pillar");
        addBlock(OBlocks.LEAD_BULB, "Lead Bulb");
        addBlock(OBlocks.CUT_LEAD, "Cut Lead");
        addBlock(OBlocks.ELECTRUM_BLOCK, "Block of Electrum");
        addBlock(OBlocks.LEAD_BOLT_CRATE, "Crate of Lead Bolts");

        addEffect(OEffects.STUNNING, "Brain Damage");
        addPotion(OPotions.STUNNING, "Brain Damage");

        addAdvTitle("adventure.mirror_mirror", "Mirror, Mirror who is the fairest?");
        addAdvDesc("adventure.mirror_mirror", "Obtain a Silver Mirror");
        addAdvTitle("story.melting_point", "Melting Point");
        addAdvDesc("story.melting_point", "Pick up Molten Lead from a cauldron");
        addAdvTitle("story.obtain_silver", "Every Stone has a Silver Lining");
        addAdvDesc("story.obtain_silver", "Smelt Raw Silver");
        addAdvTitle("story.electrum_gear", "Cover me in... Wings?");
        addAdvDesc("story.electrum_gear", "Obtain a full set of electrum armor");
        addAdvTitle("story.eat_with_lead", "Like the Romans");
        addAdvDesc("story.eat_with_lead", "Eat anything with lead in your hotbar");
        addAdvTitle("story.disc_smith", "Disc Smith");
        addAdvDesc("story.disc_smith", "Submerge a broken music disc into molten lead");

        add("trim_material.oreganized.lead", "Lead material");
        add("trim_material.oreganized.silver", "Silver material");
        add("trim_material.oreganized.electrum", "Electrum material");
        add("upgrade.oreganized.electrum_upgrade", "Electrum Upgrade");
        add("item.oreganized.smithing_template.electrum_upgrade.applies_to", "Diamond Equipment");
        add("item.oreganized.smithing_template.electrum_upgrade.ingredients", "Electrum Ingot");

        // JEED compat
        add("effect.oreganized.stunning.description", "Paralyzes the victim periodically with random intervals");

        addSubtitle("entity", "shrapnel_bomb.primed", "Shrapnel Bomb fizzes");

        addSubtitle("block", "gargoyle.growl", "Gargoyle growls");

        add("tooltip.oreganized.wip.title", "Work In Progress");
        add("tooltip.oreganized.wip.description", "Usages for this item will be available in a future release");

        add("item.oreganized.smithing_template.electrum_upgrade.additions_slot_description", "Add Electrum Ingot");

        /*
            Automatically create translations for blocks and items based on their registry name.

            This must be at the very bottom to avoid overwriting errors. These functions ignore objects
            that have already been translated above.
         */
        for (Supplier<? extends Block> blocks : Oreganized.REGISTRY_HELPER.getBlockSubHelper().getDeferredRegister().getEntries()) {
            tryBlock(blocks);
        }
        for (Supplier<? extends Item> items : Oreganized.REGISTRY_HELPER.getItemSubHelper().getDeferredRegister().getEntries()) {
            if (!items.equals(OItems.ELECTRUM_UPGRADE_SMITHING_TEMPLATE)) tryItem(items);
        }
        for (Supplier<? extends Fluid> fluids : OFluids.FLUIDS.getEntries()) {
            tryFluid(fluids);
        }
        for (Supplier<? extends EntityType<?>> entities : OEntityTypes.ENTITIES.getEntries()) {
            tryEntity(entities);
        }
    }
}
