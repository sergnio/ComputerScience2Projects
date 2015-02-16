public class GuitarString {
	static int samplingRate = 44100;
	public RingBuffer ringBuffer;
	public int x; //The number of times the method tic() was called
	
	
	//constructors
	public GuitarString(double frequency){
		int n = (int)(samplingRate/frequency);
		this.ringBuffer = new RingBuffer(n);
		for(int i = 0; i<n; i++){
			this.ringBuffer.enqueue(0);
		}
		this.x = 0;
		
	}
	public GuitarString(double[] init){
		this.ringBuffer = new RingBuffer(init.length);
		for(int i = 0; i<init.length; i++){
			this.ringBuffer.enqueue(init[i]);
		}
	}
	
	
	//mimics the plucking of a string on a guitar.  Sets all the values in the ring buffer to white noise.
	public void pluck(){
		for(int i = 0;i<this.ringBuffer.data.length;i++){
			this.ringBuffer.dequeue();
			double randValue = Math.random() -.5;
			this.ringBuffer.enqueue(randValue);
		}
	}
	
	//decays the sound played
	public void tic(){
		double a = this.ringBuffer.dequeue();
		double b = this.ringBuffer.peek();
		this.ringBuffer.enqueue(((a+b)/2)*.994);
		this.x++;
	}
	
	//returns first value in the ring buffer
	public double sample(){
		return this.ringBuffer.peek();
	}
	
	//returns how many times tic() was called
	public int time(){
		return this.x;
	}
	

}