package database.jdbc;
import java.util.ArrayList;


/**
 * The Class HelperInfo.
 */
public class HelperInfo {
	
	/** The santa's little helper's id. */
	private int slhid;
	
	/** The name. */
	private String name;
	
	/** The set of children for a helper. */
	private ArrayList<ChildInfo> childInfo;
	
	/**
	 * Instantiates a new helper information
	 */
	public HelperInfo()
	{
		childInfo = new ArrayList<>();
	}
	
	/**
	 * Gets the santa's little helper's id
	 *
	 * @return the slhid
	 */
	public int getSlhid() {
		return slhid;
	}

	/**
	 * Sets the santa's little helper's id
	 *
	 * @param slhid the santa's little helper's id
	 */
	public void setSlhid(int slhid) {
		this.slhid = slhid;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the information about all the children for a single helper.
	 *
	 * @return the child info
	 */
	public ArrayList<ChildInfo> getChildInfo() {
		return childInfo;
	}

	/**
	 * Adds a single child's info for a helper
	 *
	 * @param childInfo the child info
	 */
	public void addChildInfo(ChildInfo childInfo) {
		this.childInfo.add(childInfo);
	}


	
	
}
