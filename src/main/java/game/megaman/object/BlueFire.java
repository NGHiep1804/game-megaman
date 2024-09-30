package game.megaman.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.megaman.effect.Animation;
import game.megaman.effect.CacheDataLoader;


public class BlueFire extends Bullet{
	private Animation forwardBulletAnimation,backBulletAnimation;
	public BlueFire(float x, float y, GameWorld gameWorld) {
		super(x, y,60,30,1.0f,10, gameWorld);
		forwardBulletAnimation=CacheDataLoader.getInstance().getAnimation("bluefire");
		backBulletAnimation=CacheDataLoader.getInstance().getAnimation("bluefire");
		backBulletAnimation.flipAllImage();
	}

	@Override
	public void draw(Graphics2D g2) {
		if(getSpeedX() > 0){
            if(!forwardBulletAnimation.isIgnoreFrame(0) && forwardBulletAnimation.getCurrentFrame() == 3){
                forwardBulletAnimation.setIgnoreFrame(0);
                forwardBulletAnimation.setIgnoreFrame(1);
                forwardBulletAnimation.setIgnoreFrame(2);
            }
                
            forwardBulletAnimation.update(System.nanoTime());
            forwardBulletAnimation.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }else{
            if(!backBulletAnimation.isIgnoreFrame(0) && backBulletAnimation.getCurrentFrame() == 3){
                backBulletAnimation.setIgnoreFrame(0);
                backBulletAnimation.setIgnoreFrame(1);
                backBulletAnimation.setIgnoreFrame(2);
            }
            backBulletAnimation.update(System.nanoTime());
            backBulletAnimation.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
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
	@Override
	public void update() {
		if(forwardBulletAnimation.isIgnoreFrame(0)||backBulletAnimation.isIgnoreFrame(0))   setPosX(getPosX()+getSpeedX());
		super.update();
	}

}
