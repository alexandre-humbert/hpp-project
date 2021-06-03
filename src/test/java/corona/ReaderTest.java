package corona;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Test;

public class ReaderTest {

	@Test
	public void test() throws InterruptedException, IOException {
		BlockingQueue<String[]> ReaderQueue;
		ReaderQueue = new ArrayBlockingQueue<String[]>(100);
		Reader rd;
		BufferedReader bf = null;
	    String line;
	    String value;
		try {
			bf = new BufferedReader(new FileReader("src\\test\\resources\\Example_result\\test_reader.txt"));
			rd = new Reader("src\\test\\resources\\Example\\",ReaderQueue);
			Thread ReaderThread =new Thread(rd);  
			ReaderThread.start();
			ReaderThread.join();
			//assertEquals(ReaderQueue.size(),20);
			System.out.println("Size " + ReaderQueue.size());
			int size = ReaderQueue.size();
			for (int i=0; i<size; i++) {
				line = bf.readLine();
				value = Arrays.toString(ReaderQueue.take());
				if (line.equals(value) != true) {
					fail("Does not match");
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  

	}

}
