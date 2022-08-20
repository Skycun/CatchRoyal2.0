package fr.skycun.catchroyal.listeners;

import fr.skycun.catchroyal.Gamemode;
import fr.skycun.catchroyal.Main;
import fr.skycun.catchroyal.Senario;
import fr.skycun.catchroyal.gui.GUISenario;
import fr.skycun.catchroyal.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;

public class GUIitemListeners implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        int slot = event.getSlot();
        if(current == null) return;

        //
        //Collection
        //

        if(event.getView().getTitle().contains("§e§lCollection:")) {
            event.setCancelled(true);



        //
        //Config
        //

        }else if(event.getView().getTitle().equals("§c§lConfiguration")) {
            event.setCancelled(true);
            switch(current.getType()){
                //Start button
                case EMERALD_BLOCK:
                    player.performCommand("start");
                    break;

                //Inventaire de controle du temps de la partie
                case CLOCK:
                    player.closeInventory();
                    Inventory invConfig = Bukkit.createInventory(null, 27, "§c§lConfiguration: §lTimer");
                    player.openInventory(invConfig);
                    for (int i = 0; i < 9; i++) {
                        invConfig.setItem(i, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).durability(7).name("§b").make());
                    }
                    for (int i = 18; i < 27; i++) {
                        invConfig.setItem(i, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).durability(7).name("§b").make());
                    }
                    invConfig.setItem(13, new ItemBuilder(Material.CLOCK).name("§f§lTimer: §b" + new SimpleDateFormat("mm:ss").format(Main.INSTANCE.gameDuration * 1000)).make());
                    invConfig.setItem(15, new ItemBuilder(Material.CYAN_TERRACOTTA).name("§a§l+1 §f§lMinute").amount(1).make());
                    invConfig.setItem(16, new ItemBuilder(Material.LIGHT_BLUE_TERRACOTTA).name("§a§l+5 §f§lMinutes").amount(5).make());
                    invConfig.setItem(17, new ItemBuilder(Material.BLUE_TERRACOTTA).name("§a§l+10 §f§lMinutes").amount(10).make());
                    invConfig.setItem(11, new ItemBuilder(Material.MAGENTA_TERRACOTTA).name("§c§l-1 §f§lMinute").amount(1).make());
                    invConfig.setItem(10, new ItemBuilder(Material.PINK_TERRACOTTA).name("§c§l-5 §f§lMinutes").amount(5).make());
                    invConfig.setItem(9, new ItemBuilder(Material.RED_TERRACOTTA).name("§c§l-10 §f§lMinutes").amount(10).make());
                    break;

                //Changement de samecommection
                case BOOK:
                    if(Main.INSTANCE.sameCollection) {
                        Main.INSTANCE.setsamecollection(false);
                        player.sendMessage(" §c▌ §eLa partie est maintenant en: §f§lCollection §b§lseule");
                        player.closeInventory();
                        player.performCommand("config");
                        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1.0f, 1.2f);
                    }else {
                        Main.INSTANCE.setsamecollection(true);
                        player.sendMessage(" §c▌ §eLa partie est maintenant en: §b§lMême §f§lcollection");
                        player.closeInventory();
                        player.performCommand("config");
                        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1.0f, 1.2f);
                    }
                    break;

                //Inventaire de d'objectif
                case ITEM_FRAME:
                    player.closeInventory();
                    Inventory invObj = Bukkit.createInventory(null, 27, "§c§lConfiguration: §lObjectif");
                    player.openInventory(invObj);
                    for (int i = 0; i < 9; i++) {
                        invObj.setItem(i, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).durability(7).name("§b").make());
                    }
                    for (int i = 18; i < 27; i++) {
                        invObj.setItem(i, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).durability(7).name("§b").make());
                    }
                    invObj.setItem(10, new ItemBuilder(Material.IRON_PICKAXE).name("§a§lCassage de blocks").amount(1).make());
                    invObj.setItem(11, new ItemBuilder(Material.CRAFTING_TABLE).name("§a§lCraft").amount(1).make());
                    invObj.setItem(13, new ItemBuilder(Material.CHEST).name("§a§lRécupération items/blocks").amount(1).make());
                    invObj.setItem(14, new ItemBuilder(Material.EXPERIENCE_BOTTLE).name("§a§lAchivement").amount(1).make());

                    break;
                //Sénario
                case WRITABLE_BOOK:
                    player.closeInventory();
                    new GUISenario().openSenarioGUI(player);
                    break;
            }

            //
            // Config Temp
            //

        }else if(event.getView().getTitle().contains("§c§lConfiguration: §lTimer")){
            switch (current.getType()) {
                case CYAN_TERRACOTTA -> Main.INSTANCE.gameDuration = Main.INSTANCE.gameDuration + 1 * 60;
                case LIGHT_BLUE_TERRACOTTA -> Main.INSTANCE.gameDuration = Main.INSTANCE.gameDuration + 5 * 60;
                case BLUE_TERRACOTTA -> Main.INSTANCE.gameDuration = Main.INSTANCE.gameDuration + 10 * 60;
                case MAGENTA_TERRACOTTA -> Main.INSTANCE.gameDuration = Main.INSTANCE.gameDuration - 1 * 60;
                case PINK_TERRACOTTA -> Main.INSTANCE.gameDuration = Main.INSTANCE.gameDuration - 5 * 60;
                case RED_TERRACOTTA -> Main.INSTANCE.gameDuration = Main.INSTANCE.gameDuration - 10 * 60;
            }
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1.0f, 1.2f);
            player.closeInventory();
            Inventory invConfig = Bukkit.createInventory(null, 27, "§c§lConfiguration: §lTimer");
            player.openInventory(invConfig);
            for (int i = 0; i < 9; i++) {
                invConfig.setItem(i, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).durability(7).name("§b").make());
            }
            for (int i = 18; i < 27; i++) {
                invConfig.setItem(i, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).durability(7).name("§b").make());
            }
            invConfig.setItem(13, new ItemBuilder(Material.CLOCK).name("§f§lTimer: §b" + new SimpleDateFormat("mm:ss").format(Main.INSTANCE.gameDuration * 1000)).make());
            invConfig.setItem(15, new ItemBuilder(Material.CYAN_TERRACOTTA).name("§a§l+1 §f§lMinute").amount(1).make());
            invConfig.setItem(16, new ItemBuilder(Material.LIGHT_BLUE_TERRACOTTA).name("§a§l+5 §f§lMinutes").amount(5).make());
            invConfig.setItem(17, new ItemBuilder(Material.BLUE_TERRACOTTA).name("§a§l+10 §f§lMinutes").amount(10).make());
            invConfig.setItem(11, new ItemBuilder(Material.MAGENTA_TERRACOTTA).name("§c§l-1 §f§lMinute").amount(1).make());
            invConfig.setItem(10, new ItemBuilder(Material.PINK_TERRACOTTA).name("§c§l-5 §f§lMinutes").amount(5).make());
            invConfig.setItem(9, new ItemBuilder(Material.RED_TERRACOTTA).name("§c§l-10 §f§lMinutes").amount(10).make());

        }else if(event.getView().getTitle().contains("§c§lConfiguration: §lObjectif")){
            switch (current.getType()) {
                case CRAFTING_TABLE -> {
                    Main.INSTANCE.setGamemode(Gamemode.CRAFTING);
                    Bukkit.broadcastMessage(" §6▌ §eLe mode de jeu est maintenant: §b§lCrafting");
                }
                case IRON_PICKAXE -> {
                    Main.INSTANCE.setGamemode(Gamemode.BREAKINGBLOCKS);
                    Bukkit.broadcastMessage(" §6▌ §eLe mode de jeu est maintenant: §b§lCassage de block");
                }
                case CHEST -> {
                    Main.INSTANCE.setGamemode(Gamemode.ALLITEMS);
                    Bukkit.broadcastMessage(" §6▌ §eLe mode de jeu est maintenant: §b§lRécupération items et blocs");
                }
                case EXPERIENCE_BOTTLE -> {
                    Main.INSTANCE.setGamemode(Gamemode.ACHIEVEMENT);
                    Bukkit.broadcastMessage(" §6▌ §eLe mode de jeu est maintenant: §b§lAchivement");
                }
            }
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1.0f, 1.2f);

        //Config Sénario
        }else if(event.getView().getTitle().equals("§c§lConfiguration: §lSénario")){
            for(Senario sen : Senario.values()){
                if(sen.getItemIcon().equals(event.getCurrentItem().getType())){
                    if(Main.INSTANCE.isActiveSenario(sen)){
                        Main.INSTANCE.activeSenario.remove(sen);
                        Bukkit.broadcastMessage(" §6▌ §eLe scénario: §b§l" + sen.name() + " §eà été §c§ldéactivé");
                        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1.0f, 1.2f);
                        player.closeInventory();
                        new GUISenario().openSenarioGUI(player);
                    }else{
                        Main.INSTANCE.activeSenario.add(sen);
                        Bukkit.broadcastMessage(" §6▌ §eLe scénario: §b§l" + sen.name() + " §eà été §a§lactivé");
                        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1.0f, 1.2f);
                        player.closeInventory();
                        new GUISenario().openSenarioGUI(player);
                    }
                }
            }
        }
    }
}
