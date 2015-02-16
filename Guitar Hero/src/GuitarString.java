public class GuitarString {
	private double N;
	private RingBuffer newRingBuffer;
	public int time;

	// The first constructor creates a RingBuffer of the desired capacity N
	// (sampling rate 44,100 divided by frequency, rounded up to the nearest
	// integer), and initializes it to represent a guitar string at rest by
	// enqueueing N zeros.

	public GuitarString(double frequency) {
		N = 44100 / frequency;
		newRingBuffer = new RingBuffer((int) N);
		while (!newRingBuffer.isFull()) {
			newRingBuffer.enqueue(0.0);
		}
		time = 0;
	}

	// The second constructor creates a RingBuffer of capacity equal to the size
	// of the array, and initializes the contents of the buffer to the values in
	// the array.

	public GuitarString(double[] init) {
		newRingBuffer = new RingBuffer(init.length);
		for (int i = 0; i < init.length; i++) {
			newRingBuffer.enqueue(init[i]);
		}
		time = 0;
	}

	// Replace all N items in the ring buffer with N random values between -0.5
	// and +0.5.

	public void pluck() {
		while (!newRingBuffer.isFull()) {
			double randomNumber = Math.random() - 0.5;
			newRingBuffer.enqueue(randomNumber);
		}
	}

	// Advances the simulation one time step

	public void tic() {
		double first = newRingBuffer.dequeue();
		newRingBuffer.enqueue((first + newRingBuffer.peek()) / 2);
		time++;
	}

	// Return the value of the item at the front of the ring buffer. Use peek()
	// to implement this.

	public double sample() {
		return newRingBuffer.peek();
	}

	// Return the total number of times tic() was called.
	
	public int time() {
		return time;
	}

	public static void main(String[] args) {
		int N = 25;
		double[] samples = { .2, .4, .5, .3, -.2, .4, .3, .0, -.1, -.3 };
		GuitarString testString = new GuitarString(samples);
		for (int i = 0; i < N; i++) {
			int t = testString.time();
			double sample = testString.sample();
			System.out.printf("%6d %8.4f\n", t, sample);
			testString.tic();
		}
	}
}