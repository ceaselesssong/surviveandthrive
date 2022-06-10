package me.gleep.oreganized.items.tiers;

import me.gleep.oreganized.registry.OreganizedItems;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import java.util.function.Supplier;

import static me.gleep.oreganized.util.RegistryHandler.LEAD_INGOTS_ITEMTAG;

public enum OreganizedTiers implements Tier {
    LEAD(0, 150, 7.0F, 1.5F, 8, () -> Ingredient.of(OreganizedItems.LEAD_INGOT.get())),
    ELECTRUM(5,1279,8.2F,4.0F,15,() -> Ingredient.of(OreganizedItems.ELECTRUM_INGOT.get()));

    private final int level;
    private final int durability;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    OreganizedTiers(int level, int durability, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.durability = durability;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    @Override
    public int getUses() {
        return this.durability;
    }

    @Override
    public float getSpeed() {

        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}