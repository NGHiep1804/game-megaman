package game.megaman.object;

import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.megaman.effect.Animation;
import game.megaman.effect.CacheDataLoader;

public class RedEyeDevil extends ParticularObject{
	private Animation forwardAnimation,backAnimation;
	private long startTimeToShoot;
	private AudioClip shooting;
	public RedEyeDevil(float x, float y, GameWorld gameWorld) {
		super(x, y,127,89,0,100, gameWorld);
		backAnimation=CacheDataLoader.getInstance().getAnimation("redeye");
		forwardAnimation=CacheDataLoader.getInstance().getAnimation("redeye");
		forwardAnimation.flipAllImage();
		startTimeToShoot=0;
		setDamage(10);
		setTimeForNoBeHurt(300*1000000);
		shooting=CacheDataLoader.getInstance().getSound("redeyeshooting");
	}

	@Override
	public void attack() {
		shooting.play();
		Bullet bullet= new RedEyeBullet(getPosX(),getPosY(),getGameWorld());
		if(getDirection()==LEFT_DIR)  bullet.setSpeedX(-8);
		else   bullet.setSpeedX(8);
		bullet.setTeamType(getTeamType());
		getGameWorld().bulletManager.addObject(bullet);
	}

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		Rectangle rect=getBoundForCollisionWithMap();
		rect.x+=20;
		rect.width-=40;
		return rect;
	}

	@Override
	public void draw(Graphics2D g2) {
		if(!isObjectOutOfCameraView()) {
			if(getState()==NOBEHURT && (System.nanoTime()/10000000)%2!=1) {
				
			}
			else {
				if(getDirection()==LEFT_DIR) {
					backAnimation.update(System.nanoTime());
					backAnimation.draw((int)(getPosX()-getGameWorld().camera.getPosX()),(int)(getPosY()-getGameWorld().camera.getPosY()), g2);
				}
				else {
					forwardAnimation.update(System.nanoTime());
					forwardAnimation.draw((int)(getPosX()-getGameWorld().camera.getPosX()),(int)(getPosY()-getGameWorld().camera.getPosY()), g2);
				}
			}
		}
	}
	@Override 
	public void update() {
		super.update();
		if(System.nanoTime()-startTimeToShoot>1000*1000000) {
			attack();
			startTimeToShoot=System.nanoTime();
		}
	}
}
