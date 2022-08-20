package fr.skycun.catchroyal.tasks;

import fr.skycun.catchroyal.*;
import fr.skycun.catchroyal.tools.ResetAll;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class AutoStart extends BukkitRunnable{

	private int timer = 10;
	

	@Override
	public void run() {
		//Lancement du jeu
		Main.INSTANCE.setState(State.STARTING);
		for(Player pls : Main.INSTANCE.getIgplayer()) {
			pls.setLevel(timer);
			if(timer == 0) {
				//La partie commence
				ResetAll.player(pls);
				pls.playSound(pls.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.2f);
				Main.INSTANCE.setState(State.PLAYING);
				//Active senario
				if(Main.INSTANCE.isActiveSenario(Senario.KEEPINVENTORY)){
					Bukkit.getWorld("world").setKeepSpawnInMemory(true);
					Bukkit.getWorld("world_nether").setKeepSpawnInMemory(true);
					Bukkit.getWorld("world").setGameRule(GameRule.KEEP_INVENTORY, true);
					Bukkit.getWorld("world_nether").setGameRule(GameRule.KEEP_INVENTORY, true);
				}else{
					Bukkit.getWorld("world").setKeepSpawnInMemory(false);
					Bukkit.getWorld("world_nether").setKeepSpawnInMemory(false);
					Bukkit.getWorld("world").setGameRule(GameRule.KEEP_INVENTORY, false);
					Bukkit.getWorld("world_nether").setGameRule(GameRule.KEEP_INVENTORY, false);
				}
				//Netheribus scenario
				if(Main.INSTANCE.isActiveSenario(Senario.NETHERIBUS)){
					World nether = Bukkit.getWorld("world_nether");
					pls.teleport(nether.getSpawnLocation());
					pls.sendTitle("§6§lGO", "§cBon voyage dans le netheur", 20,20,20);

				}else{
					pls.teleport(new Location(Bukkit.getWorld("world"), 1, Bukkit.getWorld("world").getHighestBlockYAt(0, 0) + 2, 0));
					pls.setBedSpawnLocation(new Location(Bukkit.getWorld("world"), 100, Bukkit.getWorld("world").getHighestBlockYAt(0, 0) + 2, 0));
					pls.sendTitle("§6§lGO", "§aLetsgong", 20,20,20);
				}
				//HollowPlayer scenario
				if(Main.INSTANCE.isActiveSenario(Senario.HOLLOWPLAYER)){
					pls.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 99999,1));
				}

				//BOUNTY SCENARIO
				if(Main.INSTANCE.isActiveSenario(Senario.BOUNTY)){
					PlayerManager.playerManager.get(pls).setTargetPlayer(Main.INSTANCE.igplayer.get(new Random().nextInt(Main.INSTANCE.igplayer.size())));
					if(PlayerManager.playerManager.get(pls).getTargetPlayer() == pls){
						pls.sendMessage(" §9▌ §eScénario Bounty: §aVotre cible est: §b§lVous-même? §aDommage :)");
					}else{
						pls.sendMessage(" §9▌ §eScénario Bounty: §aVotre cible est: §b§l" + PlayerManager.playerManager.get(pls).getTargetPlayer().getDisplayName() + " §atuez le pour avoir 5 items randoms");
					}


				}
				//ELYTAFORVER SCENARIO
				if(Main.INSTANCE.isActiveSenario(Senario.ELYTRAFOREVER)) pls.getInventory().setChestplate(new ItemStack(Material.ELYTRA));

				//Gamemode adchivement
				if(Main.INSTANCE.isGamemode(Gamemode.ACHIEVEMENT)) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement revoke " + pls.getDisplayName() + " everything");

			}else if(timer == 1) {
				pls.sendTitle("§a§l1", "§ePrêt?", 20,20,20);
				pls.playSound(pls.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.2f);
			}else if(timer == 2) {
				pls.sendTitle("§e§l2", "§eà vos marques", 20,20,20);
				pls.playSound(pls.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.2f);
			}else if(timer == 3) {
				pls.sendTitle("§c§l3", "§eAttention", 20,20,20);
				pls.playSound(pls.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.2f);
			}else if(timer == 5 || timer == 10) {
				pls.sendMessage(" §6▌ §eLa partie commence dans §b" + timer + " secondes!");
				pls.playSound(pls.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.2f);
			}
		}
		if(timer == 0 ) {
			GameCycle start = new GameCycle();
			start.runTaskTimer(Main.INSTANCE, 0, 20);
			cancel();
		}
		timer--;
	}

}
