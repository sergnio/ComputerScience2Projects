
public class GuitarHero {

    public static void main(String[] args) {

    	//create the keyboard string.
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] guitarStrings = new GuitarString[37];

        //initialize GuitarStrings corresponding to the keys.
        for (int i = 0; i<37; i++){
        	double note = 440 * Math.pow(1.05956, i-24);
        	GuitarString x = new GuitarString(note);
        	guitarStrings[i] = x;
        }

        // the main input loop
        while (true) {

            // check if the user has typed a key, and, if so, process it
            if (StdDraw.hasNextKeyTyped()) {
 
                // the user types this character
                char key = StdDraw.nextKeyTyped();
                if(keyboard.indexOf(key)!= -1){
                // pluck the corresponding string
                	int guitarStringIndex = keyboard.indexOf(key);
                	guitarStrings[guitarStringIndex].pluck();
                }
                
            }

            // compute the superposition of the samples
            double sample = 0;
            for(int i = 0; i<37; i++){
            	sample += guitarStrings[i].sample();
            }

            // send the result to standard audio
            StdAudio.play(sample);

            // advance the simulation of each guitar string by one step
            for(int i = 0; i<37; i++){
            	guitarStrings[i].tic();
            }
            
            
        }
    }

}
