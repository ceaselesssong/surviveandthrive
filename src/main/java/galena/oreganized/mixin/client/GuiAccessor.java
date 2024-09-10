package galena.oreganized.mixin.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Gui.class)
public interface GuiAccessor {

    @Invoker
    void callRenderHeart(GuiGraphics p_283024_, Gui.HeartType p_281393_, int p_283636_, int p_283279_, int p_283188_, boolean p_283440_, boolean p_282496_);

}
