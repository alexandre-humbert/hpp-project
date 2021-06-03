package corona;

import java.util.concurrent.BlockingQueue;

public class Writer implements Runnable {

	private static BlockingQueue<String> queuewriter;
	public void run() {
		try {
			long startTime = System.currentTimeMillis();
			while (true) {
				String data = queuewriter.take();
				if(data=="-1"){//) {
					 long endTime = System.currentTimeMillis();
					 long seconds = (endTime - startTime) / 1000;	
					  System.out.println(seconds);
					break;
				}
				System.out.println(data);
			}
		} catch (InterruptedException ex) {
				
		}
	}
	public Writer(BlockingQueue<String> queuewriter) {
		this.queuewriter=queuewriter;
	}
}
