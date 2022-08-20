package fr.skycun.catchroyal;

import org.bukkit.Material;

public enum Senario {
    KEEPINVENTORY(Material.CHEST, "Garde l'inventaire quand un joueur meurt"),
    PVP(Material.IRON_SWORD, "Les joueurs peuvent se tuer"),
    NETHERIBUS(Material.NETHER_BRICK, "Les joueurs spawn dans le nether"),
    NOFALL(Material.CHAINMAIL_BOOTS, "Ne prend pas de dégats de chutes"),
    SNOWBALL(Material.SNOWBALL, "Ajoute à la collection les objets d'un joueur tuée | non implémenté"),
    NODAMAGE(Material.APPLE, "Les joueurs ne prenent AUCUN dégats"),
    BETTERTOOLS(Material.DIAMOND_PICKAXE, "Enchante automatiquement les outils"),
    RANDOMEFFECT(Material.POTION, "Chaque minutes, un nouveau effet"),
    RANDOMRESPAWN(Material.RED_BED, "Quand un joueur respawn, il respawn à un endroit aléatoire"),
    HOLLOWPLAYER(Material.SPECTRAL_ARROW, "Tout les joueurs on un effet de glowing"),
    BOUNTY(Material.TARGET, "Une prime est placé sur un joueur, tuer-le et gagner 5 items random"),
    DOUBLEOR(Material.DIAMOND_ORE, "Double les minerais cassé"),
    TELEPORTBOW(Material.BOW, "Teleporte le joueur là ou la fleche tombe"),
    ELYTRAFOREVER(Material.ELYTRA, "Spawn et respawn avec des elytras"),
    EXPLOSIONDAMAGE(Material.TNT, "Faire des dégats fera une explosion"),
    FASTSMELTING(Material.FURNACE, "La cuisson va plus vite"),
    RANDOMMOBKILL(Material.ROTTEN_FLESH, "Drop un item/block random quand un mob est tuée");
    public Material getItemIcon() {
        return itemIcon;
    }

    public String getDesc() {
        return desc;
    }

    private Material itemIcon;
    private String desc;

    Senario(Material itemIcon, String desc){
        this.itemIcon = itemIcon;
        this.desc = desc;
    }


}