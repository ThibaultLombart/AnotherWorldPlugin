package fr.thybax.anotherworldplugin.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ShopManager {

    private ShopManager() {
        throw new IllegalStateException("Utility class");
    }

    private static Inventory shopMenu;

    public static void init(){
        createShopMenu();
    }

    private static String accederShop = "§aPour acceder au Shop :";

    public static void createShopMenu(){
        //Création inventaire
        Inventory inv = Bukkit.createInventory(null, 27, "§aShop");

        //Création Gray Stained Glass Pane (pour les contours etc)
        ItemStack grey = new ItemStack(Material.GRAY_STAINED_GLASS_PANE,1);
        ItemMeta customGrey = grey.getItemMeta();
        customGrey.setDisplayName(" ");
        grey.setItemMeta(customGrey);

        //Creation bouton "Custom Item"
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD,1);
        ItemMeta customSword = sword.getItemMeta();
        customSword.setDisplayName("§eItems Customs");
        customSword.setLore(Arrays.asList("",accederShop,"§fItems Customs"));
        sword.setItemMeta(customSword);

        //Creation bouton "Blocs"
        ItemStack bloc = new ItemStack(Material.GRASS_BLOCK,1);
        ItemMeta customBloc = bloc.getItemMeta();
        customBloc.setDisplayName("§eBlocs");
        customBloc.setLore(Arrays.asList("",accederShop,"§fBlocs"));
        bloc.setItemMeta(customBloc);

        //Creation bouton "Farm"
        ItemStack farm = new ItemStack(Material.CACTUS,1);
        ItemMeta customFarm = farm.getItemMeta();
        customFarm.setDisplayName("§eFarm");
        customFarm.setLore(Arrays.asList("",accederShop,"§fFarm"));
        farm.setItemMeta(customFarm);

        // Ici on va faire plusieurs boucles permettant de mettre les grey glass pane
        for(int i = 0; i < 11; i++){
            inv.setItem(i,grey);
        }

        inv.setItem(11,farm);
        inv.setItem(12, grey);
        inv.setItem(13, bloc);
        inv.setItem(14,grey);
        inv.setItem(15,sword);
        for(int i = 16; i < 27; i++){
            inv.setItem(i,grey);
        }

        shopMenu = inv;
    }

    public static Inventory getShopMenu(){
        return shopMenu;
    }
}
