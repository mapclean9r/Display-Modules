import modules.ModuleAlias;
import org.reflections.Reflections;
import java.util.Set;

public class ModuleHandler {

    public ModuleHandler(){
        Reflections reflections = new Reflections("modules");
        Set<Class<?>> collectModules = reflections.getTypesAnnotatedWith(ModuleAlias.class);

        for (int i = 0; i < collectModules.size(); i++){
            System.out.println(collectModules.getClass());
        }
    }
}
