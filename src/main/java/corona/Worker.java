package corona;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker implements Runnable {

	private static BlockingQueue<String[]> queue;
	private static ArrayList<Chain> chains;
	private Lock lock = new ReentrantLock(true);

	public void run() {
		try {
			while (true) {
				process(queue.take());
			}
		} catch (InterruptedException ex) {

		}
	}

	public Worker(BlockingQueue q, ArrayList<Chain> chains) {
		this.queue = q;
		this.chains = chains;
	}

	private void process(String[] data) {
		Chain chain;
		if (chains.isEmpty()) {
			try {
				lock.lock();
				chain = new Chain(Integer.parseInt(data[0]), Double.parseDouble(data[1]), data[3]);
				chains.add(chain);
			} finally {
				lock.unlock();
			}
		} else {
			Iterator<Chain> it = chains.iterator();
			while (it.hasNext()) {
				try {
					lock.lock();
					chain = it.next();
					if (chain.isOutofdate(Double.parseDouble(data[1]))) {
						it.remove();
						chain = new Chain(Integer.parseInt(data[0]), Double.parseDouble(data[1]), data[3]);
						chains.add(chain);
					} else {
						chain.setLasttimestamp(Double.parseDouble(data[1]));
						if (chain.getListofid().contains(Integer.parseInt(data[2]))) {
							chain.addPerson(Integer.parseInt(data[0]), Double.parseDouble(data[1]));
						}
						chain.caclulScore();
					}

				} finally {
					lock.unlock();
				}
			}
		}
	}
}
/*
 * Lock lock = new ReentrantLock(); try { lock.lock();
 * 
 * // do something } finally { lock.unlock(); }
 * synchronized (numList) {
		    numList.add(r.nextInt(50));  
		    System.out.print("Thread = " + Thread.currentThread().getName());  
		    System.out.println(numList.toString());  
			  }
 */