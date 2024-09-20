package galena.oreganized.content.critera;

import com.google.gson.JsonObject;
import com.simibubi.create.foundation.advancement.CriterionTriggerBase;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class DummyCriterionTrigger extends SimpleCriterionTrigger<DummyCriterionTrigger.TriggerInstance> {

    private final ResourceLocation id;

    public DummyCriterionTrigger(ResourceLocation id) {
        this.id = id;
    }

    public TriggerInstance instance() {
        return new TriggerInstance(getId());
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate contextAwarePredicate, DeserializationContext deserializationContext) {
        return instance();
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public static class TriggerInstance extends CriterionTriggerBase.Instance {
        public TriggerInstance(ResourceLocation idIn) {
            super(idIn, ContextAwarePredicate.ANY);
        }

        protected boolean test(@Nullable List<Supplier<Object>> suppliers) {
            return true;
        }
    }

}
