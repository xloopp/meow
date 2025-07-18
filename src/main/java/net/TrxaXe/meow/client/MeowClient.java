package net.TrxaXe.meow.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
public class MeowClient implements ClientModInitializer {

    public static boolean AiMeowMode;
    public static boolean Replace;
    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        AiMeowMode = config.AiMeowMode;
        Replace = config.Replace;
    }
}
