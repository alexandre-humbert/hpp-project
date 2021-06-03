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
	private static BlockingQueue<ArrayList<Chain>> queue2;
	private  ArrayList<Chain> chains;
	private  static ArrayList<Chain> removeList;
	private int score = 0;
	private int scoreTop3 = 0;
	private Top3 top ;
	private double lasttimestamp;
	private  Hashtable<Integer, Chain> idToChain;
	private  Hashtable<Chain,Integer> ChainToid;
	private Lock lock = new ReentrantLock(true);
	private static int w=0;

	public void run() {
		try {
			long startTime = System.currentTimeMillis();
			while (true) {
				//w++;
				String[] data = queue.take();
				if(Integer.parseInt(data[0])==-1) {//||Integer.parseInt(data[0])>1000000){//) {
					 long endTime = System.currentTimeMillis();
					 long seconds = (endTime - startTime) / 1000;	
					  System.out.println(seconds);
					  System.out.println(w);
					break;
				}
				process(data);
			}
		} catch (InterruptedException ex) {
				
		}
	}

	public Worker(BlockingQueue q,BlockingQueue q2, ArrayList<Chain> chains,ArrayList<Chain> removeList,Hashtable<Integer, Chain> idToChain) {
		this.queue = q;
		this.queue2 = q2;
		this.chains = chains;
		this.removeList=removeList;
		this.idToChain=idToChain;
		this.ChainToid=new Hashtable<Chain,Integer>();
	}

	private void process(String[] data) throws InterruptedException {
		Chain chain;
		score = 0;
		scoreTop3 = 0;
		top = new Top3();
		lasttimestamp=Double.parseDouble(data[1]);
		if(Integer.parseInt(data[0])%10000==0) {
			for(int i=0;i<removeList.size();i++) {
				idToChain.remove(removeList.get(i).getRootId());
				removeList.clear();
			}
		}
		if (chains.isEmpty()) {							
				chain = new Chain(Integer.parseInt(data[0]), lasttimestamp, data[3]);
				chains.add(chain);
				idToChain.put(Integer.parseInt(data[0]), chain);
		} else {
			Chain chainser=null;
			if(StringUtils.isNumericSpace(data[2])) {
				chainser = idToChain.get(Integer.parseInt(data[2]));
			}
			if (chainser == null) {
				chain = new Chain(Integer.parseInt(data[0]),lasttimestamp, data[3]);
				chains.add(chain);
				idToChain.put(Integer.parseInt(data[0]), chain);
			} else {		
				if (chainser.isOutofdate(Double.parseDouble(data[1]))) {		
					removeList.add(chainser);
					chain = new Chain(Integer.parseInt(data[0]), lasttimestamp, data[3]);
				} else {
					chainser.addTimestamp(lasttimestamp);
					idToChain.put(Integer.parseInt(data[0]), chainser);
				}				
			}	
		}
		//queue2.put(new ArrayList<Chain>(chains));

			for(int i = 0;i<chains.size();i++ ) {
				Chain chain1=chains.get(i);
						if (chain1.isOutofdate(lasttimestamp)) {	
							removeList.add(chain1);
							chains.remove(chain1);
						} else {
							if(scoreTop3<=chain1.getTimestampsize()*10) {								
								score = chain1.caclulScore(lasttimestamp);						
								if (score > scoreTop3) {
									scoreTop3 = top.addTop(chain1.getRootId(), chain1.getRootCountry(), score);
								}
							}
					}
			}
			System.out.println(top);	
	}
}

