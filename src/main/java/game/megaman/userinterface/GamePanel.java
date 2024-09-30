package game.megaman.userinterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.megaman.object.GameWorld;


public class GamePanel extends JPanel implements Runnable,KeyListener{
	private Thread thread;
	private boolean isRunning=true;
	private InputManager inputManager;
	public GameWorld gameWorld;
	public GamePanel() {
		gameWorld=new GameWorld();
		inputManager=new InputManager(gameWorld);
	}
	@Override
	public void paint(Graphics g) {
		g.drawImage(gameWorld.getBufferedImage(), 0, 0, this);
	}
	public void startGame() {
		thread=new Thread(this);
		thread.start();
	}
	int a=0;
	@Override
	public void run() {
		long FPS=60;
		long period=1000*1000000/FPS;
		long beginTime;
		long sleepTime;
		beginTime=System.nanoTime();
		while(isRunning) {
			gameWorld.update();
			gameWorld.render();
			repaint();
			long deltaTime=System.nanoTime()-beginTime;
			sleepTime=period-deltaTime;
			try {
				if(sleepTime>0)    Thread.sleep(sleepTime/1000000);
				else     Thread.sleep(period/2000000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			beginTime=System.nanoTime();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		inputManager.processKeyPress(e.getKeyCode());
	}
	@Override
	public void keyReleased(KeyEvent e) {
		inputManager.processKeyRelease(e.getKeyCode());
	}
}
