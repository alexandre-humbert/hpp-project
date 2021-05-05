package corona;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Reader {

	private BufferedReader bf;
	private static BlockingQueue<String[]> queue;
	
	public Reader(String chemin,BlockingQueue q) throws FileNotFoundException {
		bf = new BufferedReader(new FileReader(chemin));
		queue=q;
	}

	public String[] getNextLine() {
		String line;
		try {
			line = bf.readLine();
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
			if(element!=null) return true; break;
		}
		return false;
	}
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		String path  = "src\\main\\resources\\20\\";
		queue = new ArrayBlockingQueue(1000);
		int nbfiles=0;
		File[] files = null;
        try {
            File f = new File(path);
            files = f.listFiles();
            nbfiles = files.length;
        }
        catch (Exception e) {
            throw new FileNotFoundException("Files not found");
        }
		
		Reader[] naiv  = new Reader[nbfiles];
		String[] used_data= new String[4];
		String[][] all_data = new String[nbfiles][];
		String[] all_data_init = { "-1" };		
		for (int i=0; i < nbfiles;i++) {
			naiv[i] = new Reader(path+ files[i].getName(), queue);
			all_data[i] = all_data_init;
		}
		
		double minTimestamp = Double.POSITIVE_INFINITY;
		int min = 0;
		for (int i = 0; i < nbfiles; i++) {
			all_data[i] = naiv[i].getNextLine();
			if (Double.parseDouble(all_data[i][4]) < minTimestamp) {
				minTimestamp = Double.parseDouble(all_data[i][4]);
				min = i;
			}
		}
		used_data[0] = all_data[min][0];
		used_data[1] = all_data[min][4];
		used_data[2] = all_data[min][5];
		used_data[3] = "France";
		queue.put(used_data);
		all_data[min] = naiv[min].getNextLine();
		while (notNull(all_data)) {
			minTimestamp = Double.POSITIVE_INFINITY;

			for (int i = 0; i < nbfiles; i++) {
				if (all_data[i] != null && Double.parseDouble(all_data[i][4]) < minTimestamp) {
					minTimestamp = Double.parseDouble(all_data[i][4]);
					min = i;
				}
			}
			used_data[0] = all_data[min][0];
			used_data[1] = all_data[min][4];
			used_data[2] = all_data[min][5];
			used_data[3] = "France";
			System.out.println(used_data[0]+" "+used_data[1]);
			queue.put(used_data);
			all_data[min] = naiv[min].getNextLine();
		}
	}
}
