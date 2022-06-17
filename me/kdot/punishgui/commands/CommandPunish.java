package me.kdot.punishgui.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.kdot.punishgui.Main;
import me.kdot.punishgui.gui.PunishGUI;
import net.md_5.bungee.api.ChatColor;

public class CommandPunish implements CommandExecutor {

	Main plugin;
	
	public CommandPunish(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("punish") || label.equalsIgnoreCase("punishgui:punish")) {
			if(sender.hasPermission("punishgui.use")) {
				if(sender instanceof Player) {
					if(args.length == 1) {
						if(Bukkit.getPlayer(args[0]) != null) {
							Player player = (Player) sender;
							plugin.reloadPlayerData();
							plugin.reloadConfig();
							PunishGUI gui = new PunishGUI(plugin, Bukkit.getPlayer(args[0]), player);
							gui.openInv();
						}else {
							sender.sendMessage(ChatColor.RED + "[Punish] Could not find player!");
						}
					}else {
						sender.sendMessage(ChatColor.RED + "[Punish] Usage: /punish <player>");
					}
				}else {
					sender.sendMessage(ChatColor.RED + "[Punish] You must be a player!");
				}
			}
		}
		return false;
	}

}
