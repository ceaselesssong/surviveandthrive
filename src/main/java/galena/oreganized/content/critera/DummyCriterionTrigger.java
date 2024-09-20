package galena.oreganized.content.critera;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

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

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ResourceLocation idIn) {
            super(idIn, ContextAwarePredicate.ANY);
        }
    }

}
