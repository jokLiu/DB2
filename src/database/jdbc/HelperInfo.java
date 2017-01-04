package database.jdbc;
import java.util.ArrayList;

public class HelperInfo {
	private int slhid;
	private String name;
	private ArrayList<ChildInfo> childInfo;
	
	public HelperInfo()
	{
		childInfo = new ArrayList<>();
	}
	
	public int getSlhid() {
		return slhid;
	}

	public void setSlhid(int slhid) {
		this.slhid = slhid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ChildInfo> getChildInfo() {
		return childInfo;
	}

	public void addChildInfo(ChildInfo childInfo) {
		this.childInfo.add(childInfo);
	}


	
	
}
