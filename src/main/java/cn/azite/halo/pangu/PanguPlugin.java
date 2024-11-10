package cn.azite.halo.pangu;

import org.springframework.stereotype.Component;
import run.halo.app.plugin.BasePlugin;
import run.halo.app.plugin.PluginContext;

@Component
public class PanguPlugin extends BasePlugin {
    public PanguPlugin(PluginContext pluginContext) {
        super(pluginContext);
    }
}