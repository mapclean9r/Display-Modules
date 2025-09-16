package handlers;

import config.Config;
import core.StartProgram;
import modules.IncludeModule;
import modules.ModuleAlias;
import org.reflections.Reflections;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ModuleHandler {
    private final Reflections reflections = new Reflections("modules");
    private final Set<Class<?>> collectedModules = reflections.getTypesAnnotatedWith(ModuleAlias.class);
    private final StartProgram window;
    private final Config config;
    private final Map<String, Point> layoutHashmap = new HashMap<>();

    public ModuleHandler(StartProgram startProgram, Config config) {
        this.window = startProgram;
        this.config = config;
        for (Config.ModuleEntry layoutConfig : config.getModules()){
            if (!layoutConfig.isActive() || layoutConfig.getPlacement() == null) continue;
            layoutHashmap.put(layoutConfig.getAlias(),
                    new Point(
                            layoutConfig.getPlacement().getRow(),
                            layoutConfig.getPlacement().getCol()));
        }
        addModulesToFrame();
    }

    public void addModulesToFrame(){
        for (Class<?> clazz : collectedModules){
            if (IncludeModule.class.isAssignableFrom(clazz) && isConfigToLoad(clazz.getAnnotation(ModuleAlias.class).value())){
                try {
                    IncludeModule task = (IncludeModule) clazz.getDeclaredConstructor().newInstance();
                    Point point = layoutHashmap.get(clazz.getAnnotation(ModuleAlias.class).value());
                    window.setModuleAt(point.x, point.y, task.RunModule());

                } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                         NoSuchMethodException e ) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /** Checks if the module in config is enabled && exists **/
    private boolean isConfigToLoad(String moduleName){
        for (Config.ModuleEntry config : config.getModules()){
            //System.out.println(moduleName + " | and | " + config.getAlias());
            if (Objects.equals(moduleName, config.getAlias()) && config.isActive()){
                return true;
            }
        }
        return false;
    }
}
