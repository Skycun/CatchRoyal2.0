package fr.skycun.catchroyal.commands;

import fr.skycun.catchroyal.Gamemode;
import fr.skycun.catchroyal.Main;
import fr.skycun.catchroyal.PlayerManager;
import fr.skycun.catchroyal.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class collect implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			if(cmd.getName().equalsIgnoreCase("collect")) {
					//Cassage de block
					Player player = (Player)sender;
					Material addItem = player.getItemInHand().getType();
					if(Main.INSTANCE.isGamemode(Gamemode.ALLITEMS)) {
						if (Main.INSTANCE.sameCollection) {
							//Si il y a déjà le block dans la list
							if (!Main.INSTANCE.alreadyList.contains(addItem.toString())) {
								Main.INSTANCE.alreadyList.add(addItem.toString());
								Bukkit.broadcastMessage("§b§l" + player.getDisplayName() + " §fa trouvé §b§l" + addItem.toString());
								player.sendMessage("§a§lBRAVO! §fVous gagnez §bun §fpoint!");
								PlayerManager.playerManager.get(player).setFound(PlayerManager.playerManager.get(player).getFound() + 1);
								Main.INSTANCE.alreadyListWho.add(player);
								player.getInventory().remove(new ItemStack(addItem, 1));
							}
							//Si on a pas tous la meme liste
						} else {
							if (!PlayerManager.playerManager.get(player).getFoundlist().contains(addItem.toString())) {
								player.sendMessage("§a§lBRAVO! §fVous avez trouvé " + addItem.toString() + " §b+1 §f point");
								PlayerManager.playerManager.get(player).setFound(PlayerManager.playerManager.get(player).getFound() + 1);
								player.getInventory().remove(new ItemStack(addItem, 1));
							}
						}
					}



					return true;
			}
		return false;

	}
		return false;
}}
