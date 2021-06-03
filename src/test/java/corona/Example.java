package corona;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Test;

public class Example {

	@Test
	public void test() {
		BlockingQueue<String[]> queue;
		BlockingQueue<ArrayList<Chain>> queue2;
		queue = new ArrayBlockingQueue<String[]>(100);
		queue2 = new ArrayBlockingQueue<ArrayList<Chain>>(100);
		Reader rd;
		try {
			rd = new Reader("src\\test\\resources\\Example\\",queue);
			Thread t1 =new Thread(rd);  
			t1.start();  
			ArrayList<Chain> chains = new  ArrayList<Chain>();
			ArrayList<Chain> removeList= new  ArrayList<Chain>();
			Hashtable<Integer, Chain> idToChain = new Hashtable<Integer, Chain>();
			Worker worker = new Worker(queue,queue2,chains,removeList,idToChain);
			Thread t2 = new Thread(worker, "t2");
			t2.start();

			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	}
