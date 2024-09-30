package game.megaman.object;

public class Camera extends GameObject {
	public Camera(float posX, float posY,float widthView,float heightView, GameWorld gameWorld) {
		super(posX, posY, gameWorld);
		this.widthView=widthView;
		this.heightView=heightView;
	}
	private float widthView;
	private float heightView;
	private boolean isLocked=false;
	@Override
	public void update() {
		if(!isLocked) {
			Megaman megaman=getGameWorld().megaman;
			if(megaman.getPosX()-getPosX()>400)    setPosX(megaman.getPosX()-400);
			if(megaman.getPosX()-getPosX()<200)    setPosX(megaman.getPosX()-200);
			if(getPosX()<0)   setPosX(0);
			if(megaman.getPosY()-getPosY()>400)     setPosY(megaman.getPosY()-400);
			else if(megaman.getPosY()-getPosY()<250)    setPosY(megaman.getPosY()-250);
		}
	}
	public void lock() {
		isLocked=true;
	}
	public void unlock() {
		isLocked=false; 
	}
	public float getWidthView() {
		return widthView;
	}
	public void setWidthView(float widthView) {
		this.widthView = widthView;
	}
	public float getHeightView() {
		return heightView;
	}
	public void setHeightView(float heightView) {
		this.heightView = heightView;
	}
}
