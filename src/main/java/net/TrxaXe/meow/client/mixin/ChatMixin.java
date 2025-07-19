package net.TrxaXe.meow.client.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.TrxaXe.meow.client.AisConverter;
import net.TrxaXe.meow.client.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static net.TrxaXe.meow.client.MeowMode.meow;

@Mixin(ChatScreen.class)
public abstract class ChatMixin {

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
            if (!config.AiMeowMode && !config.MeowMode) {
                if (config.CharModify.indexOf(chatText.charAt(0)) != -1) {
                    if (addToHistory) this.client.inGameHud.getChatHud().addToMessageHistory(chatText);
                    if (!Objects.equals(config.Suffix, "")) {
                        this.client.player.networkHandler.sendChatMessage(config.Suffix.charAt(0) + chatText.trim());
                    } else {
                        this.client.player.networkHandler.sendChatMessage(chatText.trim());
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
                            this.client.player.networkHandler.sendChatMessage(chatText.trim() + config.Suffix.charAt(0) + tmp);
                        } else {
                            this.client.player.networkHandler.sendChatMessage(chatText.trim() + tmp);
                        }
                    } else  {
                        this.client.player.networkHandler.sendChatMessage(chatText.trim() + config.Suffix);
                    }
                    ci.cancel();
                }
            } else {
                if (config.MeowMode) {
                    if (addToHistory) this.client.inGameHud.getChatHud().addToMessageHistory(chatText);
                    this.client.player.networkHandler.sendChatMessage(meow(chatText));
                    ci.cancel();
                } else {
                    if (addToHistory) this.client.inGameHud.getChatHud().addToMessageHistory(chatText);
                    chatText = AisConverter.Catgirl(chatText);
                    this.client.player.networkHandler.sendChatMessage(chatText.trim());
                    ci.cancel();
                }
            }
        }
    }
}