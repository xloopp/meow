package net.TrxaXe.meow.client.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.TrxaXe.meow.client.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class PlayerMixin {
    @Inject(at = @At("HEAD"), method = "getScale", cancellable = true)
    public void ModifyScale(CallbackInfoReturnable<Float> cir) {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        LivingEntity entity = (LivingEntity)(Object)this;
        if (entity instanceof PlayerEntity) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (entity == client.player) {
                cir.setReturnValue(config.PlayerScale);
            }
        }
    }
}
