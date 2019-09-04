package tk.bongostudios.curtaincarpet.mixins;

import net.minecraft.block.dispenser.DispenserBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import tk.bongostudios.curtaincarpet.containers.DispenserBehaviors;

@Mixin(DispenserBehavior.class)
public interface DispenserBehaviorMixin {

    @Inject(method = "registerDefaults", at = @At("TAIL"))
    static void onRegisterDefaults(CallbackInfo ci) {
        DispenserBehaviors.registerBehaviors();
    }
}