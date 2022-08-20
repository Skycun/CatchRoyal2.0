package fr.skycun.catchroyal.listeners;

import fr.skycun.catchroyal.Gamemode;
import fr.skycun.catchroyal.Main;
import fr.skycun.catchroyal.PlayerManager;
import fr.skycun.catchroyal.State;
import me.croabeast.advancementinfo.AdvancementInfo;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.inventory.ItemStack;

public class GamemodePoint implements Listener {


	//Si on est en mode de jeu breaking
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (Main.INSTANCE.isGamemode(Gamemode.BREAKINGBLOCKS)) {
			if(!Main.INSTANCE.isState(State.PLAYING) && !event.getPlayer().isOp()){ event.setCancelled(true); return;} //Si on joue pas et qu'on est pas op = no break
			if(!Main.INSTANCE.isState(State.PLAYING) && event.getPlayer().isOp()){ return;} //Si on joue pas et qu'on est op = break
			//Si on a tous la meme liste
			if(Main.INSTANCE.sameCollection){
				//Si il y a déjà le block dans la list
				if(!Main.INSTANCE.alreadyList.contains(event.getBlock().getType().toString())){
					Main.INSTANCE.alreadyList.add(event.getBlock().getType().toString());
					Bukkit.broadcastMessage("§b§l" + event.getPlayer().getDisplayName() + " §fa trouvé §b§l" + event.getBlock().getType().toString());
					event.getPlayer().sendMessage("§a§lBRAVO! §fVous gagnez §bun §fpoint!");
					PlayerManager.playerManager.get(event.getPlayer()).setFound(PlayerManager.playerManager.get(event.getPlayer()).getFound()+1);
					Main.INSTANCE.alreadyListWho.add(event.getPlayer());
				}
			//Si on a pas tous la meme liste
			}else{
				if(!PlayerManager.playerManager.get(event.getPlayer()).getFoundlist().contains(event.getBlock().getType().toString())){
					event.getPlayer().sendMessage("§a§lBRAVO! §fVous avez trouvé " + event.getBlock().getType().toString() + " §b+1 §f point");
					PlayerManager.playerManager.get(event.getPlayer()).setFound(PlayerManager.playerManager.get(event.getPlayer()).getFound()+1);
				}
			}


		}
	}

	//Si on est en mode de jeu crafting
	@EventHandler
	public void onCraft(CraftItemEvent event){
		if(Main.INSTANCE.isGamemode(Gamemode.CRAFTING)) {
			if(Main.INSTANCE.sameCollection){
				if(!Main.INSTANCE.alreadyList.contains(event.getCurrentItem().getType().toString())){
					Main.INSTANCE.alreadyList.add(event.getCurrentItem().getType().toString());
					Bukkit.broadcastMessage("§b§l" + event.getWhoClicked().getName() + " §fa trouvé §b§l" + event.getCurrentItem().getType().toString());
					event.getWhoClicked().sendMessage("§a§lBRAVO! §fVous gagnez §bun §fpoint!");
					PlayerManager.playerManager.get(event.getWhoClicked()).setFound(PlayerManager.playerManager.get(event.getWhoClicked()).getFound()+1);
					Main.INSTANCE.alreadyListWho.add((Player) event.getWhoClicked());
			}
		}else{
				if(!PlayerManager.playerManager.get(event.getWhoClicked()).getFoundlist().contains(event.getCurrentItem().getType().toString())){
					event.getWhoClicked().sendMessage("§a§lBRAVO! §fVous avez trouvé " + event.getCurrentItem().getType().toString() + " §b+1 §f point");
					PlayerManager.playerManager.get(event.getWhoClicked()).setFound(PlayerManager.playerManager.get(event.getWhoClicked()).getFound()+1);


				}
			}
		}
	}

}
