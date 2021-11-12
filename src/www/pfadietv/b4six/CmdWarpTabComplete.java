package www.pfadietv.b4six;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CmdWarpTabComplete implements TabCompleter
{
	B4six plugin;
	public CmdWarpTabComplete(B4six b4six) {
		// TODO Auto-generated constructor stub
		plugin = b4six;
	}

	@Override
	public List<String> onTabComplete(CommandSender player, Command _cmd, String cmdlabel, String[] args)
	{
		// TODO Auto-generated method stub
		if(_cmd.getName().equalsIgnoreCase("warp"))
		{
			if(args.length<=1)
			{
				List<String> warpList = new ArrayList<String>();
				for(String tempString : plugin.warpsMap.keySet())
				{
					warpList.add(tempString);
				}
				
				return warpList;
			}
		}
		if(_cmd.getName().equalsIgnoreCase("delwarp"))
		{
			if(args.length<=1)
			{
				List<String> warpList = new ArrayList<String>();
				for(String tempString : plugin.warpsMap.keySet())
				{
					warpList.add(tempString);
				}
				
				return warpList;
			}
		}
		return null;
	}
}
