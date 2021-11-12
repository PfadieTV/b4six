package www.pfadietv.b4six;

import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

public class B4sixAPI extends JavaPlugin
{
	private static B4six plugin;
	
	//Setup to run in Xssentials-Class directly after plugin is loaded
	//With that plugin-var it is possible to access data from xssentials-plugin
	protected static void setB4six(B4six b4six)
	{
		plugin=b4six;
	}
	
	public static void saveValue(UUID uuid, String obj, Object value)
	{
		plugin.onlinePlayer.get(uuid).modifySavedPlayerData(obj, value);
	}
	
	public static Object readValue(UUID uuid, String key)
	{
		Object output = null;
		output = plugin.onlinePlayer.get(uuid).readSavedPlayerData(key);
		if(output==null)
		{
			System.out.println("The value "+key+" for player "+uuid+" is not able to get read");
		}
		return output;
	}
	
	public static Object isKeyAvaible(UUID uuid, String key)
	{
		Object output = null;
		output = plugin.onlinePlayer.get(uuid).readSavedPlayerData(key);
		return output;
	}
	public static boolean isGlobalSettingSet(String key)
	{
		return plugin.globalSettings.isKeyAvaible(key);
	}
	public static void setGlobalSetting(String key, Object value)
	{
		plugin.globalSettings.setValue(key, value);
		plugin.globalSettings.saveFile();
	}
	public static Object getGlobalSetting(String key)
	{
		if(isGlobalSettingSet(key))
		{
			return plugin.globalSettings.getValue(key);
		}
		return null;
	}
}
