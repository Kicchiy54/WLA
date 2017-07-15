package com.WLA;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.plugin.PluginBase;


public class Main extends PluginBase implements Listener{

	Text text = new Text();

	public void onEnable(){
        getLogger().info("§b§l起動しました");
        getServer().getPluginManager().registerEvents(this, this);
	}

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
    	//Join時の処理
    	Player player = event.getPlayer();
    	text.firstProcess(player);
    }

}