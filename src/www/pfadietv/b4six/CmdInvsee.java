package www.pfadietv.b4six;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CmdInvsee implements CommandExecutor
{
	@SuppressWarnings("unused")
	private B4six plugin;
	private String permission;

	public CmdInvsee(B4six b4six) {
		this.plugin = b4six;
		permission = "b4six.invsee";
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args)
	{
		Player p = (Player) sender;
		
		if(p.hasPermission(permission))
		{
			if(args.length>0)
			{
				//What to do on command:
				Inventory otherInv = null;
				boolean found = false;
				Collection<? extends Player> pCol = Bukkit.getOnlinePlayers();
				for(Player pArg : pCol)
				{
					if(pArg.getName().equalsIgnoreCase(args[0]))
					{
						otherInv = pArg.getInventory();
						found=true;
						break;
					}
				}
				if(found)
				{
					p.openInventory(otherInv);
				}
				else
				{
					p.sendMessage("Der Spieler "+args[0]+" ist nicht vorhanden/online");
				}
			}
			else
			{
				p.sendMessage("Please use the correct syntax: §c/invsee <player>");
			}
		}
		else
		{
			p.sendMessage("Permission Error: Du hast leider nicht die benötigte Permission, um diesen Befehl auszuführen. Sollte dies ein Fehler sein, wende dich bitte an einen Server-Admin");
		}
		return true;
	}
}
