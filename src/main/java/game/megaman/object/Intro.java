package game.megaman.object;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.megaman.userinterface.GameFrame;
public class Intro {
	private int alpha=0;
	private BufferedImage image;
	private boolean check=false;
	private BufferedImage bufferedImage;
	private GameWorld gameWorld;
	public String path="data/intro.jpg";
	public Intro(GameWorld gameWorld){
		this.gameWorld=gameWorld;
		try {
			image=ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//bufferedImage=new BufferedImage(GameFrame.SCREEN_WIDTH,GameFrame.SCREEN_HEIGHT,BufferedImage.TYPE_INT_RGB);
	}
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	public boolean check() {
		if(alpha==0&&check)   return true;
		else    return false;
	}
	public void update() {
		if(!check) {
			alpha+=2;
			if(alpha>=230)    check=true;
		}
		else {
			alpha=Math.max(alpha-1, 0);
		}
	}
	public void render(Graphics2D g2) {
		//Graphics2D g2=(Graphics2D)bufferedImage.getGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, 1000, 600);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)alpha/255));
		g2.drawImage(image, 0, 0,GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT,null);
	}
}
