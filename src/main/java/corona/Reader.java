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

public class Reader implements Runnable {

	private String path;
	private int nbfiles;
	private BufferedReader[] bf;
	private String[][] all_data;
	private static BlockingQueue<String[]> queue;
	private  ArrayList<Chain> chains;
	private static BlockingQueue<ArrayList<Chain>> queue2;
	private static int w=0;
	
	public Reader(String chemin, BlockingQueue<String[]> q) throws FileNotFoundException {
		
		path  = chemin;
		//path = "src\\main\\resources\\20\\";
		//queue = new ArrayBlockingQueue<String[]>(1000);
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
		String[] all_data_init = { "-1" };		
		for (int i=0; i < nbfiles;i++) {
			bf[i] = new BufferedReader(new FileReader(path+files[i].getName()));
			all_data[i] = all_data_init;
		}
	}

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
			if(element!=null) return true; break;
		}
		return false;
	}
	
	public void run() {
		String[] used_data= new String[4];
		double minTimestamp = Double.POSITIVE_INFINITY;
		int min = 0;
		for (int i = 0; i < nbfiles; i++) {
			all_data[i] = getNextLine(i);
			if (Double.parseDouble(all_data[i][4]) < minTimestamp) {
				minTimestamp = Double.parseDouble(all_data[i][4]);
				min = i;
			}
		}
		used_data[0] = all_data[min][0];
		used_data[1] = all_data[min][4].replace(" ", "");	
		used_data[2] = all_data[min][5].replace(" ", "");	
		used_data[3] = "France";
		try {
			//w++;
			queue.put(used_data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		all_data[min] = getNextLine(min);
		while (notNull(all_data)) {
			used_data = new String[4];
			minTimestamp = Double.POSITIVE_INFINITY;

			for (int i = 0; i < nbfiles; i++) {
				if (all_data[i] != null && Double.parseDouble(all_data[i][4]) < minTimestamp) {
					minTimestamp = Double.parseDouble(all_data[i][4]);
					min = i;
				}
			}
			//System.out.println(all_data[min][0]);
			used_data[0] = all_data[min][0];
			used_data[1] = all_data[min][4];
			used_data[2] = all_data[min][5].replace(" ", "");			
			used_data[3] = "France";
			 //System.out.println(used_data[0]+" "+used_data[2]);
			try {
					//w++;
					//System.out.println(w);
					queue.put(used_data);	
			} catch (InterruptedException e) {
				e.printStackTrace();
				
			}
			all_data[min] = getNextLine(min);
		}
		//System.out.println(w);
		try {
			used_data[0] = "-1";
			queue.put(used_data);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws InterruptedException{ 
		queue = new ArrayBlockingQueue<String[]>(100);
		queue2 = new ArrayBlockingQueue<ArrayList<Chain>>(100);
		Reader rd;
		long startTime = System.currentTimeMillis();
		try {
			rd = new Reader("src\\main\\resources\\1000000\\",queue);
			Thread t1 =new Thread(rd);  
			t1.start();  
			ArrayList<Chain> chains = new  ArrayList<Chain>();
			ArrayList<Chain> removeList= new  ArrayList<Chain>();
			Hashtable<Integer, Chain> idToChain = new Hashtable<Integer, Chain>();
			Worker worker = new Worker(queue,queue2,chains,removeList,idToChain);
			Thread t2 = new Thread(worker, "t2");
			//Worker2 worker2 = new Worker2(queue2,chains,removeList,idToChain);
			//Thread t3 = new Thread(worker2, "t3");
			//Thread t4= new Thread(worker, "t4");			
			//Thread t4= new Thread(worker, "t4");	
			//Thread t5= new Thread(worker, "t5");	
			t2.start();
			//t3.sleep(1000);
			//t3.start();
			//t4.start();	
			//t5.start();

			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 long endTime = System.currentTimeMillis();
		  long seconds = (endTime - startTime) / 1000;	
		  System.out.println("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");

	}  
	
}
