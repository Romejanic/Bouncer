package com.bouncer;

import org.bukkit.plugin.java.JavaPlugin;

import com.bouncer.cmd.CommandBouncer;

public class Bouncer extends JavaPlugin {

	private static Bouncer instance;
	
	@Override
	public void onEnable() {
		instance = this;
		getCommand("bouncer").setExecutor(new CommandBouncer(this));
		
		getLogger().info("Started up Bouncer!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Thank you for using Bouncer!");
	}
	
	public static Bouncer instance() {
		return instance;
	}
	
}