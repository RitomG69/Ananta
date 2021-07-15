package me.ritomg.ananta.gui;

import com.lukflug.panelstudio.theme.IColorScheme;
import com.lukflug.panelstudio.theme.ITheme;
import me.ritomg.ananta.module.ModuleManager;
import me.ritomg.ananta.module.modules.client.ClickGui;
import me.ritomg.ananta.setting.settings.ColourSetting;

import java.awt.*;

public class AColorScheme implements IColorScheme {

    ClickGui clickGui = ModuleManager.getModule(ClickGui.class);

    private final boolean isVisible;
    public AColorScheme(boolean isVisible) {
        this.isVisible = isVisible;
    }

    @Override
    public void createSetting(ITheme theme, String name, String description, boolean hasAlpha, boolean allowsRainbow, Color color, boolean rainbow) {
        ColourSetting setting = new ColourSetting(name,clickGui,description,isVisible,color,hasAlpha,allowsRainbow,rainbow);
        if (!clickGui.getSettings().contains(setting)) clickGui.addSetting(setting);
    }

    @Override
    public Color getColor(String name) {
        return ((ColourSetting) clickGui.getSettingByName(name)).getColor();
    }
}
