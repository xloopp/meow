package net.TrxaXe.meow.client.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.TrxaXe.meow.client.Converter;
import net.TrxaXe.meow.client.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ChatScreen.class)
public abstract class mixin {

    @Unique
    protected final MinecraftClient client = MinecraftClient.getInstance();


    @Inject(method = "sendMessage", at = @At("HEAD"), cancellable = true)
    public void sendMessage(String chatText, boolean addToHistory, CallbackInfo ci) {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        if (chatText == null || chatText.isEmpty()) { // 检查 chatText 是否为空
            ci.cancel();
            return;
        }
        if (config.IgnoreChar.indexOf(chatText.charAt(0)) == -1) {
            if (!config.MeowMode) {
                if (config.CharModify.indexOf(chatText.charAt(0)) != -1) {
                    if (addToHistory) this.client.inGameHud.getChatHud().addToMessageHistory(chatText);
                    if (!Objects.equals(config.Suffix, "")) {
                        this.client.player.networkHandler.sendChatMessage(config.Suffix.charAt(0) + chatText);
                    } else {
                        this.client.player.networkHandler.sendChatMessage(chatText);
                    }
                    ci.cancel();
                } else {
                    if (addToHistory) this.client.inGameHud.getChatHud().addToMessageHistory(chatText);
                    if (config.Replace) {
                        for (int i = 0;i < config.RegexList.size();i++ ) {
                            if (config.ReplacementList.get(i) != null) chatText = chatText.replaceAll(config.RegexList.get(i), config.ReplacementList.get(i));
                        }
                    }
                    if (config.CharModify.indexOf(chatText.charAt(chatText.length() - 1)) != -1) {
                        char tmp = chatText.charAt(chatText.length() - 1);
                        chatText = chatText.substring(0, chatText.length() - 1);
                        if (!Objects.equals(config.Suffix, "")) {
                            this.client.player.networkHandler.sendChatMessage(chatText + config.Suffix.charAt(0) + tmp);
                        } else {
                            this.client.player.networkHandler.sendChatMessage(chatText);
                        }
                    } else  {
                        this.client.player.networkHandler.sendChatMessage(chatText + config.Suffix);
                    }
                    ci.cancel();
                }
            } else {
                if (addToHistory) this.client.inGameHud.getChatHud().addToMessageHistory(chatText);
                chatText = Converter.Catgirl(chatText);
                this.client.player.networkHandler.sendChatMessage(chatText);
                ci.cancel();
            }
        }
    }
}