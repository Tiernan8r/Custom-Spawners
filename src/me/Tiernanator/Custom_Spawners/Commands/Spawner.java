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

import me.Tiernanator.Custom_Spawners.CustomSpawnersMain;
import me.Tiernanator.Utilities.Colours.Colour;

//A command to give the player a custom spawner
public class Spawner implements CommandExecutor {

	@SuppressWarnings("unused")
	private static CustomSpawnersMain plugin;

	//Colours to use for output messages based off of their message type
	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
//	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor bad = Colour.BAD.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();

	//Initialiser
	public Spawner(CustomSpawnersMain main) {
		plugin = main;
	}

	//The command that gets called when /spawner is made
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		//If the command was done by the console or a command block, refuse the command
		if (!(sender instanceof Player)) {
			sender.sendMessage(warning + "You can't use this command.");
			return true;
		}

		//Get the player who made the command
		Player player = (Player) sender;

		//If the player didn't provide any arguments with the command, return an error
		if(args.length < 1) {
			player.sendMessage(bad + "You must provide the name of the entity it is to spawn.");
			return false;
		}
		//Get the name of the entity from the first argument
		String entityName = args[0];
		//Initialise the entity type as null
		EntityType entityType = null;
		//Try to get the correspoding entity from the name provided
		try {
			entityType = EntityType.valueOf(entityName.toUpperCase());
			//If an error occurs, send the player a warning message
		}catch (Exception e) {
			player.sendMessage(informative + entityName + bad + " is not an entity type, the entity types are:");
			//list all the valid entity names to the player so taht they don't make the same mistake twice
			for(EntityType entity : EntityType.values()) {
				player.sendMessage(highlight + " - " + entity.name());
			}
			return false;
		}

		//Get the block he player is looking at
		Block block = player.getTargetBlock((Set<Material>) null, 15);
		//Get the material type
		Material originalMaterial = block.getType();
		//The type is only air if the player was looking at a direction that didn't have a block in range
		if(originalMaterial == Material.AIR) {
			//Tell them it's out of range
			player.sendMessage(warning + "That block is out of range.");
			return false;
		}
		//Set the type of the block to be a mob spawner
		block.setType(Material.LEGACY_MOB_SPAWNER);
		//Get the mob spawner object of the block
		CreatureSpawner spawner = (CreatureSpawner) block.getState();
		//Try to change the spawner type
		try {
			spawner.setSpawnedType(entityType);
		}catch (Exception e) {
			//Tell the player the error that occured if there is one
			player.sendMessage(warning + e.getMessage());
			//Reset the block to be it's orgiianl typpe
			spawner.setType(originalMaterial);
			return false;
		}

		//Force update the block
		block.getState().update(true);
		
		return true;
	}

}
