package com.afforess.bukkit.minecartmaniaautocart;

import org.bukkit.Vector;

import com.afforess.bukkit.minecartmaniacore.DirectionUtils;

public class Autocart {

	public static final int trackMetadata[][][] = { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };

	public static Vector getMotionFromDirection(DirectionUtils.CompassDirection direction) {
		Vector vector = new Vector(0D, 0D, 0D);
		if (direction.equals(DirectionUtils.CompassDirection.NORTH)) {
			vector.setX(-0.4D);
			//System.out.println(DirectionUtils.CompassDirection.NORTH);
		}
		else if (direction.equals(DirectionUtils.CompassDirection.EAST)) {
			vector.setZ(-0.4D);
			//System.out.println(DirectionUtils.CompassDirection.EAST);
		}
		else if (direction.equals(DirectionUtils.CompassDirection.SOUTH)) {
			vector.setX(0.4D);
			//System.out.println(DirectionUtils.CompassDirection.SOUTH);
		}
		else if (direction.equals(DirectionUtils.CompassDirection.WEST)) {
			vector.setZ(0.4D);
			//System.out.println(DirectionUtils.CompassDirection.WEST);
		}
		return vector;
	}
}
