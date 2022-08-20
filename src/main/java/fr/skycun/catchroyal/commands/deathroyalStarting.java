package fr.skycun.catchroyal.commands;

import fr.skycun.catchroyal.Main;
import fr.skycun.catchroyal.State;
import fr.skycun.catchroyal.tasks.AutoStart;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class deathroyalStarting implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			if(cmd.getName().equalsIgnoreCase("start")) {
				if(!player.hasPermission("op")) {
					//si le joueur n'est pas op
					player.sendMessage(" §9▌ §cVous n'avez pas la permission d'executer cette commande");
				}else {
				//si le joueur est op
					//Si c'est pas déjà lancé
					if(Main.INSTANCE.isState(State.WAITING)) {
						AutoStart start = new AutoStart();
						start.runTaskTimer(Main.INSTANCE, 0, 20);
					}
				}
				
			return true;}
		
		}
		return false;
	}

}
