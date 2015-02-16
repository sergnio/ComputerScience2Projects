public class RingBuffer {
//instance variables
	
	int n; //How many elements in the RingBuffer
	double[] data; //The array of elements
	int first; //The index of the first element
	
	
	//initializes the RingBuffer
	public RingBuffer(int capacity){
		this.first = 0;
		this.n = 0;
		this.data = new double[capacity];
	}
	
	public int size(){
		return this.n;
	}
	
	public boolean isEmpty(){
		return (this.n <= 0);
	}
	
	public boolean isFull(){
		return (this.n >= this.data.length);
	}
	
	
	//throws an exception if the RingBuffer is full, otherwise it adds a value to the end of the RingBuffer.
	public void enqueue(double x) throws RuntimeException {
		if(isFull()){
			throw new RuntimeException("Overflow");
		}
		this.data[(this.first + this.n)%this.data.length] = x;
		this.n++;
		
		
	}
	
	//throws an exception if the RingBuffer is empty, otherwise it removes a value from the front of the Ringbuffer
	public double dequeue() throws RuntimeException{
		double returnValue = peek();
		
		this.first = (this.first +1)%this.data.length;
		this.n--;
		return returnValue;
	}
	
	double peek() throws RuntimeException{
		if(isEmpty()){
			throw new RuntimeException("Underflow");
		}
		return this.data[this.first];
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}