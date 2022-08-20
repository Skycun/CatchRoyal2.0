package fr.skycun.catchroyal.tasks;

import fr.skycun.catchroyal.Main;
import fr.skycun.catchroyal.PlayerManager;
import fr.skycun.catchroyal.State;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameoverCycle extends BukkitRunnable{

	@Override
	public void run() {
		Main.INSTANCE.setState(State.FINISH);
		for(Player pls : Main.INSTANCE.getIgplayer()) {
			pls.setGameMode(GameMode.SPECTATOR);
			pls.sendMessage("§e§lPartie Terminé", "§eRegardons maintenant les scores!");
			Main.INSTANCE.setState(State.FINISH);
			pls.playSound(pls.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.2f);
			
		}
		Bukkit.broadcastMessage(" §6▌ ");
		Bukkit.broadcastMessage(" §6▌        §e§lCATCHROYAL - §b§lPARTIE TERMINÉ");
		Bukkit.broadcastMessage(" §6▌ ");
		for(Player pls : Main.INSTANCE.getIgplayer()) {
			Bukkit.broadcastMessage(" §6▌ §b§l" + pls.getDisplayName() + " : §a" + PlayerManager.playerManager.get(pls).getFound());
		}
		Bukkit.broadcastMessage(" §6▌ ");
		Bukkit.broadcastMessage(" §6▌ §eFaite /collection pour voir les récupérations");
		Bukkit.broadcastMessage(" §6▌ ");
		for(Player pls : Main.INSTANCE.getIgplayer()) {
			if(pls.isOp()) {
				pls.sendMessage(" §6▌ §c§lADMIN:");
				pls.sendMessage(" §6▌ §eVous devez restart le serveur et supprimer le monde pour refaire une partie");
				
			}
		}
		
		
		cancel();
	}

}
