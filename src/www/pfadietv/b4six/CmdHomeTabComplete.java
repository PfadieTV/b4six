package www.pfadietv.b4six;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CmdHomeTabComplete implements TabCompleter
{
	B4six plugin;
	public CmdHomeTabComplete(B4six b4six)
	{
		// TODO Auto-generated constructor stub
		plugin = b4six;
	}

	@Override
	public List<String> onTabComplete(CommandSender player, Command _cmd, String cmdlabel, String[] args)
	{
		Player p = (Player) player;
		// TODO Auto-generated method stub
		if(_cmd.getName().equalsIgnoreCase("home"))
		{
			if(args.length<=1)
			{
				List<String> homeList = new ArrayList<String>();
				UUID uuidCurrent = p.getUniqueId();
				if(plugin.uuidMap.containsKey(uuidCurrent))
				{
					for(String tempString : plugin.uuidMap.get(uuidCurrent).keySet())
					{
						if(!tempString.equals(uuidCurrent.toString()))
						{
							homeList.add(tempString);
						}
					}
				}
				return homeList;
			}
		}
		if(_cmd.getName().equalsIgnoreCase("delhome"))
		{
			if(args.length<=1)
			{
				List<String> homeList = new ArrayList<String>();
				UUID uuidCurrent = p.getUniqueId();
				if(plugin.uuidMap.containsKey(uuidCurrent))
				{
					for(String tempString : plugin.uuidMap.get(uuidCurrent).keySet())
					{
						if(!tempString.equals(uuidCurrent.toString()))
						{
							homeList.add(tempString);
						}
					}
				}
				return homeList;
			}
		}
		if(_cmd.getName().equalsIgnoreCase("viewhome"))
		{
			if(args.length<=1)
			{
				List<String> homeList = new ArrayList<String>();
				UUID uuidCurrent = p.getUniqueId();
				if(plugin.uuidMap.containsKey(uuidCurrent))
				{
					for(String tempString : plugin.uuidMap.get(uuidCurrent).keySet())
					{
						if(!tempString.equals(uuidCurrent.toString()))
						{
							homeList.add(tempString);
						}
					}
				}
				return homeList;
			}
		}
		return null;
	}
}
