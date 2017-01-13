package database.jdbc;
/*
 * 
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The Class RetrieveInfo for getting infomartion from database
 */
public class RetrieveInfo {
	
	/**  The connection to the database. */
	private Connection conn;
	
	/** The child query. */
	private PreparedStatement childQuery;
	
	/** The gifts of child query. */
	private PreparedStatement giftsOfChildQuery;
	
	/** The helper. */
	private PreparedStatement helperQuery;
	
	/** The child. */
	private PreparedStatement childrenQuery;
	
	/** The presents. */
	private PreparedStatement presentsQuery;
	
	/**
	 * Instantiates a new retrieve info.
	 *
	 * @param conn the conn
	 */
	public RetrieveInfo(Connection conn	)
	{
		this.conn = conn;
		setStatements();
	}
	
	
	/**
	 * Sets the database statements for later use.
	 */
	private void setStatements()
	{
		try {
			//getting the information about a single child from database
			childQuery = conn.prepareStatement(	"SELECT C.cid, C.name, C.address "
											  + "FROM Child C "
											  + "WHERE C.cid=? ;");
		
			//getting all gifts for a single child
			giftsOfChildQuery = conn.prepareStatement("SELECT G.gid, G.description "
													+ "FROM Present P, Gift G " 
													+ "WHERE P.cid=? AND G.gid = P.gid ;");
			
			//getting all the information about a single santa's little helper
			helperQuery = conn.prepareStatement("SELECT H.slhid, H.name " 
											  + " FROM SantasLittleHelper H "
											  + " WHERE H.slhid= ?");
			
			//getting all the children which belong to a particular helper
			childrenQuery = conn.prepareStatement("SELECT Ch.cid, Ch.name, Ch.address "
												+ " FROM Child Ch "
												+ "WHERE Ch.cid IN "
												+ "(SELECT C.cid "
									   			+ "FROM Child C, Present P "
									   			+ "WHERE P.slhid = ? AND P.cid = C.cid);");

			//getting all the presents depending upon child and helper
			presentsQuery = conn.prepareStatement("SELECT G.gid, G.description "
												+ "FROM Gift G, Present P "
												+ "WHERE P.cid = ? AND P.slhid = ? AND G.gid = P.gid ;");
		} catch (SQLException e) {
			System.err.println("Badly designed statements");
			System.exit(1);
		}


	}
	
	
	/**
	 * Returns the all the information for a particular child
	 *
	 * @param cid the child id
	 * @return the child information
	 */
	public ChildInfo getChildInfo(int cid)
	{
		ChildInfo childInfo = new ChildInfo();
		
		try {
			//sets the parameters for the queries
			childQuery.setInt(1, cid);
			giftsOfChildQuery.setInt(1, cid);
			
			//gets the results from database
			ResultSet childRes = childQuery.executeQuery();
			ResultSet giftRes  = giftsOfChildQuery.executeQuery();
			
			//temporary variables for use in the loop
			int childID;
			String name = null;
			String address = null;
			
			//getting information about the child
			while(childRes.next())
			{
				childID =Integer.parseInt(childRes.getString("cid"));
				name = childRes.getString("name");
				address = childRes.getString("address");
				childInfo.setCid(childID);
				childInfo.setName(name);
				childInfo.setAddress(address);
			}
			
			//temporary variables for use in the loop
			int gid;
			String desc = null;
			
			//getting all the gifts which belongs to a particular child
			while(giftRes.next())
			{
				gid = Integer.parseInt(giftRes.getString("gid"));
				desc = giftRes.getString("description");
				childInfo.addGift(gid, desc);
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Badly designed code", "Error", JOptionPane.WARNING_MESSAGE);
			System.exit(1);
		}
		
		return childInfo;
	}
	
	
	/**
	 * Returns all the necessary information about the particular helper.
	 *
	 * @param lshid the santa's little helper's id
	 * @return the helper info
	 */
	public HelperInfo getHelperInfo(int lshid)
	{
		HelperInfo helperInfo = new HelperInfo();
		
		try {
			//sets the parameters for the queries
			presentsQuery.setInt(2, lshid);
			helperQuery.setInt(1, lshid);
			childrenQuery.setInt(1, lshid);
			
			//gets the results from the database
			ResultSet helperRes = helperQuery.executeQuery();
			ResultSet childRes = childrenQuery.executeQuery();
			
			//temporary variables for use in the loop
			int hid;
			String name= null;
			
			//getting information about the helper (id and name)
			while(helperRes.next())
			{
				hid = Integer.parseInt(helperRes.getString("slhid"));
				name = helperRes.getString("name");
				helperInfo.setSlhid(hid);
				helperInfo.setName(name);
			}
			
			//temporary variables for use in the database
			int cid;
			String chName = null;
			String address = null;
			ChildInfo chInfo;
			
			//getting the information for a particular child and presents for that child
			while(childRes.next())
			{
				//getting information about the child
				chInfo = new ChildInfo();
				
				cid = Integer.parseInt(childRes.getString("cid"));
				chName = childRes.getString("name");
				address = childRes.getString("address");
				
				chInfo.setCid(cid);
				chInfo.setName(chName);
				chInfo.setAddress(address);

				//getting all the presents for that child
				presentsQuery.setInt(1, cid);
				ResultSet childPres = presentsQuery.executeQuery();
				while(childPres.next())
				{
					int gid ;
					String desc = null;
					gid = Integer.parseInt(childPres.getString("gid"));
					desc = childPres.getString("description");
					chInfo.addGift(gid, desc);
				}
				
				//adding child to the helper information
				helperInfo.addChildInfo(chInfo);
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Badly designed code", "Error", JOptionPane.WARNING_MESSAGE);
			System.exit(1);
		}
		return helperInfo;

	}
	
	
}
