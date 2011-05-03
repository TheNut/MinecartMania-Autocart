package com.afforess.minecartmaniaautocart;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;
import com.afforess.minecartmaniacore.config.MinecartManiaConfigurationParser;
import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;

public class MinecartManiaAutocart extends JavaPlugin{

	public static MinecartManiaLogger log = MinecartManiaLogger.getInstance();
	public static Server server;
	public static PluginDescriptionFile description;
	private static final AutocartListener listener = new AutocartListener();
	private static final AutocartActionListener actionListener = new AutocartActionListener();
	
	public void onEnable(){
		server = this.getServer();
		description = this.getDescription();
		MinecartManiaConfigurationParser.read(description.getName().replaceAll(" ","") + "Configuration.xml", MinecartManiaCore.dataDirectory, new AutocartSettingParser());
		getServer().getPluginManager().registerEvent(Event.Type.CUSTOM_EVENT, actionListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_MOVE, listener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_ENTER, listener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_EXIT, listener, Priority.Normal, this);
		log.info( description.getName() + " version " + description.getVersion() + " is enabled!" );
	}
	
	public void onDisable(){
		
	}
	
	public static boolean isAutocartOnlyForPlayers() {
		Object o = MinecartManiaWorld.getConfigurationValue("AutocartForPlayersOnly");
		if (o != null && o instanceof Boolean) {
			Boolean value = (Boolean)o;
			return value.booleanValue();
		}
		return false;
	}
	
	public static int getDefaultThrottle() {
		return MinecartManiaWorld.getIntValue(MinecartManiaWorld.getConfigurationValue("DefaultThrottle"));
	}
}
