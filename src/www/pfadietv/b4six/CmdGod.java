package www.pfadietv.b4six;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdGod implements CommandExecutor
{
	@SuppressWarnings("unused")
	private B4six plugin;
	private String permission;

	public CmdGod(B4six b4six) {
		this.plugin = b4six;
		permission = "b4six.god";
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
				p.setInvulnerable(!p.isInvulnerable());
				if(p.isInvulnerable())
				{
					p.sendMessage("God-Mode aktiviert");
				}
				else
				{
					p.sendMessage("God-Mode deaktiviert");
				}
			}
			else if(args.length==1)
			{
				boolean found = false;
				Collection<? extends Player> pCol = Bukkit.getOnlinePlayers();
				for(Player pArg : pCol)
				{
					if(pArg.getName().equalsIgnoreCase(args[0]))
					{
						found=true;
						pArg.setInvulnerable(!pArg.isInvulnerable());
						if(pArg.isInvulnerable())
						{
							pArg.sendMessage("Du bist nun im God-Mode");
							p.sendMessage(args[0]+" ist nun im God-Mode");
						}
						else
						{
							pArg.sendMessage("Du bist nun nicht mehr im God-Mode");
							p.sendMessage(args[0]+" ist nun nicht mehr im God-Mode");
						}
						break;
					}
				}
				if(!found)
				{
					p.sendMessage("Der Spieler "+args[0]+" wurde nicht gefunden");
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
