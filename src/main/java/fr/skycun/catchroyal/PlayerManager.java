package fr.skycun.catchroyal;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerManager {
    public static HashMap<Player, PlayerManager> playerManager = new HashMap<>();
    Player player;
    //
    Integer found;
    //
    ArrayList<String> foundlist = new ArrayList<String>();


    ArrayList<String> foundlistAdvancement = new ArrayList<String>();


    Player targetPlayer;

//

    public PlayerManager(Player player){
        playerManager.put(player, this);
    }

    public Integer getFound() {
        return found;
    }

    public void setFound(Integer found) {
        this.found = found;
    }
    public void addFound(Integer found) {
        this.found =+ found;
    }

    public ArrayList<String> getFoundlist() {
        return foundlist;
    }

    public void setFoundlist(ArrayList<String> foundlist) {
        this.foundlist = foundlist;
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }

    public void setTargetPlayer(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
    }
    public ArrayList<String> getFoundlistAdvancement() {
        return foundlistAdvancement;
    }

    public void setFoundlistAdvancement(ArrayList<String> foundlistAdvancement) {
        this.foundlistAdvancement = foundlistAdvancement;
    }

    }
