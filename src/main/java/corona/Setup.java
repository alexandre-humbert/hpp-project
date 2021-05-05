package corona;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Setup{
	static Random r = new Random();  
	public static void main(String[] args) {  
		  List<Integer> numList =  
		  Collections.synchronizedList(new ArrayList<>());  
		  Runnable r1;
		   
		  r1 = () -> {  
		  while (numList.size() < 100) { 
			  synchronized (numList) {
		    numList.add(r.nextInt(50));  
		    System.out.print("Thread = " + Thread.currentThread().getName());  
		    System.out.println(numList.toString());  
			  }
		  }  
		  
		 };  
		 // }		  
		  new Thread(r1, "t1").start();  
		  new Thread(r1, "t2").start();  
		  new Thread(r1, "t3").start();  
		  
		}
}
