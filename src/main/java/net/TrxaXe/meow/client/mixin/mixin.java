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
        if (chatText.charAt(0) != '/'){
            if (!config.MeowMode) {
                switch (chatText.charAt(0)) {
                    case '?', '.', '。', '!', '(', ')':
                        if (addToHistory) this.client.inGameHud.getChatHud().addToMessageHistory(chatText);
                        if (config.Suffix != null) this.client.player.networkHandler.sendChatMessage(config.Suffix.charAt(0) + chatText);
                        ci.cancel();
                        break;
                    default:
                        if (addToHistory) this.client.inGameHud.getChatHud().addToMessageHistory(chatText);
                        if (config.Filter) {
                            chatText = chatText.replaceAll(config.Regex1, config.Replacement1);
                            chatText = chatText.replaceAll(config.Regex2, config.Replacement2);
                        }
                        switch (chatText.charAt(chatText.length() - 1)) {
                            case '?', '.', '!', '。', '(', ')':
                                char tmp = chatText.charAt(chatText.length() - 1);
                                chatText = chatText.substring(0, chatText.length() - 1);
                                if (config.Suffix != null) this.client.player.networkHandler.sendChatMessage(chatText + config.Suffix.charAt(0) + tmp);
                                break;
                            default:
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