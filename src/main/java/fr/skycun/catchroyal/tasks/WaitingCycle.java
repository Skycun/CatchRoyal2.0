package fr.skycun.catchroyal.tasks;

import fr.skycun.catchroyal.Main;
import fr.skycun.catchroyal.State;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WaitingCycle  extends BukkitRunnable{

	@Override
	public void run() {
		if(!Main.INSTANCE.isState(State.WAITING)) {
			cancel();
		}else {
			for(Player pls : Main.INSTANCE.getIgplayer()) {
				pls.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c§lEn attente du host"));
			}
		}
		
	}

}
