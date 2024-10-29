package galena.doom_and_gloom.data.provider;

import galena.doom_and_gloom.DoomAndGloom;
import galena.doom_and_gloom.content.block.SepulcherBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

import static galena.doom_and_gloom.DoomAndGloom.MOD_ID;
import static net.minecraftforge.client.model.generators.ModelProvider.BLOCK_FOLDER;

public abstract class OBlockStateProvider extends BlockStateProvider {

    public OBlockStateProvider(PackOutput output, ExistingFileHelper help) {
        super(output, MOD_ID, help);
    }

    protected ResourceLocation texture(String name) {
        return modLoc(BLOCK_FOLDER + "/" + name);
    }

    protected String name(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    protected String name(Supplier<? extends Block> block) {
        return name(block.get());
    }

    private String sepulcherSuffix(int fillLevel) {
        if (fillLevel == 0) return "";
        if (fillLevel > SepulcherBlock.MAX_LEVEL) return "_sealed_" + (fillLevel - SepulcherBlock.MAX_LEVEL);
        return "_being_filled_" + fillLevel;
    }

    public void sepulcherBlock(Supplier<? extends Block> block) {
        getVariantBuilder(block.get()).forAllStates(state -> {
            var fillLevel = state.getValue(SepulcherBlock.LEVEL);
            var name = blockTexture(block.get()).withSuffix(sepulcherSuffix(fillLevel));
            return ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(name))
                    .build();
        });
    }

    private String candleSuffix(int amount) {
        switch (amount) {
            case 1:
                return "single";
            case 2:
                return "double";
            case 3:
                return "triple";
            case 4:
                return "quadruple";
            default:
                throw new IllegalArgumentException("Illegal candle amount: " + amount);
        }
    }

    public void vigilCandle(Supplier<? extends Block> block, @Nullable String prefix) {
        getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            var candles = state.getValue(BlockStateProperties.CANDLES);
            boolean hanging = state.getValue(BlockStateProperties.HANGING);
            boolean lit = state.getValue(AbstractCandleBlock.LIT);

            var hangingSuffix = hanging ? "_ceiling" : "";
            var parent = "vigil_candle_" + candleSuffix(candles) + hangingSuffix;
            var optionalPrefix = Optional.ofNullable(prefix).map(it -> it + "_");
            var litSuffix = lit ? "_lit" : "";
            var name = optionalPrefix.orElse("default") + parent + litSuffix;
            var texture = BLOCK_FOLDER + "/" + optionalPrefix.orElse("") + "vigil_candle" + litSuffix;

            var model = models().withExistingParent(name, DoomAndGloom.modLoc(parent))
                    .texture("0", texture);

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        }, BlockStateProperties.WATERLOGGED);
    }

}
