package corona;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Naiv {

	private BufferedReader bf;
	private static BlockingQueue queue;
	private static int next_id = 0;

	public Naiv(String chemin,BlockingQueue q) throws FileNotFoundException {
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

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		String[][] all_data = { { "-1" }, { "-1" }, { "-1" } };
		String[] used_data= new String[4];
		queue = new ArrayBlockingQueue(1000);
		Naiv[] naiv = { new Naiv("src\\main\\resources\\20\\France.csv",queue),
				new Naiv("src\\main\\resources\\20\\Italy.csv",queue),
				new Naiv("src\\main\\resources\\20\\Spain.csv",queue) };
		while (all_data[0] != null && all_data[1] != null && all_data[2] != null) {
			for (int i = 0; i < 3; i++) {
				if (Integer.parseInt(all_data[i][0]) <= next_id) {
					if (Integer.parseInt(all_data[i][0]) == next_id) {
						System.out.println(all_data[i][0]+" "+all_data[i][5]);
						used_data[0]=all_data[i][0];
						used_data[1]=all_data[i][4];
						used_data[2]=all_data[i][5];
						used_data[3]="France";
						queue.put(used_data);
						next_id++;
						break;
					} else {
						all_data[i] = naiv[i].getNextLine();
						if (all_data[i] != null && Integer.parseInt(all_data[i][0]) == next_id) {
							System.out.println(all_data[i][0]+" "+all_data[i][5]);
							used_data[0]=all_data[i][0];
							used_data[1]=all_data[i][4];
							used_data[2]=all_data[i][5];
							used_data[3]="France";
							queue.put(used_data);
							next_id++;
							break;
						}
					}
				}
			}
		}
	}
}
