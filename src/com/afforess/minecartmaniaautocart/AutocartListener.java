package com.afforess.minecartmaniaautocart;

import java.util.Calendar;

import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import com.afforess.minecartmaniacore.Item;
import com.afforess.minecartmaniacore.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.MinecartManiaWorld;
import com.afforess.minecartmaniacore.config.ControlBlock;
import com.afforess.minecartmaniacore.config.ControlBlockList;
import com.afforess.minecartmaniacore.config.LocaleParser;

public class AutocartListener extends VehicleListener{
	
	public void onVehicleMove(VehicleMoveEvent event) {
		if (event.getVehicle() instanceof Minecart) {
			MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart)event.getVehicle());
		
			//Cooldown to prevent minecarts from running away
			if (Autocart.doCooldown(minecart)) {
				return;
			}
			
			if (minecart.getLocation().getBlock().getTypeId() == Item.RAILS.getId() && (!minecart.isAtIntersection() || !minecart.hasPlayerPassenger())) {
				if (!MinecartManiaAutocart.isAutocartOnlyForPlayers() || minecart.hasPlayerPassenger()) {
					int l = MinecartManiaWorld.getBlockData(minecart.minecart.getWorld(), minecart.getX(), minecart.getY(), minecart.getZ());
	
					int ai[][] = Autocart.trackMetadata[l];
					double d8 = ai[1][0] - ai[0][0];
					double d10 = ai[1][2] - ai[0][2];
					double d12 = minecart.getMotionX() * d8 + minecart.getMotionZ() * d10;
					if(d12 < 0.0D)
					{
						d8 = -d8;
						d10 = -d10;
					}
					
					d8 /= 4;
					d10 /= 4;
	
					double d13 = Math.sqrt(minecart.getMotionX() * minecart.getMotionX() + minecart.getMotionZ() * minecart.getMotionZ());
					
					minecart.setMotionX(d13 > 0.0D ? d8 : 0.0D);
					minecart.setMotionZ(d13 > 0.0D ? d10 : 0.0D);
					
					Autocart.doThrottle(minecart);
				}
			}
		}
	}
	
	public void onVehicleEnter(VehicleEnterEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getVehicle() instanceof Minecart && event.getVehicle().getPassenger() == null) {
			MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart)event.getVehicle());
			minecart.setDataValue("Cooldown", null);
			if (event.getEntered() instanceof Player) {
				((Player)event.getEntered()).sendMessage(LocaleParser.getTextKey("AutoCartEnteredMessage"));
			}
		}
	}
	
	public void onVehicleExit(VehicleExitEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getVehicle() instanceof Minecart) {
			MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart)event.getVehicle());
			ControlBlock cb = ControlBlockList.getControlBlock(minecart.getItemBeneath());
			if (!minecart.isMoving() && cb == null || !cb.isEjectorBlock()) {
				minecart.setDataValue("Cooldown", new Long(Calendar.getInstance().getTimeInMillis() + 3000));
			}
		}
	}
}
