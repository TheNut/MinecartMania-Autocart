package com.afforess.bukkit.minecartmaniaautocart;

import org.bukkit.ChatColor;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

import com.afforess.bukkit.minecartmaniacore.ChatUtils;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaMinecart;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaWorld;
import com.afforess.bukkit.minecartmaniacore.StringUtils;

public class AutocartPlayerListener extends PlayerListener{
    public void onPlayerCommand(PlayerChatEvent event) {
    	Player player = event.getPlayer();
    	if (event.isCancelled()) {
    		return;
    	}
    	if (event.getMessage().indexOf("/throttle") > -1) {
    		if (player.getVehicle() != null && player.getVehicle() instanceof Minecart) {
    			try {
		    		String num = StringUtils.getNumber(event.getMessage());
		    		double throttle = Double.valueOf(num);
		    		if (throttle <= 150D && throttle >= 0.0D) {
			    		MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart)player.getVehicle());
			    		minecart.setDataValue("throttle", new Double(throttle));
			    		event.setCancelled(true);
			    		if (throttle <= 100D) 
			    			ChatUtils.sendMultilineMessage(player, "Throttle set!", ChatColor.GREEN.toString());
			    		else
			    			ChatUtils.sendMultilineMessage(player, "Overdrive Enabled!", ChatColor.GREEN.toString());
		    		}
		    		else {
		    			ChatUtils.sendMultilineMessage(player, "Invalid command. Correct usage is \"/throttle [number]\"", ChatColor.RED.toString());
		    			event.setCancelled(true);
		    		}
    			}
    			catch (Exception e) {
    				ChatUtils.sendMultilineMessage(player, "Invalid command. Correct usage is \"/throttle [number]\"", ChatColor.RED.toString());
    				event.setCancelled(true);
    			}
    		}
    	}
    }
}
