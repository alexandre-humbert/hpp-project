package corona;

import java.io.FileNotFoundException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main_corona {
	public static final boolean DEBUG = false;
	public static final boolean BENCHMARK = false;

	private static String size="5000";
	
	public static void main(String args[]) throws InterruptedException{ 
		
		BlockingQueue<String[]> queueworker= new ArrayBlockingQueue<String[]>(100);// Queue de reader à worker
		BlockingQueue<String> queuewriter= new ArrayBlockingQueue<String>(100);// Queue de worker à writer
		Reader reader;
		long startTime = System.currentTimeMillis();
		try {
			reader = new Reader("src\\main\\resources\\"+size+"\\",queueworker);
			Thread t1 =new Thread(reader,"t1");  
			t1.start();  
			Worker worker = new Worker(queueworker,queuewriter);
			Writer writer = new Writer(queuewriter);
			Thread t2 = new Thread(worker, "t2");
			Thread t3 = new Thread(writer, "t3");
			t2.start();
			t3.start();
			t3.join();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 long endTime = System.currentTimeMillis();
		 double seconds = (endTime - startTime) / 1000.0;
		 System.out.println("Execution time : " + seconds + "s, Op : "+ size + ", Rate : " + seconds*1000/Integer.parseInt(size)+ " ms/op");
		 System.out.println("___END___");

	}
}
