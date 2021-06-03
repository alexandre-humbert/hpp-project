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


/*
 * La classe Worker est le thread qui va prende en entrée les personnes
 * entrées par le Reader le permettre  de créer et mettre à jour les chaînes 
 * puis de calculer leur score et de l'envoyer au Writer
 */
public class Worker implements Runnable {

	private static BlockingQueue<String[]> queue;
	private static BlockingQueue<String> queuewriter;
	private ArrayList<Chain> chains;// Contient toutes les chaîne active va nous permettre de mettre à jour leur score
	private ArrayList<Chain> removeList;// Contient les chaînes qui vont se faire supprimer
	private  Hashtable<Integer, Chain> idToChain;// Fais la correspondance entre les ids et leur chaîne
	private int score = 0;
	private int scoreTop3 = 0;
	private Top3 top ;
	private double lasttimestamp;

	private ReentrantLock lock = new ReentrantLock(true);
	private static int w=0;

	public void run() {
		try {
			long startTime = System.currentTimeMillis();
			while (true) {
				String[] data = queue.take();
				if(Integer.parseInt(data[0])==-1){//controle du message d'interuption
					 /*long endTime = System.currentTimeMillis();
					  long seconds = (endTime - startTime) / 1000;	
					  System.out.println(seconds);
					  System.out.println(w);*/
					  queuewriter.put("-1");//interruption du writer
					break;
				}
				process(data);
			}
		} catch (InterruptedException ex) {
				
		}
	}

	public Worker(BlockingQueue q, BlockingQueue q2) {
		this.queue = q;
		this.queuewriter = q2;
		this.chains = new  ArrayList<Chain>();
		this.removeList= new  ArrayList<Chain>();
		this.idToChain = new Hashtable<Integer, Chain>();
	}

	private void process(String[] data) throws InterruptedException {
		Chain chain;
		score = 0;
		scoreTop3 = 0;
		top = new Top3();
		lasttimestamp=Double.parseDouble(data[1]);
		if(Integer.parseInt(data[0])%1000==0) {// Suppresion tous les 1000 ids
				for(int i=0;i<removeList.size();i++) {
					idToChain.remove(removeList.get(i).getRootId());
					chains.remove(removeList.get(i));
			}	
			removeList.clear();
		}

		if (chains.isEmpty()) {// Cas ou chains est vide					
				chain = new Chain(Integer.parseInt(data[0]), lasttimestamp, data[3]);
				chains.add(chain);
				idToChain.put(Integer.parseInt(data[0]), chain);
		} else {
			Chain chainser=null;
			if(StringUtils.isNumericSpace(data[2])) {//Test si unknow 
				chainser = idToChain.get(Integer.parseInt(data[2]));//Retrouve une chaîne à partir d'un id
			}
			if (chainser == null) {//Si la chaîne n'existe pas on en créée une nouvelle
				chain = new Chain(Integer.parseInt(data[0]),lasttimestamp, data[3]);
				chains.add(chain);
				idToChain.put(Integer.parseInt(data[0]), chain);
			} else {		
				if (chainser.isOutofdate(Double.parseDouble(data[1]))) {//Test si la chaîne n'est pas trop ancienne
					removeList.add(chainser);
					chain = new Chain(Integer.parseInt(data[0]), lasttimestamp, data[3]);
					idToChain.put(Integer.parseInt(data[0]), chain);
					chains.add(chain);
				} else {//Si elle n'est pas trop ancienne on rajoute un timestamp à la chaîne 
					chainser.addTimestamp(lasttimestamp);
					idToChain.put(Integer.parseInt(data[0]), chainser);//On relie l'id à sa chaîne
				}				
			}	
		}
			for(int i = 0;i<chains.size();i++ ) {
				Chain chain1=chains.get(i);
						if (chain1.isOutofdate(lasttimestamp)) {	
							removeList.add(chain1);		
						} else {
							if(scoreTop3<=chain1.getTimestampsize()*10){//Test si le top trois est possible à atteindre pour une chaîne avec getTimestampsize son nombre actif de timestamp
								score = chain1.caclulScore(lasttimestamp);								
								if (score >= scoreTop3) {
									scoreTop3 = top.addTop(chain1.getRootId(), chain1.getRootCountry(), score,chain1.getRootTimestamp());//Si le score est supérieur au top trois il
																																		 // pourra être rajouté dedans et maj du score min du top3
								}
							}
					}
			}
			queuewriter.put(top.toString());
			//System.out.println(top);	
	}
}

