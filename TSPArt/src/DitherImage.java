/* By: Deepa Chaganty (dhc5cu) and Liz Quin (ehq3bh)
 * CS 3102 Project - TSP ART
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DitherImage {

	private BufferedImage img;

	public DitherImage(String filename) {
		try {
			img = ImageIO.read(new File(filename));
		}
		catch (IOException e) {
			System.out.println("Image \"" + filename + "\" doesn't exist.");
		}
	}

	//FLOYD-STEINBURG DITHER
	public int[][] dither() {
		int height = img.getHeight();
		int width = img.getWidth();
		int[][] dithvals = new int[height][width];
		for (int x = 0; x < dithvals.length; x++) {
			for (int y = 0; y < dithvals[x].length; y++) {
				Color pixel = new Color(img.getRGB(y, x));
				dithvals[x][y] = (pixel.getRed() + pixel.getBlue() + pixel.getGreen()) / 3;
			}
		}
		for (int x = 0; x < dithvals.length; x++) {
			for (int y = 0; y < dithvals[x].length; y++) {
				int a = dithvals[x][y];
				int p = 255;
				if (a < 128){
					p = 0;
				}
				dithvals[x][y] = p;
				int e = a - p;
				if (y < dithvals[x].length - 1)
					dithvals[x][y + 1] += (int) ((3.0/15) * e);
				if (x < dithvals.length - 1)
					dithvals[x + 1][y] += (int) ((3.0/15) * e);
			}
		}
		return dithvals;
	}
	
	//RESIZE IMAGE TO FIT TO SCREEN
	public BufferedImage fitToScreen(int m) {
		int width = m, height = m;
		if (img.getWidth() <= img.getHeight()) {
			width = (int) (((double) m / img.getHeight()) * img.getWidth());
		}
		else if (img.getWidth() > img.getHeight()) {
			height = (int) (((double) m / img.getWidth()) * img.getHeight());
		}
		//GRAY THE IMAGE
		BufferedImage resized = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics grayimg = resized.createGraphics();
		grayimg.drawImage(img, 0, 0, width, height, null);
		grayimg.dispose();
		img = resized;
		return img;
	}

}