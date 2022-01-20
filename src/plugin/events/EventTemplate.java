package plugin.events;


import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;


import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import java.util.*;

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
            ArrayList<String> Blockeditems = new ArrayList<String>();
            Blockeditems.add("LOCKS_KEY");
            Blockeditems.add("CASHCRAFT_COIN");
            Blockeditems.add("CASHCRAFT_NOTE");
            if ( event.getWhoClicked().getInventory() != event.getClickedInventory() && event.getClickedInventory().getHolder().toString().contains("Player") && !event.getWhoClicked().getInventory().getHolder().toString().contains(event.getClickedInventory().getHolder().toString()))
                for (String var1: Blockeditems)
                    if(event.getCurrentItem().toString().contains(var1))
                    {
                        event.getWhoClicked().sendMessage("1 " + event.getWhoClicked().getInventory().getHolder());
                        event.getWhoClicked().sendMessage("2 " + event.getClickedInventory().getHolder());
                        event.getWhoClicked().sendMessage("Деньги и ключи забрать нельзя");
                        Player player = (Player) event.getView().getPlayer();
                        player.updateInventory();

                        event.setCancelled(true);
                    }
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
    public void blockMilkDrink(PlayerItemConsumeEvent event)
    {
        event.getPlayer().sendMessage("You are eating: " + event.getItem());
        PotionEffect effect = event.getPlayer().getPotionEffect(PotionEffectType.WEAKNESS);
        event.getPlayer().sendMessage("Your : " + effect.getType());
        event.setCancelled(true);
        if(effect != null)
            if(effect.getAmplifier() == 255)
                event.setCancelled(true);

    }

    @EventHandler
    public void ShockEffect(EntityDamageEvent ev) //Listens to EntityDamageEvent
    {
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
                if(effect != null)
                    if(effect.getAmplifier() != 255)
                        effect = null;
                if (effect == null) {
                    ev.setCancelled(true);

                    dmg.setHealth(2);

                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 255, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 360, 255, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 360, 6, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 180, 75, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 255, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 180, 255, false, false));
                }
            }
        }
    }

}