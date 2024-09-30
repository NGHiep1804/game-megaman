package game.megaman.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.megaman.effect.Animation;
import game.megaman.effect.CacheDataLoader;

public class RedEyeBullet extends Bullet{
	private Animation forwardBulletAnimation,backBulletAnimation;
	public RedEyeBullet(float x, float y, GameWorld gameWorld) {
		super(x, y,30,30,1.0f,10, gameWorld);
		forwardBulletAnimation=CacheDataLoader.getInstance().getAnimation("redeyebullet");
		backBulletAnimation=CacheDataLoader.getInstance().getAnimation("redeyebullet");
		backBulletAnimation.flipAllImage();
	}

	@Override
	public void draw(Graphics2D g2) {
		if(getSpeedX()>0) {
			forwardBulletAnimation.update(System.nanoTime());
			forwardBulletAnimation.draw((int)(getPosX()-getGameWorld().camera.getPosX()),(int)getPosY()-(int)getGameWorld().camera.getPosY(), g2);
		}
		else {
			backBulletAnimation.update(System.nanoTime());
			backBulletAnimation.draw((int)(getPosX()-getGameWorld().camera.getPosX()),(int)getPosY()-(int)getGameWorld().camera.getPosY(), g2);
		}
	}
	@Override
	public void update() {
		super.update();
	}
	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		// TODO Auto-generated method stub
		return getBoundForCollisionWithMap();
	}
	
}
