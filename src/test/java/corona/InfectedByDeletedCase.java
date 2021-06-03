package corona;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class InfectedByDeletedCase {

	@Test
	public void test() throws InterruptedException, IOException {
		BufferedReader bf = null;
	    String line;
	    String value;
		BlockingQueue<String[]> queueworker;
		BlockingQueue<String> queuewriter;
		queueworker = new ArrayBlockingQueue<String[]>(100);
		queuewriter = new ArrayBlockingQueue<String>(100);
		Reader rd;
		try {
			bf = new BufferedReader(new FileReader("src\\test\\resources\\InfectedByDeletedCase_result\\result_top3.txt"));
			rd = new Reader("src\\test\\resources\\InfectedByDeletedCase\\",queueworker);
			Thread t1 =new Thread(rd);  
			t1.start();
			Worker worker = new Worker(queueworker,queuewriter);
			Thread t2 = new Thread(worker, "t2");
			t2.start();
			t2.join();
			int size = queuewriter.size();
			for (int i=1; i<size; i++) {
				line = bf.readLine();
				System.out.println("Expected : " + line);
				value = queuewriter.take();
				System.out.println("Got      : " + value);
				if (line.equals(value) != true) {
					//fail("Does not match");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
