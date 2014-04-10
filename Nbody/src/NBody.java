import java.io.FileInputStream;

public class NBody {	
	
	public static void main(String[] args) {
		 try {
		        System.setIn(new FileInputStream("Resources/nbody/planets.txt"));
		    } catch (Exception e) {
		        System.err.printf("Exception caught: %s", e.toString());
		        System.exit(0);
		    }
		 
		 double T = Double.parseDouble(args[0]);
		 double dT = Double.parseDouble(args[1]);
		 
		 int N = StdIn.readInt();
		 double r = StdIn.readDouble();
		 
		 double[] rx = new double[N];
		 double[] ry = new double[N];
		 double[] vx = new double[N];
		 double[] vy = new double[N];
		 double[] fx = new double[N];
		 double[] fy = new double[N];
		 double[] mass = new double[N];
		 String[] image = new String[N];
		 
		 StdAudio.play("Resources/nbody/2001.mid");
		 
		 for (int i = 0; i < N; i++) {
			 rx[i] = StdIn.readDouble();
			 ry[i] = StdIn.readDouble();
			 vx[i] = StdIn.readDouble();
			 vy[i] = StdIn.readDouble();
			 mass[i] = StdIn.readDouble();
			 image[i] = StdIn.readString();
		 }
		 
	     StdDraw.setXscale(-r, r);
	     StdDraw.setYscale(-r, r);
		 
		 double F;
		 double forceX;
		 double forceY;
		 double dX;
		 double dY;
		 double aX;
		 double aY;
		 double distance;
		 
		 	String background = "Resources/nbody/starfield.jpg";
			double timeElapsed= 0.0;
			Stopwatch myWatch = new Stopwatch();
			while (timeElapsed < T) {
				for (int i = 0; i < N; i++) {
					fx[i] = 0;
					fy[i] = 0;
				}
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						if (i != j) {
							dX = rx[j] - rx[i]; 
							dY = ry[j] - ry[i]; 
							distance = Math.sqrt((Math.pow(dX, 2) + Math.pow(
									dY, 2)));

							F = (6.67e-11 * (mass[i] * mass[j]))
									/ (distance * distance);
							forceX = F * (dX / distance);
							forceY = F * (dY / distance);
							fx[i] += forceX;
							fy[i] += forceY;
						}
					}
				}

				StdDraw.picture(0, 0, background);
				
				for (int i = 0; i < N; i++) {
					aX = fx[i] / mass[i];
					aY = fy[i] / mass[i];
					vx[i] += (dT * aX);
					vy[i] += (dT * aY);
					rx[i] += (dT * vx[i]);
					ry[i] += (dT * vy[i]);
				}
				for (int i=0; i<N; i++) {
					 StdDraw.picture(rx[i], ry[i], "Resources/nbody/" + image[i]);
				}
				
			StdDraw.show(30);
			StdDraw.clear();
			timeElapsed = myWatch.elapsedTime();
		 
		 }
	}

}
