package com.WLA;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cn.nukkit.Player;
import cn.nukkit.entity.data.Skin;

public class PlayerSkin extends Thread{

	public int entireByteArray;
	public int start;
	public int x;
	public int y;
	public BufferedImage finalSkin;
	public byte[] byteArray;
	public String name;


	public void saveSkin(Player player){

		name = player.getName();

		File newdir = new File(".\\Skins\\" + player.getName() + ".png");
		newdir.mkdir();

		Skin skin = player.getSkin();
    	byteArray = skin.getData();
    	finalSkin = new BufferedImage(64, 32, BufferedImage.TYPE_INT_ARGB);
    	entireByteArray = byteArray.length;
    	if(entireByteArray >= 8192){
    		finalSkin = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
    	}
    	start = 0;
    	x = 0;
    	y = 0;

    	SubThread sub = new SubThread();
    	sub.start();

	}


	public class SubThread extends Thread {
	    public void run()
	    {
	    	while(entireByteArray > start){
	    		if(x == 64){
	    			x = 0;
	    			y++;
	    		}
	    		int r = (byteArray[start]) & 0xFF;
	    		int g = (byteArray[start + 1]) & 0xFF;
	    		int b = (byteArray[start + 2]) & 0xFF;
	    		int a = (byteArray[start + 3]) & 0xFF;

	    		finalSkin.setRGB(x,  y, new Color(r, g, b, a).getRGB());
	    		start = start + 4;
	    		x++;
	    	}

	    	try{
				ImageIO.write(finalSkin, "png", new File(".\\Skins\\" + name + ".png"));
			}catch(IOException e){
				System.out.println("スキンの保存に失敗しました(" + name + ")");
			}
	    }
	}

}
