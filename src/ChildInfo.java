import java.util.ArrayList;

public class ChildInfo {
	private int cid;
	private String name;
	private String address;
	private ArrayList<GiftInfo> gifts ;
	
	public ChildInfo()
	{
		gifts = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ArrayList<GiftInfo> getGifts() {
		return gifts;
	}

	public void addGift(int gid, String desc) {
		gifts.add(new GiftInfo(gid, desc));
	}

	public void setCid(int cid) {
		this.cid = cid;
	}
	
	public int getCid() {
		return cid;
	}
}
