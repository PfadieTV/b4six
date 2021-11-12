package www.pfadietv.b4six;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdMsg implements CommandExecutor
{
	private B4six plugin;
	private String permission;

	public CmdMsg(B4six b4six) {
		this.plugin = b4six;
		permission = "b4six.msg";
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args)
	{
		Player p = (Player) sender;
		
		if(p.hasPermission(permission))
		{
			//What to do on command:
			if(cmd.getName().equalsIgnoreCase("msg"))
			{
				if(args.length >= 2)
				{
					Player pFrom = p;
					Player pTo = null;
					String message="";
					for(Player pAll : plugin.getServer().getOnlinePlayers())
					{
						if(pAll.getName().toString().equalsIgnoreCase(args[0].toString()))
						{
							pTo=pAll;
						}
					}
					for(int i=1; i<args.length; i++)
					{
						message+=args[i]+" ";
					}
					try
					{
						msgCmd(pFrom, pTo, message);
					}
					catch(Exception e)
					{
						p.sendMessage("Der Player "+args[0]+" wurde nicht gefunden.");
					}
				}
				else
				{
					p.sendMessage("Du musst folgende Syntax verwenden: ");
					p.sendMessage("/msg [player] <Message>");
				}
			}
			else if(cmd.getName().equalsIgnoreCase("r"))
			{
				if(args.length>=1)
				{
					String message = "";
					for(int i=0; i<args.length; i++)
					{
						message+=args[i]+" ";
					}
					Player pTemp = (Player)plugin.onlinePlayer.get(p.getUniqueId()).betterPlayerData.get("lastMsgPlayer");
					msgCmd(p, pTemp, message);
				}
			}
			else if(cmd.getName().equalsIgnoreCase("ignore"))
			{
				p.sendMessage("This command is currently in planning process");
			}
		}
		else
		{
			p.sendMessage("Permission Error: Du hast leider nicht die benötigte Permission, um diesen Befehl auszuführen. Sollte dies ein Fehler sein, wende dich bitte an einen Server-Admin");
		}
		return true;
	}
	
	public void msgCmd(Player pFrom, Player pTo, String message)
	{
		String messageReceiver = "§e[§c"+pFrom.getName()+"§e -> "+pTo.getName()+"]: §f"+message;
		pTo.sendMessage(messageReceiver);
		String messageSender = "§e["+pFrom.getName()+"§e -> §c"+pTo.getName()+"§e]: §f"+message;
		pFrom.sendMessage(messageSender);
		
		plugin.onlinePlayer.get(pTo.getUniqueId()).betterPlayerData.put("lastMsgPlayer", pFrom);;
	}
}
