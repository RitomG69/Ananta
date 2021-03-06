package me.ritomg.ananta.hud;


import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.layout.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.widget.*;
import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.base.SettingsAnimation;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.hud.HUDGUI;
import com.lukflug.panelstudio.mc12.MinecraftHUDGUI;
import com.lukflug.panelstudio.popup.MousePositioner;
import com.lukflug.panelstudio.popup.PanelPositioner;
import com.lukflug.panelstudio.popup.PopupTuple;

import me.ritomg.ananta.Ananta;
import me.ritomg.ananta.gui.TextFieldKeys;
import me.ritomg.ananta.module.Category;
import me.ritomg.ananta.module.ModuleManager;
import me.ritomg.ananta.module.modules.client.ClickGui;
import me.ritomg.ananta.module.modules.client.CustomFont;
import me.ritomg.ananta.util.font.FontUtil;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;


import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class HudGui extends MinecraftHUDGUI {

    public ClickGui clickGui = ModuleManager.getModule(ClickGui.class);
    public static final int WIDTH = 100, HEIGHT = 12, FONT_HEIGHT = 9, DISTANCE = 10, HUD_BORDER = 2;
    public static IClient client;
    public static GUIInterface guiInterface;
    public static Comparator<IModule> modulesCompar;
    public HUDGUI gui;

    public HudGui() {

        // Define interface and themes ..
        guiInterface = new GUIInterface(true) {

            @Override
            public void drawString(Point pos, int height, String s, Color c) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(pos.x,pos.y,0);
                double scale=height/(double)(FontUtil.getFontHeight()+(ModuleManager.getModule(CustomFont.class).isEnabled()?1:0));
//                GlStateManager.scale(scale,scale,1);
                end(false);
                if (clickGui.shadow.isOn())
                    FontUtil.drawStringWithShadow(s,0,0,c);
                else FontUtil.drawString(s,0,0,c);
                begin(false);
                GlStateManager.scale(scale,scale,1);
                GlStateManager.popMatrix();
            }

            @Override
            public int getFontWidth(int height, String s) {
                double scale=height/(double)(FontUtil.getFontHeight()+(ModuleManager.getModule(CustomFont.class).isEnabled()?1:0));
                return (int)Math.round(FontUtil.getStringWidth(ModuleManager.getModule(CustomFont.class).isEnabled(),s)*scale);
            }

            @Override
            protected String getResourcePrefix() {
                return "ananta:gui/";
            }
        };


        ITheme theme = Ananta.gui.getTheme();

        // Define client structure
        client=()-> Arrays.stream(Category.values()).sorted(Comparator.comparing(Enum::toString)).map(category->new ICategory() {
            @Override
            public String getDisplayName() {
                return category.toString();
            }

            @Override
            public Stream<IModule> getModules() {
                return HudManager.getHudInCategory(category).stream().sorted(Comparator.comparing(Hud::getName)).map(module->new IModule() {
                    @Override
                    public String getDisplayName() {
                        return module.getName();
                    }

                    @Override
                    public String getDescription() {
                        return module.getDescription();
                    }

                    @Override
                    public IToggleable isEnabled() {
                        return new IToggleable() {
                            @Override
                            public boolean isOn() {
                                return module.isEnabled();
                            }

                            @Override
                            public void toggle() {
                                module.toggle();
                            }
                        };
                    }

                    @Override
                    public Stream<ISetting<?>> getSettings() {
                        Stream<ISetting<?>> temp=module.getSettings().stream().map(Ananta.gui::createSettings);
                        return Stream.concat(temp,Stream.concat(Stream.of(new IBooleanSetting() {
                            @Override
                            public String getDisplayName() {
                                return "Toggle Msgs";
                            }

                            @Override
                            public void toggle() {
                                module.toggleMessage = !module.toggleMessage;
                            }

                            @Override
                            public boolean isOn() {
                                return module.toggleMessage;
                            }
                        }),Stream.of(new IKeybindSetting() {
                            @Override
                            public String getDisplayName() {
                                return "Keybind";
                            }

                            @Override
                            public int getKey() {
                                return module.getBind();
                            }

                            @Override
                            public void setKey(int key) {
                                module.setBind(key);
                            }

                            @Override
                            public String getKeyName() {
                                return Keyboard.getKeyName(module.getBind());
                            }
                        })));
                    }
                });
            }

        });

        Supplier<Animation> animation=()->new SettingsAnimation(()->clickGui.animationSpeed.getCurrent(),()->guiInterface.getTime());

        BiFunction<Context,Integer,Integer> scrollHeight=(context, componentHeight)->{
            if (clickGui.scrolling.is("Screen")) return componentHeight;
            else return Math.min(componentHeight,Math.max(10*4, HudGui.this.height-context.getPos().y-10));
        };

        // Define GUI object
        IToggleable guiToggle=new SimpleToggleable(false);
        IToggleable hudToggle=new SimpleToggleable(false) {
            @Override
            public boolean isOn() {
                if (guiToggle.isOn()&&super.isOn()) return true;
                return super.isOn();
            }
        };
        gui = new HUDGUI(guiInterface, theme.getDescriptionRenderer(),new MousePositioner(new Point(10,10)),guiToggle,hudToggle);
        // Populate HUD
        for (Hud module : HudManager.huds) {
                (module).populate(theme);
                gui.addHUDComponent((module).getComponent(),new IToggleable() {
                    @Override
                    public boolean isOn() {
                        return module.isEnabled();
                    }

                    @Override
                    public void toggle() {
                        module.toggle();
                    }
                },animation.get(), theme,HUD_BORDER);
        }


        IContainer<IFixedComponent> container = new IContainer<IFixedComponent>() {
            @Override
            public boolean addComponent(IFixedComponent component) {
                return gui.addComponent(new IFixedComponentProxy<IFixedComponent>() {

                    @Override
                    public void handleScroll (Context context, int diff) {
                        IFixedComponentProxy.super.handleScroll(context,diff);
                        if (clickGui.scrolling.is("Screen")) {
                            Point p = getPosition(guiInterface);
                            p.translate(0, -diff);
                            setPosition(guiInterface, p);
                        }
                    }

                    @Override
                    public IFixedComponent getComponent() {
                        return component;
                    }
                });
            }

            @Override
            public boolean addComponent(IFixedComponent component, IBoolean visible) {
                return gui.addComponent(new IFixedComponentProxy<IFixedComponent>() {

                    @Override
                    public void handleScroll (Context context, int diff) {
                        IFixedComponentProxy.super.handleScroll(context,diff);
                        if (clickGui.scrolling.is("Screen")) {
                            Point p = getPosition(guiInterface);
                            p.translate(0, -diff);
                            setPosition(guiInterface, p);
                        }
                    }

                    @Override
                    public IFixedComponent getComponent() {
                        return component;
                    }
                },visible);
            }

            @Override
            public boolean removeComponent(IFixedComponent component) {
                return gui.removeComponent(component);
            }
        };

        IComponentGenerator generator=new ComponentGenerator(scancode->scancode== Keyboard.KEY_DELETE, character->character>=' ', new TextFieldKeys()){
            @Override
            public IComponent getColorComponent(IColorSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
                return new ColorPickerComponent(setting,theme);
            }
        };

        PopupTuple popupTuple=new PopupTuple(new PanelPositioner(new Point(0,0)),true,new IScrollSize() {
            @Override
            public int getScrollHeight (Context context, int componentHeight) {
                return scrollHeight.apply(context,componentHeight);
            }
        });

        //normal layout
        IComponentAdder classicPanelaAder = new PanelAdder(container,false,()->true,title->title) {
            @Override
            protected IScrollSize getScrollSize (IResizable size) {
                return new IScrollSize() {
                    @Override
                    public int getScrollHeight (Context context, int componentHeight) {
                        return scrollHeight.apply(context,componentHeight);
                    }
                };
            }

            @Override
            protected IResizable getResizable(int width) {

                Dimension dimension = new Dimension(130,100 );

                return new IResizable() {
                    @Override
                    public Dimension getSize() {
                        return dimension;
                    }

                    @Override
                    public void setSize(Dimension size) {

                        if (size.getWidth() < 75) size.width = 75;
                        if (size.getHeight() <50) size.height = 50;
                        dimension.setSize(size);
                    }
                };
            }
        };
        ILayout classicPanelLayout=new PanelLayout(100,new Point(10,10),(110)/2,112,animation, level-> ChildUtil.ChildMode.DOWN, level-> ChildUtil.ChildMode.DOWN,popupTuple);
        classicPanelLayout.populateGUI(classicPanelaAder,generator,client,theme);

    }

    @Override
    protected HUDGUI getGUI() {
        return gui;
    }

    @Override
    protected GUIInterface getInterface() {
        return guiInterface;
    }

    @Override
    protected int getScrollSpeed() {
        return Objects.requireNonNull(ModuleManager.getModule(ClickGui.class)).scrollSpeed.getCurrent();
    }


}
