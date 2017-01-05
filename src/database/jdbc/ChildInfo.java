package database.jdbc;
import java.util.ArrayList;


/**
 * The Class ChildInfo.
 */
public class ChildInfo {
	
	/** The child id. */
	private int cid;
	
	/** The name. */
	private String name;
	
	/** The address. */
	private String address;
	
	/** The gifts. */
	private ArrayList<GiftInfo> gifts ;
	
	/**
	 * Instantiates a new child info.
	 */
	public ChildInfo()
	{
		gifts = new ArrayList<>();
	}

	/**
	 * Gets the name of a single child
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of a single child
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the address of a child.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the set of gifts to for a particular child
	 *
	 * @return the gifts
	 */
	public ArrayList<GiftInfo> getGifts() {
		return gifts;
	}

	/**
	 * Adds the gift to the set of gifts.
	 *
	 * @param gid the gift id
	 * @param desc the description
	 */
	public void addGift(int gid, String desc) {
		gifts.add(new GiftInfo(gid, desc));
	}

	/**
	 * Sets the child id
	 *
	 * @param cid the child id
	 */
	public void setCid(int cid) {
		this.cid = cid;
	}
	
	/**
	 * Gets the child id
	 *
	 * @return the child id
	 */
	public int getCid() {
		return cid;
	}
}
