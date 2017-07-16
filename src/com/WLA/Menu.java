package com.WLA;

import java.util.HashMap;

import cn.nukkit.Player;
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

		switch(num){//初期画面(Join時のまま)
			case 1 :
				setMenuNumber(player, 2);
				text.addText(player, "§l>>§6クエストカウンター§r§l<<\n§r§7クエストの受注\n§aクエストに参加", pos);
				new Main().addSound(player, new ClickSound(pos));
				break;
			case 2 :
				setMenuNumber(player, 1);
				text.addText(player, "§l>>§6クエストカウンター§r§l<<\n§r§aクエストの受注\n§7クエストに参加", pos);
				new Main().addSound(player, new ClickSound(pos));
				break;
		}

	}

}
