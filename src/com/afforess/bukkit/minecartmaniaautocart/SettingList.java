package com.afforess.bukkit.minecartmaniaautocart;

import com.afforess.bukkit.minecartmaniacore.config.Setting;

public class SettingList {
	public final static Setting[] config = {
		new Setting(
				"Autocart for Players Only", 
				Boolean.FALSE, 
				"Only minecarts with players in them will be effected by Autocart.",
				MinecartManiaAutocart.description.getName()
		)
	};
}
