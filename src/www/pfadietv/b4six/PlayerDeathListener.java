package www.pfadietv.b4six;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener
{
	private B4six plugin;

	public PlayerDeathListener(B4six b4six)
	{
		this.plugin = b4six;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e)
	{
		//What to do on event
		Location backLoc = e.getEntity().getLocation();
		Player p = e.getEntity();
		setBackLocation(p, backLoc);
		e.getEntity().sendMessage("§eUse the §4/back §ecommand to return to your death point");
	}
	
	private void setBackLocation(Player p, Location backLoc)
	{
		plugin.onlinePlayer.get(p.getUniqueId()).betterPlayerData.put("backLoc", backLoc);
		plugin.onlinePlayer.get(p.getUniqueId()).betterPlayerData.put("backLocSet", true);
	}
}
