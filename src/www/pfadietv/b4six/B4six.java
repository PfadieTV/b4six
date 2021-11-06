package www.pfadietv.b4six;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class B4six extends JavaPlugin implements Listener{

	@Override
	public void onEnable()
	{
		this.getServer().getPluginManager().registerEvents(this, this);
		setup();
		System.out.println("[B4six] Plugin activated successfully.");
	}
	
	public void setup()
	{
		registerEvents();
		registerCommands();
	}
	
	public void registerEvents()//Load external classes to use for event listeners and regarding methods
	{
		
	}
	
	public void registerCommands()//Load external classes to use for commands, its methods and tab-comletes
	{
		/* 
		 * Example/Naming:
		 * <Classname: Cmd[command]> <classInstance: exec[command]> = new <classname>(this)
		 * getCommand("<command>").setExecutor(classInstance);
		 */
		
		CmdPing execPing = new CmdPing(this);
		getCommand("ping").setExecutor(execPing);
	}
	public Player getPlayerObject(String playerName)
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
