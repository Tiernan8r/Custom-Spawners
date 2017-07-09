package me.Tiernanator.Custom_Spawners.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Custom_Spawners.CustomSpawnersMain;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagShort;
import net.minecraft.server.v1_12_R1.NBTTagString;

public class Skulls implements CommandExecutor {

	@SuppressWarnings("unused")
	private static CustomSpawnersMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor bad = Colour.BAD.getColour();

	public Skulls(CustomSpawnersMain main) {
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
			player.sendMessage(bad + "You must provide a player name for a skull");
			return false;
		}
		String playerName = args[0];
		
		ItemStack skull = new ItemStack(Material.SKULL_ITEM);
		
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		net.minecraft.server.v1_12_R1.ItemStack nmsSkull = CraftItemStack.asNMSCopy(skull);
		nbtTagCompound = nmsSkull.save(nbtTagCompound);
		NBTTagString nbtTagString = new NBTTagString(playerName);
		nbtTagCompound.set("SkullOwner", nbtTagString);
		nbtTagCompound.set("Damage", new NBTTagShort((short) 3));
		nmsSkull.setTag(nbtTagCompound);
		net.minecraft.server.v1_12_R1.ItemStack skullItem = new net.minecraft.server.v1_12_R1.ItemStack(nbtTagCompound);
		skull = CraftItemStack.asBukkitCopy(skullItem);
		
		player.getInventory().addItem(skull);
		
		return true;
	}

}
