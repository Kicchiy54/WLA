package com.WLA.Quest;

public class Quest_1{

	public String Quest_Name = "ジャイアント討伐演習！";
	public String Quest_Target = "ジャイアント1体の討伐";
	public String Quest_Field_Name = "闘技場";

	public Integer Quest_ID = 1;
	public Integer Quest_Type = 1;
	public Integer Quest_Field_ID = 1;
	public Integer Quest_Max = 3;
	public Integer Quest_Difficulty = 3;

	public Boolean Quest_Armor = false;

	public Quest get(){
		Quest quest = new Quest().getQuest(this.Quest_Name, this.Quest_Target, this.Quest_Field_Name, this.Quest_ID, this.Quest_Type, this.Quest_Field_ID, this.Quest_Max, this.Quest_Difficulty, this.Quest_Armor);
		//System.out.println(quest.getQuest_Name());
		return quest;
	}

}