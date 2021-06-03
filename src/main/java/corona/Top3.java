package corona;

public class Top3 {

	private String top1_country_origin=null;
	private int top1_chain_root_person_id=0;
	private int top1_chain_score=0;
	private double top1_rootimestamp=0;
	private String top2_country_origin=null;
	private int top2_chain_root_person_id=0;
	private int top2_chain_score=0;
	private double top2_rootimestamp=0;
	private String top3_country_origin=null;
	private int top3_chain_root_person_id=0;
	private int top3_chain_score=0;
	private double top3_rootimestamp=0;
	
	public int addTop(int rootid,String country,int score,double  rootimestamp) {
		if(score>top1_chain_score||(score==top1_chain_score&&rootimestamp<top1_rootimestamp)) {
			this.top3_country_origin=top2_country_origin;
			this.top3_chain_root_person_id=top2_chain_root_person_id;
			this.top3_chain_score=top2_chain_score;
			this.top3_rootimestamp=top2_rootimestamp;
			this.top2_country_origin=top1_country_origin;
			this.top2_chain_root_person_id=top1_chain_root_person_id;
			this.top2_chain_score=top1_chain_score;
			this.top2_rootimestamp=top1_rootimestamp;
			this.top1_country_origin=country;
			this.top1_chain_root_person_id=rootid;
			this.top1_chain_score=score;
			this.top1_rootimestamp=rootimestamp;
		}else {
			if(score>top2_chain_score||(score==top2_chain_score&&rootimestamp<top2_rootimestamp)) {
				this.top3_country_origin=top2_country_origin;
				this.top3_chain_root_person_id=top2_chain_root_person_id;
				this.top3_chain_score=top2_chain_score;
				this.top3_rootimestamp=top2_rootimestamp;
				this.top2_country_origin=country;
				this.top2_chain_root_person_id=rootid;
				this.top2_chain_score=score;
				this.top2_rootimestamp=rootimestamp;
			}else {
				if(score>top3_chain_score||(score==top3_chain_score&&rootimestamp<top3_rootimestamp)) {
					this.top3_country_origin=country;
					this.top3_chain_root_person_id=rootid;
					this.top3_chain_score=score;
					this.top3_rootimestamp=rootimestamp;
				}
			}
		}
		return this.top3_chain_score;
	}
	
	public String getTop1_country_origin() {
		return top1_country_origin;
	}
	public void setTop1_country_origin(String top1_country_origin) {
		this.top1_country_origin = top1_country_origin;
	}
	public int getTop1_chain_root_person_id() {
		return top1_chain_root_person_id;
	}
	public void setTop1_chain_root_person_id(int top1_chain_root_person_id) {
		this.top1_chain_root_person_id = top1_chain_root_person_id;
	}
	public int getTop1_chain_score() {
		return top1_chain_score;
	}
	public void setTop1_chain_score(int top1_chain_score) {
		this.top1_chain_score = top1_chain_score;
	}
	public String getTop2_country_origin() {
		return top2_country_origin;
	}
	public void setTop2_country_origin(String top2_country_origin) {
		this.top2_country_origin = top2_country_origin;
	}
	public int getTop2_chain_root_person_id() {
		return top2_chain_root_person_id;
	}
	public void setTop2_chain_root_person_id(int top2_chain_root_person_id) {
		this.top2_chain_root_person_id = top2_chain_root_person_id;
	}
	public int getTop2_chain_score() {
		return top2_chain_score;
	}
	public void setTop2_chain_score(int top2_chain_score) {
		this.top2_chain_score = top2_chain_score;
	}
	public String getTop3_country_origin() {
		return top3_country_origin;
	}
	public void setTop3_country_origin(String top3_country_origin) {
		this.top3_country_origin = top3_country_origin;
	}
	public int getTop3_chain_root_person_id() {
		return top3_chain_root_person_id;
	}
	public void setTop3_chain_root_person_id(int top3_chain_root_person_id) {
		this.top3_chain_root_person_id = top3_chain_root_person_id;
	}
	public int getTop3_chain_score() {
		return top3_chain_score;
	}
	public void setTop3_chain_score(int top3_chain_score) {
		this.top3_chain_score = top3_chain_score;
	}

	@Override
	public String toString() {
		String output = "[[" + top1_country_origin + ", root_id="
				+ top1_chain_root_person_id + ", score=" + top1_chain_score + "]";
		if (top2_country_origin != null) {
			output += "[" + top2_country_origin + ", root_id=" + top2_chain_root_person_id + ", score=" + top2_chain_score +"]";
		}
		if (top3_country_origin != null) {
			output += "[" + top3_country_origin + ", root_id=" + top3_chain_root_person_id + ", score=" + top3_chain_score +"]";
		}
		return output + "]";
	}
	

}
