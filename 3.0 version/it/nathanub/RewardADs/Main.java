package it.nathanub.RewardADs;

/*
 * Written with love by @Nathanub ;)
 */

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import it.nathanub.RewardADs.Logic.Announcer;
import it.nathanub.RewardADs.Logic.Events;
import it.nathanub.RewardADs.Tools.DB;
import it.nathanub.RewardADs.Tools.InitializeInformations;
import it.nathanub.RewardADs.Tools.ReadConfig;
import it.nathanub.RewardADs.Tools.Error;

public class Main extends JavaPlugin {
	private static Main instance;

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		instance = this;
		
		getLogger().info("Plugin RewardADs enabled!");
		getServer().getPluginManager().registerEvents(new Events(this), this);
		
		new InitializeInformations();
		
		System.out.println(new DB().getServerName());
		
		if(new DB().getServerName() != null) {
			if(new ReadConfig().isAnnouncerEnabled()) Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Announcer(), 10, 20 * new ReadConfig().getAnnouncesDelay());
			
			new DB().setEarnPerAD();
		} else {
			new Error("configurations.yml", "not configurated code");
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void load() {
		new InitializeInformations();
		cancelAllTasks();
		
		if(new DB().getServerName() != null) {
			if(new ReadConfig().isAnnouncerEnabled()) Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Announcer(), 10, 20 * new ReadConfig().getAnnouncesDelay());
			
			new DB().setEarnPerAD();
		} else {
			new Error("configurations.yml", "not configurated code");
		}
	}
	
	public static Main getInstance() {
        return instance;
    }
	
	public static void cancelAllTasks() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        for(BukkitTask task : scheduler.getPendingTasks()) {
            task.cancel();
        }
    }
}