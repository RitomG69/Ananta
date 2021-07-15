package me.ritomg.ananta.gui;

import com.lukflug.panelstudio.theme.IColorScheme;
import com.lukflug.panelstudio.theme.ITheme;
import me.ritomg.ananta.module.Module;
import me.ritomg.ananta.module.ModuleManager;
import me.ritomg.ananta.module.modules.client.ClickGui;
import me.ritomg.ananta.setting.settings.ColourSetting;

import java.awt.*;

public class AColorScheme implements IColorScheme {

    Module m;
    public AColorScheme(Module m) {
        this.m = m;
    }

    @Override
    public void createSetting(ITheme theme, String name, String description, boolean hasAlpha, boolean allowsRainbow, Color color, boolean rainbow) {
        m.addSetting(new ColourSetting(name,m,description,true,color,hasAlpha,allowsRainbow,rainbow));
    }

    @Override
    public Color getColor(String name) {
        return ((ColourSetting) m.getSettingByName(name)).getColor();
    }
}