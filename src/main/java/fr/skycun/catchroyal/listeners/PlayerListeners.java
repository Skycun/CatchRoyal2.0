package fr.skycun.catchroyal.listeners;

import java.text.SimpleDateFormat;

import fr.skycun.catchroyal.*;
import fr.skycun.catchroyal.tools.ResetAll;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.skycun.catchroyal.tools.ItemBuilder;

public class PlayerListeners implements Listener {
    private Main main;
    public PlayerListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(main.isState(State.WAITING) || main.isState(State.STARTING)) {
            // Le joueur rejoint pendant l'attente
            ResetAll.player(player);
            main.getIgplayer().add(player);
            Main.INSTANCE.addToScoreboard(player);
            if(!player.isOp()) {
                player.setGameMode(GameMode.ADVENTURE);
            }else {
                player.setGameMode(GameMode.CREATIVE);
            }
            player.teleport(new Location(Bukkit.getWorld("world"), 0, 102, 0));
            Location loc = new Location(Bukkit.getWorld("world"), 0, 100, 0);
            loc.getBlock().setType(Material.WHITE_STAINED_GLASS);
            event.setJoinMessage(" §a▌ §b" + player.getDisplayName() + " §ea rejoint la partie");
            new PlayerManager(player);
            PlayerManager.playerManager.get(player).setFound(0);
            ItemStack rules = new ItemBuilder(Material.BOOK).name("§d§lRègles §8✦ Clique droit").make();
            player.getInventory().setItem(8, rules);
            player.sendMessage(" §7▌        ");
            player.sendMessage(" §7▌          §e§lBIENVENUE EN CATCHROYAL!");
            player.sendMessage(" §7▌");
            player.sendMessage(" §7▌ §eLe but est de collecté différente choses avant");
            player.sendMessage(" §7▌ §ela fin du temps");
            if(main.isGamemode(Gamemode.BREAKINGBLOCKS)){
                player.sendMessage(" §7▌ §b§lMODE DE JEU: Cassage de block");
                player.sendMessage(" §7▌   §e- Vous devez récupéré le plus de block différent avant");
                player.sendMessage(" §7▌     §eles autres");
            }else if(main.isGamemode(Gamemode.CRAFTING)){
                player.sendMessage(" §7▌ §b§lMODE DE JEU: Craft");
                player.sendMessage(" §7▌   §e- Vous devez craft le plus de trucs différent avant");
                player.sendMessage(" §7▌     §eles autres");
            }else if(main.isGamemode(Gamemode.ACHIEVEMENT)){
                player.sendMessage(" §7▌ §b§lMODE DE JEU: Achievement");
                player.sendMessage(" §7▌   §e- Vous devez avoir le plus d'achievement différent avant");
                player.sendMessage(" §7▌     §eles autres");

            }
            player.sendMessage(" §7▌ §d§lCOMMANDES");
            player.sendMessage(" §7▌   §e- /collection: pour voir les choses récolté par");
            player.sendMessage(" §7▌   §e  tout le monde");
            player.sendMessage(" §7▌   §e- /regles ou rules: pour voir les règles de la partie");
            player.sendMessage(" §7▌ §a§lSCENARIO");
            if(Main.INSTANCE.activeSenario.isEmpty()){
                player.sendMessage(" §7▌   §e-Aucun");
            }else{
                player.sendMessage(" §7▌   §e-" + Main.INSTANCE.activeSenario.toString());
            }
            if(player.isOp()) {
                player.sendMessage(" §7▌ §C§lADMIN");
                player.sendMessage(" §7▌   §e- /start: pour commencer la partie");
                player.sendMessage(" §7▌   §e- /config: pour configurer la partie");
                player.sendMessage(" §7▌   Cliqué sur le comparateur pour configuré la partie");
                player.sendMessage(" §7");
                ItemStack config = new ItemBuilder(Material.LEGACY_REDSTONE_COMPARATOR).name("§c§lConfigurations §8✦ Clique droit").make();
                player.getInventory().setItem(4, config);


            }


        }else {
            //Le joueur rejoint en pleine game
            if(main.getIgplayer().contains(player)) {
                event.setJoinMessage(" §a▌ §b" + player.getDisplayName() + " §eest revenue");
                //il était en train de jouer

            }else {
                //il ne jouais pas
                event.setJoinMessage(" §7▌ §b" + player.getDisplayName() + " §eregarde la partie");
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(new Location(Bukkit.getWorld("world"), 0, 100, 0));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.2f);
                player.sendMessage(" §7▌          §e§lBIENVENUE EN CATCHROYAL!");
                player.sendMessage(" §7▌");
                player.sendMessage(" §7▌ §eLa partie à déjà commencé, vous êtes donc en spectaeur");
                player.sendMessage(" §7▌ §eEn tant que spectateur vous ne pouvez pas intéragir avec le monde");
                player.sendMessage(" §7▌ §eévité de tricher en denant des informations au joueurs");
                player.sendMessage(" §7▌");
            }

        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage(" §c| §b" + player.getDisplayName() + " §ea quitté la partie");
        if(main.isState(State.WAITING) || main.isState(State.STARTING)) main.getIgplayer().remove(player);
    }



    //Clique droit sur un item
    @EventHandler
    public void onRightclick(PlayerInteractEvent event) {
        if(event.getItem() == null) {
            //Si il y a quelque chose dans la main
        }else {
            //Sinon
            ItemStack it = event.getItem();
            Player player = event.getPlayer();
            ItemMeta im = it.getItemMeta();
            //Les règles
            if(im.getDisplayName().contentEquals("§d§lRègles §8✦ Clique droit")) {
                player.performCommand("rules");
                //La config
            }else if(im.getDisplayName().contentEquals("§c§lConfigurations §8✦ Clique droit")) {
                player.performCommand("config");
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        event.setCancelled(true);
        if(event.getPlayer().isOp()){
            Bukkit.broadcastMessage("§a§l" + event.getPlayer().getDisplayName() + " §7▶ " + event.getMessage());
        }else{
            Bukkit.broadcastMessage("§f§l" + event.getPlayer().getDisplayName() + " §7▶ " + event.getMessage());
        }
    }
}