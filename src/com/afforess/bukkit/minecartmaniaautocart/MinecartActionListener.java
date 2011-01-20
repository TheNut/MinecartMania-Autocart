package com.afforess.bukkit.minecartmaniaautocart;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.afforess.bukkit.minecartmaniacore.ChatUtils;
import com.afforess.bukkit.minecartmaniacore.DirectionUtils;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaMinecart;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaWorld;
import com.afforess.bukkit.minecartmaniacore.event.MinecartActionEvent;
import com.afforess.bukkit.minecartmaniacore.event.MinecartManiaListener;

public class MinecartActionListener extends MinecartManiaListener{
	public void onMinecartActionEvent(MinecartActionEvent event) {
		MinecartManiaMinecart minecart = event.getMinecart();
		
		if (minecart.getDataValue("old rail data") != null) {
			@SuppressWarnings("unchecked")
			ArrayList<Integer> blockData = (ArrayList<Integer>)minecart.getDataValue("old rail data");
			MinecartManiaWorld.setBlockData(blockData.get(0), blockData.get(1), blockData.get(2), blockData.get(3));
			minecart.setDataValue("old rail data", null);
		}
		
		if (minecart.isAtIntersection() && minecart.hasPlayerPassenger()) {
			minecart.stopCart();
			Player passenger = minecart.getPlayerPassenger();
			//set the track straight
			int data = DirectionUtils.getMinetrackRailDataForDirection(minecart.getPreviousFacingDir(), minecart.getPreviousFacingDir());
			Block oldBlock = MinecartManiaWorld.getBlockAt(minecart.getX(), minecart.getY(), minecart.getZ());
			ArrayList<Integer> blockData = new ArrayList<Integer>();
			blockData.add(new Integer(oldBlock.getX()));
			blockData.add(new Integer(oldBlock.getY()));
			blockData.add(new Integer(oldBlock.getZ()));
			blockData.add(new Integer(oldBlock.getData()));
			minecart.setDataValue("old rail data", blockData);
			if (data != -1)
				MinecartManiaWorld.setBlockData(minecart.getX(), minecart.getY(), minecart.getZ(), data);
			ChatUtils.sendMultilineMessage(passenger, "Tap your minecart in the desired direction", ChatColor.YELLOW.toString());
		}
		
	}
}
