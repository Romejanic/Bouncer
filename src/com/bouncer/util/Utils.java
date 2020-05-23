package com.bouncer.util;

import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utils {

	public static final String CHAT_COLOUR = ChatColor.GOLD.toString();
	
	public static boolean hasPermission(String perm, CommandSender sender) {
		return sender.hasPermission("bouncer." + perm) || sender.hasPermission("bouncer.*");
	}
	
	public static boolean assertHasPermission(String perm, CommandSender sender) {
		boolean flag = hasPermission(perm, sender);
		if(!flag) sender.sendMessage(ChatColor.RED + "Sorry! You don't have permission to use that command.");
		return flag;
	}
	
	public static String joinList(List<String> list, String sep) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> iter = list.iterator();
		while(iter.hasNext()) {
			sb.append(iter.next());
			if(iter.hasNext()) {
				sb.append(sep);
			}
		}
		return sb.toString();
	}
	
}