package corona;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main_corona {
	public static void main(String args[]) throws InterruptedException{ 
		BlockingQueue<String[]> queue;
		BlockingQueue<ArrayList<Chain>> queue2;
		queue = new ArrayBlockingQueue<String[]>(100);
		queue2 = new ArrayBlockingQueue<ArrayList<Chain>>(100);
		Reader rd;
		long startTime = System.currentTimeMillis();
		try {
			rd = new Reader("src\\main\\resources\\1000000\\",queue);
			Thread t1 =new Thread(rd);  
			t1.start();  
			ArrayList<Chain> chains = new  ArrayList<Chain>();
			ArrayList<Chain> removeList= new  ArrayList<Chain>();
			Hashtable<Integer, Chain> idToChain = new Hashtable<Integer, Chain>();
			Worker worker = new Worker(queue,queue2,chains,removeList,idToChain);
			Thread t2 = new Thread(worker, "t2");
			//Worker2 worker2 = new Worker2(queue2,chains,removeList,idToChain);
			//Thread t3 = new Thread(worker2, "t3");
			//Thread t4= new Thread(worker, "t4");			
			//Thread t4= new Thread(worker, "t4");	
			//Thread t5= new Thread(worker, "t5");	
			t2.start();
			//t3.sleep(1000);
			//t3.start();
			//t4.start();	
			//t5.start();

			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 long endTime = System.currentTimeMillis();
		  long seconds = (endTime - startTime) / 1000;	
		  System.out.println("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");

	}
}
