package zsantana.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import zsantana.Core;

public class Configuration {

	private static Core _CORE;

	public static void setCore(Core core) {
		_CORE = core;
	}

	private final File _FILE;
	private final FileConfiguration _FILE_CONFIGURATION;
	private final List<BiConsumer<File, FileConfiguration>> _OPERATIONS;

	public Configuration(File file) {
		this._FILE = file;
		try {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this._FILE_CONFIGURATION = YamlConfiguration.loadConfiguration(file);
		try {
			this._FILE_CONFIGURATION.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this._OPERATIONS = new ArrayList<>();
		ConfigurationHandler.addConfiguration(this);
	}

	public Configuration(String fileName) {
		this._FILE = new File(_CORE.getDataFolder().getParentFile() + File.separator + "Core" + File.separator + fileName + ".yml");
		try {
			if (!this._FILE.exists()) {
				this._FILE.getParentFile().mkdirs();
				this._FILE.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this._FILE_CONFIGURATION = YamlConfiguration.loadConfiguration(this._FILE);
		try {
			this._FILE_CONFIGURATION.save(this._FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this._OPERATIONS = new ArrayList<>();
		this._FILE_CONFIGURATION.options().copyDefaults(true);
		ConfigurationHandler.addConfiguration(this);
	}

	public FileConfiguration getFileConfiguration() {
		return this._FILE_CONFIGURATION;
	}

	public void set(Map<String, Object> set) {
		for (String key : set.keySet()) {
			this._FILE_CONFIGURATION.set(key, set.get(key));
		}
		try {
			this._FILE_CONFIGURATION.save(this._FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addDefaults(Map<String, Object> set) {
		for (String key : set.keySet()) {
			this._FILE_CONFIGURATION.addDefault(key, set.get(key));
		}
		try {
			this._FILE_CONFIGURATION.save(this._FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addSaver(BiConsumer<File, FileConfiguration> operator) {
		this._OPERATIONS.add(operator);
	}
	
	public void onDisable() {
		try {
			if (!this._FILE.exists()) {
				this._FILE.getParentFile().mkdirs();
				this._FILE.createNewFile();
			}
			for (BiConsumer<File, FileConfiguration> operator : this._OPERATIONS) {
				operator.accept(this._FILE, this._FILE_CONFIGURATION);
			}
			this._FILE_CONFIGURATION.save(this._FILE);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
