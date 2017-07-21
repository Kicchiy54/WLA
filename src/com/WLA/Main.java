package com.WLA;


import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.sound.AnvilUseSound;
import cn.nukkit.level.sound.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;

import com.WLA.Quest.Quest;


public class Main extends PluginBase implements Listener{

	Text text = new Text();
	Menu menu = new Menu();
	Quest quest = new Quest();
	PlayerSkin ps = new PlayerSkin();

	String MainWorldName = "Main";//Monitor,Next,Select等を表示するメインのワールド

	Integer NextBlockID = 57;//MonitorをネクストするブロックのID(ダイヤモンドブロック)
	Integer SelectBlockID = 41;//MonitorをネクストするブロックのID(金ブロック)

	Vector3 NextBlockPos = new Vector3(308.0, 5.0, 296.0); //NEXTの文字の座標
	Vector3 SelectBlockPos = new Vector3(308.0, 5.0, 300.0); //SELECTの〃

	static HashMap<String, String>PlayerStatus = new HashMap<String, String>();
	//プレイヤーの状態を保存
	static HashMap<String, Quest>TemporaryQuest = new HashMap<String, Quest>();
	//プレイヤーがクエストを受注する時にどのクエストを受けるかの一時保存用

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
    			menu.selectMenu(player, menu.getMenuNumber(player));
    		}
    	}

    	//player.sendMessage(pos.x + ", " + pos.y + ", " + pos.z);

    }


    @EventHandler
    public boolean onChat(PlayerChatEvent event){

    	Player player = event.getPlayer();
    	if(getStatus(player) == "WaitingQuestID"){
    		event.setCancelled();
    		String chat = event.getMessage();

    		try{//chatを数字に変えれるか->変えられない(=数字以外)は弾く
    			Integer.parseInt(chat);
    		}catch(NumberFormatException e){
    		    player.sendMessage("§c§l有効な数字を入力してください");
    		    return false;
    		}
    		int num = Integer.parseInt(chat);

    		try {
    			quest.getQuestByID(num);
    		} catch (ArrayIndexOutOfBoundsException e) {
    			player.sendMessage("§c§l指定されたIDのクエストは存在しません");
    		    return false;
    		}

    		menu.setMenuNumber(player, 5);
    		new Main().addSound(player, new AnvilUseSound(text.Monitor));
    		Quest newquest = quest.getQuestByID(num);

    		String armor = "";
    		if(newquest.getQuest_Armor()){
    			armor = "なし";
    		}else{
    			armor = "あり";
    		}

    		String difficult = "";
    		for(int i = 0; i < newquest.getQuest_Difficulty(); i++){
    			difficult = difficult + "✪";
    		}

    		String menutext = "§l>>§r§eこのクエストを受注しますか？§r§l<<§r\n" +
    					  "クエスト名:§6§l" + newquest.getQuest_Name() + "§r\n" +
    					  "ターゲット:§b§l" + newquest.getQuest_Target() + "§r\n" +
    					  "フィールド:§b§l" + newquest.getQuest_Field_Name() + "§r\n" +
    					  "最大人数:§b§l" + newquest.getQuest_Max() + "§r\n" +
    					  "装備:§b§l" + armor + "§r\n" +
    					  "難易度:§b§l" + difficult + "§r\n" +
    					  " §cNEXTで戻る§r/§aSELECTで受注";
    		text.addText(player, menutext, text.Monitor);
    		setTemporaryQuest(player, newquest);
    		setStatus(player, "Normal");
    	}
    	return false;
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


    public void setStatus(Player player, String status){

    	/*プレイヤーの状態をセットする
    	 * "WaitingQuestID" => クエストIDの発言待ち
    	 * "Normal" => 通常状態
    	 * "CreateRoom" => ルームに参加中(ホスト)
    	 */

		PlayerStatus.put(player.getName(), status);

    }


    public String getStatus(Player player){

    	return PlayerStatus.get(player.getName());

    }


    public void setTemporaryQuest(Player player, Quest quest){//temporaryは仮という意味

    	//プレイヤーがQuestIDを入力->ルームに移動までクエストを保存しておく必要が有るため

		TemporaryQuest.put(player.getName(), quest);

    }


    public Quest getTemporaryQuest(Player player){

    	return TemporaryQuest.get(player.getName());

    }


    public void removeTemporaryQuest(Player player){

    	TemporaryQuest.remove(player.getName());

    }

}