package www.pfadietv.b4six;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class B4six extends JavaPlugin implements Listener
{
	File fileHomes = new File("plugins/Xssentials","homes.yml");
	FileConfiguration dataHomes = YamlConfiguration.loadConfiguration(fileHomes);
	File fileWarps = new File("plugins/Xssentials","warps.yml");
	FileConfiguration dataWarps = YamlConfiguration.loadConfiguration(fileWarps);
	File fileBetterPlayer = new File("plugins/Xssentials","betterPlayerData.yml");
	FileConfiguration dataPlayers = YamlConfiguration.loadConfiguration(fileBetterPlayer);
	File fileGlobalSettings = new File("plugins/Xssentials","settings.yml");
	FileConfiguration dataSettings = YamlConfiguration.loadConfiguration(fileGlobalSettings);
	
	
	HashMap<String, Object> singleHomeMap = new HashMap<>();
	HashMap<String, HashMap<String, Object>> homesMap = new HashMap<>();
	HashMap<UUID, HashMap<String, HashMap<String, Object>>> uuidMap = new HashMap<>();

	HashMap<String, Object> singleWarpMap = new HashMap<>();
	HashMap<String, HashMap<String, Object>> warpsMap = new HashMap<>();
	
	HashMap<String, Object> dataMap = new HashMap<>();
	
	HashMap<UUID, BetterPlayer> onlinePlayer = new HashMap<>();
	
	GlobalSettings globalSettings;
	
	@Override
	public void onEnable()
	{
		this.getServer().getPluginManager().registerEvents(this, this);
		setup();
		System.out.println("[Xssentials] Plugin activated successfully.");
		System.out.println("Following settings will be used:");
		for(String key : dataSettings.getKeys(true))
		{
			System.out.println(key + ": " + dataSettings.get(key));	
		}
		System.out.println("Those settings can be changed in ./plugins/Xssentials/settings.yml");
	}
	
	public void setup()
	{
		globalSettings = new GlobalSettings(this);
		B4sixAPI.setB4six(this);
		registerEvents();
		registerCommands();
	}
	
	public void registerEvents()//Load external classes to use for event Listeners and Methods
	{
		new ServerJoinListener(this);
		new TeleportListener(this);
		new PlayerDeathListener(this);
	}
	public void registerCommands()
	{
		CmdPing execPing = new CmdPing(this);
		getCommand("ping").setExecutor(execPing);
		
		CmdFeed execFeed = new CmdFeed(this);
		getCommand("feed").setExecutor(execFeed);
		
		CmdHeal execHeal = new CmdHeal(this);
		getCommand("heal").setExecutor(execHeal);
		
		CmdRepair execRepair = new CmdRepair(this);
		getCommand("repair").setExecutor(execRepair);
		
		CmdWeather execWeather = new CmdWeather(this);
		getCommand("sun").setExecutor(execWeather);
		getCommand("rain").setExecutor(execWeather);
		getCommand("thunder").setExecutor(execWeather);
		getCommand("weather").setExecutor(execWeather);
		
		CmdTime execTime = new CmdTime(this);
		getCommand("day").setExecutor(execTime);
		getCommand("night").setExecutor(execTime);
		
		CmdCompass execCompass = new CmdCompass(this);
		getCommand("compass").setExecutor(execCompass);
		
		CmdFly execFly = new CmdFly(this);
		getCommand("fly").setExecutor(execFly);
		getCommand("flyspeed").setExecutor(execFly);
		
		CmdHome execHome = new CmdHome(this);
		CmdHomeTabComplete TabCompleteHome = new CmdHomeTabComplete(this);
		getCommand("home").setExecutor(execHome);
		getCommand("home").setTabCompleter(TabCompleteHome);
		getCommand("homes").setExecutor(execHome);
		getCommand("sethome").setExecutor(execHome);
		getCommand("delhome").setExecutor(execHome);
		getCommand("delhome").setTabCompleter(TabCompleteHome);
		getCommand("viewhome").setExecutor(execHome);
		getCommand("viewhome").setTabCompleter(TabCompleteHome);
		getCommand("payTeleport").setExecutor(execHome);
		
		CmdBack execBack = new CmdBack(this);
		getCommand("back").setExecutor(execBack);
		
		CmdWarp execWarp = new CmdWarp(this);
		CmdWarpTabComplete TabCompleteWarp = new CmdWarpTabComplete(this);
		getCommand("warp").setExecutor(execWarp);
		getCommand("warp").setTabCompleter(TabCompleteWarp);
		getCommand("warphelp").setExecutor(execWarp);
		getCommand("warps").setExecutor(execWarp);
		getCommand("setwarp").setExecutor(execWarp);
		getCommand("delwarp").setExecutor(execWarp);
		getCommand("delwarp").setTabCompleter(TabCompleteWarp);
		getCommand("setspawn").setExecutor(execWarp);
		getCommand("spawn").setExecutor(execWarp);
		
		
		CmdCraft execCraft = new CmdCraft(this);
		getCommand("craft").setExecutor(execCraft);
		
		CmdGod execGod = new CmdGod(this);
		getCommand("god").setExecutor(execGod);
		
		CmdEnder execEnder = new CmdEnder(this);
		CmdEnderTabComplete TabCompleteEnder = new CmdEnderTabComplete(this);
		getCommand("ender").setExecutor(execEnder);
		getCommand("ender").setTabCompleter(TabCompleteEnder);
		
		CmdInvsee execInvsee = new CmdInvsee(this);
		CmdInvseeTabComplete TabCompleteInvsee = new CmdInvseeTabComplete(this);
		getCommand("invsee").setExecutor(execInvsee);
		getCommand("invsee").setTabCompleter(TabCompleteInvsee);
		
		CmdMsg execMsg = new CmdMsg(this);
		getCommand("msg").setExecutor(execMsg);
		getCommand("r").setExecutor(execMsg);
		
		CmdGamemode execGamemode = new CmdGamemode(this);
		getCommand("gms").setExecutor(execGamemode);
		getCommand("gmc").setExecutor(execGamemode);
		
		CmdTpa execTpa = new CmdTpa(this);
		getCommand("tpa").setExecutor(execTpa);
		getCommand("tpaaccept").setExecutor(execTpa);
		getCommand("tpadeny").setExecutor(execTpa);
		getCommand("tpahere").setExecutor(execTpa);
		getCommand("tpatoggle").setExecutor(execTpa);
	}
	
	public Player getPlayer(String playerName)
	{		
		for(Player p : getServer().getOnlinePlayers())
		{
			if(playerName.equalsIgnoreCase(p.getName().toString()))
			{
				return p;
			}
		}
		return null;
	}
}
