package database.jdbc;


/**
 * The Class GiftInfo.
 */
public class GiftInfo {
	
	/** The gift id */
	private int gid;
	
	/** The description */
	private String desc;
	
	/**
	 * Instantiates a new gift info.
	 *
	 * @param gid the gift id
	 * @param desc the description
	 */
	public GiftInfo(int gid, String desc)
	{
		this.gid = gid;
		this.desc = desc;
	}
	
	/**
	 * Gets the gift id
	 *
	 * @return the gid
	 */
	public int getGid()
	{
		return gid;
	}
	
	/**
	 * Gets the description
	 *
	 * @return the desc
	 */
	public String getDesc()
	{
		return desc;
	}
}
