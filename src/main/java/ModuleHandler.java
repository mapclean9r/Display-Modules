import modules.IncludeModule;
import modules.ModuleAlias;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class ModuleHandler {
    private final Reflections reflections = new Reflections("modules");
    private final Set<Class<?>> collectedModules = reflections.getTypesAnnotatedWith(ModuleAlias.class);
    public ModuleHandler() {
        addModulesToFrame();
    }

    public void addModulesToFrame(){

        for (Class<?> clazz : collectedModules){
            if (IncludeModule.class.isAssignableFrom(clazz)){
                try {
                    IncludeModule task = (IncludeModule) clazz.getDeclaredConstructor().newInstance();
                    ModuleAlias annotation = clazz.getAnnotation(ModuleAlias.class);

                    System.out.println("------\n" + "Class: " + clazz.getName() +
                            "\nAnnotation Value: " + annotation.value() + "\n------");

                    task.RunModule();
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                         NoSuchMethodException e ) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
