package www.pfadietv.b4six;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdWeather implements CommandExecutor
{
	@SuppressWarnings("unused")
	private B4six plugin;
	private String permission;

	public CmdWeather(B4six b4six) {
		this.plugin = b4six;
		permission = "b4six.weather";
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args)
	{
		Player p = (Player) sender;
		
		if(p.hasPermission(permission))
		{
			//What to do on command:
			if(cmd.getName().equalsIgnoreCase("sun"))
			{
				p.getWorld().setStorm(false);
				p.getWorld().setThundering(false);
				p.sendMessage("Es scheint nun die Sonne");
			}
			else if(cmd.getName().equalsIgnoreCase("rain"))
			{
				p.getWorld().setStorm(true);
				p.sendMessage("Es beginnt zu regnen");
			}
			else if(cmd.getName().equalsIgnoreCase("thunder"))
			{
				p.getWorld().setThundering(true);
				p.sendMessage("Achtung - es gibt ein Gewitter");
			}
			else if(cmd.getName().equalsIgnoreCase("weather"))
			{
				if(p.getWorld().isClearWeather())
				{
					p.sendMessage("Aktuell scheint die Sonne.");
				}
				else if(p.getWorld().isThundering())
				{
					p.sendMessage("Aktuell gibt es ein Gewitter - bitte vor Blitzen aufpassen.");
				}
				else if(p.getWorld().hasStorm())
				{
					p.sendMessage("Aktuell gibt es einen Regenschauer.");
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
