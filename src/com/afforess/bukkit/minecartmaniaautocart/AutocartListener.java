package com.afforess.bukkit.minecartmaniaautocart;

import org.bukkit.Minecart;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import com.afforess.bukkit.minecartmaniacore.DirectionUtils;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaMinecart;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaWorld;

public class AutocartListener extends VehicleListener{
	
    public void onVehicleDamage(VehicleDamageEvent event) {
    	if (event.getVehicle() instanceof Minecart) {
    		MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart)event.getVehicle());
    		if (minecart.minecart.getPassenger() != null) {
    			if (minecart.isOnRails()) {
    				if(event.getAttacker() != null && event.getAttacker().getEntityID() == minecart.minecart.getPassenger().getEntityID()) {
		    			DirectionUtils.CompassDirection facingDir = DirectionUtils.getDirectionFromRotation((minecart.minecart.getPassenger().getLocation().getYaw() - 90.0F) % 360.0F);
    					//responding to chat direction prompt
    					if (minecart.isAtIntersection() && minecart.hasPlayerPassenger()) {
    						int data = DirectionUtils.getMinetrackRailDataForDirection(minecart.getPreviousFacingDir(), DirectionUtils.getOppositeDirection(facingDir));
    						if (data != -1) {
    						//	System.out.println("Moving forward");
    							MinecartManiaWorld.setBlockData(minecart.getX(), minecart.getY(), minecart.getZ(), data);
    							minecart.minecart.setVelocity(Autocart.getMotionFromDirection(facingDir));
    						}
    						else {
    							if (minecart.getPreviousFacingDir().equals(facingDir)) {
    								minecart.minecart.setVelocity(Autocart.getMotionFromDirection(facingDir));
    							}
    						}
    					}
    					else {
			    			//Not moving
			    			if (minecart.getDirectionOfMotion().equals(DirectionUtils.CompassDirection.NO_DIRECTION)) {
			    				minecart.minecart.setVelocity(Autocart.getMotionFromDirection(facingDir));
			    			}
			    			//stop motion
			    			else {
			    				minecart.stopCart();
			    			}
    					}
    				}
    			}
    		}
    	}
    }
    
    public void onVehicleMove(VehicleMoveEvent event) {
    	if (event.getVehicle() instanceof Minecart) {
    		MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart)event.getVehicle());
    		
    		if (!minecart.isMoving()) {
    			//Cooldown to prevent minecarts from running away
	    		if (Autocart.doCooldown(minecart)) {
	    			return;
	    		}
    		}
    		
			if (minecart.isOnRails() && (!minecart.isAtIntersection() || !minecart.hasPlayerPassenger())) {
	    		int l = MinecartManiaWorld.getBlockData(minecart.getX(), minecart.getY(), minecart.getZ());

		    	int ai[][] = Autocart.trackMetadata[l];
	            double d8 = ai[1][0] - ai[0][0];
	            double d10 = ai[1][2] - ai[0][2];
	            double d12 = minecart.getMotionX() * d8 + minecart.getMotionZ() * d10;
	            if(d12 < 0.0D)
	            {
	                d8 = -d8;
	                d10 = -d10;
	            }
	            d8 /= 5;
	            d10 /= 5;

	            double d13 = Math.sqrt(minecart.getMotionX() * minecart.getMotionX() + minecart.getMotionZ() * minecart.getMotionZ());
	            
	            minecart.setMotionX(d13 > 0.0D ? d8 : 0.0D);
	            minecart.setMotionZ(d13 > 0.0D ? d10 : 0.0D);
	    	}
    	}
    }
}
