package galena.oreganized.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import galena.oreganized.content.item.ScribeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Block.class)
public class BlockMixin {

    @ModifyVariable(
            method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;",
            at = @At(value = "STORE")
    )
    private static LootParams.Builder modifyLootBuilder(LootParams.Builder builder, @Local BlockState state, @Local ItemStack stack) {
        if (stack.getItem() instanceof ScribeItem scribe && scribe.dropsLikeSilktouch(stack, state)) {
            var virtual = stack.copy();
            virtual.enchant(Enchantments.SILK_TOUCH, 1);
            return builder.withParameter(LootContextParams.TOOL, virtual);
        }
        return builder;
    }

}
