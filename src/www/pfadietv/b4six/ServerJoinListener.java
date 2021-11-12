package www.pfadietv.b4six;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ServerJoinListener implements Listener
{
	private B4six plugin;

	public ServerJoinListener(B4six b4six)
	{
		this.plugin = b4six;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		if(!plugin.onlinePlayer.containsKey(p.getUniqueId()))
		{
			plugin.onlinePlayer.put(p.getUniqueId(), new BetterPlayer(p, plugin));
			System.out.println("New BetterPlayer created for Player: "+p.getUniqueId());
		}
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		plugin.onlinePlayer.remove(e.getPlayer().getUniqueId());
		System.out.println("BetterPlayer removed for Player: "+e.getPlayer().getUniqueId());
	}
	@EventHandler
	public void onKick(PlayerKickEvent e)
	{
		plugin.onlinePlayer.remove(e.getPlayer().getUniqueId());
		System.out.println("BetterPlayer removed for Player: "+e.getPlayer().getUniqueId());
	}
}
