package www.pfadietv.b4six;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdTpa implements CommandExecutor
{
	private B4six plugin;
	private String permission;

	public CmdTpa(B4six b4six)
	{
		this.plugin = b4six;
		permission = "b4six.tpa";
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args)
	{
		Player p = (Player) sender;
		
		if(p.hasPermission(permission))
		{
			//What to do on command:
			if(cmd.getName().equalsIgnoreCase("tpa"))
			{
				if(args.length==1)
				{
					Player pTo = plugin.getPlayer(args[0]);
					if(pTo != null)
					{
						sendTeleportRequest(p, pTo, false);
					}
					else
					{
						p.sendMessage("Der Spieler "+args[0]+" wurde nicht gefunden");
					}
				}
				else if(args.length==0)
				{
					//works like tpaaccept
				}
				else
				{
					p.sendMessage("You have to enter a command in following structure:");
					p.sendMessage("/tpa <playerTo>");
				}
			}
			else if(cmd.getName().equalsIgnoreCase("tpaaccept"))
			{
				reactToTeleportRequest(true, p);
			}
			else if(cmd.getName().equalsIgnoreCase("tpadeny"))
			{
				reactToTeleportRequest(false, p);
			}
			else if(cmd.getName().equalsIgnoreCase("tpahere"))
			{
				if(args.length==1)
				{
					Player pFrom = plugin.getPlayer(args[0]);
					if(pFrom != null)
					{
						sendTeleportRequest(pFrom, p, true);
					}
					else
					{
						p.sendMessage("Der Spieler "+args[0]+" wurde nicht gefunden");
					}
				}
				else
				{
					p.sendMessage("You have to enter a command in following structure:");
					p.sendMessage("/tpa <player>");
				}
			}
			else if(cmd.getName().equalsIgnoreCase("tpatoggle"))
			{
				if(args.length==0)
				{
					toggleTpaAccept(p);
				}
				else if(args.length==1)
				{
					if(Boolean.parseBoolean(args[0]))
					{
						setToggleTpaAccept(p, true);
					}
					else
					{
						setToggleTpaAccept(p, false);
					}
				}
				else
				{
					
				}
			}
		}
		else
		{
			p.sendMessage("Permission Error: Du hast leider nicht die benötigte Permission, um diesen Befehl auszuführen. Sollte dies ein Fehler sein, wende dich bitte an einen Server-Admin");
		}
		return true;
	}
	
	private void toggleTpaAccept(Player p)
	{
		boolean tempToggle = !(boolean)plugin.onlinePlayer.get(p.getUniqueId()).readSavedPlayerData("toggleTPA");
		plugin.onlinePlayer.get(p.getUniqueId()).modifySavedPlayerData("toggleTPA", tempToggle);
		p.sendMessage("Currently your tpa acception automation is set to "+tempToggle);
	}
	
	private void setToggleTpaAccept(Player p, boolean toggle)
	{
		plugin.onlinePlayer.get(p.getUniqueId()).modifySavedPlayerData("toggleTPA", toggle);
		p.sendMessage("Currently your tpa acception automation is set to "+toggle);
	}
	
	private void sendTeleportRequest(Player pFrom, Player pTo, boolean pFromAsRequestTo)
	{
		Player pRequest = null;
		System.out.println("DEBUG:-|pFrom, pTo|-"+pFrom.getName()+"-"+pTo.getName());
		if(pFromAsRequestTo)
		{
			pRequest=pFrom;
		}
		else
		{
			pRequest=pTo;
		}
		System.out.println("DEBUG:-|pFrom, pTo, pRequest|-"+pFrom.getName()+"-"+pTo.getName()+"-"+pRequest.getName());
		plugin.onlinePlayer.get(pRequest.getUniqueId()).betterPlayerData.put("tpa", new TeleportRequest(pFrom, pTo, pRequest));
		System.out.println("DEBUG:-|pFrom, pTo, pRequest|-"+pFrom.getName()+"-"+pTo.getName()+"-"+pRequest.getName());
		
		//Check if tpaToggle is active
		if((boolean)plugin.onlinePlayer.get(pTo.getUniqueId()).readSavedPlayerData("toggleTPA"))
		{
			reactToTeleportRequest(true, pRequest);
		}
		else
		{
			pRequest.sendMessage("§e[Teleport-request]");
			pRequest.sendMessage("§c"+pFrom.getName()+"§e->§c"+pTo.getName());
			pRequest.sendMessage("§eTo Accept this request type §c/tpaaccept");
			pRequest.sendMessage("§eTo Deny this request type §c/tpadeny");
		}
	}
	
	private void reactToTeleportRequest(boolean allow, Player p)
	{
		if(allow)
		{
			if(plugin.onlinePlayer.get(p.getUniqueId()).betterPlayerData.containsKey("tpa"))
			{
				TeleportRequest tpa = (TeleportRequest)plugin.onlinePlayer.get(p.getUniqueId()).betterPlayerData.get("tpa");
				Player pFrom = tpa.getFrom();
				Player pTo = tpa.getTo();
				pFrom.teleport(pTo);
			}
			else
			{
				p.sendMessage("There is currently no teleport request");
			}
		}
		else
		{
			p.sendMessage("You successfully denied the teleport request");
			TeleportRequest tpa = (TeleportRequest)plugin.onlinePlayer.get(p.getUniqueId()).betterPlayerData.get("tpa");
			if(!p.equals(tpa.getFrom()))
			{
				tpa.getFrom().sendMessage("The requested teleport was denied by "+p.getName()); //Change to "tpa.getFrom()" if not working
			}
			else
			{
				tpa.getTo().sendMessage("The requested teleport was denied by "+p.getName()); //Change to "tpa.getFrom()" if not working
			}
		}
	}
}

class TeleportRequest
{
	private Player pFrom;
	private Player pTo;
	private Player pRequest;
	TeleportRequest(Player pFrom, Player pTo, Player pRequest)
	{
		this.pFrom=pFrom;
		this.pTo=pTo;
		this.pRequest=pRequest;
	}
	public Player getFrom()
	{
		return pFrom;
	}
	public Player getTo()
	{
		return pTo;
	}
	public Player getRequestor()
	{
		return pRequest;
	}
}
