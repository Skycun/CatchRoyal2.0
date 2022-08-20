package fr.skycun.catchroyal.tools;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ResetAll {

    public ResetAll(Player player){
    }

    public static void player(Player player){
        player.setLevel(0);
        player.setExp(0);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFlying(false);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
    }
}
