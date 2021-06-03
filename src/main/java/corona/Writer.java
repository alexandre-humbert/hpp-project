package corona;

import java.util.concurrent.BlockingQueue;
/*
 * La classe Writer va simplement prendre le score en entrée puis l'afficher,
 * il serait possible de la modifier pour le mettre dans un fichier à la place 
 */
public class Writer implements Runnable {

	private static BlockingQueue<String> queuewriter;
	private long op;
	public void run() {
		try {
			long startTime = System.currentTimeMillis();
			while (true) {				
				String data = queuewriter.take();
				if(data=="-1"){//) {
					 long endTime = System.currentTimeMillis();
					 long seconds = (endTime - startTime) / 1000;	
					  System.out.println(" size : "+(op-1)+"; seconds : "+seconds+"; ms/op : "+(endTime - startTime)/(float)op+";");
					break;
				}
				System.out.println(data);
				op++;
			}
		} catch (InterruptedException ex) {
				
		}
	}
	public Writer(BlockingQueue<String> queuewriter) {
		this.queuewriter=queuewriter;
	}
}
