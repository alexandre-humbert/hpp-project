package corona;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Test;

public class ReaderTest {

	@Test
	public void test() throws InterruptedException {
		BlockingQueue<String[]> ReaderQueue;
		ReaderQueue = new ArrayBlockingQueue<String[]>(100);
		Reader rd;
		try {
			rd = new Reader("src\\test\\resources\\Example\\",ReaderQueue);
			Thread ReaderThread =new Thread(rd);  
			ReaderThread.start();
			ReaderThread.join();
			//assertEquals(ReaderQueue.size(),20);
			System.out.println("Size " + ReaderQueue.size());
			int size = ReaderQueue.size();
			for (int i=0; i<size; i++) {
				System.out.println(ReaderQueue.take()[0]);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  

	}

}
