import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoController implements ActionListener {

	private RetrieveInfo model;
	private View view;
	
	
	public InfoController(RetrieveInfo model, View view)
	{
		this.model = model;
		this.view = view;
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
