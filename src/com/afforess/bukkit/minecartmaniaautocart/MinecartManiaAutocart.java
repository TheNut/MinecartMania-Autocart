package com.afforess.bukkit.minecartmaniaautocart;
import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Player;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecartManiaAutocart extends JavaPlugin{

	public MinecartManiaAutocart(PluginLoader pluginLoader, Server instance,
			PluginDescriptionFile desc, File plugin, ClassLoader cLoader) {
		super(pluginLoader, instance, desc, plugin, cLoader);
		server = instance;
	}

	public static Logger log;
	public static Server server;
	private static final AutocartListener listener = new AutocartListener();
	

	public void onEnable(){
		log = Logger.getLogger("Minecraft");
		
		Plugin MinecartMania = server.getPluginManager().getPlugin("Minecart Mania Core");
		
		if (MinecartMania == null) {
			log.severe("Minecart Mania Autocart requires Minecart Mania Core to function!");
			log.severe("Minecart Mania Autocart is disabled!");
			this.setEnabled(false);
		}
		else {
	        getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_DAMAGE, listener, Priority.High, this);
	        getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_MOVE, listener, Priority.High, this);
	        
	        PluginDescriptionFile pdfFile = this.getDescription();
	        log.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
		}
	}
	
	public void onDisable(){
		
	}
	
	public boolean isDebugging(final Player player) {
		return false;
    }

    public void setDebugging(final Player player, final boolean value) {
        
    }

}
