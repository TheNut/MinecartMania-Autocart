package com.afforess.minecartmaniaautocart;

import com.afforess.minecartmaniacore.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.event.MinecartClickedEvent;
import com.afforess.minecartmaniacore.event.MinecartManiaListener;
import com.afforess.minecartmaniacore.utils.DirectionUtils;

public class AutocartActionListener extends MinecartManiaListener{

	public void onMinecartClickedEvent(MinecartClickedEvent event) {
		if (event.isActionTaken()) {
			return;
		}
		MinecartManiaMinecart minecart = event.getMinecart();
		if (!minecart.isMoving()) {
			DirectionUtils.CompassDirection facingDir = DirectionUtils.getDirectionFromMinecartRotation((minecart.minecart.getPassenger().getLocation().getYaw() - 90.0F) % 360.0F);
			minecart.minecart.setVelocity(Autocart.getMotionFromDirection(facingDir));
		}
		else {
			minecart.stopCart();
		}
	}
}
