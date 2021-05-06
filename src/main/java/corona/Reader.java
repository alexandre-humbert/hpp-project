package corona;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Reader {

	private BufferedReader bf;
	private static BlockingQueue<String[]> queue;
	private static int next_id = 0;

	public Reader(String chemin, BlockingQueue q) throws FileNotFoundException {
		bf = new BufferedReader(new FileReader(chemin));
		queue = q;
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

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		String[][] all_data = { { "-1" }, { "-1" }, { "-1" } };
		String[] used_data = new String[4];
		queue = new ArrayBlockingQueue(5000);
		Reader[] naiv = { new Reader("src\\main\\resources\\5000\\France.csv", queue),
				new Reader("src\\main\\resources\\5000\\Italy.csv", queue),
				new Reader("src\\main\\resources\\5000\\Spain.csv", queue) };
		double minTimestamp = Double.POSITIVE_INFINITY;
		int min = 0;
		for (int i = 0; i < 3; i++) {
			all_data[i] = naiv[i].getNextLine();
			if (Double.parseDouble(all_data[i][4]) < minTimestamp) {
				minTimestamp = Double.parseDouble(all_data[i][4]);
				min = i;
			}
		}
		used_data[0] = all_data[min][0];
		used_data[1] = all_data[min][4].replace(" ", "");	
		used_data[2] = all_data[min][5].replace(" ", "");	
		used_data[3] = "France";
		queue.put(used_data);
		all_data[min] = naiv[min].getNextLine();
		while (all_data[0] != null || all_data[1] != null || all_data[2] != null) {
			used_data = new String[4];
			minTimestamp = Double.POSITIVE_INFINITY;

			for (int i = 0; i < 3; i++) {
				if (all_data[i] != null && Double.parseDouble(all_data[i][4]) < minTimestamp) {
					minTimestamp = Double.parseDouble(all_data[i][4]);
					min = i;
				}
			}
			used_data[0] = all_data[min][0];
			used_data[1] = all_data[min][4];
			used_data[2] = all_data[min][5].replace(" ", "");			
			used_data[3] = "France";
			System.out.println(used_data[2]);
			queue.put(used_data);
			all_data[min] = naiv[min].getNextLine();
		}
		ArrayList<Chain> chains = new  ArrayList<Chain>();
		ArrayList<Chain> removeList= new  ArrayList<Chain>();
		Hashtable<Integer, Chain> idToChain = new Hashtable<Integer, Chain>();
		Worker worker = new Worker(queue,chains,removeList,idToChain);
		Thread t1 = new Thread(worker);
		t1.start();
	}
}
