package modules.clock;

import modules.IncludeModule;
import modules.ModuleAlias;

@ModuleAlias("clock-module-default")
public class clockModule implements IncludeModule {
    @Override
    public void RunModule() {
        System.out.println("module"+this.getClass().toString()+" said hello!");
    }
}
