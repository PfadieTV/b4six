package www.pfadietv.b4six;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdHome implements CommandExecutor
{
	private B4six plugin;
	private String permission;
	private HashMap<String, Object> singleHomeMap;
	private String doesTpCostKey;
	private String valueTpCostKey;
	private String tpPearlBankValue;
	
	public CmdHome(B4six b4six) {
		this.plugin = b4six;
		permission = "b4six.home";
		setup();
	}
	private void setup()
	{
		doesTpCostKey = "doTeleportsCostEnderPearls";
		valueTpCostKey = "pearlCostForEachTeleport";
		tpPearlBankValue = "storedEnderPearls";
		if(!plugin.globalSettings.isKeyAvaible(doesTpCostKey))
		{
			plugin.globalSettings.setValue(doesTpCostKey, false);
			plugin.globalSettings.saveFile();
		}
		if(!plugin.globalSettings.isKeyAvaible(valueTpCostKey))
		{
			plugin.globalSettings.setValue(valueTpCostKey, 1);
			plugin.globalSettings.saveFile();
		}
		
		//Load all Homes
		for(String key : plugin.dataHomes.getKeys(true)) //Outputs all Keys (even without home and more detail)
		{
			Object[] splitKey = key.split("\\.");
			if(splitKey.length!=3)//Breaks if key not complete
			{
				
			}
			else
			{
				UUID kUUID = UUID.fromString((String) splitKey[0]);
				String kHome = (String) splitKey[1];
				String kDataField = (String) splitKey[2];
				
				if(!plugin.uuidMap.containsKey(kUUID))
				{
					plugin.uuidMap.put(kUUID, new HashMap<String, HashMap<String, Object>>());
				}
				if(!plugin.uuidMap.get(kUUID).containsKey(kHome))
				{
					plugin.uuidMap.get(kUUID).put(kHome, new HashMap<String, Object>());
				}
				plugin.uuidMap.get(kUUID).get(kHome).put(kDataField, plugin.dataHomes.get(key));
			}
		}
		System.out.println("Homes erfolgreich geladen.");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args)
	{
		Player p = (Player) sender;
		
		if(p.hasPermission(permission))
		{
			//What to do on command:
			
			if(cmd.getName().equalsIgnoreCase("home"))
			{
				if(args.length==1)
				{
					tpHome(p, args[0]);												//TP to specified home
				}
				else if(args.length==0)
				{
					tpHome(p, p.getUniqueId().toString());							//TP to standard home
				}
			}
			else if(cmd.getName().equalsIgnoreCase("homes"))						//List all homes
			{
				homesList(p);
			}
			else if(cmd.getName().equalsIgnoreCase("sethome"))
			{
				if(args.length==1)													//create new home
				{
					newHome(p, args[0]);
				}
				else 																//Set standard home point
				{
					newHome(p, p.getUniqueId().toString());
				}
			}
			else if(cmd.getName().equalsIgnoreCase("delhome"))
			{
				if(args.length==1)
				{
					deleteHome(p, args[0]);
				}
				else
				{
					p.sendMessage("No home selected. Please specify a home with adding its name to the command.");
				}
			}
			else if(cmd.getName().equalsIgnoreCase("viewhome"))
			{
				if(args.length==1)
				{
					viewHome(p, args[0]);
				}
				else
				{
					p.sendMessage("No home selected. Please specify a home with adding its name to the command.");
				}
			}
			else if(cmd.getName().equalsIgnoreCase("payTeleport"))
			{
				if(args.length==0)
				{
					if((boolean)plugin.globalSettings.getValue(doesTpCostKey))
					{
						pay(p);
					}
					else
					{
						p.sendMessage("No payment required to use teleports");
					}
				}
				else
				{
					p.sendMessage("Please use the correct syntax to open the payment window. /payTeleport");
				}
			}
		}
		else
		{
			p.sendMessage("Permission Error: Du hast leider nicht die benötigte Permission, um diesen Befehl auszuführen. Sollte dies ein Fehler sein, wende dich bitte an einen Server-Admin");
		}
		return true;
	}
	public void newHome(Player p, String title)
	{
		UUID uuid = p.getUniqueId();
		String world = p.getWorld().getName();
		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		float yaw = p.getLocation().getYaw();
		float pitch = p.getLocation().getPitch();
		
		if(!plugin.uuidMap.containsKey(uuid))
		{
			plugin.uuidMap.put(uuid, new HashMap<String, HashMap<String, Object>>());
		}
		
		//Store home in runtime
		plugin.uuidMap.get(uuid).put(title, new HashMap<String, Object>());
		plugin.uuidMap.get(uuid).get(title).put("world", world);
		plugin.uuidMap.get(uuid).get(title).put("x", x);
		plugin.uuidMap.get(uuid).get(title).put("y", y);
		plugin.uuidMap.get(uuid).get(title).put("z", z);
		plugin.uuidMap.get(uuid).get(title).put("yaw", yaw);
		plugin.uuidMap.get(uuid).get(title).put("pitch", pitch);
		
		try
		{
			//Store home on file
			plugin.dataHomes.set(uuid+"."+title+".world", world);
			plugin.dataHomes.set(uuid+"."+title+".x", x);
			plugin.dataHomes.set(uuid+"."+title+".y", y);
			plugin.dataHomes.set(uuid+"."+title+".z", z);
			plugin.dataHomes.set(uuid+"."+title+".yaw", yaw);
			plugin.dataHomes.set(uuid+"."+title+".pitch", pitch);
			p.sendMessage("You've successfully set a new home.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			p.sendMessage("Das Home konnte nicht gespeichert werden. Bitte versuche es erneut oder kontaktiere einen Admin");
		}
		saveFile(p);
	}
	private void saveFile(Player p)
	{
		try
		{
			plugin.dataHomes.save(plugin.fileHomes);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			p.sendMessage("Fatal Error during save process");
		}
	}
	private void homesList(Player p)
	{
		UUID uuidCurrent = p.getUniqueId();
		if(plugin.uuidMap.containsKey(uuidCurrent))
		{
			p.sendMessage("List of all created homes:");
			for(String tempString : plugin.uuidMap.get(uuidCurrent).keySet())
			{
				if(!tempString.equals(uuidCurrent.toString()))
				{
					p.sendMessage(tempString);
				}
				else
				{
					p.sendMessage("<noName>");
				}
			}
		}
		else
		{
			p.sendMessage("Du hast noch keine Homes gesetzt.");
			p.sendMessage("Um ein Home zu setzen, nutze den Befehl /home <name>");
		}
	}
	private void tpHome(Player p, String homeName)
	{
		UUID uuid = p.getUniqueId();
		//TP to specified home
		if(plugin.uuidMap.containsKey(uuid))
		{
			if(plugin.uuidMap.get(uuid).containsKey(homeName))
			{
				singleHomeMap = plugin.uuidMap.get(uuid).get(homeName);
				World homeWorld = Bukkit.getWorld(singleHomeMap.get("world").toString());
				double x = (double) singleHomeMap.get("x");
				double y = (double) singleHomeMap.get("y");
				double z = (double) singleHomeMap.get("z");
				float yaw;
				float pitch;
				try
				{
					yaw = (float) singleHomeMap.get("yaw");
					pitch = (float) singleHomeMap.get("pitch");
				}
				catch(Exception e)
				{
					yaw = (float) (double)singleHomeMap.get("yaw");
					pitch = (float) (double)singleHomeMap.get("pitch");
				}
				Location newLoc = new Location(homeWorld, x, y, z, yaw, pitch);
				
				//Check for setting if teleports are costing ender pearls
				if((boolean)plugin.globalSettings.getValue(doesTpCostKey))
				{
					BetterPlayer pl = plugin.onlinePlayer.get(p.getUniqueId());
					//Check if Key Available
					if(pl.containsKey(tpPearlBankValue))
					{
						//Check if Player had enough pearls
						if((int)pl.readSavedPlayerData(tpPearlBankValue)>=(int)plugin.globalSettings.getValue(valueTpCostKey))
						{
							int newEnderPearlValue = (int)pl.readSavedPlayerData(tpPearlBankValue) - (int)plugin.globalSettings.getValue(valueTpCostKey);
							doTeleport(p, newLoc);
							pl.modifySavedPlayerData(tpPearlBankValue, newEnderPearlValue);
							p.sendMessage("You have " + newEnderPearlValue + " Ender-Pearls left to use for teleportations.");
						}
						else
						{
							p.sendMessage("You do not have stored enough Ender-Pearls to your account.");
							p.sendMessage("Each teleport will cost you " + (int)plugin.globalSettings.getValue(valueTpCostKey) + " Ender-Pearls");
						}
					}
					else
					{
						p.sendMessage("To use /home please store Ender-Pearls at first.");
						p.sendMessage("To do so you may use the command /payTeleport");
					}
				}
				else
				{
					doTeleport(p, newLoc);
				}
			}
			else
			{
				if(homeName==uuid.toString()){}
				else
				{
					messageHomeNotAvaible(p, homeName);	
				}
			}
		}
		else
		{
			if(homeName!=uuid.toString()){}
			else
			{
				messageHomeNotAvaible(p, homeName);
			}
		}
		
	}
	private void doTeleport(Player p, Location newLoc)
	{
		p.teleport(newLoc);
	}
	private void deleteHome(Player p, String homeName)
	{
		UUID uuidCurrent = p.getUniqueId();
		if(plugin.uuidMap.containsKey(uuidCurrent))
		{
			if(plugin.uuidMap.get(uuidCurrent).containsKey(homeName))
			{
				//Remove data
				try
				{
					plugin.dataHomes.set(uuidCurrent+"."+homeName+".world", null);
					plugin.dataHomes.set(uuidCurrent+"."+homeName+".x", null);
					plugin.dataHomes.set(uuidCurrent+"."+homeName+".y", null);
					plugin.dataHomes.set(uuidCurrent+"."+homeName+".z", null);
					plugin.dataHomes.set(uuidCurrent+"."+homeName+".yaw", null);
					plugin.dataHomes.set(uuidCurrent+"."+homeName+".pitch", null);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					p.sendMessage(e.getCause().toString());
				}
				saveFile(p);
				//Remove from runtime
				plugin.uuidMap.get(uuidCurrent).get(homeName).clear();
				plugin.uuidMap.get(uuidCurrent).remove(homeName);
				if(plugin.uuidMap.get(uuidCurrent).isEmpty())
				{
					plugin.uuidMap.remove(uuidCurrent);
				}
				p.sendMessage("Das home mit der Bezeichnung "+homeName+" wurde erfolgreich entfernt.");
			}
			else
			{
				messageHomeNotAvaible(p, homeName);
			}
		}
	}
	private void viewHome(Player p, String homeName)
	{
		UUID uuid = p.getUniqueId();
		//TP to specified home
		if(plugin.uuidMap.containsKey(uuid))
		{
			if(plugin.uuidMap.get(uuid).containsKey(homeName))
			{
				singleHomeMap = plugin.uuidMap.get(uuid).get(homeName);
				double x = (double) singleHomeMap.get("x");
				double z = (double) singleHomeMap.get("z");
				p.sendMessage("Der gewählte Punkt liegt an den Koordinaten: §c("+Math.round(x)+"|"+Math.round(z)+")");
			}
			else
			{
				if(homeName==uuid.toString()){}
				else
				{
					messageHomeNotAvaible(p, homeName);	
				}
			}
		}
		else
		{
			if(homeName!=uuid.toString()){}
			else
			{
				messageHomeNotAvaible(p, homeName);
			}
		}
		
	}
	private void pay(Player player)
	{
		ItemStack offHand = player.getInventory().getItemInOffHand();
		int addValue;
		if(!offHand.getType().equals(Material.ENDER_PEARL))
		{
			player.sendMessage("You need to put ender pearls in your off-hand to store them for teleportations");
			return;
		}
		else
		{
			addValue = offHand.getAmount();
		}
		BetterPlayer p = plugin.onlinePlayer.get(player.getUniqueId());
		int storedValue=0;
		if(p.containsKey(tpPearlBankValue))
		{
			storedValue = (int)p.readSavedPlayerData(tpPearlBankValue);
		}
		p.modifySavedPlayerData(tpPearlBankValue, storedValue+addValue);
		player.getInventory().getItemInOffHand().setAmount(0);
		player.sendMessage("You have added "+addValue+" to your account. You now have "+ (storedValue+addValue) +" Ender-Pearls stored.");
		player.sendMessage("Each teleport with /home will cost you " + (int)plugin.globalSettings.getValue(valueTpCostKey) + " Ender-Pearls");
	}
	private void messageHomeNotAvaible(Player p, String homeName)
	{
		p.sendMessage("Das Home "+homeName+" ist nicht vorhanden. Mit '/homes' erhältst du eine Liste aller homes.");
	}
	@SuppressWarnings("unused")
	private void outputUUIDMap()
	{
		//Output UUID-Map to Console
		for(UUID tempUUID : plugin.uuidMap.keySet())
		{
			System.out.println(tempUUID);
			for(String tempHome : plugin.uuidMap.get(tempUUID).keySet())
			{
				System.out.println(tempHome);
				for(Object tempObj : plugin.uuidMap.get(tempUUID).get(tempHome).keySet())
				{
					System.out.println(tempObj+": "+plugin.uuidMap.get(tempUUID).get(tempHome).get(tempObj));
				}
			}
		}
	}
}
