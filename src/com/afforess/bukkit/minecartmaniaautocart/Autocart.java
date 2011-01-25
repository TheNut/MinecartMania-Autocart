package com.afforess.bukkit.minecartmaniaautocart;

import java.util.Calendar;
import org.bukkit.util.Vector;

import com.afforess.bukkit.minecartmaniacore.DirectionUtils;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaMinecart;

public class Autocart {

	public static final int trackMetadata[][][] = { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };

	public static Vector getMotionFromDirection(DirectionUtils.CompassDirection direction) {
		Vector vector = new Vector(0D, 0D, 0D);
		if (direction.equals(DirectionUtils.CompassDirection.NORTH)) {
			vector.setX(-0.5D);
		}
		else if (direction.equals(DirectionUtils.CompassDirection.EAST)) {
			vector.setZ(-0.5D);
		}
		else if (direction.equals(DirectionUtils.CompassDirection.SOUTH)) {
			vector.setX(0.5D);
		}
		else if (direction.equals(DirectionUtils.CompassDirection.WEST)) {
			vector.setZ(0.5D);
		}
		return vector;
	}
	
	public static boolean doCooldown(MinecartManiaMinecart minecart) {
		Object data = minecart.getDataValue("Cooldown");
		if (data != null) {
			long cooldown = ((Long)data).longValue();
			if (Calendar.getInstance().getTimeInMillis() > cooldown) {
				minecart.setDataValue("Cooldown", null);
			}
			else {
				minecart.stopCart();
				return true;
			}
		}
		return false;
	}

	public static void doThrottle(MinecartManiaMinecart minecart) {
		Object value = minecart.getDataValue("throttle");
		if (value != null) {
			double throttle = ((Double)(value)).doubleValue();
			minecart.setMotionX(minecart.getMotionX() * throttle / 100);
			minecart.setMotionY(minecart.getMotionY() * throttle / 100);
			minecart.setMotionZ(minecart.getMotionZ() * throttle / 100);
		}
	}
}
