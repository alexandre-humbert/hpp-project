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

public class Worker2 implements Runnable {

	private static BlockingQueue<ArrayList<Chain>> queue2;
	private  ArrayList<Chain> chains;
	private static ArrayList<Integer> removeList;
	private  Hashtable<Integer, Chain> idToChain;
	private Lock lock = new ReentrantLock(true);

	public void run() {
		try {
			long startTime = System.currentTimeMillis();
			while (true) {
				//System.out.println("uo");
				 if (queue2.isEmpty()) {
					 long endTime = System.currentTimeMillis();
					 long seconds = (endTime - startTime) / 1000;	
					  System.out.println(seconds);
	                    return;
	                }
				process(queue2.take());
				
				
			}
		} catch (InterruptedException ex) {

		}
	}

	public Worker2(BlockingQueue<ArrayList<Chain>> q, ArrayList<Chain> chainsd,ArrayList<Integer> removeList,Hashtable<Integer, Chain> idToChain) {
		Worker2.queue2 = q;
		//this.chains = chains;
		this.removeList=removeList;
		this.idToChain=idToChain;
	}

	private void process(ArrayList<Chain> chains2) {
		//System.out.println(data[0]);
		int score = 0;
		int scoreTop3 = 0;
		Top3 top2 = new Top3();		
			int i = 0;
			//System.out.println(chains2.size());
			while(i<chains2.size() ) {
				//try {												
					//lock.lock();
					//if(i<chains.size()) {	
					Chain chain2=chains2.get(i);
					//System.out.println(chain1);
					//System.out.println(Double.parseDouble(data[1])-chain1.getLasttimestamp());
					if (chain2.isOutofdate(chains2.get(chains2.size() - 1).getLasttimestamp())) {	
						//System.out.println(Double.parseDouble(data[1])-chain1.getLasttimestamp());
						//removeList.add(chain2);
						removeList.add(chain2.getRootId());
						//idToChain.remove(chain1.getRootId());
						chains.remove(chain2);
					} else {
						if(scoreTop3<chain2.getTimestampsize()*10) {
						//System.out.println(Double.parseDouble(data[1])-chain1.getLasttimestamp());
						//System.out.println(chain1.getListoftimestamp().size());
						score = chain2.caclulScore(chains2.get(chains2.size() - 1).getLasttimestamp());						
						//System.out.println(score);
						if (score > scoreTop3) {
							//scoreTop3 = top2.addTop(chain2.getRootId(), chain2.getRootCountry(), score);
						}
						}
					}
					//}
					
				//} finally {
					//lock.unlock();
				//}
				
					
				i++;
			}
			System.out.println(top2);
			//System.out.println("Thread = "+Integer.parseInt());  
		}
	}

/*
 * Lock lock = new ReentrantLock(); try { lock.lock();
 * 
 * // do something } finally { lock.unlock(); } synchronized (numList) {
 * numList.add(r.nextInt(50)); System.out.print("Thread = " +
 * Thread.currentThread().getName()); System.out.println(numList.toString()); }
 */
