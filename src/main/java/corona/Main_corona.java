package corona;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main_corona {
	private static String size="1000000";
	
	public static void main(String args[]) throws InterruptedException{ 
		
		BlockingQueue<String[]> queueworker= new ArrayBlockingQueue<String[]>(100);// Queue
		BlockingQueue<String> queuewriter= new ArrayBlockingQueue<String>(100);
		ArrayList<Chain> chains = new  ArrayList<Chain>();
		ArrayList<Chain> removeList= new  ArrayList<Chain>();
		Reader reader;
		long startTime = System.currentTimeMillis();
		try {
			reader = new Reader("src\\main\\resources\\"+size+"\\",queueworker);
			Thread t1 =new Thread(reader,"t1");  
			t1.start();  
			Hashtable<Integer, Chain> idToChain = new Hashtable<Integer, Chain>();
			Worker worker = new Worker(queueworker,queuewriter,chains,removeList,idToChain);
			Writer writer = new Writer(queuewriter);
			Thread t2 = new Thread(worker, "t2");
			Thread t3 = new Thread(writer, "t3");
			t2.start();
			t3.start();			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 long endTime = System.currentTimeMillis();
		  long seconds = (endTime - startTime) / 1000;	
		  System.out.println("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");

	}
}
