package com.WLA;

import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;

public class Text {

	Vector3 Monitor = new Vector3(308.5, 5.5, 298.5); //モニターの座標
	Vector3 Next = new Vector3(308.5, 5, 296.5); //NEXTの文字の座標
	Vector3 Select = new Vector3(308.5, 5, 300.5); //SELECTの〃

	HashMap<String, HashMap<Vector3, Long>>Parent = new HashMap<String, HashMap<Vector3, Long>>();
	//プレイヤーの名前(String型)をキーにして中身にさらに配列(多次元配列) 中身は座標(Vector3型)をキーにして値はEntityID(Long型)

	public void addText(Player player, String text, Vector3 pos){//FloatingTextを追加する関数

		HashMap<Vector3, Long>child = Parent.get(player.getName());
        if(child.containsKey(pos)){//座標のキーが存在するか？
        	//存在する場合、同じ座標にTextが被ってはいけないため、もともとあったEIDを使用して上書き
        	Long eid = child.get(pos);
            setNPC(player, eid, text, pos);
        }else{
        	//存在しない場合、ParentにHashMap(pos, eid)を挿入

            Entity.entityCount++;
            long eid = Entity.entityCount;

            child.put(pos, eid);
            Parent.put(player.getName(), child);

            setNPC(player, eid, text, pos);
        }

	}


	public void removeText(Player player, Vector3 pos){

		HashMap<Vector3, Long> child = Parent.get(player.getName());
		if(child.containsKey(pos)){//本当に引数のposはParentに存在しているか？
			Long eid = child.get(pos);
			RemoveEntityPacket pk = new RemoveEntityPacket();
			pk.eid = eid;
			player.dataPacket(pk);
			child.remove(pos);
			Parent.put(player.getName(), child);//posを削除したchildをParentに挿入
		}

	}


	public void setNPC(Player player, long eid, String text, Vector3 pos){//透明のネームタグだけ見えるEntityをPacketで召喚
		AddEntityPacket pk = new AddEntityPacket();
		pk.entityRuntimeId = eid;
		pk.entityUniqueId = eid;
		pk.type = EntityItem.NETWORK_ID;
		pk.x = (float)pos.x;
		pk.y = (float)pos.y;
		pk.z = (float)pos.z;
		pk.speedX = 0;
		pk.speedY = 0;
		pk.speedZ = 0;
		pk.yaw = 0;
		pk.pitch = 0;

		int flags = 0;
		flags |= 1 << Entity.DATA_FLAG_CAN_SHOW_NAMETAG;
		flags |= 1 << Entity.DATA_FLAG_ALWAYS_SHOW_NAMETAG;
		flags |= 1 << Entity.DATA_FLAG_IMMOBILE;

		pk.metadata = new EntityMetadata()
			.putLong(Entity.DATA_FLAGS, flags)
			.putLong(Entity.DATA_LEAD_HOLDER_EID, -1)
			.putString(Entity.DATA_NAMETAG, text);

		player.dataPacket(pk);
	}


	public void firstProcess(Player player){//Join時にMonitor,Next,Selectの浮遊文字を出現させる

		HashMap<Vector3, Long>child = new HashMap<Vector3, Long>();

		Entity.entityCount++;
        long eid1 = Entity.entityCount;

        Entity.entityCount++;
        long eid2 = Entity.entityCount;

        Entity.entityCount++;
        long eid3 = Entity.entityCount;

        child.put(Monitor, eid1);
        child.put(Next, eid2);
        child.put(Select, eid3);

        Parent.put(player.getName(), child);

        setNPC(player, eid1, "§l>>§6§lクエストカウンター§r§l<<\n§r§aクエストの受注\n§r§7クエストに参加", new Vector3((float)Monitor.x, (float)Monitor.y, (float)Monitor.z));
        setNPC(player, eid2, "§e§lSELECT", new Vector3((float)Select.x, (float)Select.y, (float)Select.z));
        setNPC(player, eid3, "§b§lNEXT", new Vector3((float)Next.x, (float)Next.y, (float)Next.z));
	}

}
