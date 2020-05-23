package com.bouncer.cmd;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.bouncer.Bouncer;
import com.bouncer.util.Utils;

public class CommandBouncer implements CommandExecutor {

	private final String[] subCommands = { "help", "version", "reload" };
	private final String[] commands = {
		"kick",
		"ban",
		"ban-ip",
		"tempban",
		"mute",
		"tempmute",
		"pardon",
		"pardon-ip",
		"unmute",
		"banlist",
		"baniplist",
		"mutelist",
		"lockout"
	};
	private final int COMMANDS_PER_PAGE = 8;
	
	private final FileConfiguration commandDesc;
	
	public CommandBouncer(JavaPlugin plugin) {
		this.commandDesc = new YamlConfiguration();
		try {
			this.commandDesc.load(new InputStreamReader(plugin.getResource("command-desc.yml")));
		} catch (IOException | InvalidConfigurationException e) {
			plugin.getLogger().log(Level.WARNING, "Failed to load command descriptions!", e);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Utils.CHAT_COLOUR + ChatColor.BOLD + "Bouncer Subcommands:");
			for(String sub : subCommands) {
				if(!Utils.hasPermission(sub, sender)) continue;
				sender.sendMessage(
					Utils.CHAT_COLOUR + "/" + label + " " + sub
					+ ChatColor.YELLOW + " | " +
					this.commandDesc.getString(sub, "No description provided")
				);
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
				int pages = (int)Math.ceil((double)commands.length / COMMANDS_PER_PAGE);
				int pageNum = args.length >= 2 ? (Utils.safeParseInt(args[1], 1)-1) : 0;
				if(pageNum < 0) pageNum = 0;
				if(pageNum >= pages) pageNum = pages-1;
				int startIdx = pageNum * COMMANDS_PER_PAGE;
				sender.sendMessage(Utils.CHAT_COLOUR + ChatColor.BOLD + "== HELP PAGE " + (pageNum+1) + "/" + pages + " ==");
				int lenPage = Math.min(commands.length-startIdx, COMMANDS_PER_PAGE);
				for(int i = startIdx; i < startIdx + lenPage; i++) {
					sender.sendMessage(
						Utils.CHAT_COLOUR + "/" + commands[i] + " "
						+ ChatColor.YELLOW + "| " +
						this.commandDesc.getString(commands[i], "No description provided")
					);
				}
				sender.sendMessage(Utils.CHAT_COLOUR + "For more commands type " + ChatColor.BOLD + "/" + label + " help [n]");
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