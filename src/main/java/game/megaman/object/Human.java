package game.megaman.object;

import java.awt.Rectangle;

public abstract class Human extends ParticularObject{
	private boolean isJumping;
	private boolean isDicking;
	private boolean isLanding;
	public Human(float x, float y, float width, float height, float mass, int blood, GameWorld gameWorld) {
		super(x, y, width, height, mass, blood, gameWorld);
		setState(ALIVE);
	}
	public boolean isJumping() {
		return isJumping;
	}
	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}
	public boolean isDicking() {
		return isDicking;
	}
	public void setDicking(boolean isDicking) {
		this.isDicking = isDicking;
	}
	public boolean isLanding() {
		return isLanding;
	}
	public void setLanding(boolean isLanding) {
		this.isLanding = isLanding;
	}
	public abstract void run();
	public abstract void jump();
	public abstract void dick();
	public abstract void standUp();
	public abstract void stopRun();
	@Override
	public void update() {
		super.update();
		if(getState()==ALIVE||getState()==NOBEHURT) {
			if(!isLanding) {
				setPosX(getPosX()+getSpeedX());
				if(getDirection()==LEFT_DIR&&getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap())!=null) {
					Rectangle rectLeftWall=getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap());
					setPosX(rectLeftWall.x+rectLeftWall.width+getWidth()/2);
				}
				if(getDirection()==RIGHT_DIR&&getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap())!=null) {
					Rectangle rectRightWall=getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap());
					setPosX(rectRightWall.x-getWidth()/2);
				}
				Rectangle boundForCollisionWithMapFuture=getBoundForCollisionWithMap();
				boundForCollisionWithMapFuture.y+=(getSpeedY()!=0?getSpeedY():2);
				Rectangle rectTop=getGameWorld().physicalMap.haveCollisionWithTop(boundForCollisionWithMapFuture);
				Rectangle rectLand=getGameWorld().physicalMap.haveCollisionWithLand(boundForCollisionWithMapFuture);
				if(rectTop!=null) {
					setSpeedY(0);
					setPosY(rectTop.y+getGameWorld().physicalMap.getTitleSize()+getHeight()/2);
				}
				else if(rectLand!=null) {
					setJumping(false);
					if(getSpeedY()>0)   setLanding(true);
					setSpeedY(0);
					setPosY(rectLand.y-getHeight()/2);
				}
				else {
					setJumping(true);
					setSpeedY(getSpeedY()+getMass());
					setPosY(getPosY()+getSpeedY());
				}
			}
		}
	}
}
