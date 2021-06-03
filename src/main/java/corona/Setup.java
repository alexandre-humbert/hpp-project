package corona;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Setup{
	static Random r = new Random();  
	static Lock lock = new ReentrantLock(true);
	public static void main(String[] args) {  
		  List<Integer> numList =  
		  Collections.synchronizedList(new ArrayList<Integer>());  
		  Runnable r1;
		   
		  r1 = () -> {  
			  long startTime = System.currentTimeMillis();
		  while (numList.size() < 20000) { 
			  try {
					//System.out.println(chains.size());
					lock.lock();
					numList.add(r.nextInt(50));  
					System.out.print("Thread = " + Thread.currentThread().getName());  
					System.out.println(numList.toString());  
				} finally {
					lock.unlock();
				}			  
			  }
		  long endTime = System.currentTimeMillis();
		  long seconds = (endTime - startTime) / 1000;	
		  System.out.println(seconds);
		 };  
		 		  
		  new Thread(r1, "t1").start();  
		  new Thread(r1, "t2").start();  
		  new Thread(r1, "t3").start();  
		 
		  
		}
}
