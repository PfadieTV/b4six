package www.pfadietv.b4six;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CmdEnder implements CommandExecutor
{
	@SuppressWarnings("unused")
	private B4six plugin;

	public CmdEnder(B4six b4six) {
		this.plugin = b4six;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args)
	{
		Player p = (Player) sender;
		if(args.length==0)
		{
			if(p.hasPermission("xssentials.ender"))
			{
				//What to do on command:
				p.openInventory(p.getEnderChest());
			}
			else
			{
				p.sendMessage("Permission Error: Du hast leider nicht die benötigte Permission, um diesen Befehl auszuführen. Sollte dies ein Fehler sein, wende dich bitte an einen Server-Admin");
			}
		}
		else if(args.length==1)
		{
			if(p.hasPermission("xssentials.enderOP"))
			{
				boolean found = false;
				Inventory enderInventory = null;
				Collection<? extends Player> pCol = Bukkit.getOnlinePlayers();
				for(Player pArg : pCol)
				{
					if(pArg.getName().equalsIgnoreCase(args[0]))
					{
						enderInventory = pArg.getEnderChest();
						found=true;
						break;
					}
				}
				if(found)
				{
					p.openInventory(enderInventory);
				}
				else
				{
					p.sendMessage("Der Spieler "+args[0]+" ist nicht vorhanden/online");
				}
			}
			else
			{
				p.sendMessage("Permission Error: Du hast leider nicht die benötigte Permission, um diesen Befehl auszuführen. Sollte dies ein Fehler sein, wende dich bitte an einen Server-Admin");
			}
		}
		else
		{
			p.sendMessage("Syntax Error: Please use /ender to see your ender chest");
		}
		
		return true;
	}
}
