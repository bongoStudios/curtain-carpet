package tk.bongostudios.curtaincarpet.mixins;

import tk.bongostudios.curtaincarpet.CurtainExtension;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClient_noopMixin {

    @Inject(method = "<init>",at = @At("RETURN"))
    private void loadMe(CallbackInfo ci) {
        CurtainExtension.noop();
    }
}
