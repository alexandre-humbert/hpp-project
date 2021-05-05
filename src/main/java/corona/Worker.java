package corona;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker implements Runnable {

	private static BlockingQueue<String[]> queue;
	private static BlockingQueue<String[]> queue2;
	private static ArrayList<Chain> chains;
	private static ArrayList<Chain> removeList;
	private static Hashtable<Integer, Chain> idToChain = new Hashtable<Integer, Chain>();
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
		int score=0;
		if (chains.isEmpty()) {
			try {
				lock.lock();
				chain = new Chain(Integer.parseInt(data[0]), Double.parseDouble(data[1]), data[3]);
				chains.add(chain);
			} finally {
				lock.unlock();
			}
		} else {
			Chain chainser = idToChain.get(Integer.parseInt(data[0]));
			if (chainser==null) {
				chain = new Chain(Integer.parseInt(data[0]), Double.parseDouble(data[1]), data[3]);
			} else {
				if (chainser.isOutofdate(Double.parseDouble(data[1]))) {
					chains.remove(chainser);
					removeList.add(chainser);
					chain = new Chain(Integer.parseInt(data[0]), Double.parseDouble(data[1]), data[3]);
				} else {
					chainser.addTimestamp(Double.parseDouble(data[1]));
					idToChain.put(Integer.parseInt(data[0]), chainser);
				}
			}
			Iterator<Chain> it = chains.iterator();
			while (it.hasNext()) {
				try {
					lock.lock();
					chain = it.next();
					if (chain.isOutofdate(Double.parseDouble(data[1]))) {
						it.remove();
						removeList.add(chain);
						chain = new Chain(Integer.parseInt(data[0]), Double.parseDouble(data[1]), data[3]);
						chains.add(chain);
					} else {
						chain.setLasttimestamp(Double.parseDouble(data[1]));
						score=chain.caclulScore();						
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
 * // do something } finally { lock.unlock(); } synchronized (numList) {
 * numList.add(r.nextInt(50)); System.out.print("Thread = " +
 * Thread.currentThread().getName()); System.out.println(numList.toString()); }
 */