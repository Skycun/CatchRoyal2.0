package fr.skycun.catchroyal.commands;

import fr.skycun.catchroyal.Gamemode;
import fr.skycun.catchroyal.Main;
import fr.skycun.catchroyal.PlayerManager;
import fr.skycun.catchroyal.tools.ItemBuilder;
import me.croabeast.advancementinfo.AdvancementInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.advancement.Advancement;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class collection implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			if(cmd.getName().equalsIgnoreCase("collection")) {
					//Cassage de block
					Player player = (Player)sender;
					if (Main.INSTANCE.isGamemode(Gamemode.BREAKINGBLOCKS) || Main.INSTANCE.isGamemode(Gamemode.CRAFTING) || Main.INSTANCE.isGamemode(Gamemode.ALLITEMS)) {
						//Même collection
						if (Main.INSTANCE.sameCollection == true) {
							Inventory inv = Bukkit.createInventory(null, 54, "§e§lCollection: §a" + Main.INSTANCE.alreadyList.size());
							player.openInventory(inv);
							for (int i = 0; i < Main.INSTANCE.alreadyList.size(); i++) {
								if(Main.INSTANCE.alreadyListWho.get(i) == player){
									ItemStack block = new ItemBuilder(Material.getMaterial(Main.INSTANCE.alreadyList.get(i))).lore("§b§l" + player.getDisplayName()).make();
									ItemMeta itemM = block.getItemMeta();
									itemM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
									itemM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
									block.setItemMeta(itemM);
									inv.setItem(i, block);
								}else{
									ItemStack block = new ItemBuilder(Material.getMaterial(Main.INSTANCE.alreadyList.get(i))).lore("§a§l" + Main.INSTANCE.alreadyListWho.get(i).getDisplayName()).make();
									inv.setItem(i, block);
								}
							}
							return true;
							//Collection seul
						} else {
							Inventory inv = Bukkit.createInventory(null, 54, "§e§lCollection: §a" + PlayerManager.playerManager.get(player).getFound());
							player.openInventory(inv);
							for (int i = 0; i < PlayerManager.playerManager.get(player).getFoundlist().size() + 1; i++) {
								ItemStack block = new ItemStack(Material.getMaterial(PlayerManager.playerManager.get(player).getFoundlist().get(i)));
								inv.setItem(i, block);
							}
							return true;
						}

					}else if(Main.INSTANCE.isGamemode(Gamemode.ACHIEVEMENT)){
						if (Main.INSTANCE.sameCollection == true) {
							Inventory inv = Bukkit.createInventory(null, 54, "§e§lCollection: §a" + Main.INSTANCE.alreadyList.size());
							player.openInventory(inv);
							for (int i = 0; i < Main.INSTANCE.alreadyList.size(); i++) {
								if(Main.INSTANCE.alreadyListWho.get(i) == player){
									Advancement adv = Main.INSTANCE.alreadyListAdvancement.get(i);
									AdvancementInfo info = new AdvancementInfo(adv);
									ItemStack block = new ItemBuilder(info.getItem()).lores(new String[]{"§b§l" + player.getDisplayName(), "§a", "§b" + info.getDescription()}).make();
									ItemMeta itemM = block.getItemMeta();
									itemM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
									itemM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
									block.setItemMeta(itemM);
									inv.setItem(i, block);
								}else{
									Advancement adv = Main.INSTANCE.alreadyListAdvancement.get(i);
									AdvancementInfo info = new AdvancementInfo(adv);
									ItemStack block = new ItemBuilder(info.getItem()).lores(new String[]{"§a§l" + Main.INSTANCE.alreadyListWho.get(i).getDisplayName(), "&a","&b" + info.getDescription()}).make();
									inv.setItem(i, block);
								}
							}
							return true;
							//Collection seul
						} else {
							Inventory inv = Bukkit.createInventory(null, 54, "§e§lCollection: §a" + PlayerManager.playerManager.get(player).getFound());
							player.openInventory(inv);
							for (int i = 0; i < PlayerManager.playerManager.get(player).getFoundlist().size() + 1; i++) {
								Advancement adv = Main.INSTANCE.alreadyListAdvancement.get(i);
								AdvancementInfo info = new AdvancementInfo(adv);
								ItemStack block = new ItemStack(Material.getMaterial(PlayerManager.playerManager.get(player).getFoundlist().get(i)));
								inv.setItem(i, block);
							}
							return true;
						}
					}

			}
		return false;

	}
		return false;
}}
