package handlers;

import core.StartProgram;
import modules.IncludeModule;
import modules.ModuleAlias;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class ModuleHandler {
    private final Reflections reflections = new Reflections("modules");
    private final Set<Class<?>> collectedModules = reflections.getTypesAnnotatedWith(ModuleAlias.class);
    private final StartProgram window;

    public ModuleHandler(StartProgram startProgram) {
        this.window = startProgram;
        addModulesToFrame();
    }

    public void addModulesToFrame(){
        for (Class<?> clazz : collectedModules){
            if (IncludeModule.class.isAssignableFrom(clazz)){
                try {
                    IncludeModule task = (IncludeModule) clazz.getDeclaredConstructor().newInstance();
                    window.setContentPane(task.RunModule());

                } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                         NoSuchMethodException e ) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
