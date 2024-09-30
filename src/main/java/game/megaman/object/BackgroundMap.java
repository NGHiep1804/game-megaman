package game.megaman.object;

import java.awt.Color;
import java.awt.Graphics2D;

import game.megaman.effect.CacheDataLoader;
import game.megaman.userinterface.GameFrame;



public class BackgroundMap extends GameObject{
	public int[][] map;
	private int titleSize;
	public BackgroundMap(float posX, float posY, GameWorld gameWorld) {
		super(posX, posY, gameWorld);
		map=CacheDataLoader.getInstance().getBackgroundMap();
		titleSize=30;
	}

	@Override
	public void update() {
		
	}
	public void draw(Graphics2D g2) {
		Camera camera=getGameWorld().camera;
		g2.setColor(Color.red);
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map[0].length;j++) {
				if(map[i][j]!=0 && j*titleSize-camera.getPosX()>-30 && j*titleSize-camera.getPosX()<GameFrame.SCREEN_WIDTH
						&& i*titleSize-camera.getPosY()>-30 && i*titleSize-camera.getPosY()<GameFrame.SCREEN_HEIGHT){
					g2.drawImage(CacheDataLoader.getInstance().getFrameImage("tiled"+map[i][j]).getImage(),(int)getPosX()+j*titleSize-(int)camera.getPosX(),
						(int)getPosY()+i*titleSize-(int)camera.getPosY(),null);
				}
			}
		}
	}
}