package com.afforess.bukkit.minecartmaniaautocart;

import org.bukkit.ChatColor;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

import com.afforess.bukkit.minecartmaniacore.ChatUtils;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaFlatFile;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaMinecart;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaWorld;

public class AutocartPlayerListener extends PlayerListener{
    public void onPlayerCommand(PlayerChatEvent event) {
    	Player player = event.getPlayer();
    	if (event.getMessage().indexOf("/throttle") > -1) {
    		if (player.getVehicle() != null && player.getVehicle() instanceof Minecart) {
    			try {
		    		String num = MinecartManiaFlatFile.getNumber(event.getMessage());
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
    	/*else if (event.getMessage().toLowerCase().indexOf("/getdata") > -1) {
    		ChatUtils.sendMultilineMessage(player, "Block Data: " + MinecartManiaWorld.getBlockData(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()), ChatColor.GREEN.toString());
    		event.setCancelled(true);
    	}
    	else if (event.getMessage().toLowerCase().indexOf("/getpos") > -1) {
    		ChatUtils.sendMultilineMessage(player, "Pos X: " + player.getLocation().getBlockX() + " Pos Y: " + player.getLocation().getBlockY() + " Pos Z: " + player.getLocation().getBlockZ(), ChatColor.GREEN.toString());
    		event.setCancelled(true);
    	}
    	else if (event.getMessage().toLowerCase().indexOf("/getrotation") > -1) {
    		ChatUtils.sendMultilineMessage(player, "Yaw: " + player.getLocation().getYaw(), ChatColor.GREEN.toString());
    		event.setCancelled(true);
    	}
    	/*else if (event.getMessage().toLowerCase().indexOf("/direction") > -1) {
    		ChatUtils.sendMultilineMessage(player, "Direction: " + DirectionUtils.getDirectionFromRotation(player.getLocation().getYaw() -90F %360).toString() + " Minecart Direction: " + DirectionUtils.getDirectionFromMinecartRotation(player.getLocation().getYaw() -90F %360).toString(), ChatColor.GREEN.toString());
    		event.setCancelled(true);
    	}*/
    }
}
