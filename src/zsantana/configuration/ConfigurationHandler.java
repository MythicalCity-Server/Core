package zsantana.configuration;

import java.util.ArrayList;
import java.util.List;

import zsantana.handlers.Handler;

public class ConfigurationHandler extends Handler {
	
	private static List<Configuration> _CONFIGS;
	
	protected static void addConfiguration(Configuration configuration) {
		_CONFIGS.add(configuration);
	}
	
	@Override
	protected void enable() {
		_CONFIGS = new ArrayList<>();
	}

	@Override
	protected void disable() {
		for (Configuration config : _CONFIGS) {
			config.onDisable();
		}
	}
}
