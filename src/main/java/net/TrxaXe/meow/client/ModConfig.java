package net.TrxaXe.meow.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import java.util.ArrayList;
import java.util.List;

// How to add a config
@Config(name = "Meow")
public class ModConfig implements ConfigData {
     public boolean MeowMode = false;
     public boolean Replace = true;
     public String CharModify = "~?.!。()";
     public String IgnoreChar = "/#";
     public String Suffix = "喵~";
     public List<String> ReplacementList = new ArrayList<>(List.of("喵","喵"));
     public List<String> RegexList = new ArrayList<>(List.of("(?<!\\w)草+(?!\\w)","(?<!\\w)c+(?!\\w)"));
}
