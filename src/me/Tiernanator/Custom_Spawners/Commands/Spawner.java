package me.Tiernanator.Custom_Spawners.Commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Custom_Spawners.CustomSpawnersMain;

public class Spawner implements CommandExecutor {

	@SuppressWarnings("unused")
	private static CustomSpawnersMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
//	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor bad = Colour.BAD.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();

	public Spawner(CustomSpawnersMain main) {
		plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(warning + "You can't use this command.");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(args.length < 1) {
			player.sendMessage(bad + "You must provide the name of the entity it is to spawn.");
			return false;
		}
		String entityName = args[0];
		EntityType entityType = null;
		try {
			entityType = EntityType.valueOf(entityName.toUpperCase());
		}catch (Exception e) {
			player.sendMessage(informative + entityName + bad + " is not an entity type, the entity types are:");
			for(EntityType entity : EntityType.values()) {
				player.sendMessage(highlight + " - " + entity.name());
			}
			return false;
		}
		
		Block block = player.getTargetBlock((Set<Material>) null, 15);
		Material originalMaterial = block.getType();
		if(originalMaterial == Material.AIR) {
			player.sendMessage(warning + "That block is out of range.");
			return false;
		}
		block.setType(Material.MOB_SPAWNER);
		CreatureSpawner spawner = (CreatureSpawner) block.getState();
		try {
			spawner.setSpawnedType(entityType);
		}catch (Exception e) {
			player.sendMessage(warning + e.getMessage());
			spawner.setType(originalMaterial);
			return false;
		}
		
		block.getState().update(true);
		
		return true;
	}

}
