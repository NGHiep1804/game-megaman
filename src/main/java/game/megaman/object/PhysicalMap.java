package game.megaman.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.megaman.effect.CacheDataLoader;

public class PhysicalMap extends GameObject {
	public int[][] physicalMap;
	private int titleSize;
	public PhysicalMap(float x,float y,GameWorld gameWorld) {
		super(x,y,gameWorld);
		this.titleSize=30;
		physicalMap=CacheDataLoader.getInstance().getPhysiMap();
	}
	public int getTitleSize() {
		return titleSize;
	}
	public Rectangle haveCollisionWithLand(Rectangle rect) {
		int posX1=rect.x/titleSize;
		posX1-=2;
		int posX2=(rect.x+rect.width)/titleSize;
		posX2+=2;
		int posY1=(rect.y+rect.height)/titleSize;
		if(posX1<0)   posX1=0;
		if(posX2>=physicalMap[0].length)  posX2=physicalMap[0].length-1;
		for(int y=posY1 ;y<physicalMap.length;y++) {
			for(int x=posX1;x<=posX2;x++) {
				if(physicalMap[y][x]==1) {
					Rectangle r=new Rectangle((int)getPosX()+x*titleSize,(int)getPosY()+y*titleSize,titleSize,titleSize);
					if(rect.intersects(r)) {
						return r;
					}
				}
			}
		}
		return null;
	}
	public Rectangle haveCollisionWithTop(Rectangle rect) {
		int posX1=rect.x/titleSize;
		posX1-=2;
		int posX2=(rect.x+rect.width)/titleSize;
		posX2+=2;
		int posY=rect.y/titleSize;
		if(posX1<0)  posX1=0;
		if(posX2>=physicalMap[0].length)  posX2=physicalMap[0].length-1;
		for(int y=posY;y>=0;y--) {
			for(int x=posX1;x<=posX2;x++) {
				if(physicalMap[y][x]==1) {
					Rectangle r=new Rectangle((int)getPosX()+x*titleSize,(int)getPosY()+y*titleSize,titleSize,titleSize);
					if(rect.intersects(r))   return r;
				}
			}
		}
		return null;
	}
	public Rectangle haveCollisionWithRightWall(Rectangle rect) {
		int posY1=rect.y/titleSize;
		posY1-=2;
		int posY2=(rect.y+rect.height)/titleSize;
		posY2+=2;
		int posX1=(rect.x+rect.width)/titleSize;
		int posX2=posX1+3;
		if(posY1<0)  posY1=0;
		if(posX2>=physicalMap[0].length)  posX2=physicalMap[0].length-1;
		if(posY2>=physicalMap.length)  posY2=physicalMap.length-1;
		for(int x=posX1;x<=posX2;x++) {
			for(int y=posY1;y<=posY2;y++) {
				if(physicalMap[y][x]==1) {
					Rectangle r=new Rectangle((int)getPosX()+x*titleSize,(int)getPosY()+y*titleSize,titleSize,titleSize);
					if(r.y<rect.y+rect.height-1&&rect.intersects(r)) {
						//System.out.println(r.x+" "+r.y+" "+r.width+" "+r.height);
						return r;
					}
				}
			}
		}
		return null;
	}
	public Rectangle haveCollisionWithLeftWall(Rectangle rect) {
		int posY1=rect.y/titleSize;
		posY1-=2;
		int posY2=(rect.y+rect.height)/titleSize;
		posY2+=2;
		int posX1=(rect.x+rect.width)/titleSize;
		int posX2=posX1-3;
		if(posY1<0)  posY1=0;
		if(posX2<0) posX2=0;
		if(posY2>=physicalMap.length)  posY2=physicalMap.length-1;
		for(int x=posX1;x>=posX2;x--) {
			for(int y=posY1;y<=posY2;y++) {
				if(physicalMap[y][x]==1) {
					Rectangle r=new Rectangle((int)getPosX()+x*titleSize,(int)getPosY()+y*titleSize,titleSize,titleSize);
					if(r.y<rect.y+rect.height-1&&rect.intersects(r)) {
					   	//System.out.println(r.x+" "+r.y+" "+r.width+" "+r.height);
						return r;
					}
				}
			}
		}
		return null;
	}
	@Override
	public void update() {
		
	}
}
