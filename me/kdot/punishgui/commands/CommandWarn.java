package me.kdot.punishgui.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.kdot.punishgui.Main;
import net.md_5.bungee.api.ChatColor;

public class CommandWarn implements CommandExecutor {

	Main plugin;
	
	public CommandWarn(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("warn") || label.equalsIgnoreCase("punishgui:warn")) {
			if(sender.hasPermission("punishgui.warn")) {
				if(args.length >= 1) {
					if(Bukkit.getPlayer(args[0]) != null) {
						String desc = "";
						for(int i = 0; i < args.length; i++) {
							if (i < 1) continue;
							desc += args[i] + " ";
						}
						if(desc.isBlank()) {
							Bukkit.getPlayer(args[0]).sendMessage(ChatColor.DARK_RED + "You have been warned!");
							Bukkit.getPlayer(args[0]).sendTitle(ChatColor.DARK_RED + "You have been Warned!", "", 10, 60, 10);
							sender.sendMessage(ChatColor.RED + "[Punish] You have warned " + Bukkit.getPlayer(args[0]).getName() + "!");
						}else {
							Bukkit.getPlayer(args[0]).sendMessage(ChatColor.DARK_RED + "You have been warned! Reason: " + desc);
							Bukkit.getPlayer(args[0]).sendTitle(ChatColor.DARK_RED + "You have been Warned!", ChatColor.DARK_RED + desc, 10, 60, 10);
							sender.sendMessage(ChatColor.RED + "[Punish] You have warned " + Bukkit.getPlayer(args[0]).getName() + " for " + desc);
						}
					}else {
						sender.sendMessage(ChatColor.RED + "[Punish] Could not find player!");
					}
				}else {
					sender.sendMessage(ChatColor.RED + "[Punish] Usage: /warn <player> <reason>");
				}
			}
		}
		return false;
	}
	
}
