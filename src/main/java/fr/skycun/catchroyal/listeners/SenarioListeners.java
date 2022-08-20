package fr.skycun.catchroyal.listeners;

import fr.skycun.catchroyal.Main;
import fr.skycun.catchroyal.PlayerManager;
import fr.skycun.catchroyal.Senario;
import fr.skycun.catchroyal.State;
import fr.skycun.catchroyal.tools.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class SenarioListeners implements Listener {

    //Senario KEEPINVENTORY && NETHERIBUS && BOUNTY && ELYTRAFOREVER
    @EventHandler
    private void onDeath(PlayerDeathEvent event){
        if(Main.INSTANCE.isActiveSenario(Senario.KEEPINVENTORY)){
            event.getEntity().getWorld().setGameRule(GameRule.KEEP_INVENTORY, Boolean.TRUE);
        }
        if(Main.INSTANCE.isActiveSenario(Senario.NETHERIBUS)){
            World nether = Bukkit.getWorld("world_nether");
            event.getEntity().teleport(nether.getSpawnLocation());
        }
        if(Main.INSTANCE.isActiveSenario(Senario.BOUNTY)){
            Player killer = event.getEntity().getKiller();
            Player victim = event.getEntity().getPlayer();
            if(PlayerManager.playerManager.get(killer).getTargetPlayer() == victim){
                killer.sendMessage(" §9▌ §eScénario Bounty: §aVous avez tué votre cible, voici votre prime");
                killer.sendMessage(" §9▌ §eScénario Bounty: §aVous avez plus de cible");
                killer.playSound(killer.getLocation(), Sound.BLOCK_BELL_USE, 1f,1f);
                for (int i = 1; i < 6; i++) {
                    ItemStack random = new ItemStack(Material.values()[new Random().nextInt(Material.values().length)]);
                    int r = new Random().nextInt(100);
                    if(r == 0) random = new ItemBuilder(Material.PAPER).name("§a§lfdp").lore("§aSigné: " + victim.getDisplayName()).make();
                    victim.getWorld().dropItem(victim.getLocation(), random);
                    PlayerManager.playerManager.get(killer).setTargetPlayer(killer);
                }
            }
        }
    }
    
    //Scénario Nethribus && randomrespawn
    @EventHandler
    private void onRespawn(PlayerRespawnEvent event){
        if(Main.INSTANCE.isActiveSenario(Senario.NETHERIBUS)){
            World nether = Bukkit.getWorld("world_nether");
            event.getPlayer().teleport(nether.getSpawnLocation());
        }
        if (Main.INSTANCE.isActiveSenario(Senario.RANDOMRESPAWN)) {
            int max = 500;
            int min = -500;
            int x = new Random().nextInt(max - min) + min;
            int y = new Random().nextInt(max - min) + min;
            event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), x, event.getPlayer().getWorld().getHighestBlockYAt(x, y), y));
            event.getPlayer().sendTitle("§aTéléporté en","§b" + x + " " + y,1,5,1);
        }
        if(Main.INSTANCE.isActiveSenario(Senario.ELYTRAFOREVER)){
            event.getPlayer().getInventory().setChestplate(new ItemStack(Material.ELYTRA));
        }
    }

    //SENARIO RANDOMMOBKILL
    @EventHandler
    private void onMobKill(EntityDeathEvent event){
        if(!(event.getEntity() instanceof Player)){
            if(Main.INSTANCE.isActiveSenario(Senario.RANDOMMOBKILL)){
                ItemStack random = new ItemStack(Material.values()[new Random().nextInt(Material.values().length)]);
                if(random.getItemMeta().equals(Material.AIR)) random = new ItemBuilder(Material.PAPER).name("§a§lfdp").lore("§aSigné: Le " + event.getEntity().getName()).make();
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), random);
            }
        }
    }

    //Senario NOFALL & NODAMAGE
    @EventHandler
    private void onFallDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            if(Main.INSTANCE.isActiveSenario(Senario.NOFALL)) {
                if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) event.setCancelled(true);
            }else if (Main.INSTANCE.isActiveSenario(Senario.NODAMAGE)){
                event.setCancelled(true);
        }
    }

    }
    //PVP && EXPOSIONDAMAGE
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            if(!Main.INSTANCE.isActiveSenario(Senario.PVP) || !Main.INSTANCE.isState(State.PLAYING)){
                event.setCancelled(true);
            }
        }
        if(event.getDamager() instanceof Player || event.getDamager() instanceof Arrow || event.getDamager() instanceof Snowball || event.getDamager() instanceof Egg){
            if(Main.INSTANCE.isActiveSenario(Senario.EXPLOSIONDAMAGE)) event.getDamager().getWorld().createExplosion(event.getEntity().getLocation(), 5);
        }
    }

    @EventHandler
    public void onCraftTools(CraftItemEvent event){
        Player player = (Player) event.getWhoClicked();
        if(!Main.INSTANCE.isActiveSenario(Senario.BETTERTOOLS)) return;
            if(event.getCurrentItem().toString().contains("PICKAXE") ||
                    event.getCurrentItem().toString().contains("AXE") ||
                    event.getCurrentItem().toString().contains("SHOVEL") ||
                    event.getCurrentItem().toString().contains("HOE")){
                ItemStack item = new ItemStack(event.getCurrentItem());
                ItemMeta itemM = item.getItemMeta();
                itemM.addEnchant(Enchantment.DURABILITY, 3,true);
                itemM.addEnchant(Enchantment.DIG_SPEED, 3,true);
                item.setItemMeta(itemM);
                event.setCurrentItem(item);
            }
    }

    //Double ORE
    @EventHandler
    private void onBreak(BlockBreakEvent event){
        if (!Main.INSTANCE.isActiveSenario(Senario.DOUBLEOR)) return;
        Material Mblock = event.getBlock().getType();
        Block block = event.getBlock();
        if(Mblock.equals(Material.COAL_ORE) || Mblock.equals(Material.DEEPSLATE_COAL_ORE)) block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.COAL));
        if(Mblock.equals(Material.IRON_ORE) || Mblock.equals(Material.DEEPSLATE_IRON_ORE)) block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.RAW_IRON_BLOCK));
        if(Mblock.equals(Material.DIAMOND_ORE) || Mblock.equals(Material.DEEPSLATE_DIAMOND_ORE)) block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.DIAMOND));
        if(Mblock.equals(Material.GOLD_ORE) || Mblock.equals(Material.DEEPSLATE_GOLD_ORE)) block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.RAW_GOLD));
        if(Mblock.equals(Material.COPPER_ORE) || Mblock.equals(Material.DEEPSLATE_COPPER_ORE)) block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.RAW_COPPER));
        if(Mblock.equals(Material.EMERALD_ORE) || Mblock.equals(Material.DEEPSLATE_EMERALD_ORE)) block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.EMERALD));

    }

    //TeleportBow
    @EventHandler
    private void onShoot(ProjectileHitEvent event){
        if(!Main.INSTANCE.isActiveSenario(Senario.TELEPORTBOW)) return;
        if(!(event.getEntity() instanceof Arrow)) return;
        if(!(event.getEntity().getShooter() instanceof Player)) return;
        Player player = (Player) event.getEntity().getShooter();
        Location l = event.getEntity().getLocation();
        l.setYaw(player.getLocation().getYaw());
        l.setPitch(player.getLocation().getPitch());
        player.teleport(l);
        player.playSound(l, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
        player.spawnParticle(Particle.DRAGON_BREATH, event.getEntity().getLocation(), 15);
    }
    private void startUpdate(final Furnace tile, final int increase) {
        new BukkitRunnable() {
            public void run() {
                if (tile.getCookTime() > 0 || tile.getBurnTime() > 0) {
                    tile.setCookTime((short)(tile.getCookTime() + increase));
                    tile.update();
                }
                else {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.INSTANCE, 1L, 1L);
    }

    @EventHandler
    public void onFurnaceBurn(final FurnaceBurnEvent event) {
        if(!Main.INSTANCE.isActiveSenario(Senario.FASTSMELTING)) return;
        Random RND = new Random();
        this.startUpdate((Furnace)event.getBlock().getState(), RND.nextBoolean() ? 1 : 2);
    }
}

