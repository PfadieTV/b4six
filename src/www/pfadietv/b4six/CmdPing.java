package www.pfadietv.b4six;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdPing implements CommandExecutor{

	@SuppressWarnings("unused")
	private B4six plugin;

	public CmdPing(B4six b4six) {
		this.plugin = b4six;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args)
	{
		Player p = (Player) sender;
		
		p.sendMessage("Pong");
		return true;
	}
}