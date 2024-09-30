package game.megaman.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.megaman.userinterface.GameFrame;

public class BoundManager {
	private ArrayList<Bound> bounds;
	private int index;
	private BufferedImage bufferedImage;
	private GameWorld gameWorld;
	public BoundManager(GameWorld gameWorld){
		this.gameWorld=gameWorld;
		index=0;
		bounds=new ArrayList<Bound>();
		bufferedImage=new BufferedImage(GameFrame.SCREEN_WIDTH,GameFrame.SCREEN_HEIGHT,BufferedImage.TYPE_INT_RGB);
		Bound play=new Bound(300,100);
		play.setText("Play");
		play.setColor(new Color(255,0,0,128));
		play.setActive(true);
		bounds.add(play);
		Bound newGame=new Bound(300,250);
		newGame.setText("New game");
		newGame.setColor(new Color(0,128,0,128));
		bounds.add(newGame);
		Bound about=new Bound(300,400);
		about.setText("About");
		about.setColor(new Color(0,0,255,128));
		bounds.add(about);
	}
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	public void updateLen() {
		index++;
		if(index>=bounds.size())  index=0;
	    bounds.get(index).setActive(true);
	    if(index==0)     bounds.get(bounds.size()-1).setActive(false);
	    else    bounds.get(index-1).setActive(false);
	}
	public void updateXuong() {
		index--;
		if(index<0)  index=bounds.size()-1;
	    bounds.get(index).setActive(true);
	    if(index==bounds.size()-1)     bounds.get(0).setActive(false);
	    else    bounds.get(index+1).setActive(false);
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void render(Graphics2D g2) {
		//Graphics2D g2=(Graphics2D) bufferedImage.getGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, 1000, 600);
		for(int i=0;i<bounds.size();i++) {
			Bound bound=bounds.get(i);
			bound.render(g2);
		}
	}
}
