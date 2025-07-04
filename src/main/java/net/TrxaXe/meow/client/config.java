package net.TrxaXe.meow.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

// How to add a config
@Config(name = "Meow")
class config implements ConfigData {
     boolean test = true;
}
