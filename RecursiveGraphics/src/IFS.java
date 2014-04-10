import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class IFS {
    public static void main(String[] args) throws FileNotFoundException {
    	
    	// set it to read input from a data file
		String resourceFolder = "resources/";
		String fileName = resourceFolder + "myfractal.txt";
		FileInputStream fileInputStream = new FileInputStream(fileName);
		System.setIn(fileInputStream);
		
        // number of iterations
        int T = 100000;

        // probability distribution for choosing each rule
        double[] dist = StdArrayIO.readDouble1D();

        // update matrices
        double[][] cx = StdArrayIO.readDouble2D();
        double[][] cy = StdArrayIO.readDouble2D();

        // current value of (x, y)
        double x = 0.0, y = 0.0;

        // do T iterations of the chaos game
        for (int t = 0; t < T; t++) { 

            // pick a random rule according to the probability distribution
            int r = StdRandom.discrete(dist); 

            // do the update
            double x0 = cx[r][0]*x + cx[r][1]*y + cx[r][2]; 
            double y0 = cy[r][0]*x + cy[r][1]*y + cy[r][2]; 
            x = x0; 
            y = y0; 

            // draw the resulting point
            StdDraw.point(x, y); 

            // for efficiency, display only every 100 iterations
            if (t % 100 == 0) StdDraw.show(10);
        } 

        // ensure everything gets drawn
        StdDraw.show(0);
    } 
}