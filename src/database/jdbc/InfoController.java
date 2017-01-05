package database.jdbc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO: Auto-generated Javadoc
/**
 * The Class InfoController.
 */
public class InfoController implements ActionListener {

	/** The model for retrieving information from database. */
	private RetrieveInfo model;
	
	/** The view, the GUI display */
	private View view;
	
	
	/**
	 * Instantiates a new info controller.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public InfoController(RetrieveInfo model, View view)
	{
		this.model = model;
		this.view = view;
	}
	
	
	/* 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//when action is performed we get type of selection (child or helper info)
		String selection = view.getTypeOfSelection();
		
		//then we get the primary key which user inputs
		int pk = view.getPrimaryKey();
		
		//depending upon selection we get the information from the database and update the view
		switch(selection)
		{
			case View.childInfo :
				view.updateChild(model.getChildInfo(pk));
				break;
			case View.helperInfo :
				view.updateHelper(model.getHelperInfo(pk));
				break;
		}
		
		//update the view
		view.repaint();
		

	}

}
