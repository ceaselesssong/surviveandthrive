package galena.oreganized.data.provider;

import galena.oreganized.Oreganized;
import galena.oreganized.index.OTrimMaterials;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.function.Supplier;

import static net.minecraft.data.models.ItemModelGenerators.TRIM_TYPE_PREDICATE_ID;

public abstract class OItemModelProvider extends ItemModelProvider {

    public OItemModelProvider(PackOutput output, ExistingFileHelper help) {
        super(output, Oreganized.MOD_ID, help);
    }

    protected String blockName(Supplier<? extends Block> block) {
        return ForgeRegistries.BLOCKS.getKey(block.get()).getPath();
    }

    private ResourceLocation texture(String name) {
        return modLoc("block/" + name);
    }

    public ItemModelBuilder block(Supplier<? extends Block> block) {
        return block(block, blockName(block));
    }

    public ItemModelBuilder block(Supplier<? extends Block> block, String name) {
        return withExistingParent(blockName(block), modLoc("block/" + name));
    }

    public ItemModelBuilder blockFlat(Supplier<? extends Block> block) {
        return blockFlat(block, blockName(block));
    }

    public ItemModelBuilder blockFlat(Supplier<? extends Block> block, Supplier<? extends Block> fullBlock) {
        return blockFlat(block, blockName(fullBlock));
    }

    public ItemModelBuilder blockFlat(Supplier<? extends Block> block, String name) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + name));
    }

    public ItemModelBuilder blockFlatWithItemName(Supplier<? extends Block> block, String name) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + name));
    }

    public ItemModelBuilder normalItem(Supplier<? extends Item> item) {
        return withExistingParent(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + ForgeRegistries.ITEMS.getKey(item.get()).getPath()));
    }

    public ItemModelBuilder toolItem(Supplier<? extends Item> item) {
        return withExistingParent(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + ForgeRegistries.ITEMS.getKey(item.get()).getPath()));
    }

    public ItemModelBuilder crossbowOverwrite(String name) {
        return withExistingParent(name, "item/crossbow")
                .texture("layer0", modLoc(ITEM_FOLDER + "/" + name));
    }

    public ItemModelBuilder wall(Supplier<? extends WallBlock> wall, Supplier<? extends Block> fullBlock) {
        return wallInventory(ForgeRegistries.BLOCKS.getKey(wall.get()).getPath(), texture(blockName(fullBlock)));
    }

    public ItemModelBuilder twoLayered(String name, ResourceLocation texture, ResourceLocation overlayTexture) {
        existingFileHelper.trackGenerated(overlayTexture, TEXTURE);
        return withExistingParent(name, "item/generated")
                .texture("layer0", texture)
                .texture("layer1", overlayTexture);
    }

    public void trimmedItem(Supplier<? extends ArmorItem> item) {
        ResourceLocation texture = TextureMapping.getItemTexture(item.get());

        var model = normalItem(item);

        OTrimMaterials.TRIM_MATERIALS.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    var material = entry.getKey();
                    var itemModelIndex = entry.getValue();

                    var overlayName = item.get().getType().getName() + "_trim_" + material;

                    ResourceLocation overlayTexture = (new ResourceLocation(overlayName)).withPrefix("trims/items/");
                    var overrideModel = twoLayered(texture.getPath() + "_" + material + "_trim", texture, overlayTexture);

                    model.override()
                            .model(overrideModel)
                            .predicate(TRIM_TYPE_PREDICATE_ID, itemModelIndex);
                });

    }
}
