package fr.thybax.anotherworldplugin.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;

public class Items {

    private Items() {
        throw new IllegalStateException("Utility class");
    }
    protected static HashMap<String, ItemStack> allItems = new HashMap<>();

    private static String description = "§eDescription : ";

    public static void init(){
        ItemStack lavaBucket = new ItemStack(Material.LAVA_BUCKET, 1);
        ItemMeta lavaBucketMeta = lavaBucket.getItemMeta();

        lavaBucketMeta.setDisplayName("§7Seau de Lave infini");
        lavaBucketMeta.setLore(Arrays.asList("",description,"§f Seau de lave INFINI"));
        lavaBucketMeta.setCustomModelData(1);
        lavaBucketMeta.setLocalizedName("infinitelavabucket");
        lavaBucketMeta.addEnchant(Enchantment.ARROW_DAMAGE,1,false);
        lavaBucketMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        lavaBucket.setItemMeta(lavaBucketMeta);

        allItems.put("infinitelavabucket",lavaBucket);

        ItemStack waterBucket = new ItemStack(Material.WATER_BUCKET, 1);
        ItemMeta waterBucketMeta = waterBucket.getItemMeta();

        waterBucketMeta.setDisplayName("§7Seau d'Eau infini");
        waterBucketMeta.setLore(Arrays.asList("",description,"§f Seau d'Eau INFINI"));
        waterBucketMeta.setCustomModelData(2);
        waterBucketMeta.setLocalizedName("infinitewaterbucket");
        waterBucketMeta.addEnchant(Enchantment.ARROW_DAMAGE,1,false);
        waterBucketMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        waterBucket.setItemMeta(waterBucketMeta);

        allItems.put("infinitewaterbucket",waterBucket);




        ItemStack emptyBucket = new ItemStack(Material.BUCKET, 1);
        ItemMeta emptyBucketMeta = emptyBucket.getItemMeta();

        emptyBucketMeta.setDisplayName("§7Seau vide infini");
        emptyBucketMeta.setLore(Arrays.asList("",description,"§f Seau vide INFINI"));
        emptyBucketMeta.setCustomModelData(2);
        emptyBucketMeta.setLocalizedName("infiniteemptybucket");
        emptyBucketMeta.addEnchant(Enchantment.ARROW_DAMAGE,1,false);
        emptyBucketMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        emptyBucket.setItemMeta(emptyBucketMeta);

        allItems.put("infiniteemptybucket",emptyBucket);



        ItemStack cheque = new ItemStack(Material.PAPER, 1);
        ItemMeta chequeMeta = cheque.getItemMeta();

        chequeMeta.setDisplayName("§7Cheque");
        chequeMeta.setLore(Arrays.asList("",description,"§f Cheque d'argent"));
        chequeMeta.setCustomModelData(3);
        chequeMeta.setLocalizedName("cheque");
        chequeMeta.addEnchant(Enchantment.ARROW_DAMAGE,1,false);
        chequeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        cheque.setItemMeta(chequeMeta);

        allItems.put("cheque",cheque);
    }

    public static ItemStack getCustomItem(String name){
        if(allItems.containsKey(name)){
            return allItems.get(name);
        } else {
            return null;
        }
    }

    public static @Nullable String getLocalizedName(@Nullable ItemStack itemstack) {
        if(itemstack != null && itemstack.hasItemMeta()){
            ItemMeta result = itemstack.getItemMeta();
            if(result.hasLocalizedName()){
                return result.getLocalizedName();
            }
        }
        return null;
    }

    public static Boolean equalsLocalizedName(@Nullable ItemStack itemStack1,@Nullable ItemStack itemStack2) {
        if(getLocalizedName(itemStack1) == null || getLocalizedName(itemStack2) == null){
            return false;
        }
        return getLocalizedName(itemStack1).equals(getLocalizedName(itemStack2));
    }

}