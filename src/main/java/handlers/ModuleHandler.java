package handlers;

import config.ModuleConfig;
import core.StartProgram;
import modules.IncludeModule;
import modules.ModuleAlias;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Set;

public class ModuleHandler {
    private final Reflections reflections = new Reflections("modules");
    private final Set<Class<?>> collectedModules = reflections.getTypesAnnotatedWith(ModuleAlias.class);
    private final StartProgram window;
    private final ModuleConfig moduleConfig;

    public ModuleHandler(StartProgram startProgram, ModuleConfig moduleConfig) {
        this.window = startProgram;
        this.moduleConfig = moduleConfig;
        addModulesToFrame();
    }

    public void addModulesToFrame(){
        for (Class<?> clazz : collectedModules){
            if (IncludeModule.class.isAssignableFrom(clazz) && isConfigToLoad(clazz.getAnnotation(ModuleAlias.class).value())){
                try {
                    IncludeModule task = (IncludeModule) clazz.getDeclaredConstructor().newInstance();
                    //window.setContentPane(task.RunModule());
                    //todo config for r c
                    window.setModuleAt(0, 1, task.RunModule());

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
