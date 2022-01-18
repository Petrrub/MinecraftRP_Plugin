package plugin.events;

import org.bukkit.Color;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;


import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeWrapper;

public class EventTemplate implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage("Welcome, " + event.getPlayer().getName() + "!");
    }

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

    @EventHandler
    public void onPlayerSneaking(PlayerToggleSneakEvent event)
    {
        if(event.getPlayer().getPassengers() != null)
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