package me.ritomg.ananta.module.modules.theme;

import me.ritomg.ananta.module.Category;
import me.ritomg.ananta.module.Module;
import me.ritomg.ananta.module.ModuleManager;
import me.ritomg.ananta.module.modules.client.ClickGui;
import me.ritomg.ananta.setting.settings.BooleanSetting;

@Module.Info(name = "RainbowTheme", description = "Change color of Rainbow Theme", category = Category.Client)
public class RainbowTheme extends Module {

    public BooleanSetting ignoreDisabled = new BooleanSetting("IgnoreDisabled",this,true);
    public BooleanSetting buttonRainbow = new BooleanSetting("ButtowRainbow",this,false);

    public void onEnable() {
        ModuleManager.getModule(ClickGui.class).theme.setCurrentMode("RainbowTheme");
    }
}
