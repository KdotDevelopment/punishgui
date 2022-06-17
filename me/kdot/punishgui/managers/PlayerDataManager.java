package me.kdot.punishgui.managers;

import org.bukkit.entity.Player;

import me.kdot.punishgui.Main;

public class PlayerDataManager {
	
	private Main plugin;
	
	private final String[] offenseList = {"spam", "griefing", "disrespect", "alt-abuse", "flying-speed", "client-visuals", "pvp", "doxxing", "lag", "exploit", "endangerment", "misc"};
	
	public PlayerDataManager(Main plugin) {
		this.plugin = plugin;
	}
	
	public void createPlayerData(Player player) {
		for(String type : offenseList) {
			if(!plugin.getPlayerData().isSet(player.getUniqueId().toString() + "." + type)) {
				plugin.getPlayerData().set(player.getUniqueId().toString() + "." + type, 0);
			}
		}
		plugin.savePlayerData();
	}
	
	public void setPlayerData(Player player, String name, int data) {
		plugin.getPlayerData().set(player.getUniqueId().toString() + "." + name, data);
		plugin.savePlayerData();
	}
	
	public void addOffenseCount(Player player, String name) {
		plugin.getPlayerData().set(player.getUniqueId().toString() + "." + name, getPlayerData(player, name) + 1);
		plugin.savePlayerData();
	}
	
	public void subtractOffenseCount(Player player, String name) {
		plugin.getPlayerData().set(player.getUniqueId().toString() + "." + name, getPlayerData(player, name) - 1);
		plugin.savePlayerData();
	}
	
	public int getPlayerData(Player player, String name) {
		if(!(plugin.getPlayerData().isSet(player.getUniqueId().toString() + "." + name))) {
			createPlayerData(player);
			return 0;
		}
		return plugin.getPlayerData().getInt(player.getUniqueId().toString() + "." + name);
	}
	
	public String[] getOffenseList() {
		return offenseList;
	}
	
}
