package www.pfadietv.b4six;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdHeal implements CommandExecutor
{
	private B4six plugin;
	private String permission;

	public CmdHeal(B4six b4six) {
		this.plugin = b4six;
		permission = "b4six.heal";
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
				heal(p);
			}
			else if(args.length==1)
			{
				for(Player allP : plugin.getServer().getOnlinePlayers())
				{
					if(allP.getName().equalsIgnoreCase(p.getName()))
					{
						heal(allP);
					}
				}
			}
			else
			{
				p.sendMessage("Syntax Error: Please use following syntax");
				p.sendMessage("/heal <player>");
			}
			
		}
		else
		{
			p.sendMessage("Permission Error: Du hast leider nicht die ben�tigte Permission, um diesen Befehl auszuf�hren. Sollte dies ein Fehler sein, wende dich bitte an einen Server-Admin");
		}
		return true;
	}
	private void heal(Player p)
	{
		p.setHealth(20);
		p.sendMessage("Du bist jetzt wieder geheilt!");
	}
}
