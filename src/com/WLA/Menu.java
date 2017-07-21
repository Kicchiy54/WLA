package com.WLA;

import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.level.sound.AnvilBreakSound;
import cn.nukkit.level.sound.AnvilFallSound;
import cn.nukkit.level.sound.AnvilUseSound;
import cn.nukkit.level.sound.ClickSound;
import cn.nukkit.math.Vector3;

import com.WLA.Quest.Quest;

public class Menu{

	Text text = new Text();

	static HashMap<String, Integer>MenuNumber = new HashMap<String, Integer>();
	static HashMap<String, Integer>SelectRoom = new HashMap<String, Integer>();//ルーム一覧の時、いまどのページを見ているか？


	public int getMenuNumber(Player player){//今どこのメニューをひらいてるか？

		return MenuNumber.get(player.getName());

	}


	public void setMenuNumber(Player player, int num){//メニューナンバーをセット

		MenuNumber.put(player.getName(), num);

	}


	public void nextMenu(Player player, int num){//メニューを次に進める

		Vector3 pos = text.Monitor;//モニターの座標

		switch(num){

			case 1:
				setMenuNumber(player, 2);
				text.addText(player, "§l>>§6クエストカウンター§r§l<<\n§r§7クエストの受注\n§aクエストに参加", pos);
				new Main().addSound(player, new ClickSound(pos));
				break;

			case 2:
				setMenuNumber(player, 1);
				text.addText(player, "§l>>§6クエストカウンター§r§l<<\n§r§aクエストの受注\n§7クエストに参加", pos);
				new Main().addSound(player, new ClickSound(pos));
				break;

			case 4:
				setShowRooms(player);
				new Main().addSound(player, new ClickSound(pos));
				break;

			case 5:
				setMenuNumber(player, 1);
				text.addText(player, "§l>>§6クエストカウンター§r§l<<\n§r§aクエストの受注\n§7クエストに参加", pos);
				new Main().addSound(player, new AnvilFallSound(pos));
				new Main().removeTemporaryQuest(player);
				break;
		}

	}


	public void selectMenu(Player player, int num){//メニューを選択する

		Vector3 pos = text.Monitor;//モニターの座標

		switch(num){

			case 1:
				setMenuNumber(player, 3);
				text.addText(player, "§l>>§eクエストの受注§r§l<<\n§r受注するクエストの§aID§rを\n" +
						"§cチャットで§r発言してください\n" + "§a※SELECTでトップメニューへ", pos);
				new Main().addSound(player, new ClickSound(pos));
				new Main().setStatus(player, "WaitingQuestID");
				break;

			case 2:
				setShowRooms(player);
				new Main().addSound(player, new ClickSound(pos));
				setMenuNumber(player, 4);
				break;

			case 3:
				setMenuNumber(player, 1);
				text.addText(player, "§l>>§6クエストカウンター§r§l<<\n§r§aクエストの受注\n§7クエストに参加", pos);
				new Main().addSound(player, new AnvilFallSound(pos));
				new Main().setStatus(player, "Normal");
				break;

			case 4:
				if(SelectRoom.get(player.getName()) == 16){
					setMenuNumber(player, 1);
					text.addText(player, "§l>>§6クエストカウンター§r§l<<\n§r§aクエストの受注\n§7クエストに参加", pos);
					new Main().addSound(player, new AnvilFallSound(pos));
					new Main().setStatus(player, "Normal");
					break;
				}else{
					setMenuNumber(player, 1);
					text.addText(player, "§l>>§6クエストカウンター§r§l<<\n§r§aクエストの受注\n§7クエストに参加", pos);
					new Main().addSound(player, new AnvilFallSound(pos));
					new Main().setStatus(player, "Normal");
					SelectRoom.remove(player.getName());
					player.sendMessage(SelectRoom.get(player.getName()) + "に参加");
					break;
				}

			case 5:
				Quest quest = new Main().getTemporaryQuest(player);
				int RoomID = new Room().findRoom();
				if(RoomID == 0){
					player.sendMessage("§c§lルームの空きがありません。後ほどお試し下さい");
					setMenuNumber(player, 1);
					text.addText(player, "§l>>§6クエストカウンター§r§l<<\n§r§aクエストの受注\n§7クエストに参加", pos);
					new Main().addSound(player, new AnvilBreakSound(pos));
					new Main().setStatus(player, "Normal");
					break;
				}
				new Room().createRoom(player, RoomID, quest);
				player.sendTitle("\n§b§lRoom" + RoomID + " を作成しました\n§rQuest:§6§l" + quest.getQuest_Name());
				setMenuNumber(player, 4);
				text.allRemove(player);
				new Main().addSound(player, new AnvilUseSound(pos));
				new Main().setStatus(player, "CreateRoom");
				break;

		}

	}


	public void setShowRooms(Player player){

		String name = player.getName();

		if(!SelectRoom.containsKey(name)){
			SelectRoom.put(name, 0);
		}
		if(SelectRoom.get(name) == 16){
			SelectRoom.put(name, 1);
		}else{
			SelectRoom.put(name, SelectRoom.get(name) + 1);
		}

		int RoomID = SelectRoom.get(name);
		if(RoomID != 16){
			String str = "";

			if(Room.RoomHostName.containsKey(RoomID)){
				String players;
				if(Room.RoomPlayers.get(RoomID).length >= Room.RoomQuest.get(RoomID).getQuest_Max()){
					players = "§c" + Room.RoomPlayers.get(RoomID).length + "/" + Room.RoomPlayers.get(RoomID).length;
				}else{
					players = "§a" + Room.RoomPlayers.get(RoomID).length + "/" + Room.RoomQuest.get(RoomID).getQuest_Max();
				}
				if(!Room.RoomStatus.get(RoomID)){
					players = "§c現在参加不可能";
				}

				str = "§l>>§r§a§lRoom" + RoomID + "§r§l<<\n§rQUEST:§6§l" + Room.RoomQuest.get(RoomID).getQuest_Name() +
							 "\n§rHOST:§b§l" + Room.RoomHostName.get(RoomID) + "\n§rPLAYERS:" +
							 players + "\n§bNEXTで次のページ§r/§eSELECTで参加";
			}else{
				str = "§l>>§r§a§lRoom" + RoomID + "§r§l<<\n§r§7現在空室です";
			}

			text.addText(player, str, text.Monitor);
		}else{
			String str = "§l>>§aトップメニューへ戻る§r§l<<\n§b※NEXTで再度ルーム確認§r\n" +
						 "§e※SELECTでトップメニューへ";
			text.addText(player, str, text.Monitor);
		}
	}


}
