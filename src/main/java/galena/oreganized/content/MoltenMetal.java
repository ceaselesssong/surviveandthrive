package galena.oreganized.content;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.Map;
import java.util.function.Supplier;

public class MoltenMetal {

    protected Block solid;
    protected Fluid molten;


    public MoltenMetal(Supplier<? extends Block> solid, Supplier<? extends Fluid> molten) {
        this.solid = solid.get();
        this.molten = molten.get();
    }

    public Block getSolid() {
        return solid;
    }

    public Fluid getMolten() {
        return molten;
    }
}
