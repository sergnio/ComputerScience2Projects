public class RingBuffer {
	private double[] data;
	private int first;
	private int last;

	
	public RingBuffer(int capacity) {
		this.data = new double[capacity];
		this.first = 0;
		this.last = 0;
	}
	public int size() {
		return this.last;
	}
	public boolean isEmpty() {
		return (size() == 0);
	}
	public boolean isFull() {
		return (size() == data.length);
	}
	public void enqueue(double x) {
		if (isFull()) {
			double[] newData = new double[2 * this.data.length];
			for (int i = 0; i < this.data.length; i++)
				newData[i] = data[(this.first + i) % this.data.length];
			this.first = 0;
			this.data = newData;
		}
		this.data[(this.first + this.last) % this.data.length] = x;
		this.last++;
	}
	public double dequeue() throws RuntimeException {
		double returnValue = peek();
		this.first = (this.first + 1) % this.data.length;
		this.last--;
		return returnValue;
	}
	public double peek() throws RuntimeException {
		if (isEmpty())
			throw new RuntimeException("Underflow");
		return this.data[this.first];
	}
	
	public static void main(String[] args) {
	      int N = Integer.parseInt(args[0]);
	      RingBuffer buffer = new RingBuffer(N);  
	      for (int i = 1; i <= N; i++) {
	          buffer.enqueue(i);
	      }
	      double t = buffer.dequeue();
	      buffer.enqueue(t);
	      System.out.println("Size after wrap-around is " + buffer.size());
	      while (buffer.size() >= 2) {
	          double x = buffer.dequeue();
	          double y = buffer.dequeue();
	          buffer.enqueue(x + y);
	      }
	      System.out.println(buffer.peek());
	  }
}