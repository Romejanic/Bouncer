package com.bouncer;

import org.bukkit.plugin.java.JavaPlugin;

public class Bouncer extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("Started up Bouncer!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Thank you for using Bouncer!");
	}
	
}