package net.TrxaXe.meow.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.TrxaXe.meow.client.MeowClient;

@Mixin(ChatScreen.class)
public abstract class mixin {

    @Unique
    protected final MinecraftClient client = MinecraftClient.getInstance();


    @Inject(method = "sendMessage", at = @At("HEAD"), cancellable = true)
    public void sendMessage(String chatText, boolean addToHistory, CallbackInfo ci) {
        if (chatText == null || chatText.isEmpty()) { // 检查 chatText 是否为空
            ci.cancel();
            return;
        }
        if (chatText.charAt(0) != '/'){
            switch (chatText.charAt(0)) {
                case '?', '.', '。', '!','(',')':
                    this.client.player.networkHandler.sendChatMessage("喵"+chatText);
                    ci.cancel();
                    break;
                default:
                    if(!MeowClient.MeowMode) {
                        if (addToHistory) this.client.inGameHud.getChatHud().addToMessageHistory(chatText);
                        chatText = chatText.replaceAll("(?<!\\w)c+(?!\\w)", "喵");
                        chatText = chatText.replaceAll("(?<!\\w)草+(?!\\w)", "喵");
                       switch (chatText.charAt(chatText.length() -1)) {
                           case '?','.','!','。','(',')':
                                char tmp = chatText.charAt(chatText.length() -1);
                                chatText = chatText.substring(0, chatText.length() - 1);
                                this.client.player.networkHandler.sendChatMessage(chatText + "喵" + tmp);
                                break;
                           default:
                                this.client.player.networkHandler.sendChatMessage(chatText + "喵~");
                       }
                        ci.cancel();
                    }else {
                        if (addToHistory) this.client.inGameHud.getChatHud().addToMessageHistory(chatText);
                        chatText = chatText.replaceAll(".", "喵");
                        this.client.player.networkHandler.sendChatMessage(chatText);
                        ci.cancel();
                    }
            }
        }
    }
}