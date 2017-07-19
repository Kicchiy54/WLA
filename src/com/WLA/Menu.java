package com.WLA;

import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.level.sound.AnvilFallSound;
import cn.nukkit.level.sound.ClickSound;
import cn.nukkit.math.Vector3;

public class Menu{

	Text text = new Text();

	static HashMap<String, Integer>MenuNumber = new HashMap<String, Integer>();


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
				setMenuNumber(player, 4);
				text.addText(player, "§l>>§eクエストに参加§r§l<<\n§r§aNPC§rをタップして参加してください\n" +
						"§a※SELECTでトップメニューへ\n§a※NEXTでNPCを更新", pos);
				new Main().addSound(player, new ClickSound(pos));
				break;

			case 3:
			case 4:
				setMenuNumber(player, 1);
				text.addText(player, "§l>>§6クエストカウンター§r§l<<\n§r§aクエストの受注\n§7クエストに参加", pos);
				new Main().addSound(player, new AnvilFallSound(pos));
				break;
		}

	}

}
