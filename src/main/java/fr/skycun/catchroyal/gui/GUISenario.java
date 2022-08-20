package fr.skycun.catchroyal.gui;

import fr.skycun.catchroyal.Main;
import fr.skycun.catchroyal.Senario;
import fr.skycun.catchroyal.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUISenario {


    public void GUISenario(){
    }

    public void openSenarioGUI(Player player){
        Inventory inv = Bukkit.createInventory(null, 54, "§c§lConfiguration: §lSénario");
        player.openInventory(inv);
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).name("§b").make());
        }
        for (int i = 45; i < 54; i++) {
            inv.setItem(i, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).name("§b").make());
        }

        int times = 0;
        for(Senario sen : Senario.values()){
            if(Main.INSTANCE.isActiveSenario(sen)){
                ItemStack item = new ItemBuilder(sen.getItemIcon()).name("§b§l" + sen.name() + ": §aactivé").lore("§a" + sen.getDesc() ).make();
                ItemMeta itemM = item.getItemMeta();
                itemM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                itemM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                item.setItemMeta(itemM);
                inv.setItem(9+times, item);
                times++;
            }else{
                inv.setItem(9 +times, new ItemBuilder(sen.getItemIcon()).name("§b§l" + sen.name() + ": §cdésactivé").lore("§a" + sen.getDesc() ).make());
                times++;
            }
        }
    }
}
