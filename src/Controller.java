import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
	private View view;
	private RetrieveInfo model;
	
	public Controller(View view, RetrieveInfo model)
	{
		this.view = view;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String selection = view.getTypeOfSelection();
		int pk = view.getPrimaryKey();
		switch(selection)
		{
			case View.childInfo :
				view.updateChild(model.printChildInfo(pk));
			case View.helperInfo :
				view.updateHelper(model.printHelperInfo(pk));
		}
		view.repaint();
		
	}
}
