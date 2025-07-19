package net.TrxaXe.meow.client;

import me.shedaniel.autoconfig.AutoConfig;

public class MeowMode {
    public static String meow(String chatText) {
        StringBuilder result = new StringBuilder();
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        for (char c : chatText.toCharArray()) {
            if (config.CharModify.indexOf(c) != -1) {
                result.append(c);
            } else {
                result.append(
                        Integer.toString(c, 3)
                                .replace('0', ' ')
                                .replace('1', '喵')
                                .replace('2', '\u200C')
                );
                result.append('呜');
            }
        }

        return result.toString();
    }
}
