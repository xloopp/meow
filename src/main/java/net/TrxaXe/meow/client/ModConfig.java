package net.TrxaXe.meow.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

// How to add a config
@Config(name = "Meow")
public class ModConfig implements ConfigData {
     boolean test = true;
}
