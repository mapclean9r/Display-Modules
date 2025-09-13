package config;

import java.util.List;
import java.util.Map;

public class ModuleConfig {
    private String type;
    private List<ModuleEntry> modules;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<ModuleEntry> getModules() { return modules; }
    public void setModules(List<ModuleEntry> modules) { this.modules = modules; }

    public static class ModuleEntry {
        private String alias;
        private boolean active;
        private Map<String, Object> settings;

        public String getAlias() { return alias; }
        public void setAlias(String alias) { this.alias = alias; }

        public boolean isActive() { return active; }
        public void setActive(boolean active) { this.active = active; }

    }
}