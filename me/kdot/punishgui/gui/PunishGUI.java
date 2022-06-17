package me.kdot.punishgui.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.kdot.punishgui.Main;
import me.kdot.punishgui.managers.PlayerDataManager;
import me.kdot.punishgui.managers.PunishmentManager;
import net.md_5.bungee.api.ChatColor;

public class PunishGUI implements Listener {
	
	private Inventory inv;
	private PlayerDataManager dmanager;
	private PunishmentManager pmanager;
	
	private Main plugin;
	private Player sender;
	private Player player;
	
	private NamespacedKey key = new NamespacedKey(Bukkit.getPluginManager().getPlugin("PunishGUI"), "PunishGUI");
	
	public PunishGUI(Main plugin, Player player, Player sender) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		dmanager = new PlayerDataManager(plugin);
		pmanager = new PunishmentManager(plugin, sender, player, dmanager);
		this.plugin = plugin;
		this.sender = sender;
		this.player = player;
	}
	
	/* Spam/Advertising ; Griefing ; Disrespect ; Alt Abuse ; Flying/Speed ; Xray/Client Visuals ; PVP Hacks ; Doxxing ; Server Lag ; Duping/Exploits ; Endangerment ; Misc Hacking ; Impersonation
	 * Perm Ban ; Perm Mute ; IP Ban ; Custom Ban/Mute
	 */
	
	public void openInv() { //sender = moderator sending command; player = person getting banned
		inv = Bukkit.createInventory(player, 54, ChatColor.RED + "Punish " + player.getName());
		
		inv.setItem(10, guiItem(Material.BOOK, "&cSpam/Advertising", "spam", "&7Offenses: " + dmanager.getPlayerData(player, "spam")));
		inv.setItem(11, guiItem(Material.BRICKS, "&cGriefing", "griefing", "&7Offenses: " + dmanager.getPlayerData(player, "griefing")));
		inv.setItem(12, guiItem(Material.DEAD_BUSH, "&cDisrespect", "disrespect", "&7Offenses: " + dmanager.getPlayerData(player, "disrespect")));
		inv.setItem(13, guiItem(Material.PAINTING, "&cAlt Abuse", "alt-abuse", "&7Offenses: " + dmanager.getPlayerData(player, "alt-abuse")));
		inv.setItem(14, guiItem(Material.ELYTRA, "&cFlying/Speed Hacks", "flying-speed", "&7Offenses: " + dmanager.getPlayerData(player, "flying-speed")));
		inv.setItem(15, guiItem(Material.DIAMOND_ORE, "&cXray/Client-Side Visuals", "client-visuals", "&7Offenses: " + dmanager.getPlayerData(player, "client-visuals")));
		inv.setItem(16, guiItem(Material.DIAMOND_SWORD, "&cPVP Hacks", "pvp", "&7Offenses: " + dmanager.getPlayerData(player, "pvp")));
		
		inv.setItem(20, guiItem(Material.RED_BED, "&cDoxxing", "doxxing", "&7Offenses: " + dmanager.getPlayerData(player, "doxxing")));
		inv.setItem(21, guiItem(Material.PISTON, "&cServer Lag", "lag", "&7Offenses: " + dmanager.getPlayerData(player, "lag")));
		inv.setItem(22, guiItem(Material.DIAMOND_BLOCK, "&cDuping/Game Exploits", "exploit", "&7Offenses: " + dmanager.getPlayerData(player, "exploit")));
		inv.setItem(23, guiItem(Material.PLAYER_HEAD, "&cEndangerment", "endangerment", "&7Offenses: " + dmanager.getPlayerData(player, "endangerment")));
		inv.setItem(24, guiItem(Material.WRITABLE_BOOK, "&cMisc. Hacking", "misc", "&7Offenses: " + dmanager.getPlayerData(player, "misc")));
		
		inv.setItem(39, guiItem(Material.RED_STAINED_GLASS_PANE, "&4&lPerm Ban", "permban", ""));
		inv.setItem(40, guiItem(Material.RED_STAINED_GLASS_PANE, "&4&lPerm Mute", "permmute", ""));
		inv.setItem(41, guiItem(Material.RED_STAINED_GLASS_PANE, "&4&lIP Ban", "ipban", ""));
		inv.setItem(45, guiItem(Material.BARRIER, "&cCancel", "cancel", ""));
		inv.setItem(53, guiItem(Material.LAPIS_BLOCK, "&9&lCustom Punishment", "custom", ""));
		
		sender.openInventory(inv);
	}
	
	ItemStack guiItem(Material material, String name, String basicName, String lore) {
		ItemStack item = new ItemStack(material, 1);
	    ItemMeta meta = item.getItemMeta();
	    meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, basicName); //So the item can be securely identified later
	    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
	    if(lore != "") { //So it doesnt have that annoying little space under the title
	    	meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', lore)));
	    }
	    item.setItemMeta(meta);
	    return item;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory() != inv) return;
		
		e.setCancelled(true);
		
		if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;
		
		handleItemClick(e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING));
	}
	
	private void handleItemClick(String basicName) {
		if(basicName == "cancel") {
			sender.closeInventory();
		}
		if(basicName == "custom") {
			sender.closeInventory();
			sender.sendMessage(ChatColor.RED + "[Punish] This feature is not yet available!");
		}
		
		if(basicName != "cancel" && basicName != "permban" && basicName != "permmute" && basicName != "ipban" && basicName != "custom") {
			sender.closeInventory();
			dmanager.addOffenseCount(player, basicName);
			String[] command = pmanager.getPunishmentCommand(basicName);
			if(command[0].equals("ERROR")) {
				sender.sendMessage("[Punish] An internal error occured. Please use /tempban and notify a developer immediately.");
			}else {
				sender.performCommand(command[0]);
				plugin.getServer().broadcastMessage(command[1]);
			}
		}
	}
	
}
