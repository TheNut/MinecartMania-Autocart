package com.afforess.bukkit.minecartmaniaautocart;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.afforess.bukkit.minecartmaniacore.MinecartManiaCore;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaFlatFile;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaWorld;

public class Configuration {
	/**
	 ** Initializes configuration values
	 ** 
	 **/
	public static void loadConfiguration() {
		readFile();
	}

	public static void readFile() {	
		
		File directory = new File("MinecartMania" + File.separator);
		if (!directory.exists())
			directory.mkdir();
		File options = new File("MinecartMania" + File.separator + "MinecartManiaAutocart.txt");
		if (!options.exists() || invalidFile(options))
		{
			WriteFile(options);
		}
		ReadFile(options);
	}
	
	private static boolean invalidFile(File file)
	{
		try {
			BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
			for (String s = ""; (s = bufferedreader.readLine()) != null; )
			{
				if (s.indexOf(MinecartManiaCore.description.getVersion()) > -1)
				{
					return false;
				}

			}
			bufferedreader.close();
		}
		catch (IOException exception)
		{
			return true;
		}
		return true;
	}
	
	private static void WriteFile(File file)
	{
		try
		{
			file.createNewFile();
			BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file));
			MinecartManiaFlatFile.createNewHeader(bufferedwriter, "Minecraft Mania Stations" + MinecartManiaAutocart.description.getVersion(), "Minecart Mania Autocart Config Settings", true);
			MinecartManiaFlatFile.createNewSetting(bufferedwriter, "Autocart for Players Only", "false", 
			"Only minecarts with players in them will be effected by Autocart.");

			bufferedwriter.close();
		}
		catch (Exception exception)
		{
			MinecartManiaCore.log.severe("Failed to write Minecart Mania settings!");
			exception.printStackTrace();
		}
	}

	private static void ReadFile(File file)
	{
		try {
			MinecartManiaWorld.setConfigurationValue("Autocart for Players Only", new Boolean(
					MinecartManiaFlatFile.getValueFromSetting(file, "Autocart for Players Only", "false")));

		}
		catch (Exception exception)
		{
			MinecartManiaCore.log.severe("Failed to read Minecart Mania settings!");
			exception.printStackTrace();
		}
	}

}
