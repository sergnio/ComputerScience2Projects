public class GuitarHero {
	public static void main(String[] args) {

		// initialize the arrays for the number of tones and keys you are able
		// to use
		double[] notes = new double[37];
		GuitarString[] strings = new GuitarString[37];
		int nextNote;
		int firstNote = 0;

		// create the keyboard string and initialize GuitarStrings to the
		// corresponding keys
		String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
		for (int i = 0; i < 37; i++) {
			double concert = 440.0 * Math.pow(2, (i - 24) / 12.0);
			notes[i] = concert;
			for (int j = 0; j < 37; j++) {
				strings[j] = new GuitarString(concert);
			}
		}

		// the main input loop
		while (true) {

			// check if user types a key, match index of key to keyboard string
			// if possible

			if (StdDraw.hasNextKeyTyped()) {
				char key = StdDraw.nextKeyTyped();
				int charIndexInKeyboard = keyboard.indexOf(key);

				// if the key is not in keyboard string, go to top of loop
				if (charIndexInKeyboard == -1) {
					continue;
				}
				// play the corresponding note to the key pressed
				nextNote = keyboard.charAt(charIndexInKeyboard);
				strings[nextNote].pluck();
				double sample = strings[firstNote].sample() + strings[nextNote].sample();
				StdAudio.play(sample);
				strings[nextNote].tic();
				firstNote = nextNote;
			}
		}
	}
}