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
		BlockingQueue<String[]> queue= new ArrayBlockingQueue<String[]>(100);
		BlockingQueue<String> queuewriter= new ArrayBlockingQueue<String>(100);
		Reader rd;
		try {
			rd = new Reader("src\\test\\resources\\Example\\",queue);
			Thread t1 =new Thread(rd);  
			t1.start();  
			Worker worker = new Worker(queue,queuewriter);
			Thread t2 = new Thread(worker, "t2");
			t2.start();

			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	}
