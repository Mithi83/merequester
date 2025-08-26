package com.almostreliable.merequester.mixin.injects;

import com.almostreliable.merequester.ModConstants;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

import appeng.init.InitMenuTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InitMenuTypes.class)
public abstract class InitMenuTypesMixin {

    @Inject(method = "queueRegistration", at = @At("HEAD"), cancellable = true)
    private static void merequester$cancelImplicitReg(ResourceLocation id, MenuType<?> menuType, CallbackInfo ci) {
        if (id.getNamespace().equals(ModConstants.MOD_ID)) ci.cancel();
    }
}
