package game.megaman.object;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Bound {
	private int x,y;
	private int h,w;
	private Color color;
	private BufferedImage bufferedImage;
	private boolean isActive;
	private int textX,textY;
	private Font textFont;
	private String text;
	public Bound(int x,int y) {
		this.x=x;
		this.y=y;
		this.color=color;
		h=80;
		w=(500-x)*2;
		bufferedImage=new BufferedImage(1000,600,BufferedImage.TYPE_INT_RGB);
		textFont=new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 20);
		isActive=false;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Color getColor() {
		if(isActive)  return color;
		else    return Color.gray;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public void update() {
	}
	public void render(Graphics2D g2) {
		FontMetrics fm=g2.getFontMetrics(textFont);
		int tmp=(fm.getAscent()+fm.getDescent())/2;
		textX=x+(w-fm.stringWidth(text))/2;
		textY=y+h-(h-tmp)/2;
		g2.setColor(getColor());
		g2.fillRoundRect(x, y, w, h, 15, 10);
		g2.setColor(Color.black);
		g2.setFont(textFont);
		g2.drawString(text,textX,textY);
	}
}
