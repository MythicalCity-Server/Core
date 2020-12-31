package testing;

import java.util.HashMap;
import java.util.Map;

import zsantana.configuration.Configuration;

public class TestConfig {
	
	public TestConfig() {
		Configuration config = new Configuration("testfile");
		
		Map<String, Object> set = new HashMap<>();
		
		set.put("test", true);
		
		config.addDefaults(set);
		
		config.addSaver((file, fileconfig) -> {
			fileconfig.set("ploop", false);
		});
	}
}
