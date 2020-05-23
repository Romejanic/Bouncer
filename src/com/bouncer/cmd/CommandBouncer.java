package com.bouncer.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import com.bouncer.Bouncer;
import com.bouncer.util.Utils;

public class CommandBouncer implements CommandExecutor {

	public final String[] subCommands = { "help", "version", "reload" };
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Utils.CHAT_COLOUR + ChatColor.BOLD + "Bouncer Subcommands:");
			for(String sub : subCommands) {
				if(!Utils.hasPermission(sub, sender)) continue;
				sender.sendMessage(Utils.CHAT_COLOUR + "/" + label + " " + sub);
			}
			sender.sendMessage(Utils.CHAT_COLOUR + "For a full list of commands, type " + ChatColor.BOLD + "/" + label + " help");
		} else {
			String sub = args[0].toLowerCase();
			if(!Utils.assertHasPermission(sub, sender)) return true;
			switch(sub) {
			case "version":
				PluginDescriptionFile desc = Bouncer.instance().getDescription();
				sender.sendMessage(Utils.CHAT_COLOUR + ChatColor.BOLD + "Bouncer v" + desc.getVersion());
				sender.sendMessage(Utils.CHAT_COLOUR + "Created by " + Utils.joinList(desc.getAuthors(), ", "));
				sender.sendMessage("TODO: add update checker"); // TODO: add update checker
				break;
			case "reload":
				sender.sendMessage("You asked to reload the config but there isn't one");
				break;
			case "help":
				sender.sendMessage("You asked for help but there's none there");
				break;
			default:
				sender.sendMessage(ChatColor.RED + "Unrecognised subcommand: " + sub);
				sender.sendMessage(ChatColor.RED + "Type " + ChatColor.BOLD + "/" + label + ChatColor.RED + " for a list of subcommands.");
				break;
			}
		}
		return true;
	}

}