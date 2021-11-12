package www.pfadietv.b4six;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import www.pfadietv.b4six.B4six;

public class BetterPlayer
{
	public HashMap<String, Object> betterPlayerData = new HashMap<>();
	private HashMap<String, Object> betterPlayerSavedData = new HashMap<>();
	private B4six plugin;
	
	public BetterPlayer(Player p, B4six b4six)
	{
		plugin = b4six;
		betterPlayerData.put("backLocSet", false);
		betterPlayerData.put("lastMsgPlayer", null);
		betterPlayerData.put("player", p);
		betterPlayerSavedData.put("toggleTPA", false);
		
		loadBetterPlayerData(p);
	}
	
	public void modifySavedPlayerData(String key, Object value)
	{
		betterPlayerSavedData.put(key, value);
		plugin.dataPlayers.set(((Player)betterPlayerData.get("player")).getUniqueId()+"."+key, value);
		saveBetterPlayerData();
	}
	public Object readSavedPlayerData(String key)
	{
		if(betterPlayerSavedData.containsKey(key))
		{
			return betterPlayerSavedData.get(key);
		}
		return null;
	}
	public boolean containsKey(String key)
	{
		if(betterPlayerSavedData.containsKey(key))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void loadBetterPlayerData(Player p)
	{
		System.out.println("BetterPlayerData loading...");
		UUID uuid = p.getUniqueId();
		
		//plugin.dataPlayers.set(uuid+"."+key, value);   <---- struct
		for(String key : plugin.dataPlayers.getKeys(true)) //Outputs all Keys (even without home and more detail)
		{
			Object[] splitKey = key.split("\\.");
			if(splitKey.length!=2)
			{
				
			}
			else
			{
				UUID dataUUID = UUID.fromString((String) splitKey[0]);
				if(dataUUID.equals(uuid))//Correct player picked
				{
					String kObject = (String) splitKey[1];
					betterPlayerSavedData.put(kObject, plugin.dataPlayers.get(key));
					System.out.println("Loaded values for: "+kObject);
				}
				else
				{
					//Not the correct player
				}
			}
		}
		System.out.println("BetterPlayerData loaded successfully");
		
	}
	
	public void saveBetterPlayerData()
	{
		try
		{
			plugin.dataPlayers.save(plugin.fileBetterPlayer);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			((Player)betterPlayerData.get("player")).sendMessage("Fatal Error during save process");
		}
	}
}
