package com.company;


import javax.swing.JComponent;
import java.awt.*;
import java.awt.image.BufferedImage;


public class JImageDisplay extends JComponent
{
	public BufferedImage img;
	public JImageDisplay(int w, int h)
	{
		img = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		Dimension dim = new Dimension(w,h);
		super.setPreferredSize(dim);
	}
	//@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(img,0,0,img.getWidth(),img.getHeight(),null);
	}
	// Set every pixel in the image to black.
	public void clearImage()
	{
		for(int i = 0; i<img.getHeight();i++)
			for(int j = 0; i<img.getWidth();j++)
				img.setRGB(j,i,0);
	}
	// Set pixel at (x,y) to color.
	public void drawPixel(int x, int y, int rgbColor)
	{
		img.setRGB(x,y,rgbColor);
	}
	public BufferedImage getBufferedImage() {
		return img;
	}

}