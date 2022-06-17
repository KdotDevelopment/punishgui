package me.kdot.punishgui.managers;

import org.bukkit.entity.Player;

import me.kdot.punishgui.Main;
import net.md_5.bungee.api.ChatColor;

public class PunishmentManager {
	
	private Main plugin;
	//private Player sender;
	private Player player;
	private PlayerDataManager dmanager;
	
	public PunishmentManager(Main plugin, Player sender, Player player, PlayerDataManager dmanager) { //sender = moderator, player = banned guy
		this.plugin = plugin;
		//this.sender = sender;
		this.player = player;
		this.dmanager = dmanager;
	}
	
	public String[] getPunishmentCommand(String basicName) {
		int offenseLevel = dmanager.getPlayerData(player, basicName);
		String configString = plugin.getConfig().getString(basicName + "." + offenseLevel);
		String[] parsedString = parseConfigString(configString);
		String reasonString = plugin.getConfig().getString(basicName + ".string");
		
		if(parsedString[0].equals("Warning")) {
			return new String[] {"punishgui:warn " + player.getName() + " " + reasonString, ChatColor.GREEN + player.getName() + ChatColor.WHITE + " has been warned. Reason: " + ChatColor.GREEN + reasonString};
		}
		if(parsedString[0].equals("Ban")) {
			String finalString = "essentials:tempban " + player.getName() + " " + parsedString[1]; //EX: "tempban KdotDevelopment 3"
			if(parsedString[2].equals("Hours")) {
				finalString = finalString + "h";
			}
			if(parsedString[2].equals("Days")) {
				finalString = finalString + "d";
			}
			if(parsedString[2].equals("Weeks")) {
				finalString = finalString + "w";
			}
			if(parsedString[2].equals("Months")) {
				finalString = finalString + "mo";
			}
			if(parsedString[2].equals("Years")) {
				finalString = finalString + "y";
			}
			finalString = finalString + " " + reasonString;
			return new String[] {finalString, ChatColor.GREEN + player.getName() + ChatColor.WHITE + " has been temporarily banned for " + parsedString[1] + " " + parsedString[2].toLowerCase() + ". Reason: " + ChatColor.GREEN + reasonString};
		}
		if(parsedString[0].equals("Mute")) {
			String finalString = "essentials:mute " + player.getName() + " " + parsedString[1]; //EX: "mute KdotDevelopment 3"
			if(parsedString[2].equals("Hours")) {
				finalString = finalString + "h";
			}
			if(parsedString[2].equals("Days")) {
				finalString = finalString + "d";
			}
			if(parsedString[2].equals("Weeks")) {
				finalString = finalString + "w";
			}
			if(parsedString[2].equals("Months")) {
				finalString = finalString + "mo";
			}
			if(parsedString[2].equals("Years")) {
				finalString = finalString + "y";
			}
			finalString = finalString + " " + reasonString;
			return new String[] {finalString, ChatColor.GREEN + player.getName() + ChatColor.WHITE + " has been temporarily muted for " + parsedString[1] + " " + parsedString[2].toLowerCase() + ". Reason: " + ChatColor.GREEN + reasonString};
		}
		if(parsedString[0].equals("IPBan")) {
			String finalString = "essentials:tempbanip " + player.getName() + " " + parsedString[1]; //EX: "tempbanip KdotDevelopment 3"
			if(parsedString[2].equals("Hours")) {
				finalString = finalString + "h";
			}
			if(parsedString[2].equals("Days")) {
				finalString = finalString + "d";
			}
			if(parsedString[2].equals("Weeks")) {
				finalString = finalString + "w";
			}
			if(parsedString[2].equals("Months")) {
				finalString = finalString + "mo";
			}
			if(parsedString[2].equals("Years")) {
				finalString = finalString + "y";
			}
			finalString = finalString + " " + reasonString;
			return new String[] {finalString, ChatColor.GREEN + player.getName() + ChatColor.WHITE + " has been temporarily IP-Banned for " + parsedString[1] + " " + parsedString[2].toLowerCase() + ". Reason: " + ChatColor.GREEN + reasonString};
		}
		if(parsedString[0].equals("Perm Ban")) {
			return new String[] {"essentials:ban " + player.getName() + " " + reasonString, ChatColor.GREEN + player.getName() + ChatColor.WHITE + " has been permanently banned. Reason: " + ChatColor.GREEN + reasonString};
		}
		if(parsedString[0].equals("Perm Mute")) {
			return new String[] {"essentials:mute " + player.getName() + " " + reasonString, ChatColor.GREEN + player.getName() + ChatColor.WHITE + " has been permanently muted. Reason: " + ChatColor.GREEN + reasonString};
		}
		if(parsedString[0].equals("Perm IPBan")) {
			return new String[] {"essentials:banip " + player.getName() + " " + reasonString, ChatColor.GREEN + player.getName() + ChatColor.WHITE + " has been permanently IP-Banned. Reason: " + ChatColor.GREEN + reasonString};
		}
		
		return new String[] {"ERROR", "ERROR"};
	}
	
	private String[] parseConfigString(String configString) {
		String[] finalResult;
		finalResult = configString.split("-");
		return finalResult;
	}
	
}
