package www.pfadietv.b4six;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener
{
	private B4six plugin;

	public TeleportListener(B4six b4six)
	{
		this.plugin = b4six;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e)
	{
		Player p = e.getPlayer();
		Location backLoc = e.getFrom();
		setBackLocation(p, backLoc);
	}
	
	private void setBackLocation(Player p, Location backLoc)
	{
		plugin.onlinePlayer.get(p.getUniqueId()).betterPlayerData.put("backLoc", backLoc);
		plugin.onlinePlayer.get(p.getUniqueId()).betterPlayerData.put("backLocSet", true);
	}
}
