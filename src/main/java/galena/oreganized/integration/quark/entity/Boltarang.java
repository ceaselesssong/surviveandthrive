package galena.oreganized.integration.quark.entity;

import galena.oreganized.integration.quark.QCompatRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import vazkii.quark.content.tools.config.PickarangType;
import vazkii.quark.content.tools.entity.rang.AbstractPickarang;
import vazkii.quark.content.tools.entity.rang.Pickarang;
import vazkii.quark.content.tools.module.PickarangModule;

public class Boltarang extends AbstractPickarang<Pickarang> {


    public Boltarang(EntityType<Boltarang> type, Level worldIn) {
        super(type, worldIn);
    }

    public Boltarang(EntityType<Boltarang> type, Level worldIn, LivingEntity throwerIn) {
        super(type, worldIn, throwerIn);
    }

    public Boltarang(Level worldIn, LivingEntity throwerIn) {
        super(QCompatRegistry.BOLTARANG.get(), worldIn, throwerIn);
    }

    @Override
    public PickarangType<Pickarang> getPickarangType() {
        return PickarangModule.pickarangType;
    }

}
