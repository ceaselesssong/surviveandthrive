package galena.oreganized.index;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum OItemTiers implements Tier {
    LEAD(0, 150, 7.0F, 1.5F, 8, () -> Ingredient.of(OTags.Items.INGOTS_LEAD)),

    /*
        Electrum should be better than diamond but not the same as netherite
        in terms of toughness and durability, taking on some benefits
        from gold tools such as speed and enchantability instead.

        Here's the tiers for diamond, gold and netherite for comparison.

         DIAMOND(3, 1561, 8.0F, 3.0F, 10, () -> {
            return Ingredient.of(Items.DIAMOND);
        }),
        GOLD(0, 32, 12.0F, 0.0F, 22, () -> {
            return Ingredient.of(Items.GOLD_INGOT);
        }),
        NETHERITE(4, 2031, 9.0F, 4.0F, 15, () -> {
            return Ingredient.of(Items.NETHERITE_INGOT);
        });
    */
    ELECTRUM(4,1561,11.0F,3.0F,18, () -> Ingredient.of(OTags.Items.INGOTS_ELECTRUM));


    private final int level;
    private final int durability;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    OItemTiers(int level, int durability, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.durability = durability;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    public int getUses() {
        return this.durability;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.damage;
    }

    public int getLevel() {
        return this.level;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
