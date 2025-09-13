package modules.clock;

import modules.IncludeModule;
import modules.ModuleAlias;

@ModuleAlias(value = "clock-module-default")
public class ClockModule implements IncludeModule {
    @Override
    public void RunModule() {
        System.out.println("module loaded: " + this.getClass().getAnnotation(ModuleAlias.class).value());
    }

}
