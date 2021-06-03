package corona;

import java.util.ArrayList;

public class Chain {

	private int rootId;
	private String rootCountry;
	private double rootTimestamp;
	private ArrayList<Integer> listofid;
	private ArrayList<Double> listoftimestamp;// liste de tous les timestamp de la liste
	private int score;
	private double lasttimestamp;// dernier timestamp depuis sa mise à jour
	private int timestampsize;// taille des timestamp encore actif

	public Chain(int id, double timestamp, String country) {
		super();
		this.rootId = id;
		this.rootCountry = country;
		this.listoftimestamp = new ArrayList<Double>();
		this.listoftimestamp.add(timestamp);
		this.score = 10;
		this.lasttimestamp = timestamp;
		this.timestampsize=1;
		this.rootTimestamp=timestamp;
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

	public double getLasttimestamp() {
		return lasttimestamp;
	}

	public void setLasttimestamp(double lasttimestamp) {
		this.lasttimestamp = lasttimestamp;
	}
	public double getRootTimestamp() {
		return rootTimestamp;
	}

	public void setRootTimestamp(double rootTimestamp) {
		this.rootTimestamp = rootTimestamp;
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
		for (int i = this.listoftimestamp.size()-1; i >= this.listoftimestamp.size()-timestampsize; i--) {
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
