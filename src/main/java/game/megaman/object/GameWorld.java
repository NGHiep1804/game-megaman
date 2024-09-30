package game.megaman.object;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.megaman.effect.CacheDataLoader;
import game.megaman.effect.FrameImage;
import game.megaman.userinterface.GameFrame;



public class GameWorld {	
	private BufferedImage bufferedImage;
	public Megaman megaman;
	public PhysicalMap physicalMap;
	public BackgroundMap backgroundMap;
	public Camera camera;
	public BulletManager bulletManager;
	public ParticularObjectManager particularObjectManager;
	public BoundManager boundManager;
	public Intro intro;
	//public AudioClip bgMusic;
	public static final int FINAL_BOSS_X=3600;
	
	public static final int PAUSEGAME=0;
	public static final int TUTORIAL=1;
	public static final int GAMEPLAY=2;
	public static final int GAMEOVER=3;
	public static final int GAMEWIN=4;
	
	public static final int INTROGAME=0;
	public static final int MEETFINALBOSS=1;
	
	public int openIntroGameY=0;
	public int state=PAUSEGAME;
	public int previousState=state;
	public int tutorialState=INTROGAME;
	private int demGameWin=0;
	private int demGameOver=0;
	private boolean finalbossTrigger = true;
    ParticularObject boss;
	
	public FrameImage avatar=CacheDataLoader.getInstance().getFrameImage("avatar");
	private int numberOfLife=0;
	public GameWorld() {
		bufferedImage=new BufferedImage(GameFrame.SCREEN_WIDTH,GameFrame.SCREEN_HEIGHT,BufferedImage.TYPE_INT_BGR);
		setUp();
		//bgMusic=CacheDataLoader.getInstance().getSound("bgmusic");
	}
	public void setUp() {
		megaman=new Megaman(300,300,this);
		physicalMap=new PhysicalMap(0,0,this);
		camera=new Camera(0,0,GameFrame.SCREEN_WIDTH,GameFrame.SCREEN_HEIGHT,this);
		bulletManager=new BulletManager(this);
		backgroundMap=new BackgroundMap(0,0,this);
		particularObjectManager=new ParticularObjectManager(this);
		particularObjectManager.addObject(megaman);
		intro=new Intro(this);
		boundManager=new BoundManager(this);
		initEnemies();
	}
	private void initEnemies() {
		ParticularObject redeye = new RedEyeDevil(1250, 410, this);
        redeye.setDirection(ParticularObject.LEFT_DIR);
        redeye.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye);        
        
