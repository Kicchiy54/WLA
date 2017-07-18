package com.WLA;


import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.sound.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;


public class Main extends PluginBase implements Listener{

	Text text = new Text();
	Menu menu = new Menu();
	PlayerSkin ps = new PlayerSkin();

	String MainWorldName = "Main";//Monitor,Next,Select等を表示するメインのワールド

	Integer NextBlockID = 57;//MonitorをネクストするブロックのID(ダイヤモンドブロック)
	Integer SelectBlockID = 41;//MonitorをネクストするブロックのID(金ブロック)

	Vector3 NextBlockPos = new Vector3(308.0, 5.0, 296.0); //NEXTの文字の座標
	Vector3 SelectBlockPos = new Vector3(308.0, 5.0, 300.0); //SELECTの〃


	HashMap<String, Integer>MenuNumber = new HashMap<String, Integer>();


	public void onEnable(){

        getLogger().info("§b§l起動しました");
        getServer().getPluginManager().registerEvents(this, this);

	}


    @EventHandler
    public void onJoin(PlayerJoinEvent event){

    	//Join時の処理
    	Player player = event.getPlayer();
    	text.firstProcess(player);
    	ps.saveSkin(player);

    }



    @EventHandler
    public void onInteract(PlayerInteractEvent event){

    	Player player = event.getPlayer();
    	Block block = event.getBlock();
    	Vector3 pos = new Vector3(block.x, block.y, block.z);
    	if(block.getId() == NextBlockID){//NEXTをタッチしたら
    		if(pos.x == NextBlockPos.x && pos.y == NextBlockPos.y && pos.z == NextBlockPos.z){
    			menu.nextMenu(player, menu.getMenuNumber(player));
    		}
    	}else if(block.getId() == SelectBlockID){//SELECTをタッチしたら
    		if(pos.x == SelectBlockPos.x && pos.y == SelectBlockPos.y && pos.z == SelectBlockPos.z){
    			//menu.selectMenu(player, menu.getMenuNumber(player)); コード未記入
    		}
    	}

    }


    @EventHandler
    public void onChange(EntityLevelChangeEvent event){

    	Entity entity = event.getEntity();
    	if(entity instanceof Player){//ワールドを移動したのはプレイヤーか？
	    	Level origin = event.getOrigin();//移動前のワールド
	    	Level target = event.getTarget();//移動前のワールド
	    	if(origin.getName() != target.getName() && target.getName() != MainWorldName && origin.getName() == MainWorldName){
	    		//移動前と移動先のワールドが違う & 移動先がメイン以外 & 移動前がメインの場合、Monitor等を削除
	    		text.allRemove((Player)entity);//EntityをPlayerに変換
	    	}else if(origin.getName() != target.getName() && target.getName() == MainWorldName && origin.getName() != MainWorldName){
	    		//移動前と移動先のワールドが違う & 移動先がメイン & 移動前がメイン以外の場合、Monitor等を出現
	    		text.firstProcess((Player)entity);
	    	}
    	}

    }


    public void addSound(Player player, Sound sound){

    	//音を鳴らす関数(いちいちこのコードを音楽鳴らす際に書くのがめんどくさいから＾＾)
    	Player[] ps = new Player[1];
    	ps[0] = player;
    	player.getLevel().addSound(sound, ps);
    	//addSoundの第2引数にPlayer[]を入れることでPlayer[]の中に入ってる人にだけ音が聞こえる

    }

}