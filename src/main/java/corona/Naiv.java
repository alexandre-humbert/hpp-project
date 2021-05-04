package corona;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Naiv {

	BufferedReader bf;
	static int next_id = 0;

	public Naiv(String chemin) throws FileNotFoundException {
		bf = new BufferedReader(new FileReader(chemin));
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

	public static void main(String[] args) throws FileNotFoundException {
		String[][] all_data = { { "-1" }, { "-1" }, { "-1" } };
		Naiv[] naiv = { new Naiv("src\\main\\resources\\20\\France.csv"),
				new Naiv("src\\main\\resources\\20\\Italy.csv"),
				new Naiv("src\\main\\resources\\20\\Spain.csv") };
		while (all_data[0] != null && all_data[1] != null && all_data[2] != null) {
			for (int i = 0; i < 3; i++) {
				if (Integer.parseInt(all_data[i][0]) <= next_id) {
					if (Integer.parseInt(all_data[i][0]) == next_id) {
						System.out.println(all_data[i][0]);
						next_id++;
						break;
					} else {
						all_data[i] = naiv[i].getNextLine();
						if (all_data[i] != null && Integer.parseInt(all_data[i][0]) == next_id) {
							System.out.println(all_data[i][0]);
							next_id++;
							break;
						}
					}
				}
			}
		}
	}
}
