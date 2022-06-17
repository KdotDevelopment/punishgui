package me.kdot.punishgui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.kdot.punishgui.commands.CommandPunish;
import me.kdot.punishgui.commands.CommandWarn;

public class Main extends JavaPlugin {
	
	private File playerDataf;
	private FileConfiguration playerData; 
	
	@Override
	public void onEnable() {
		initFiles();
		saveDefaultConfig();
		saveConfig();
		
		this.getCommand("punish").setExecutor(new CommandPunish(this));
		this.getCommand("warn").setExecutor(new CommandWarn(this));
	}
	
	private void initFiles() {
		playerDataf = new File(getDataFolder(), "playerdata.yml");
		
		if(!playerDataf.exists()) {
			playerDataf.getParentFile().mkdirs();
			saveResource("playerdata.yml", false);
		}
		
		playerData = new YamlConfiguration();
		
		try {
			playerData.load(playerDataf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getPlayerData() {
		return playerData;
	}
	
	public void savePlayerData() {
		try {
			playerData.save(playerDataf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reloadPlayerData() {
		try {
			playerData.load(playerDataf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
}
