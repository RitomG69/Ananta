package me.ritomg.ananta.setting.settings;

import com.lukflug.panelstudio.setting.IColorSetting;
import me.ritomg.ananta.module.Module;
import me.ritomg.ananta.setting.Setting;

import java.awt.*;

public class ColourSetting extends Setting  {

    private Color color;
    private  boolean isRainbow = false,allowRainbow;
    private  boolean hasAlpha;

    public ColourSetting(String name, Module parent, Color color, boolean isRainbow, boolean allowRainbow) {
        super(name, parent);
        this.color = color;
        this.isRainbow = isRainbow;
        this.allowRainbow = allowRainbow;
        hasAlpha = false;
    }

    public ColourSetting(String name, Module parent, boolean isVisible, Color color, boolean isRainbow, boolean allowRainbow,boolean hasAlpha) {
        super(name, parent, isVisible);
        this.color = color;
        this.isRainbow = isRainbow;
        this.allowRainbow = allowRainbow;
        this.hasAlpha = hasAlpha;
    }

    public ColourSetting(String name, Module parent, Color color) {
        super(name, parent);
        this.color = color;
        isRainbow = false;
        allowRainbow = true;
        this.hasAlpha = false;
    }

    public ColourSetting(String name, Module parent, boolean isVisible, Color color) {
        super(name, parent, isVisible);
        this.color = color;
        this.isRainbow = false;
        this.allowRainbow = true;
        hasAlpha = false;
    }


    public ColourSetting(String name, Module parent, boolean isVisible, Color color,boolean hasAlpha) {
        super(name, parent, isVisible);
        this.color = color;
        this.isRainbow = false;
        this.allowRainbow = true;
        this.hasAlpha = hasAlpha;
    }

    public ColourSetting(String name, Module parent,String description, boolean isVisible, Color color,boolean hasAlpha) {
        super(name, parent,description, isVisible);
        this.color = color;
        this.isRainbow = false;
        this.allowRainbow = true;
        this.hasAlpha = hasAlpha;
    }

    public ColourSetting(String name, Module parent,String description, boolean isVisible, Color color,boolean hasAlpha,boolean allowRainbow,boolean isRainbow) {
        super(name, parent,description, isVisible);
        this.color = color;
        this.isRainbow = isRainbow;
        this.allowRainbow = allowRainbow;
        this.hasAlpha = hasAlpha;
    }

    public long getColorRGB() {
        long colorgb = getColor().getRGB();
        return colorgb;
    }

    public void setColorRGB(long RGB) {
        setColor(new Color((int) RGB));
    }

    public Color getColor() {
        return this.color;
    }

    public static Color fromHSB(float hue, float saturation, float brightness) {
        return Color.getHSBColor(hue, saturation, brightness);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isRainbow() {
        return isRainbow;
    }

    public void setRainbow(boolean rainbow) {
        isRainbow = rainbow;
    }

    public boolean isAllowRainbow() {
        return allowRainbow;
    }

    public void setAllowRainbow(boolean allowRainbow) {
        this.allowRainbow = allowRainbow;
    }

    public boolean isHasAlpha() {
        return hasAlpha;
    }

    public void setHasAlpha(boolean hasAlpha) {
        this.hasAlpha = hasAlpha;
    }
}
