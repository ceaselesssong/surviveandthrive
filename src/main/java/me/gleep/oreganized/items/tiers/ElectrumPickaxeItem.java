package me.gleep.oreganized.items.tiers;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class ElectrumPickaxeItem extends PickaxeItem {
    public ElectrumPickaxeItem(Tier tier, int attackDamage, float attackSpeed) {
        super(tier, attackDamage, attackSpeed, new Properties()
                .stacksTo(1)
                .defaultDurability(tier.getUses() - 200)
                .tab(CreativeModeTab.TAB_TOOLS)
        );
    }
}
