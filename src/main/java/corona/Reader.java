package corona;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.apache.commons.io.FilenameUtils;
/*
 * La classe Reader va lire les csv les parser 
 * puis les envoyer dans une queue vers un worker
 */
public class Reader implements Runnable {

	private static double sizeLimit=300000;
	
	private String path;
	private int nbfiles;
	private BufferedReader[] bf;
	private String[][] all_data;
	private String[] countries;
	private static BlockingQueue<String[]> queue;
	private static int w=0;
	
	public Reader(String chemin, BlockingQueue<String[]> q) throws FileNotFoundException {
		
		path  = chemin;
		//path = "src\\main\\resources\\20\\";
		queue = q;
		nbfiles=0;
		File[] files = null;
        try {
            File f = new File(path);
            files = f.listFiles();
            nbfiles = files.length;
        }
        catch (Exception e) {
            throw new FileNotFoundException("Files not found");
        }
		
		all_data = new String[nbfiles][];
		bf = new BufferedReader[nbfiles];
		countries = new String[nbfiles];
		String[] all_data_init = { "-1" };		
		for (int i=0; i < nbfiles;i++) {
			bf[i] = new BufferedReader(new FileReader(path+files[i].getName()));
			all_data[i] = all_data_init;
			countries[i] = FilenameUtils.removeExtension(files[i].getName()); 
		}
	}
/*
 * Permet d'obtenir la prochaine ligne d'un fichier
 */
	public String[] getNextLine(int i) {
		String line;
		try {
			line = bf[i].readLine();
			if (line != null) {
				String[] data = line.split(",");
				return data;
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}


	public static boolean notNull(String[][] array) {
		for (String[] element : array) {
			if(element!=null) {
				return true; 
			}
		}
		return false;
	}
	
	public void run() {
		String[] used_data= new String[4];
		double minTimestamp = Double.POSITIVE_INFINITY;
		int min = 0;
		for (int i = 0; i < nbfiles; i++) {
			all_data[i] = getNextLine(i);
			if (Double.parseDouble(all_data[i][4]) < minTimestamp) {//Determine le premier cas de la prochaine ligne des trois fichiers
				minTimestamp = Double.parseDouble(all_data[i][4]);
				min = i;
			}
		}
		used_data[0] = all_data[min][0];
		used_data[1] = all_data[min][4].replace(" ", "");	
		used_data[2] = all_data[min][5].replace(" ", "");	
		used_data[3] = countries[min];
		try {
			queue.put(used_data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		all_data[min] = getNextLine(min);
		while (notNull(all_data)) {// Boucle temps que toute les données ne sont pas nulle
			used_data = new String[4];
			minTimestamp = Double.POSITIVE_INFINITY;

			for (int i = 0; i < nbfiles; i++) { 
				if (all_data[i] != null && Double.parseDouble(all_data[i][4]) < minTimestamp) {
					minTimestamp = Double.parseDouble(all_data[i][4]);
					min = i;
				}
			}
			
			if(Double.parseDouble( all_data[min][0])>sizeLimit) {// Limte de donnée à traiter
				break;
			}
			used_data[0] = all_data[min][0];
			used_data[1] = all_data[min][4];
			used_data[2] = all_data[min][5].replace(" ", "");			
			used_data[3] = countries[min];
			try {
;
					queue.put(used_data);
			} catch (InterruptedException e) {
				e.printStackTrace();
				
			}
			all_data[min] = getNextLine(min);
		}
		used_data = new String[4];
		try {			
			used_data[0] = "-1";
			used_data[1] = "-1";
			used_data[2] = "-1";			
			used_data[3]="-1";
			queue.put(used_data);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
