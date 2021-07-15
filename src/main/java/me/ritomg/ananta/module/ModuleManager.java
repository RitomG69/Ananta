package me.ritomg.ananta.module;

import me.ritomg.ananta.module.modules.client.*;
import me.ritomg.ananta.module.modules.combat.AutoXp;
import me.ritomg.ananta.module.modules.misc.*;
import me.ritomg.ananta.module.modules.movement.*;
import me.ritomg.ananta.module.modules.render.FullBright;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private static List<Module> modules;

    public static void init() {
        modules = new ArrayList<>();
        //themes
        addnewModule(new ClickGui());
        addnewModule(new ClearTheme());
        addnewModule(new ImpactTheme());
        addnewModule(new RainbowTheme());
        addnewModule(new WindowsTheme());

        addnewModule(new Sprint());
        addnewModule(new ChatUtils());
        addnewModule(new AutoXp());
        addnewModule(new FullBright());
    }

    public static List<Module> getModulesinCategory(Category c) {
        List<Module> module = new ArrayList<>();
        for (Module m : modules) {
            if (m.getCategory().equals(c)) {
                module.add(m);
            }
        }
        return module;
    }

    public static void addnewModule(Module m) {
        modules.add(m);
    }

    public static <T extends Module> T getModule(Class<T> clazz) {
        for (Module m : modules) {
            if (m.getClass().equals(clazz)) {
                return (T) m;
            }
        }
        return null;
    }

    public static List<Module> getModules() {
        return modules;
    }
}