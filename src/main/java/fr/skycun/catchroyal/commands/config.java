package fr.skycun.catchroyal.commands;

import fr.skycun.catchroyal.Gamemode;
import fr.skycun.catchroyal.Main;
import fr.skycun.catchroyal.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.text.SimpleDateFormat;

public class config implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			if(cmd.getName().equalsIgnoreCase("config")) {
				if(!player.hasPermission("op")) return true;
				Inventory inv = Bukkit.createInventory(null, 54, "§c§lConfiguration");
				player.openInventory(inv);
				for (int i = 0; i < 9; i++) {
					inv.setItem(i, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).name("§b").make());
				}
				for (int i = 45; i < 54; i++) {
					inv.setItem(i, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).name("§b").make());
				}
				inv.setItem(22, new ItemBuilder(Material.EMERALD_BLOCK).name("§a§lCommencer la partie").lore("§c").lore("§eLancer la partie quand tout le monde est là").lore("§c").lore("§a§lClique gauche pour lancer").lore("§c").make());
				if(Main.INSTANCE.isGamemode(Gamemode.BREAKINGBLOCKS)) inv.setItem(31, new ItemBuilder(Material.ITEM_FRAME).name("§f§lObjectif: §bCassage de blocks").lore("§c").lore("§eQuand on casse un nouveau block").lore("§ele joueur gagne un point").lore("§c").lore("§a§lClique gauche pour modifier").lore("§c").make());
				if(Main.INSTANCE.isGamemode(Gamemode.ACHIEVEMENT)) inv.setItem(31, new ItemBuilder(Material.ITEM_FRAME).name("§f§lObjectif: §bAchievement").lore("§c").lore("§eQuand on obtient un achievement").lore("§ele joueur gagne un point").lore("§c").lore("§a§lClique gauche pour modifier").lore("§c").make());
				if(Main.INSTANCE.isGamemode(Gamemode.CRAFTING)) inv.setItem(31, new ItemBuilder(Material.ITEM_FRAME).name("§f§lObjectif: §bCraft").lore("§c").lore("§eQuand on craft").lore("§ele joueur gagne un point").lore("§c").lore("§a§lClique gauche pour modifier").lore("§c").make());
				if(Main.INSTANCE.isGamemode(Gamemode.DEATH)) inv.setItem(31, new ItemBuilder(Material.ITEM_FRAME).name("§f§lObjectif: §bMorts").lore("§c").lore("§eQuand on meurt").lore("§ele joueur gagne un point").lore("§c").lore("§a§lClique gauche pour modifier").lore("§c").make());
				if(Main.INSTANCE.isGamemode(Gamemode.ALLITEMS)) inv.setItem(31, new ItemBuilder(Material.ITEM_FRAME).name("§f§lObjectif: §bTout les items/blocks").lore("§c").lore("§eFaite /collect pour mettre l'item de votre main").lore("§ele joueur gagne un point").lore("§c").lore("§a§lClique gauche pour modifier").lore("§c").make());
				inv.setItem(20, new ItemBuilder(Material.CLOCK).name("§f§lTimer: §b" + new SimpleDateFormat("mm:ss").format(Main.INSTANCE.gameDuration * 1000)).lore("§c").lore("§eDéfinit le temps de la partie").lore("§ePlutôt game longue ou courte?").lore("§c").lore("§a§lClique gauche pour modifier").lore("§c").make());
				inv.setItem(29, new ItemBuilder(Material.WHITE_BANNER).name("§f§léquipe: §bSolo").lore("§c").lore("§eDéfinit le nombre d'équipe").lore("§eet le nombre de membre").lore("§c").lore("§c§lFonction non-implémenté").lore("§c").make());
				inv.setItem(29, new ItemBuilder(Material.WHITE_BANNER).name("§f§léquipe: §bSolo").lore("§c").lore("§eDéfinit le nombre d'équipe").lore("§eet le nombre de membre").lore("§c").lore("§c§lFonction non-implémenté").lore("§c").make());
				if(Main.INSTANCE.sameCollection) inv.setItem(24, new ItemBuilder(Material.BOOK).name("§b§lMême §f§lCollection:").lore("§c").lore("§eDéfinit si tout les joueurs on a même collection").lore("§eIci, un joueur ne peut pas prendre un objectif déjà pris").lore("§c").lore("§a§lClique gauche pour modifier").lore("§c").make());
				if(!Main.INSTANCE.sameCollection) inv.setItem(24, new ItemBuilder(Material.BOOK).name("§f§lCollection §b§lSeul").lore("§c").lore("§eDéfinit si tout les joueurs on a même collection").lore("§eIci, un joueur peut prendre un objectif déjà pris").lore("§c").lore("§a§lClique gauche pour modifier").lore("§c").make());
				inv.setItem(33, new ItemBuilder(Material.WRITABLE_BOOK).name("§f§lSénarios").lore("§c").lore("§eDéfinit les sénarios présent").lore("§edans la partie").lore("§c").lore("§a§lClique gauche pour modifier").lore("§c").make());
				player.updateInventory();
				
				
				return true;
			}
		}
		return false;
	}

	
	
}
