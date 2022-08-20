package fr.skycun.catchroyal.commands;

import fr.skycun.catchroyal.Gamemode;
import fr.skycun.catchroyal.Main;
import fr.skycun.catchroyal.Senario;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class rules implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("rules") || cmd.getName().equalsIgnoreCase("regles")) {
				player.sendMessage(" §7▌ §A§lVoici les règles:");
				player.sendMessage(" §7▌");
				player.sendMessage(" §7▌ §eLe but est de collecté différente choses avant");
				player.sendMessage(" §7▌ §ela fin du temps");
				player.sendMessage(" §7▌ §6§lTEMPS: " + Main.INSTANCE.gameDuration / 60 + " minutes");
				if(Main.INSTANCE.isGamemode(Gamemode.BREAKINGBLOCKS)){
					player.sendMessage(" §7▌ §b§lMODE DE JEU: Cassage de block");
					player.sendMessage(" §7▌   §e- Vous devez récupéré le plus de block différent avant");
					player.sendMessage(" §7▌     §eles autres");
				}else if(Main.INSTANCE.isGamemode(Gamemode.CRAFTING)){
					player.sendMessage(" §7▌ §b§lMODE DE JEU: Craft");
					player.sendMessage(" §7▌   §e- Vous devez craft le plus de trucs différent avant");
					player.sendMessage(" §7▌     §eles autres");
				}else if(Main.INSTANCE.isGamemode(Gamemode.ACHIEVEMENT)){
					player.sendMessage(" §7▌ §b§lMODE DE JEU: Achievement");
					player.sendMessage(" §7▌   §e- Vous devez avoir le plus d'achievement différent avant");
					player.sendMessage(" §7▌     §eles autres");
					}
				if(Main.INSTANCE.getsamecollection() == true) {
					player.sendMessage(" §7▌ §e§lMÊME COLLECTION");
					player.sendMessage(" §7▌   §e- Vous avez la même collection que les autres alors");
					player.sendMessage(" §7▌     §eallez récupéré vos choses avant les autres");
					player.sendMessage(" §7▌     §eSi il est déjà pris alors vous ne remportez rien");	
				}else if(Main.INSTANCE.getsamecollection() == false) {
					player.sendMessage(" §7▌ §b§lCOLLECTION SEUL");
					player.sendMessage(" §7▌   §e- Vous avez une collection pour vous seul");
					player.sendMessage(" §7▌     §ealors si quelqu'un quelqu'un a déjà pris quelque");
					player.sendMessage(" §7▌     §echose, vous pouvez aussi le prendre");	
				}
				player.sendMessage(" §7▌ §9§lSCENARIOS:");
				if(Main.INSTANCE.activeSenario.isEmpty()){
					player.sendMessage(" §7▌ §bAucun");
				}else{
					for(Senario sen : Main.INSTANCE.activeSenario){
						player.sendMessage(" §7▌ §b" + sen.name() + " §7: " + sen.getDesc());
					}
				}
				
				return true;
			}
			return true;
	}
		return false;
	}

}
