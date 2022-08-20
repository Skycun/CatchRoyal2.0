package fr.skycun.catchroyal;

import dev.jcsoftware.jscoreboards.JGlobalMethodBasedScoreboard;
import dev.jcsoftware.jscoreboards.JPerPlayerMethodBasedScoreboard;
import dev.jcsoftware.jscoreboards.JPerPlayerScoreboard;
import fr.skycun.catchroyal.commands.*;
import fr.skycun.catchroyal.listeners.GUIitemListeners;
import fr.skycun.catchroyal.listeners.GamemodePoint;
import fr.skycun.catchroyal.listeners.PlayerListeners;
import fr.skycun.catchroyal.listeners.SenarioListeners;
import fr.skycun.catchroyal.tasks.WaitingCycle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {
    public List<Player> igplayer = new ArrayList<>();
    //Si m§me collection
    //Liste des blocks/adchivemment.. d§j§ r§cup§r§
    public List<String> alreadyList = new ArrayList<>();
    public List<Advancement> alreadyListAdvancement = new ArrayList<>();
    public List<Player> alreadyListWho = new ArrayList<>();
    //List pour break
    private State state;
    public List<Senario> activeSenario = new ArrayList<>();
    public static Main INSTANCE;
    public Gamemode gamemode;
    public Boolean sameCollection;
    public int gameDuration;

    public JPerPlayerScoreboard scoreboard;
    //Enable disable
    @Override
    public void onEnable() {
        //Création des scoreboards

        scoreboard = new JPerPlayerScoreboard(
                (player) -> {
                    return "&3&l【  &b&lCatchRoyal  &3&l】";
                },
                (player) -> {
                    if(isState(State.WAITING)){
                        String mdj = "null";
                        if(isGamemode(Gamemode.BREAKINGBLOCKS)) mdj = "Cassage de block";
                        if(isGamemode(Gamemode.CRAFTING)) mdj = "Craft";
                        if(isGamemode(Gamemode.ALLITEMS)) mdj = "Tout les items/blocs";
                        if(isGamemode(Gamemode.ACHIEVEMENT)) mdj = "Achivement";
                        if(isGamemode(Gamemode.DEATH)) mdj = "Mort";
                        return Arrays.asList(
                                "&5",
                                "&5▌ &d&lGAME:",
                                "  &b" + mdj,
                                "  &bMême &fcollection",
                                "  &fUtilisez /rules",
                                "&f",
                                "&6▌ &e&lSERVEUR:",
                                "  &fJoueurs: &b" + Main.INSTANCE.getIgplayer().size(),
                                "  &c&lEn attente du host",
                                "&c",
                                "&b&lby @Skycun:"
                        );
                    } else if (isState(State.PLAYING)) {
                        return Arrays.asList(
                                "&5",
                                "&6▌ &e&lGAME:",
                                "  &fTemps total:&b " + new SimpleDateFormat("mm:ss").format(Main.INSTANCE.gameDuration * 1000),
                                "  &fCollection:&b " + Main.INSTANCE.alreadyList.size(),
                                "  &fVos points:&b " + PlayerManager.playerManager.get(player).getFound(),
                                "  &fJoueurs:&b " + Main.INSTANCE.getIgplayer().size(),
                                "&5",
                                "&5▌ &d&lCONSEILS:",
                                "  &eutilise /collection",
                                "&c",
                                "&b&lby @Skycun"
                                );
                    }

                    return null;
                });

        Bukkit.getOnlinePlayers().forEach(this::addToScoreboard);
        INSTANCE = this;
        System.out.println("DeathRoyal | Plugin Enabled");
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListeners(this), this);
        pm.registerEvents(new GamemodePoint(), this);
        pm.registerEvents(new GUIitemListeners(), this);
        pm.registerEvents(new SenarioListeners(), this);
        setState(State.WAITING);
        igplayer.clear();
        setGamemode(Gamemode.BREAKINGBLOCKS);
        alreadyList.clear();
        gameDuration = 20*60;
        sameCollection = true;
        activeSenario.clear();
        getServer().getWorld("world").setSpawnLocation(new Location(Bukkit.getWorld("world"), 100, Bukkit.getWorld("world").getHighestBlockYAt(0, 0) + 2, 0));

        //Déclaration commandes
        this.getCommand("start").setExecutor(new deathroyalStarting());
        //this.getCommand("collection").setExecutor(new collection());
        this.getCommand("rules").setExecutor(new rules());
        //this.getCommand("regles").setExecutor(new rules());
        this.getCommand("config").setExecutor(new config());
        this.getCommand("collection").setExecutor(new collection());
        this.getCommand("collect").setExecutor(new collect());


        //Commmencement WaitingCycle
        WaitingCycle waiting = new WaitingCycle();
        waiting.runTaskTimer(Main.INSTANCE, 0, 20*5);

    }

    public void addToScoreboard(Player player) {
        scoreboard.addPlayer(player);
        scoreboard.updateScoreboard();
    }


    @Override
    public void onDisable() {
        System.out.println("DeathRoyal | Plugin Disabled");
    }
    //§tat du jeu? Start...
    public boolean isState(State state){
        return this.state == state;
    }

    public void setState(State state) {
        this.state = state;
    }
    //Liste des joueurs en jeux
    public List<Player> getIgplayer(){
        return igplayer;
    }
    //R§gles

    //Quel est le mode de jeu? Breaking...
    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }
    public boolean isGamemode(Gamemode gamemode){
        return this.gamemode == gamemode;
    }
    //R§gles
    public void setsamecollection(Boolean samecollection){
        this.sameCollection = samecollection;
    }
    public Boolean getsamecollection(){
        return sameCollection;
    }

    public boolean isActiveSenario(Senario senario){
        for(Senario sen: activeSenario){
            if(senario.equals(sen)){
                return true;
            }
        }
        return false;
    }


}