
public class Sierpinski {
	
	public static void triangle(double x0, double y0, double x1, double y1, double x2, double y2) {
		double[] arrayX = new double[3];
		double[] arrayY = new double[3];
		arrayX[0] = x0;
		arrayX[1] = x1;
		arrayX[2] = x2;
		arrayY[0] = y0;
		arrayY[1] = y1;
		arrayY[2] = y2;
		StdDraw.filledPolygon(arrayX, arrayY);
	}

	public static void sierpinski(double x0, double y0, double x1, double y1, double x2, double y2, int level){
		if (level == 0) {
			triangle(x0, y0, x1, y1, x2, y2); 
			}
		else {
			//double avgX1, avgX2, avgX3, avgY1, avgY2, avgY3;
			double midX1, midX2, midX3, midY1, midY2, midY3;
			midX1 = (x0 + x2) / 2;
			midX2 = (x1 + x2) / 2;
			midX3 = (x0 + x1) / 2;
			midY1 = (y0 + y2) / 2;
			midY2 = (y1 + y2) / 2;
			midY3 = (y0 + y1) / 2;
			
			sierpinski(midX3, midY3, x1, y1, midX2, midY2, level-1);
			sierpinski(x0, y0, midX3, midY3, midX1, midY1, level-1);
			sierpinski(midX1, midY1, midX2, midY2, x2, y2, level-1);
			
		}
	}
public static void main(String[] args){
	sierpinski(0, 0, 1, 0, .5, .866, 2);
}
}
