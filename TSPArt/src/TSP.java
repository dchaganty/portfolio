/* By: Deepa Chaganty (dhc5cu) and Liz Quin (ehq3bh)
 * CS 3102 Project - TSP ART
 */

import java.util.ArrayList;
import java.util.Stack;
import java.awt.Point;
import java.awt.geom.Line2D;

public class TSP {
	private ArrayList<Point> coordinates;

	public TSP(int[][] dithimg) {
		coordinates = new ArrayList<Point>();
		for (int x = 0; x < dithimg.length; x++) {
			for (int y = 0; y < dithimg[x].length; y++) {
				if (dithimg[x][y] == 0) {
					coordinates.add(new Point(y, x));
				}
			}
		}
	}

	//NEAREST NEIGHBOR TSP ALGORITHM
	public ArrayList<Point[]> nearestNeighbor() {
		ArrayList<Point> neighbor = new ArrayList<Point>();
		Point first = coordinates.get((int) (.2 * (double)coordinates.size()));
		neighbor.add(first);
		while (coordinates.size() > 0) {
			coordinates.remove(first);
			int next = 0;
			for (int i = 1; i < coordinates.size(); i++) {
				if (first.distance(coordinates.get(i)) < first.distance(coordinates.get(next))) {
					next = i;
				}
			}
			first = coordinates.get(next);
			neighbor.add(first);
			coordinates.remove(next);
		}
		ArrayList<Point[]> listpath = new ArrayList<Point[]>();
		Point p = neighbor.get(0);
		for(int i = 1; i < neighbor.size(); i++) {
			Point next = neighbor.get(i);
			listpath.add(new Point[] {next, first});
			p = next;
		}
		return listpath;	
	}

	//CHECKS IF EDGES SHARE ENDPOINTS 
	public boolean samePoint(Point[] first, Point[] second) {
		if(first[0].equals(second[0]) || first[1].equals(second[0])|| first[0].equals(second[1]) || first[1].equals(second[1])) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//CHECKS IF EDGES ARE EQUAL
	public boolean equals(Point[] first, Point[] second) {
		if(first[0].equals(second[0]) && first[1].equals(second[1])) {
			return true;
		}
		else {
			return false;
		}
	}

	//CHECKS IF EDGES HAVE SAME SLOPE 
	public boolean parallel(Point[] first, Point[] second) {
		double x1 = first[1].getX() - first[0].getX();
		double x2 = second[1].getX() - second[0].getX();
		double y1 = first[1].getY() - first[0].getY();
		double y2 = second[1].getY() - second[0].getY();
		if (x1 == 0 && x1 == x2) {
			return true;
		}
		if ((x1 == 0 && x1 != x2)|| (x2 == 0 && x2 != x1)) {
			return false;
		}
		double m1 = y1/ x1;
		double m2 = y2 / x2;
		if(m1 == m2 || m1 == 1/m2) {
			return true;
		}
		else {
			return false;
		}
	}

	//REVERSE PATH
	public ArrayList<Point> switchPath(ArrayList<Point> path, int first, int last) {
		ArrayList<Point> reversed = new ArrayList<Point>();
		for (int x = first + 1; x < last; x++) {
			reversed.add(0, path.get(x));
		}
		for (int x = first + 1; x < last; x++) {
			path.set(x, reversed.get(0));
			reversed.remove(0);
		}
		return path;
	}

	public ArrayList<Point> uncross(ArrayList<Point> p) {
		boolean intersect = false;
		boolean samep = false;
		boolean equal = false;
		boolean parallel = false;
		ArrayList<Point> path = new ArrayList<Point>(p);
		
		//LOOPS THROUGH ALL PAIRS OF EDGES
		for (int x = 0; x < path.size() - 1; x++) {
			Point[] first = { path.get(x), path.get(x + 1) };
			for (int y = x + 1; y < path.size() - 1; y++) {
				Point[] second = {path.get(y), path.get(y + 1)};
				intersect = Line2D.linesIntersect(first[0].getX(),
						first[0].getY(), first[1].getX(), first[1].getY(),
						second[0].getX(), second[0].getY(), second[1].getX(),
						second[1].getY());
				samep = samePoint(first, second);
				equal = equals(first, second);
			if (equal == false && samep == false && intersect == true) {
				//IF NOT PARALLEL, SWAP ENDPOINT OF FIRST EDGE WITH START POINT OF SECOND EDGE
				    parallel = parallel(first,second);
					if(parallel == false) {
						Point tmp = path.get(x + 1);
						path.set(x + 1, path.get(y));
						path.set(y, tmp);
					}
					// IF PARALLEL, SWAP END POINTS 
					else{
						Point n = path.get(x + 1);
						path.set(x + 1, path.get(y + 1));
						path.set(y + 1, n);
					}
					path = switchPath(path, x + 1, y);
					return path;
				}
			}
		}
		return path;
	}
}