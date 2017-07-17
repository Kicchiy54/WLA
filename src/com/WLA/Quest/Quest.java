package com.WLA.Quest;

public class Quest {

	/*
	 * Type = 1 討伐クエスト, 2 小型討伐, 3 アイテム納品クエスト
	 * Armor = true あり, false なし
	 * FieldID = 1 闘技場,
	 */

	String Quest_Name;
	String Quest_Target;
	String Quest_Field_Name;

	Integer Quest_ID;
	Integer Quest_Type;
	Integer Quest_Field_ID;
	Integer Quest_Max;
	Integer Quest_Difficulty;

	boolean Quest_Armor;


	public Quest getQuest(String Q_N, String Q_T, String Q_F_N, Integer Q_ID, Integer Q_Ty, Integer Q_F_ID, Integer Q_M, Integer Q_D, boolean Q_A){

		this.Quest_Name = Q_N;
		this.Quest_Target = Q_T;
		this.Quest_Field_Name = Q_F_N;
		this.Quest_ID = Q_ID;
		this.Quest_Type = Q_Ty;
		this.Quest_Field_ID = Q_F_ID;
		this.Quest_Max = Q_M;
		this.Quest_Difficulty = Q_D;
		this.Quest_Armor = Q_A;

		return this;

	}


	public Quest[] getAllQuests(){//ここはクエスト追加の際に必須 全てのクエストを取得

		Quest quests[] = new Quest[1];
		//①new Quest[x] のxをクエストとの数に変更

		Quest quest1 = new Quest_1().get();
		//②Quest questx = new Quest_x().get() と追記していく

		quests[0] = quest1;
		//③quests[1] = quest2....

		return quests;

	}


	public Quest getQuestByID(int ID){
		Quest[] quests = this.getAllQuests();
		for (int count = 0; count <= quests.length; count++) {
			if(quests[count] != null){
				if(quests[count].getQuest_ID() == ID){
					return quests[count];
				}
			}
		}
		return null;
	}


	public String getQuest_Name(){

		return this.Quest_Name;

	}


	public String getQuest_Target(){

		return this.Quest_Target;

	}


	public String getQuest_Field_Name(){

		return this.Quest_Field_Name;

	}


	public Integer getQuest_ID(){

		return this.Quest_ID;

	}


	public Integer getQuest_Type(){

		return this.Quest_Type;

	}


	public Integer getQuest_Field_ID(){

		return this.Quest_Field_ID;

	}


	public Integer getQuest_Max(){

		return this.Quest_Max;

	}


	public Integer getQuest_Difficulty(){

		return this.Quest_Difficulty;

	}


	public boolean getQuest_Armor(){

		return this.Quest_Armor;

	}


}