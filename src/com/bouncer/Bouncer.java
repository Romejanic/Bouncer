package com.bouncer;

import org.bukkit.plugin.java.JavaPlugin;

import com.bouncer.cmd.CommandBouncer;
import com.bouncer.util.Metrics;

public class Bouncer extends JavaPlugin {

	private static final int BSTATS_PLUGIN_ID = 7644;
	
	private static Bouncer instance;
	
	@SuppressWarnings("unused")
	private Metrics metrics;
	
	@Override
	public void onEnable() {
		instance = this;
		this.metrics = new Metrics(this, BSTATS_PLUGIN_ID);
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