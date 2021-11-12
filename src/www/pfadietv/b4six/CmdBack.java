package www.pfadietv.b4six;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdBack implements CommandExecutor
{
	private B4six plugin;
	private String permission;

	public CmdBack(B4six b4six) {
		this.plugin = b4six;
		permission = "b4six.back";
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args)
	{
		Player p = (Player) sender;
		
		if(p.hasPermission(permission))
		{
			//What to do on command:
			if((boolean) plugin.onlinePlayer.get(p.getUniqueId()).betterPlayerData.get("backLocSet"))
			{
				p.teleport((Location)plugin.onlinePlayer.get(p.getUniqueId()).betterPlayerData.get("backLoc"));
			}
			else
			{
				p.sendMessage("You never teleported yourself in that session.");
			}
		}
		else
		{
			p.sendMessage("Permission Error: Du hast leider nicht die benötigte Permission, um diesen Befehl auszuführen. Sollte dies ein Fehler sein, wende dich bitte an einen Server-Admin");
		}
		return true;
	}
}
