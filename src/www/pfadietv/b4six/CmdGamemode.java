package www.pfadietv.b4six;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdGamemode implements CommandExecutor
{
	private B4six plugin;
	private String permission;

	public CmdGamemode(B4six b4six) {
		this.plugin = b4six;
		permission = "b4six.gamemode";
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args)
	{
		Player p = (Player) sender;
		
		if(p.hasPermission(permission))
		{
			//What to do on command:
			if(args.length==0)
			{
				if(cmd.getName().equalsIgnoreCase("gms"))
				{
					p.setGameMode(GameMode.SURVIVAL);
				}
				else if(cmd.getName().equalsIgnoreCase("gmc"))
				{
					p.setGameMode(GameMode.CREATIVE);
				}
			}
			else if(args.length==1)
			{
				if(p.hasPermission("xssentials.gamemodeOP"))
				{
					Player pToPerformOn = plugin.getPlayer(args[0]);
					if(pToPerformOn==null)
					{
						p.sendMessage("Player "+args[0].toString()+" not found");
					}
					else
					{
						if(cmd.getName().equalsIgnoreCase("gms"))
						{
							pToPerformOn.setGameMode(GameMode.SURVIVAL);
						}
						else if(cmd.getName().equalsIgnoreCase("gmc"))
						{
							pToPerformOn.setGameMode(GameMode.CREATIVE);
						}
					}
				}
			}
		}
		else
		{
			p.sendMessage("Permission Error: Du hast leider nicht die benötigte Permission, um diesen Befehl auszuführen. Sollte dies ein Fehler sein, wende dich bitte an einen Server-Admin");
		}
		return true;
	}
}
