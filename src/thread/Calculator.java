package thread;

public class Calculator {
	int[] array;
	long sum;
	int numOfThreads;
	
	
	public Calculator(int numOfThreads, int arrayLength) 
	{
		this.numOfThreads = numOfThreads;
		array = new int[arrayLength];
		for (int i = 0; i < array.length; i++)
			array[i] = i;
		sum = 0;
	}
	
	public long calculate() 
	{
		sum = 0;

		//FirstThread firstThread = new FirstThread();
		//SecondThread secondThread = new SecondThread();
		
		long start = System.currentTimeMillis();
		
		WorkerThread[] workers = new WorkerThread[numOfThreads];
		
		for (int i = 0; i < numOfThreads; i++)
		{
			workers[i] = new WorkerThread(i*array.length / numOfThreads, (i+1) *array.length / numOfThreads);
			workers[i].start();
		}

		try
		{
			//wait for all the threads to complete
			for (int i = 0; i < numOfThreads; i++)
			{
				workers[i].join(); //wait for completion of that thread, then join parent thread
			}
			
			long end = System.currentTimeMillis();
			
			System.out.println("Total sum = " + sum);
			System.out.println("Total runtime: " + (end - start));
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		// Spawn numOfThreads threads, and let each thread handle a fraction of the array
		// For simplicity, each thread handles [i*array.length / numOfThreads, (i+1) *array.length / numOfThreads )
		
		return sum;
	}
	
	public class WorkerThread extends Thread
	{
		//data
		int lowerBound;
		int upperBound;
		
		//constructor
		public WorkerThread(int lowerBound, int upperBound)
		{
			this.lowerBound = lowerBound;
			this.upperBound = upperBound;
		}
		
		//actual logic of the thread
		public void run() 
		{
			for (int i = lowerBound; i < upperBound; i++)
			{
				add(array[i]);
			}
		}
	}
	
	public synchronized void add(int increment)
	{
		sum += increment;
	}
	

	public static void main(String args[]) {
		int numOfThreads = 1;

		Calculator cal = new Calculator(numOfThreads, 1000000);
		cal.calculate();
	}	
}
