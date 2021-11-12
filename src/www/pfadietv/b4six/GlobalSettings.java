package www.pfadietv.b4six;

import java.io.IOException;

public class GlobalSettings
{
	B4six plugin;
	public GlobalSettings(B4six parent)
	{
		this.plugin = parent;
	}
	public void setValue(String key, Object value)
	{
		plugin.dataSettings.set(key, value);
	}
	public Object getValue(String key)
	{
		if(isKeyAvaible(key))
		{
			Object value = plugin.dataSettings.get(key);
			return value;
		}
		else
		{
			return null;
		}
	}
	public boolean isKeyAvaible(String key)
	{
		boolean isAvailable;
		isAvailable = plugin.dataSettings.contains(key);
		return isAvailable;
	}
	public void saveFile()
	{
		try
		{
			plugin.dataSettings.save(plugin.fileGlobalSettings);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Fatal Error during save process");
		}
	}
}
