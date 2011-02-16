package com.afforess.minecartmaniaautocart;

import com.afforess.minecartmaniacore.config.Setting;

public class SettingList {
	public final static Setting[] config = {
		new Setting(
				"Autocart for Players Only", 
				Boolean.FALSE, 
				"Only minecarts with players in them will be effected by Autocart.",
				MinecartManiaAutocart.description.getName()
		),
		new Setting(
				"Default Throttle", 
				new Integer(100), 
				"The default throttle percent for minecarts. Must be between 1 and 200.",
				MinecartManiaAutocart.description.getName()
		)
	};
}
