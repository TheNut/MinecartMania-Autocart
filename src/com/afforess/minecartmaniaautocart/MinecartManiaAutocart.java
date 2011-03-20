package com.afforess.minecartmaniaautocart;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.MinecartManiaWorld;
import com.afforess.minecartmaniacore.config.MinecartManiaConfigurationParser;

public class MinecartManiaAutocart extends JavaPlugin{

	public static Logger log;
	public static Server server;
	public static PluginDescriptionFile description;
	private static final AutocartListener listener = new AutocartListener();
	private static final AutocartActionListener actionListener = new AutocartActionListener();
	
	public void onEnable(){
		log = Logger.getLogger("Minecraft");
		server = this.getServer();
		description = this.getDescription();
		Plugin MinecartMania = server.getPluginManager().getPlugin("Minecart Mania Core");
		
		if (MinecartMania == null) {
			log.severe("Minecart Mania Autocart requires Minecart Mania Core to function!");
			log.severe("Minecart Mania Autocart is disabled!");
			this.setEnabled(false);
		}
		else {
			MinecartManiaConfigurationParser.read(description.getName().replaceAll(" ","") + "Configuration.xml", MinecartManiaCore.dataDirectory, SettingList.config);
			getServer().getPluginManager().registerEvent(Event.Type.CUSTOM_EVENT, actionListener, Priority.Normal, this);
	        getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_MOVE, listener, Priority.Normal, this);
	        getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_ENTER, listener, Priority.Normal, this);
	        getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_EXIT, listener, Priority.Normal, this);
	        
	        PluginDescriptionFile pdfFile = this.getDescription();
	        log.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
		}
	}
	
	public void onDisable(){
		
	}
	
	public static boolean isAutocartOnlyForPlayers() {
		Object o = MinecartManiaWorld.getConfigurationValue("Autocart for Players Only");
		if (o != null && o instanceof Boolean) {
			Boolean value = (Boolean)o;
			return value.booleanValue();
		}
		return false;
	}
	
	public static int getDefaultThrottle() {
		return MinecartManiaWorld.getIntValue(MinecartManiaWorld.getConfigurationValue("Default Throttle"));
	}
}