        ParticularObject redeye2 = new RedEyeDevil(2500, 500, this);
        redeye2.setDirection(ParticularObject.LEFT_DIR);
        redeye2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye2);
        
        ParticularObject redeye3 = new RedEyeDevil(3450, 500, this);
        redeye3.setDirection(ParticularObject.LEFT_DIR);
        redeye3.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye3);
        
        ParticularObject redeye4 = new RedEyeDevil(500, 1190, this);
        redeye4.setDirection(ParticularObject.RIGHT_DIR);
        redeye4.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye4);
        
	}
	public void update() {
		switch(state) {
			case PAUSEGAME:
				intro.update();
				if(intro.check())      switchState(TUTORIAL);
				break;
			case TUTORIAL:
				tutorialUpdate();
				break;
			case GAMEPLAY:
				particularObjectManager.updateObjects();
				bulletManager.updateObjects();
				physicalMap.update();
				camera.update();
				if(megaman.getPosX()>FINAL_BOSS_X&&finalbossTrigger) {
					finalbossTrigger=false;
					switchState(TUTORIAL);
					tutorialState=MEETFINALBOSS;
					openIntroGameY=550;
					boss=new FinalBoss(FINAL_BOSS_X+700,460,this);
					boss.setTeamType(ParticularObject.ENEMY_TEAM);
					boss.setDirection(ParticularObject.LEFT_DIR);
					particularObjectManager.addObject(boss);
				}
				if(megaman.getState()==ParticularObject.DEATH) {
					numberOfLife--;
					if(numberOfLife>=0) {
						megaman.setBlood(100);
						megaman.setPosY(megaman.getPosY()-50);
						megaman.setState(ParticularObject.NOBEHURT);
						particularObjectManager.addObject(megaman);
					}
					else {
						switchState(GAMEOVER);
						//bgMusic.stop();
					}
				}
				if(!finalbossTrigger&&boss.getState()==ParticularObject.DEATH)
					switchState(GAMEWIN);
				break;
			case GAMEOVER:
				if(demGameOver>100) {
					switchState(TUTORIAL);
					tutorialState=INTROGAME;
					reset();
					demGameOver=0;
				}
				else      demGameOver++;
				break;
			case GAMEWIN:
				if(demGameWin>100) {
					switchState(TUTORIAL);
					tutorialState=INTROGAME;
					reset();
					demGameWin=0;
				}
				else     demGameWin++;
				break;
		}
	}
	public void render() {
		Graphics2D g2=(Graphics2D) bufferedImage.getGraphics();
		if(g2!=null) {
			switch(state) {
				case PAUSEGAME:
					intro.render(g2);
					break;
				 case TUTORIAL:
	                    backgroundMap.draw(g2);
	                    if(tutorialState == MEETFINALBOSS){
	                        particularObjectManager.draw(g2);
	                    }
	                    tutorialRender(g2);
	                    
	                    break;
	            case GAMEWIN:
				case GAMEPLAY:
					backgroundMap.draw(g2);
					particularObjectManager.draw(g2);
					bulletManager.draw(g2);
					g2.setColor(Color.gray);
					g2.fillRect(19, 59, 102, 22);
					g2.setColor(Color.red);
					g2.fillRect(20, 60, megaman.getBlood(),20);
					for(int i=0;i<numberOfLife;i++) {
						g2.drawImage(CacheDataLoader.getInstance().getFrameImage("hearth").getImage(),20+i*40,18, null);
					}
					if(state == GAMEWIN){
                        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("gamewin").getImage(), 300, 300, null);
                    }
					break;
				case GAMEOVER:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("GAME OVER!", 450, 300);
                    break;
			}
		}
	}
	public void switchState(int state) {
		previousState=this.state;
		this.state=state;
	}
	private void tutorialUpdate() {
		switch(tutorialState) {
			case INTROGAME:
				break;
			case MEETFINALBOSS:
				if(openIntroGameY>=450)   openIntroGameY--;
				if(camera.getPosX()<FINAL_BOSS_X)    camera.setPosX(camera.getPosX()+2);
				if(megaman.getPosX()<FINAL_BOSS_X+150) {
					megaman.setDirection(ParticularObject.RIGHT_DIR);
					megaman.run();
					megaman.update();
				}
				else    megaman.stopRun();
				if(openIntroGameY<450&&camera.getPosX()>=FINAL_BOSS_X&&megaman.getPosX()>=FINAL_BOSS_X+150) {
					camera.lock();
					megaman.stopRun();
					physicalMap.physicalMap[14][120]=1;
					physicalMap.physicalMap[15][120]=1;
					physicalMap.physicalMap[16][120]=1;
					physicalMap.physicalMap[17][120]=1;
					backgroundMap.map[14][120]=17;
					backgroundMap.map[15][120]=17;
					backgroundMap.map[16][120]=17;
					backgroundMap.map[17][120]=17;
				}
				break;
		}
	}
	private void drawString(Graphics2D g2,String text,int x,int y) {
		for(String str:text.split("\n")) {
			g2.drawString(str, x, y+=g2.getFontMetrics().getHeight());
		}
	}
	private void tutorialRender(Graphics2D g2) {
		switch(tutorialState) {
			case INTROGAME:
				boundManager.render(g2);
				break;
			case MEETFINALBOSS:
				int yMid=GameFrame.SCREEN_HEIGHT/2-15;
				int y1=yMid-GameFrame.SCREEN_HEIGHT/2-openIntroGameY/2;
				int y2=yMid+openIntroGameY/2;
				g2.setColor(Color.black);
				g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
				g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
				break;
		}
	}
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	public void reset() {
		physicalMap.physicalMap[14][120]=0;
		physicalMap.physicalMap[15][120]=0;
		physicalMap.physicalMap[16][120]=0;
		physicalMap.physicalMap[17][120]=0;
		backgroundMap.map[14][120]=47;
		backgroundMap.map[15][120]=48;
		backgroundMap.map[16][120]=47;
		backgroundMap.map[17][120]=48;
		finalbossTrigger=true;
		setUp();
	}
}
