package net.TrxaXe.meow.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

// How to add a config
@Config(name = "Meow")
public class ModConfig implements ConfigData {
     public boolean MeowMode = false;
     public boolean Filter = true;
     public String Suffix = "喵~";
     public String Replacement = "喵";
     public String Regex1 = "(?<!\\w)c+(?!\\w)";
     public String Regex2 = "(?<!\\w)草+(?!\\w)";
}
