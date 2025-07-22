package net.TrxaXe.meow.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.util.logging.Logger;

import static net.TrxaXe.meow.client.DecodeMeow.unmeow;

public class MeowClient implements ClientModInitializer {
    public static final Logger logger = Logger.getLogger("MeowMod");
    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        ClientReceiveMessageEvents.MODIFY_GAME.register((message, overlay) -> {
            ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
            String s = message.getString();
            if ((s.indexOf('喵') != -1 || s.indexOf(' ') != -1) && s.indexOf('\u200C') != -1 && s.indexOf('呜') != -1 && config.DecodeMeow) {
                Gson gson = new Gson();
                String json = gson.toJson(TextCodecs.CODEC.encodeStart(JsonOps.INSTANCE, message).getOrThrow());
                Text DecodedMessage = TextCodecs.CODEC
                        .decode(JsonOps.INSTANCE, gson.fromJson(unmeow(json), JsonElement.class))
                        .getOrThrow()
                        .getFirst();

                Text HoverText = Text.literal(" 显示原文")
                        .setStyle(Style.EMPTY.withHoverEvent(new HoverEvent.ShowText(message)))
                        .formatted(Formatting.GRAY);
                if (DecodeMeow.Modified) {
                    return DecodedMessage.copy().append(HoverText);
                }
                return DecodedMessage;
            }
            return message;
        });
    }
}
