package com.WLA;

import java.util.HashMap;

import cn.nukkit.Player;

import com.WLA.Quest.Quest;

public class Room {

	static HashMap<Integer, String> RoomHostName= new HashMap<Integer, String>();//RoomIDをキーにしてホストの名前取得
	static HashMap<Integer, Quest> RoomQuest = new HashMap<Integer, Quest>();//〃クエストを取得
	static HashMap<Integer, Player[]> RoomPlayers = new HashMap<Integer, Player[]>();//〃ルームに参加している人を取得
	static HashMap<Integer, Boolean> RoomStatus = new HashMap<Integer, Boolean>();//〃ルームに参加できるか(クエストに出発済みの時に参加してはだめなため)


	public int findRoom(){

		for(int i = 1; i <= 15; i++){
			if(!RoomHostName.containsKey(i)){
				return i;
			}
		}
		return 0;

	}


	public void createRoom(Player player, int RoomID, Quest quest){
		RoomHostName.put(RoomID, player.getName());
		RoomQuest.put(RoomID, quest);
		RoomPlayers.put(RoomID, new Player[]{player});
		RoomStatus.put(RoomID, true);
	}

}
