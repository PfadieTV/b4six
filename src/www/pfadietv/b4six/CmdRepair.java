package www.pfadietv.b4six;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdRepair implements CommandExecutor
{
	@SuppressWarnings("unused")
	private B4six plugin;
	private String permission;

	public CmdRepair(B4six b4six) {
		this.plugin = b4six;
		permission = "b4six.repair";
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args)
	{
		Player p = (Player) sender;
		
		if(p.hasPermission(permission))
		{
			//What to do on command:
			p.getInventory().getItemInMainHand().setDurability((short)0);
			p.sendMessage("Das gehaltene Item wurde repariert");
		}
		else
		{
			p.sendMessage("Permission Error: Du hast leider nicht die benötigte Permission, um diesen Befehl auszuführen. Sollte dies ein Fehler sein, wende dich bitte an einen Server-Admin");
		}
		return true;
	}
}
