package game.megaman.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.megaman.effect.Animation;
import game.megaman.effect.CacheDataLoader;

public class RocketBullet extends Bullet{
	private Animation forwardBulletAnimationUp,forwardBulletAnimationDown,forwardBulletAnimation;
	private Animation backBulletAnimationUp,backBulletAnimationDown,backBulletAnimation;
	private long startTimeForChangeSpeedY;
	public RocketBullet(float x, float y, GameWorld gameWorld) {
		super(x, y, 30,30,1.0f,10, gameWorld);
		backBulletAnimationUp=CacheDataLoader.getInstance().getAnimation("rocketUp");
		backBulletAnimationDown=CacheDataLoader.getInstance().getAnimation("rocketDown");
		backBulletAnimation=CacheDataLoader.getInstance().getAnimation("rocket");
		forwardBulletAnimationUp=CacheDataLoader.getInstance().getAnimation("rocketUp");
		forwardBulletAnimationDown=CacheDataLoader.getInstance().getAnimation("rocketDown");
		forwardBulletAnimation=CacheDataLoader.getInstance().getAnimation("rocket");
		forwardBulletAnimation.flipAllImage();
		forwardBulletAnimationUp.flipAllImage();
		forwardBulletAnimationDown.flipAllImage();
	}

	@Override
	public void draw(Graphics2D g2) {
		if(getSpeedX()>0) {
			if(getSpeedY()>0) {
				forwardBulletAnimationDown.draw((int)(getPosX()-getGameWorld().camera.getPosX()), (int)getPosY()-(int)getGameWorld().camera.getPosY(), g2);
			}
			else if(getSpeedY()<0) {
				forwardBulletAnimationUp.draw((int)(getPosX()-getGameWorld().camera.getPosX()), (int)getPosY()-(int)getGameWorld().camera.getPosY(), g2);
			}
			else {
				forwardBulletAnimation.draw((int)(getPosX()-getGameWorld().camera.getPosX()), (int)getPosY()-(int)getGameWorld().camera.getPosY(), g2);
			}
		}
		else {
			if(getSpeedY()>0) {
				backBulletAnimationDown.draw((int)(getPosX()-getGameWorld().camera.getPosX()), (int)getPosY()-(int)getGameWorld().camera.getPosY(), g2);
			}
			else if(getSpeedY()<0) {
				backBulletAnimationUp.draw((int)(getPosX()-getGameWorld().camera.getPosX()), (int)getPosY()-(int)getGameWorld().camera.getPosY(), g2);
			}
			else {
				backBulletAnimation.draw((int)(getPosX()-getGameWorld().camera.getPosX()), (int)getPosY()-(int)getGameWorld().camera.getPosY(), g2);
			}
		}
	}
	private void changeSpeedY() {
		if(System.currentTimeMillis()%3==0)    setSpeedY(getSpeedX());
		else if(System.currentTimeMillis()%3==1)    setSpeedY(-getSpeedX());
		else   setSpeedY(0);
	}
	@Override
	public void update() {
		super.update();
		if(System.nanoTime()-startTimeForChangeSpeedY>500*1000000) {
			startTimeForChangeSpeedY=System.nanoTime();
			changeSpeedY();
		}
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		return getBoundForCollisionWithMap();
	}

}
