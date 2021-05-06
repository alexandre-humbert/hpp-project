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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class Worker implements Runnable {

	private static BlockingQueue<String[]> queue;
	private static BlockingQueue<String[]> queue2;
	private static ArrayList<Chain> chains;
	private static ArrayList<Chain> removeList;
	private static Hashtable<Integer, Chain> idToChain;
	private Lock lock = new ReentrantLock(true);

	public void run() {
		try {
			while (true) {
				process(queue.take());
			}
		} catch (InterruptedException ex) {

		}
	}

	public Worker(BlockingQueue q, ArrayList<Chain> chains,ArrayList<Chain> removeList,Hashtable<Integer, Chain> idToChain) {
		this.queue = q;
		this.chains = chains;
		this.removeList=removeList;
		this.idToChain=idToChain;
	}

	private void process(String[] data) {
		//System.out.println(data[0]);
		Chain chain;
		int score = 0;
		int scoreTop3 = 0;
		Top3 top = new Top3();
		if (chains.isEmpty()) {
			try {
				//System.out.println(chains.size());
				lock.lock();
				chain = new Chain(Integer.parseInt(data[0]), Double.parseDouble(data[1]), data[3]);
				chains.add(chain);
			} finally {
				lock.unlock();
			}
		} else {
			Chain chainser=null;
			if(StringUtils.isNumericSpace(data[2])) {
				chainser = idToChain.get(Integer.parseInt(data[2]));
			}
			if (chainser == null) {
				chain = new Chain(Integer.parseInt(data[0]), Double.parseDouble(data[1]), data[3]);
				chains.add(chain);
				idToChain.put(Integer.parseInt(data[0]), chain);
			} else {		
				if (chainser.isOutofdate(Double.parseDouble(data[1]))) {		
					//System.out.println(Double.parseDouble(data[1])-chainser.getLasttimestamp());
					chains.remove(chainser);
					removeList.add(chainser);
					chain = new Chain(Integer.parseInt(data[0]), Double.parseDouble(data[1]), data[3]);
				} else {
					//System.out.println(Double.parseDouble(data[1])-chainser.getLasttimestamp());
					chainser.addTimestamp(Double.parseDouble(data[1]));
					idToChain.put(Integer.parseInt(data[0]), chainser);
				}				
			}			
			//System.out.println(chains.size());		
			//for ( Chain chain1:chains) {
			//for(Iterator<Chain> it = chains.iterator();it.hasNext();) {
			//System.out.println(chains);
			for(int i = 0; i<chains.size(); i++) {
				try {
					lock.lock();
					Chain chain1=chains.get(i);
					//System.out.println(Double.parseDouble(data[1])-chain1.getLasttimestamp());
					if (chain1.isOutofdate(Double.parseDouble(data[1]))) {	
						//System.out.println(Double.parseDouble(data[1])-chain1.getLasttimestamp());
						removeList.add(chain1);
						chains.remove(chain1);
						chain1 = new Chain(Integer.parseInt(data[0]), Double.parseDouble(data[1]), data[3]);
						chains.add(chain1);
					} else {
						//System.out.println(Double.parseDouble(data[1])-chain1.getLasttimestamp());
						//System.out.println(chain1.getListoftimestamp().size());
						score = chain1.caclulScore(Double.parseDouble(data[1]));						
						//System.out.println(score);
						if (score > scoreTop3) {
							scoreTop3 = top.addTop(chain1.getRootId(), chain1.getRootCountry(), score);
						}
					}
				} finally {
					
					lock.unlock();
				}
			}
			System.out.println(top.toString());
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