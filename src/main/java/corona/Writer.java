package corona;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import static corona.Main_corona.DEBUG;
import static corona.Main_corona.BENCHMARK;
/*
 * La classe Writer va simplement prendre le score en entrée puis l'afficher,
 * il serait possible de la modifier pour le mettre dans un fichier à la place 
 */
public class Writer implements Runnable {
	private static BlockingQueue<String> queuewriter;
	BufferedWriter  write;
	private long op;
	public void run() {
		try {
			long startTime = System.currentTimeMillis();
			while (true) {				
				String data = queuewriter.take();
				if(data=="-1"){//) {
					 long endTime = System.currentTimeMillis();
					 double seconds = (endTime - startTime) / 1000.0;
					 if (BENCHMARK) {
					  System.out.println("Writer : size : "+op+"; seconds : "+seconds+"; ms/op : "+(endTime - startTime)/(float)op+";");
					 }
					 write.flush();
					 break;
				}
				System.out.println(data);
				write.append(data);
				write.write(System.getProperty( "line.separator" ));
				op++;
			}
		} catch (InterruptedException | IOException ex) {
				
		}
	}
	public Writer(BlockingQueue<String> queuewriter) throws IOException {
		this.queuewriter=queuewriter;
		write= new BufferedWriter(new FileWriter("Score.csv"));
	}
}
