package net.TrxaXe.meow.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
public class MeowClient implements ClientModInitializer {

    public static boolean MeowMode;

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("meow")
                .executes(context -> {
                    MeowMode = !MeowMode;
                    // 发送反馈信息
                    if (MeowMode) {
                        context.getSource().sendFeedback(Text.literal("MeowMode is now ON!"));
                    } else {
                        context.getSource().sendFeedback(Text.literal("MeowMode is now OFF!"));
                    }
                            return 1;
                        }
                )));
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
