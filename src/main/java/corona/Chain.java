package corona;

import java.util.ArrayList;

public class Chain {

	private int rootId;
	private String rootCountry;
	private ArrayList<Integer> listofid;
	private ArrayList<Double> listoftimestamp;
	private int score;
	private double lasttimestamp;
	private int timestampsize;
	private int calculCompter=0;

	public Chain(int id, double timestamp, String country) {
		super();
		this.rootId = id;
		this.rootCountry = country;
		//this.listofid = new ArrayList<Integer>();
		this.listoftimestamp = new ArrayList<Double>();
		//this.listofid.add(id);
		this.listoftimestamp.add(timestamp);
		this.score = 10;
		this.lasttimestamp = timestamp;
		this.timestampsize=1;
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

	/*public ArrayList<Integer> getListofid() {
		return listofid;
	}

	public void setListofid(ArrayList<Integer> listofid) {
		this.listofid = listofid;
	}*/

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

	public int getCalculCompter() {
		return score;
	}

	public void addCalculCompter() {
		this.calculCompter ++;
	}
	public void resetCalculCompter() {
		this.calculCompter=0;
	}
	public double getLasttimestamp() {
		return lasttimestamp;
	}

	public void setLasttimestamp(double lasttimestamp) {
		this.lasttimestamp = lasttimestamp;
	}

	public void addTimestamp( double timestamp) {
		this.listoftimestamp.add(timestamp);
		this.lasttimestamp=timestamp;
		this.timestampsize+=1;
	}

	public int getTimestampsize() {
		return this.timestampsize;
	}
	
	public int caclulScore(double timestamp) {
		int scoret = 0;
		for (int i = this.listoftimestamp.size()-1; i > this.listoftimestamp.size()-timestampsize; i--) {
			if (timestamp - listoftimestamp.get(i) <= 604800) {
				scoret +=10;
			} else {
				if (timestamp - listoftimestamp.get(i) > 604800
						&& timestamp - listoftimestamp.get(i) <= 1209600) {
					scoret += 4;
				} else {
					this.timestampsize=(listoftimestamp.size()-i-1);
					break;
				}
			}
		}
		this.score=scoret;
		return scoret;
	}
	
	public boolean isOutofdate(Double timestamp) {
		if(timestamp-this.lasttimestamp> 1209600) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Chain [rootId=" + rootId + ", rootCountry=" + rootCountry + ", listofid=" + listofid
				+ ", listoftimestamp=" + listoftimestamp + ", score=" + score + ", lasttimestamp=" + lasttimestamp
				+ "]";
	}
	
}
