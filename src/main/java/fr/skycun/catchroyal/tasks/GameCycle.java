package fr.skycun.catchroyal.tasks;

import fr.skycun.catchroyal.Main;
import fr.skycun.catchroyal.Senario;
import fr.skycun.catchroyal.State;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Random;

public class GameCycle extends BukkitRunnable{

	public static int timer =0;
	private int td;
	
	@Override
	public void run() {
		Main.INSTANCE.setState(State.PLAYING);
		Main.INSTANCE.scoreboard.updateScoreboard();
		timer++;
		int delay = Main.INSTANCE.gameDuration;
		
		for(Player pls : Main.INSTANCE.getIgplayer()) {
			pls.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent( "§a§l" + new SimpleDateFormat("mm:ss").format(timer * 1000)));
		}
		
		if(timer == delay-60 || timer == delay-30 || timer == delay-10 || timer == delay-5 || timer == delay-3 || timer == delay-2 || timer == delay-1) {
			for(Player pls : Main.INSTANCE.getIgplayer()) {
				td = delay - timer;
				pls.sendMessage(" §9▌ §ePlus que §b" + td + " secondes §erestant!");
				pls.playSound(pls.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.2f);
			}
		}else if(timer == delay) {
			//Game over
			GameoverCycle start = new GameoverCycle();
			start.runTaskTimer(Main.INSTANCE, 0, 20);
			cancel();

		}

		if(Main.INSTANCE.isActiveSenario(Senario.RANDOMEFFECT)){
			if(timer % 60 == 0){
				PotionEffect random = new PotionEffect(PotionEffectType.values()[new Random().nextInt(PotionEffectType.values().length)], 20*60,1);
				for(Player pls : Main.INSTANCE.getIgplayer()){
					pls.addPotionEffect(random);
				}
			}
		}
	}

}
