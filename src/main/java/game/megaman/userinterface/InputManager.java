package game.megaman.userinterface;

import java.awt.event.KeyEvent;

import game.megaman.object.GameWorld;


public class InputManager {
	private GameWorld gameWorld;
	public InputManager(GameWorld gameWorld) {
		this.gameWorld=gameWorld;
	}
	public void processKeyPress(int keyCode) {
		switch(keyCode) {
			case KeyEvent.VK_UP:
				if(gameWorld.state==GameWorld.TUTORIAL)       gameWorld.boundManager.updateXuong();
				gameWorld.megaman.jump();
				break;
			case KeyEvent.VK_DOWN:
				if(gameWorld.state==GameWorld.TUTORIAL)     gameWorld.boundManager.updateLen();
				gameWorld.megaman.dick();
				break;
			case KeyEvent.VK_LEFT:
				gameWorld.megaman.setDirection(gameWorld.megaman.LEFT_DIR);
				gameWorld.megaman.run();
				break;
			case KeyEvent.VK_RIGHT:
				gameWorld.megaman.setDirection(gameWorld.megaman.RIGHT_DIR);
				gameWorld.megaman.run();
				break;
			case KeyEvent.VK_ENTER:	
				if(gameWorld.state==GameWorld.TUTORIAL) {
					if(gameWorld.boundManager.getIndex()<2)      gameWorld.switchState(GameWorld.GAMEPLAY);
				}
//				gameWorld.bgMusic.loop();
//				gameWorld.bgMusic.play();
				break;
			case KeyEvent.VK_SPACE:
				gameWorld.megaman.jump();
				break;
			case KeyEvent.VK_A:
				gameWorld.megaman.attack();
				break;
		}
	}
	public void processKeyRelease(int keyCode) {
		switch(keyCode) {
			case KeyEvent.VK_UP:
				
				break;
			case KeyEvent.VK_DOWN:
				gameWorld.megaman.standUp();
				break;
			case KeyEvent.VK_LEFT:
				if(gameWorld.megaman.getSpeedX()<0)     gameWorld.megaman.stopRun();
				break;
			case KeyEvent.VK_RIGHT:
				if(gameWorld.megaman.getSpeedX()>0)     gameWorld.megaman.stopRun();
				break;
			case KeyEvent.VK_ENTER:
				
				break;
			case KeyEvent.VK_SPACE:
				
				break;
		}
	}
}
