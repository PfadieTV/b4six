package www.pfadietv.b4six;

import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdWarp implements CommandExecutor
{
	private B4six plugin;
	private HashMap<String, Object> singleWarpMap = new HashMap<>();

	public CmdWarp(B4six b4six) {
		this.plugin = b4six;
		setup();
	}

	private void setup() {
		//Load all Warps
		for(String key : plugin.dataWarps.getKeys(true)) //Outputs all Keys (even without home and more detail)
		{
			Object[] splitKey = key.split("\\.");
			if(splitKey.length!=2)//Breaks if key not complete
			{
				
			}
			else
			{
				String kWarp = (String) splitKey[0];
				String kDataField = (String) splitKey[1];
				
				if(!plugin.warpsMap.containsKey(kWarp))
				{
					plugin.warpsMap.put(kWarp, new HashMap<String, Object>());
				}
				plugin.warpsMap.get(kWarp).put(kDataField, plugin.dataWarps.get(key));
			}
		}
		System.out.println("Warps erfolgreich geladen.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args)
	{
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("warphelp"))
		{
			p.sendMessage("§eHere you find a list of all commands avaible with your permissions:");
			if(p.hasPermission("xssentials.warp"))
			{
				p.sendMessage("§c/warp <name>");
				p.sendMessage("Teleport to position from warp");
			}
			if(p.hasPermission("xssentials.spawn"))
			{
				p.sendMessage("§c/spawn");
				p.sendMessage("Teleport to position from spawn");
			}
			if(p.hasPermission("xssentials.setwarp"))
			{
				p.sendMessage("§c/setwarp <name>");
				p.sendMessage("Creates a new warp from your current location");
			}
			if(p.hasPermission("xssentials.setspawn"))
			{
				p.sendMessage("§c/setspawn");
				p.sendMessage("Set the spawnpoint to your current location");
				p.sendMessage("Creates a warp called 'spawn'");
			}
			if(p.hasPermission("xssentials.warpDeaktivate"))
			{
				p.sendMessage("§c/warp <name> setActive <true/false>");
				p.sendMessage("Provides you with the opportunity to (de)activate a specific warp");
			}
			if(p.hasPermission("xssentials.delwarp"))
			{
				p.sendMessage("§c/delwarp <name>");
				p.sendMessage("Deletes the selected warp");
			}
			if(p.hasPermission("xssentials.warps"))
			{
				p.sendMessage("§c/warps");
				p.sendMessage("List of all avaible warps");
			}
		}
		else if(cmd.getName().equalsIgnoreCase("warp"))
		{
			if(p.hasPermission("xssentials.warp"))
			{
				if(args.length == 1)
				{
					tpWarp(p, args[0]);
				}
				else if(args.length == 3)
				{
					if(p.hasPermission("xssentials.warpDeaktivate"))
					{
						if(args[0].equalsIgnoreCase("spawn"))
						{
							p.sendMessage("This warp is used for /spawn. It is not allowed to get deactivated");
							p.sendMessage("To remove that spawn, you can type '/delwarp spawn'");
						}
						else
						{
							if(args[1].equalsIgnoreCase("setActive"))
							{
								boolean active = false;
								if(args[2].equalsIgnoreCase("True"))
								{
									active = true;
								}
								setWarpActive(p, args[0], active);
							}
						}
					}
				}
				else
				{
					p.sendMessage("Wrong syntax!");
					p.sendMessage("Please use following: /warp <name>");
				}
			}
			else
			{
				noPermission(p);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("spawn"))
		{
			if(p.hasPermission("xssentials.spawn"))
			{
				tpWarp(p, "spawn");
			}
			else
			{
				noPermission(p);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("warps"))
		{
			if(p.hasPermission("xssentials.warps"))
			{
				warpsList(p);
			}
			else
			{
				noPermission(p);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("setwarp"))
		{
			if(p.hasPermission("xssentials.setwarp"))
			{
				if(args.length==1)
				{
					if(args[0].equalsIgnoreCase("spawn"))
					{
						p.sendMessage("The warp 'spawn' is reserved for '/spawn'-command");
					}
					newWarp(p, args[0]);
				}
				else
				{
					p.sendMessage("Wrong syntax!");
					p.sendMessage("Please use following: /setwarp <name>");
				}
			}
			else
			{
				noPermission(p);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("setspawn"))
		{
			if(p.hasPermission("xssentials.setspawn"))
			{
				if(args.length==0)
				{
					newWarp(p, "spawn");
				}
				else
				{
					p.sendMessage("No parameter required");
				}
			}
			else
			{
				noPermission(p);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("delwarp"))
		{
			if(p.hasPermission("xssentials.delwarp"))
			{
				deleteWarp(p, args[0]);
			}
			else
			{
				noPermission(p);
			}
		}
		return true;
	}
	
	private void newWarp(Player p, String warpName)
	{
		String world = p.getWorld().getName();
		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		float yaw = p.getLocation().getYaw();
		float pitch = p.getLocation().getPitch();
		boolean active = true;
		
		if(!plugin.warpsMap.containsKey(warpName))
		{
			plugin.warpsMap.put(warpName, new HashMap<String, Object>());
		}
		
		plugin.warpsMap.get(warpName).put("world", world);
		plugin.warpsMap.get(warpName).put("x", x);
		plugin.warpsMap.get(warpName).put("y", y);
		plugin.warpsMap.get(warpName).put("z", z);
		plugin.warpsMap.get(warpName).put("yaw", yaw);
		plugin.warpsMap.get(warpName).put("pitch", pitch);
		plugin.warpsMap.get(warpName).put("active", active);
		
		try
		{
			//Store home on Server
			plugin.dataWarps.set(warpName+".world", world);
			plugin.dataWarps.set(warpName+".x", x);
			plugin.dataWarps.set(warpName+".y", y);
			plugin.dataWarps.set(warpName+".z", z);
			plugin.dataWarps.set(warpName+".yaw", yaw);
			plugin.dataWarps.set(warpName+".pitch", pitch);
			plugin.dataWarps.set(warpName+".active", active);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			p.sendMessage("Das Warp konnte nicht gespeichert werden. Bitte versuche es erneut oder kontaktiere einen Admin");
		}
		saveFile(p);
	}
	private void warpsList(Player p)
	{
		int i=0;
		boolean anyWarpActive = false;
		int countWarps = plugin.warpsMap.size();
		String[] warpList = new String[countWarps];
		if(plugin.warpsMap.isEmpty())
		{
			noWarpActive(p);
		}
		else
		{
			for(String tempString : plugin.warpsMap.keySet())
			{
				if((boolean) plugin.warpsMap.get(tempString).get("active")) //Warp is marked as not active
				{
					warpList[i]=tempString;
					anyWarpActive=true;
				}
				else
				{
					warpList[i]=null;
				}
				i++;
			}
			if(anyWarpActive)
			{
				//Arrays.sort(warpList);
				p.sendMessage("List of all created warps:");
				for(String warpName : warpList)
				{
					if(warpName!=null)
					{
						p.sendMessage(warpName);
					}
				}
			}
			else
			{
				noWarpActive(p);
			}
		}
	}
	private void tpWarp(Player p, String warpName)
	{
		//TP to specified home
		if(plugin.warpsMap.containsKey(warpName))
		{
			singleWarpMap = plugin.warpsMap.get(warpName);
			World warpWorld = Bukkit.getWorld(singleWarpMap.get("world").toString());
			double x = (double) singleWarpMap.get("x");
			double y = (double) singleWarpMap.get("y");
			double z = (double) singleWarpMap.get("z");
			float yaw;
			float pitch;
			boolean active = (boolean) singleWarpMap.get("active");
			try
			{
				yaw = (float) singleWarpMap.get("yaw");
				pitch = (float) singleWarpMap.get("pitch");
			}
			catch(Exception e)
			{
				yaw = (float) (double) singleWarpMap.get("yaw");
				pitch = (float) (double) singleWarpMap.get("pitch");
			}
			if(active)
			{
				Location newLoc = new Location(warpWorld, x, y, z, yaw, pitch);
				p.teleport(newLoc);
			}
			else
			{
				p.sendMessage("That warp is currently not active. Please contact an administrator if that seems to be wrong");
			}
		}
		else
		{
			p.sendMessage("This Warp is not avaible. Please take a look on all warps");
			p.sendMessage("/warps");
			//messageWarpNotAvaible(p, warpName);
		}
		
	}
	
	private void deleteWarp(Player p, String warpName)
	{
		if(plugin.warpsMap.containsKey(warpName))
		{
			//Remove data
			try
			{
				plugin.dataWarps.set(warpName+"."+"world", null);
				plugin.dataWarps.set(warpName+"."+"x", null);
				plugin.dataWarps.set(warpName+"."+"y", null);
				plugin.dataWarps.set(warpName+"."+"z", null);
				plugin.dataWarps.set(warpName+"."+"yaw", null);
				plugin.dataWarps.set(warpName+"."+"pitch", null);
				plugin.dataWarps.set(warpName+"."+"active", null);
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				p.sendMessage(e.getCause().toString());
			}
			saveFile(p);
			//Remove from runtime
			plugin.warpsMap.get(warpName).clear();
			plugin.warpsMap.remove(warpName);
			p.sendMessage("Der Warp mit der Bezeichnung "+warpName+" wurde erfolgreich entfernt.");
		}
	}
	
	private void setWarpActive(Player p, String warpName, boolean active)
	{
		if(plugin.dataWarps.contains(warpName+"."+"active"))
		{
			plugin.dataWarps.set(warpName+"."+"active", active);
			plugin.warpsMap.get(warpName).put("active", active);
			if(active)
			{
				p.sendMessage("The Warp "+warpName+" is now set as active");
			}
			else
			{
				p.sendMessage("The Warp "+warpName+" is now set as not active");
			}
		}
		saveFile(p);
	}
	
	private void saveFile(Player p)
	{
		try
		{
			plugin.dataWarps.save(plugin.fileWarps);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			p.sendMessage("Fatal Error during save process");
		}
	}
	
	private void noPermission(Player p)
	{
		p.sendMessage("Permission Error: Du hast leider nicht die benötigte Permission, um diesen Befehl auszuführen. Sollte dies ein Fehler sein, wende dich bitte an einen Server-Admin");
	}
	
	private void noWarpActive(Player p)
	{
		p.sendMessage("Es sind noch keine Warps vorhanden.");
		if(p.hasPermission("xssentials.setwarp")) 
		{
			p.sendMessage("Um einen neuen Warp zu erstellen, nutze den Befehl /setwarp <name>");
		}
	}
}
