public class GuitarString {
	private double N;
	private RingBuffer newRingBuffer;
	public int time;
	//
	public GuitarString(double frequency) {
		N = 44100/frequency;
		newRingBuffer = new RingBuffer((int)N);
		while (! newRingBuffer.isFull()) {
			newRingBuffer.enqueue(0.0);
		}
		time = 0;
	}
	public GuitarString(double[] init) {
		newRingBuffer = new RingBuffer(init.length);
		for (int i=0; i<init.length; i++) {
			newRingBuffer.enqueue(init[i]);
		}
		time = 0;
	}
	public void pluck() {
		while (! newRingBuffer.isFull()) {
			double randomNumber = Math.random() - 0.5;
			newRingBuffer.enqueue(randomNumber);
		}
	}
	
	public void tic() {
		double first = newRingBuffer.dequeue();
		newRingBuffer.enqueue((first+newRingBuffer.peek())/2);
		time++;
	}
	
    public double sample()
    {
        return newRingBuffer.peek();
    }
    
    public int time()
    {
        return time;
    }
    
    public static void main(String[] args) {
    }
}