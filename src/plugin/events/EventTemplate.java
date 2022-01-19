package plugin.events;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;


import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeWrapper;

public class EventTemplate implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage("Welcome, " + event.getPlayer().getName() + "!");
    }

    //Inventory openInv = null;

    @EventHandler
    public void onPlayerRClick(PlayerInteractEntityEvent event) throws InterruptedException {
        if(event != null)
        if(event.getRightClicked() instanceof Player)
        {
            PotionEffect effect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 0, 1);
            effect = ((Player) event.getRightClicked()).getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            if(effect != null)
                if(effect.getAmplifier() == 5) {
                    if(event.getPlayer().isSneaking() == false) {
                        ((Player) event.getRightClicked()).sendMessage("Inventory Checked by " + event.getPlayer().getName());
                        event.getPlayer().openInventory(((Player) event.getRightClicked()).getInventory());

                        //event.getPlayer().in
                        //wait(1000);
                        //event.getPlayer().closeInventory();
                    }
                    else
                    {

                        event.getPlayer().addPassenger(event.getRightClicked());
                    }

                }
        }

    }


    /*
    * Не дает забирать ключи из инвентаря
    *
    *
    * */
    @EventHandler
    public void onInventoryView(InventoryClickEvent event)
    {
        if(event.getCurrentItem() != null) {
            Inventory inv = event.getInventory();

            event.getWhoClicked().sendMessage("You clicked1 " + event.getCurrentItem());

            event.getWhoClicked().sendMessage("You clicked " + event.getCurrentItem().getType());
            event.getWhoClicked().sendMessage("getHolder " + event.getClickedInventory().getHolder().toString());
            //if (event.getWhoClicked().getInventory() != inv && !inv.getItem(17).equals(1)) return;
            //ItemStack bricks = new ItemStack(Material.BRICK);
            //bricks.setAmount(event.getCurrentItem().getAmount());

            //if ((event.getWhoClicked().getInventory().getHolder() != event.getClickedInventory().getHolder() && event.getClickedInventory().getHolder().toString().contains("CraftPlayer"))  && event.getCurrentItem().toString().contains("LOCKS_KEY")) {
            if ( event.getWhoClicked().getInventory() != event.getClickedInventory() && event.getClickedInventory().getHolder().toString().contains("Player") && event.getCurrentItem().toString().contains("LOCKS_KEY")) {

                //event.setCursor(null);
                Player player = (Player) event.getView().getPlayer();
                event.getWhoClicked().getInventory().remove(event.getCurrentItem());
                player.updateInventory();

                event.setCancelled(true);

                //event.getView().getPlayer();
                //event.getWhoClicked().openInventory(event.getWhoClicked().getInventory());

            }
            //
        }
    }


    @EventHandler
    public void onPlayerSneaking(PlayerToggleSneakEvent event)
    {
        if(event.getPlayer().getPassengers().isEmpty() == false)
        {
            event.getPlayer().removePassenger(event.getPlayer().getPassengers().get(0));
        }
    }


    @EventHandler
    public void ShockEffect(EntityDamageEvent ev) //Listens to EntityDamageEvent
    {
        //ev.
        if(ev.getEntity() instanceof Player) {
            Damageable dmg;
            dmg = null;
            Player player = null;
            if (ev.getEntity() instanceof Player) {
                dmg = (Damageable) ev.getEntity();
                player = (Player) ev.getEntity();
            }
            if (((dmg.getHealth() - ev.getFinalDamage()) <= 0) //Checks if the entity will die and if entity is player
                    && ev.getEntity() instanceof Player) {
                player = (Player) ev.getEntity();
                for (PotionEffect col : player.getActivePotionEffects()) {
                    player.sendMessage("" + col.getType());
                    player.sendMessage("" + col);
                }

                PotionEffect effect = player.getPotionEffect(PotionEffectType.WEAKNESS);
                if (effect == null) {
                    ev.setCancelled(true);
                    //player.openInventory(player.getInventory());

                    //Set Player afterShock Health
                    dmg.setHealth(3);

                    //List of Effects
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 255));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 360, 255));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 180, 75));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 255));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 180, 255));

                    //player.addPotionEffect(new PotionEffect(new PotionEffectTypeWrapper(34));
                }//Teleport, set health, whatever.
            }
        }
    }

}