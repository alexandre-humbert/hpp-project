package corona;

import java.util.ArrayList;

public class Chain {

	private int rootId;
	private String rootCountry;
	private ArrayList<Integer> listofid;
	private ArrayList<Double> listoftimestamp;
	private int score;
	private double lasttimestamp;

	public Chain(int id, double timestamp, String country) {
		super();
		this.rootId = id;
		this.rootCountry = country;
		this.listofid = new ArrayList<Integer>();
		this.listoftimestamp = new ArrayList<Double>();
		this.listofid.add(id);
		this.listoftimestamp.add(timestamp);
		this.score = 10;
		this.lasttimestamp = timestamp;
	}

	public int getRootId() {
		return rootId;
	}

	public void setRootId(int rootId) {
		this.rootId = rootId;
	}

	public String getRootCountry() {
		return rootCountry;
	}

	public void setRootCountry(String rootCountry) {
		this.rootCountry = rootCountry;
	}

	public ArrayList<Integer> getListofid() {
		return listofid;
	}

	public void setListofid(ArrayList<Integer> listofid) {
		this.listofid = listofid;
	}

	public ArrayList<Double> getListoftimestamp() {
		return listoftimestamp;
	}

	public void setListoftimestamp(ArrayList<Double> listoftimestamp) {
		this.listoftimestamp = listoftimestamp;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public double getLasttimestamp() {
		return lasttimestamp;
	}

	public void setLasttimestamp(double lasttimestamp) {
		this.lasttimestamp = lasttimestamp;
	}

	public void addPerson(int id, double timestamp) {
		this.listofid.add(id);
		this.listoftimestamp.add(timestamp);
	}

	public void caclulScore() {
		int scoret = 0;
		for (int i = this.listoftimestamp.size(); i > 0; i--) {
			if (lasttimestamp - listoftimestamp.get(i) <= 604800) {
				scoret = +10;
			} else {
				if (lasttimestamp - listoftimestamp.get(i) > 604800
						&& lasttimestamp - listoftimestamp.get(i) <= 1209600) {
					scoret = +4;
				} else {
					break;
				}
			}
		}
		this.score=scoret;
	}
	
	public boolean isOutofdate(Double timestamp) {
		if(timestamp-this.lasttimestamp> 1209600) {
			return false;
		}
		return true;

	}
}
