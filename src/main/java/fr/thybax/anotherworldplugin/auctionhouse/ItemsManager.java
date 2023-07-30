package fr.thybax.anotherworldplugin.auctionhouse;

import net.minecraft.world.item.Item;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ItemsManager {

    private ArrayList<ItemsAuction> listeItem;

    public ItemsManager(){
        listeItem = new ArrayList<>();
    }

    public ArrayList<ItemsAuction> getListeItem(){
        return listeItem;
    }

    public void addItem(ItemStack item, int price, UUID owner){
        Date dateCreation = new Date(System.currentTimeMillis());
        Date dateExpiration = new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000));
        ItemsAuction itemA = new ItemsAuction(item, price,dateCreation,dateExpiration, owner);
        listeItem.add(0,itemA);
    }

    public void remove(ItemsAuction item){
        listeItem.remove(item);
    }


    class ItemsAuction{
        private ItemStack item;
        private int price;
        private Date dateCreation;
        private Date dateExpiration;
        private UUID owner;

        private ItemsAuction(ItemStack item, int price, Date dateCreation,Date dateExpiration, UUID owner){
            this.item = item;
            this.price = price;
            this.dateCreation = dateCreation;
            this.dateExpiration = dateExpiration;
            this.owner = owner;
        }

        public boolean aExpire() {
            return new Date().after(dateExpiration);
        }

        public String tempsRestantFormate() {
            long tempsRestantMillis = dateExpiration.getTime() - System.currentTimeMillis();
            if (tempsRestantMillis <= 0) {
                return "Expiré";
            }

            long secondes = tempsRestantMillis / 1000;
            long minutes = secondes / 60;
            long heures = minutes / 60;
            long jours = heures / 24;

            heures %= 24;
            minutes %= 60;
            secondes %= 60;

            return jours + "j " + heures + "h " + minutes + "m " + secondes + " s";
        }

        public ItemStack getItem() {
            return item;
        }

        public int getPrice() {
            return price;
        }

        public Date getDateCreation() {
            return dateCreation;
        }

        public Date getDateExpiration() {
            return dateExpiration;
        }

        public UUID getOwner() {
            return owner;
        }
    }

}
