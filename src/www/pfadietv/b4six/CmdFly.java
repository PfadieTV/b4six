package www.pfadietv.b4six;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdFly implements CommandExecutor
{
	@SuppressWarnings("unused")
	private B4six plugin;
	private String permission;

	public CmdFly(B4six b4six) {
		this.plugin = b4six;
		permission = "b4six.fly";
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args)
	{
		Player p = (Player) sender;
		float standardFlySpeed = 0.1f;
		float customFlySpeed = 1f;
		
		if(p.hasPermission(permission))
		{
			//What to do on command:
			if(cmd.getName().equalsIgnoreCase("fly"))
			{
				p.setAllowFlight(!p.getAllowFlight());
				if(p.getAllowFlight())
				{
					p.sendMessage("Flight-Mode ist aktiv");
				}
				else
				{
					p.sendMessage("Flight-Mode ist nicht aktiv");
				}
			}
			else if(cmd.getName().equalsIgnoreCase("flyspeed"))
			{
				if(p.hasPermission("xssentials.flyspeed"))
				{
					if(args.length==0)
					{
						p.setFlySpeed(standardFlySpeed);
						p.sendMessage("Flyspeed is back at standard value (Flyspeed = 1)");
					}
					else if(args.length==1)
					{
						customFlySpeed = Float.valueOf(args[0]+"f")/10;
						if(0<=customFlySpeed && 1>=customFlySpeed)
						{
							p.setFlySpeed(customFlySpeed);
							p.sendMessage("Flyspeed is now set to "+(customFlySpeed*10));
						}
						else
						{
							p.sendMessage("Der Wert für Flyspeed muss zwischen 0 und 10 liegen");
						}
					}
					else
					{
						p.sendMessage("Syntax Error - Please use following format:");
						p.sendMessage("/flyspeed <value>");
					}
				}
				else
				{
					p.sendMessage("Permission Error: Du hast leider nicht die benötigte Permission, um diesen Befehl auszuführen. Sollte dies ein Fehler sein, wende dich bitte an einen Server-Admin");
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
