package game.megaman.userinterface;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import game.megaman.effect.CacheDataLoader;


public class GameFrame extends JFrame{
	public static final int SCREEN_HEIGHT=600;
	public static final int SCREEN_WIDTH=1000;
	public GamePanel gamePanel;
	public GameFrame() {
		Toolkit toolkit=this.getToolkit();
		Dimension dimention=toolkit.getScreenSize();
		this.setBounds((dimention.width-SCREEN_WIDTH)/2,(dimention.height-SCREEN_HEIGHT)/2,SCREEN_WIDTH,SCREEN_HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		try {
			CacheDataLoader.getInstance().loadData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		gamePanel=new GamePanel();
		this.add(gamePanel);
		this.addKeyListener(gamePanel);
		this.setResizable(false);
	}
	public void startGame() {
		gamePanel.startGame();
	}

	public static void main(String[] args) {
		GameFrame gameFrame=new GameFrame();
		gameFrame.setVisible(true);
		gameFrame.startGame();
	}

}