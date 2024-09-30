package game.megaman.effect;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class CacheDataLoader {
	private static CacheDataLoader instance;
	private String frameFile="data/frame.txt";
	private String animationFile="data/animation.txt";
	private String physicalFile="data/phys_map.txt";
	private String backgroundMapFile="data/background_map.txt";
	private String soundFile="data/sounds.txt";
	private Hashtable<String,FrameImage> frameImages;
	private Hashtable<String,Animation> animations;
	private Hashtable<String,AudioClip>sounds;
	private int[][] physiMap;
	private int[][] backgroundMap;
	public int[][] getBackgroundMap() {
		return instance.backgroundMap;
	}
	private CacheDataLoader() {
		frameImages=new Hashtable<String,FrameImage>();
		animations=new Hashtable<String,Animation>();
	}
	public static CacheDataLoader getInstance() {
		if(instance==null)    instance=new CacheDataLoader();
		return instance;
	}
	public int[][] getPhysiMap(){
		return instance.physiMap;
	}
	public AudioClip getSound(String name) {
		return instance.sounds.get(name);
	}
	public void loadSound() throws IOException{
		sounds=new Hashtable<String,AudioClip>();
		FileReader fr=new FileReader(soundFile);
		BufferedReader br=new BufferedReader(fr);
		String line=null;
		if(br.readLine()==null)    throw new IOException();
		else {
			fr=new FileReader(soundFile);
			br=new BufferedReader(fr);
			while((line=br.readLine()).equals(""));
			int n=Integer.parseInt(line);
			for(int i=0;i<n;i++) {
				AudioClip audioClip=null;
				while((line=br.readLine()).equals(""));
				String[] str=line.split(" ");
				String name=str[0];
				String path=str[1];
				audioClip=Applet.newAudioClip(new URL("file","",path));
				instance.sounds.put(name, audioClip);
			}
		}
		br.close();
	}
	public void loadFrame() throws IOException{
		frameImages=new Hashtable<String,FrameImage>();
		FileReader fr=new FileReader(frameFile);
		BufferedReader br=new BufferedReader(fr);
		String line=null;
		if(br.readLine()==null) {
			throw new IOException();
		}
		else {
			fr=new FileReader(frameFile);
			br=new BufferedReader(fr);
			while((line=br.readLine()).equals(""));
			int n=Integer.parseInt(line);
			String path=null;
			BufferedImage imageData=null;
			for(int i=0;i<n;i++) {
				FrameImage frame=new FrameImage();
				while((line=br.readLine()).equals(""));
				frame.setName(line);
				while((line=br.readLine()).equals(""));
				String[] str=line.split(" ");
				boolean refreshImage=(path==null||!path.equals(str[1]));
				path=str[1];
				while((line=br.readLine()).equals(""));
				str=line.split(" ");
				int x=Integer.parseInt(str[1]);
				while((line=br.readLine()).equals(""));
				str=line.split(" ");
				int y=Integer.parseInt(str[1]);
				while((line=br.readLine()).equals(""));
				str=line.split(" ");
				int w=Integer.parseInt(str[1]);
				while((line=br.readLine()).equals(""));
				str=line.split(" ");
				int h=Integer.parseInt(str[1]);
				if(refreshImage) {
					refreshImage=false;
					imageData=ImageIO.read(new File(path));
				}
				if(imageData!=null) {
					BufferedImage image=imageData.getSubimage(x, y, w, h);
					frame.setImage(image);
				}
				instance.frameImages.put(frame.getName(), frame);
			}
		}
		br.close();
	}
	public FrameImage getFrameImage(String name) {
		FrameImage frameImage=new FrameImage(instance.frameImages.get(name));
		return frameImage;
	}
	public void loadBackgroundMap()throws IOException{
		FileReader fr=new FileReader(backgroundMapFile);
		BufferedReader br=new BufferedReader(fr);
		String line=null;
		line=br.readLine();
		int numberOfRow=Integer.parseInt(line);
		line=br.readLine();
		int numberOfColumn=Integer.parseInt(line);
		instance.backgroundMap=new int[numberOfRow][numberOfColumn];
		for(int i=0;i<numberOfRow;i++) {
			line=br.readLine();
			String[] str=line.split(" |  ");
			for(int j=0;j<numberOfColumn;j++) {
				instance.backgroundMap[i][j]=Integer.parseInt(str[j]);
			}
		}
		br.close();
	}
	public void loadAnimation() throws IOException {
		animations=new Hashtable<String,Animation>();
		FileReader fr=new FileReader(animationFile);
		BufferedReader br=new BufferedReader(fr);
		String line=null;
		if(br.readLine()==null) {
			throw new IOException();
		}
		else {
			fr=new FileReader(animationFile);
			br=new BufferedReader(fr);
			while((line=br.readLine()).equals(""));
			int n=Integer.parseInt(line);
			for(int i=0;i<n;i++) {
				Animation ani=new Animation();
				while((line=br.readLine()).equals(""));
				ani.setName(line);
				while((line=br.readLine()).equals(""));
				String[] str=line.split(" ");
				for(int j=0;j<str.length;j+=2) {
					ani.add(getFrameImage(str[j]), Double.parseDouble(str[j+1]));
				}
				instance.animations.put(ani.getName(), ani);
			}
		}
		br.close();
	}
	public Animation getAnimation(String name){			
		Animation ani=new Animation(instance.animations.get(name));
		return ani;
	}
	public void loadData() throws IOException {
		loadFrame();
		loadAnimation();
		loadPhisMap();
		loadBackgroundMap();
		loadSound();
	}
	public void loadPhisMap() throws IOException{
		FileReader fr=new FileReader(physicalFile);
		BufferedReader br=new BufferedReader(fr);
		String line= null;
		line=br.readLine();
		int numberOfRows=Integer.parseInt(line);
		line=br.readLine();
		int numberOfColumns=Integer.parseInt(line);
		instance.physiMap=new int[numberOfRows][numberOfColumns];
		for(int i=0;i<numberOfRows;i++) {
			line=br.readLine();
			String[] str=line.split(" ");
			for(int j=0;j<numberOfColumns;j++) {
				instance.physiMap[i][j]=Integer.parseInt(str[j]);
			}
		}
		br.close();
	}
}
