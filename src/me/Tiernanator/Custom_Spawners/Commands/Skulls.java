package me.Tiernanator.Custom_Spawners.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Tiernanator.Custom_Spawners.CustomSpawnersMain;
import me.Tiernanator.Utilities.Colours.Colour;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagShort;
import net.minecraft.server.v1_14_R1.NBTTagString;

//Gives the player a skull based off the provided entity name
public class Skulls implements CommandExecutor {

	@SuppressWarnings("unused")
	private static CustomSpawnersMain plugin;

	//Define some colours for warnings
	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor bad = Colour.BAD.getColour();

	public Skulls(CustomSpawnersMain main) {
		plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		//can only give skulls to players
		if (!(sender instanceof Player)) {
			sender.sendMessage(warning + "You can't use this command.");
			return true;
		}

		//get the player that made the command
		Player player = (Player) sender;
		//if they didn't provide an argument then return an error
		if(args.length < 1) {
//			tell the player the error
			player.sendMessage(bad + "You must provide a player name for a skull");
			return false;
		}
		//get the name of the player
		String playerName = args[0];

		//create a new skull item
		@SuppressWarnings("deprecation")
		ItemStack skull = new ItemStack(Material.LEGACY_SKULL_ITEM);

		//get a new instance of all the associated nbt tags for the skull
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		//get a copy of all the nbot data of the skull
		net.minecraft.server.v1_14_R1.ItemStack nmsSkull = CraftItemStack.asNMSCopy(skull);
		//add the existing nbt data to the data
		nbtTagCompound = nmsSkull.save(nbtTagCompound);
		//Createa a new nbt string
		NBTTagString nbtTagString = new NBTTagString(playerName);
		//Set the skull owner to be the player
		nbtTagCompound.set("SkullOwner", nbtTagString);
		//Set the skull to be a plyer skull
		nbtTagCompound.set("Damage", new NBTTagShort((short) 3));
		//Applyt the taga to the item
		nmsSkull.setTag(nbtTagCompound);
		//Create an item from the tag
		net.minecraft.server.v1_14_R1.ItemStack skullItem = new net.minecraft.server.v1_14_R1.ItemStack(nbtTagCompound);
		//Get the item tot the correct data type and give it to the palyer
		skull = CraftItemStack.asBukkitCopy(skullItem);
		
		player.getInventory().addItem(skull);
		
		return true;
	}

}
