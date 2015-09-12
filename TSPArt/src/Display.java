/* By: Deepa Chaganty (dhc5cu) and Liz Quin (ehq3bh)
 * CS 3102 Project - TSP ART
 */

import javax.swing.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;
import java.awt.*;


public class Display extends JPanel {

	private String img = "soup2.jpg";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;

	private int width;
	private int height;
	private ArrayList<Point> coordinates;
	private DitherImage newimg;
	private BufferedImage resizedimg;
	private int[][] dithimg;
	private BufferedImage screen;
	private Graphics getscreen;
	private ArrayList<Point[]> shortpath;
	private TSP tsppath;

	public Display(int h, int w) {
		height = h;
		width = w;
		newimg = new DitherImage(img);
		resizedimg = newimg.fitToScreen(150);
		dithimg = newimg.dither();
		tsppath = new TSP(dithimg);
		shortpath = tsppath.nearestNeighbor();
		coordinates= new ArrayList<Point>();
		for(Point[] edge : shortpath){
			coordinates.add(edge[0]);
		}
		coordinates.add(shortpath.get(shortpath.size()-1)[1]);
		screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		getscreen = screen.getGraphics();
		int count = 0;
		System.out.println(coordinates.size()/10);
		for (int i = 0; i < coordinates.size()/10; i++) {
			createPath(12, 12, 4, Color.BLACK);
			count++;
			System.out.println(count);
		}
		getscreen.setColor(Color.white);
		getscreen.fillRect(0, 0, width, height);
		createPath(12, 12, 4, Color.black);
		repaint();
	}

	// DRAWS TSP PATH TO SCREEN
	public void createPath( int x, int y, int spacing, Color color) {
		getscreen.setColor(color);
		Point previous = coordinates.get(0);
		for(int i = 0; i < coordinates.size(); i++) {
			getscreen.drawLine(x + (int)(previous.getX() * spacing),
								  y + (int)(previous.getY() * spacing),
								  x + (int)(coordinates.get(i).getX() * spacing),
								  y + (int)(coordinates.get(i).getY() * spacing));
			previous = coordinates.get(i);
		}
		coordinates = tsppath.uncross(coordinates);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(screen, 0, 0, width, height, null);
	}

    public static void main(String[] args) {
    	JFrame window = new JFrame("");
    	window.setSize(WIDTH, HEIGHT);
    	window.setLocation(100, 100);
    	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	window.setContentPane(new Display(HEIGHT, WIDTH));
    	window.setVisible(true);
    }
}