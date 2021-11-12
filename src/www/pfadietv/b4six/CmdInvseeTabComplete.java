package www.pfadietv.b4six;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CmdInvseeTabComplete implements TabCompleter
{
	B4six plugin;
	public CmdInvseeTabComplete(B4six b4six)
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command _cmd, String cmdlabel, String[] args)
	{
		// TODO Auto-generated method stub
		if(_cmd.getName().equalsIgnoreCase("ender"))
		{
			if(args.length<=1)
			{
				List<String> playerList = new ArrayList<String>();
				for(Player p : Bukkit.getOnlinePlayers())
				{
					playerList.add(p.getName());
				}
				return playerList;
			}
		}	
		return null;
	}
}
