package handlers;

import config.LayoutConfig;
import config.ModuleConfig;
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
    private final ModuleConfig moduleConfig;
    private final Map<String, Point> layoutHashmap = new HashMap<>();

    public ModuleHandler(StartProgram startProgram, ModuleConfig moduleConfig, LayoutConfig layoutConfig) {
        this.window = startProgram;
        this.moduleConfig = moduleConfig;
        for (int i = 0; i < layoutConfig.getPlacements().size(); i++){

            layoutHashmap.put(layoutConfig.getPlacements().get(i).getAlias(),
                    new Point(
                            layoutConfig.getPlacements().get(i).getRow(),
                            layoutConfig.getPlacements().get(i).getCol()));
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
        for (ModuleConfig.ModuleEntry config : moduleConfig.getModules()){
            //System.out.println(moduleName + " | and | " + config.getAlias());
            if (Objects.equals(moduleName, config.getAlias()) && config.isActive()){
                return true;
            }
        }
        return false;
    }
}
