package www.pfadietv.b4six;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdCompass implements CommandExecutor
{
	@SuppressWarnings("unused")
	private B4six plugin;
	private String permission;

	public CmdCompass(B4six b4six)
	{
		this.plugin = b4six;
		permission = "b4six.compass";
	}

	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args)
	{
		Player p = (Player) sender;
		if(p.hasPermission(permission))
		{
			//What to do on command:
			String orientation = "";
			double rotation = (p.getLocation().getYaw()+180) % 360;
	        if (rotation < 0) {
	            rotation += 360.0;
	        }
	         if (0 <= rotation && rotation < 22.5) {
	        	 orientation = "Norden";
	        } else if (22.5 <= rotation && rotation < 67.5) {
	        	orientation ="Nord-Ost";
	        } else if (67.5 <= rotation && rotation < 112.5) {
	        	orientation ="Osten";
	        } else if (112.5 <= rotation && rotation < 157.5) {
	        	orientation ="Süd-Osten";
	        } else if (157.5 <= rotation && rotation < 202.5) {
	        	orientation ="Süden";
	        } else if (202.5 <= rotation && rotation < 247.5) {
	        	orientation ="Süd-Westen";
	        } else if (247.5 <= rotation && rotation < 292.5) {
	        	orientation ="Westen";
	        } else if (292.5 <= rotation && rotation < 337.5) {
	        	orientation ="Nord-Westen";
	        } else if (337.5 <= rotation && rotation < 360.0) {
	        	orientation ="Norden";
	        } else {
	        	orientation ="Unknown";
	        }
			
			p.sendMessage(orientation);
		}
		else
		{
			p.sendMessage("Permission Error: Du hast leider nicht die benötigte Permission, um diesen Befehl auszuführen. Sollte dies ein Fehler sein, wende dich bitte an einen Server-Admin");
		}
		return true;
	}
}
