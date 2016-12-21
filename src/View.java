import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class View extends JFrame{
	
	private JComboBox<String> box;
	private JTextArea text;
	private JTextArea block1;
	private JScrollPane block;
	private JButton search ;
	public static final String childInfo = "Child Information";
	public static final String helperInfo = "Helper Information";
	public View()
	{
		super("Information");
			
		
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout( new GridBagLayout());
		//add(displayPanelNorth);
		

		
		Component display2 = new JLabel("Select Type of Search:");
		box = new JComboBox<>();
		box.addItem(childInfo);
		box.addItem(helperInfo);
		
		JPanel panel1 = new JPanel();
		panel1.add(display2);
		panel1.add(box);
		panel1.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = displayPanel.getComponentCount();
		gbc.anchor = GridBagConstraints.WEST;
		displayPanel.add(panel1, gbc);
		 
		
		
		Component display = new JLabel("Insert Primary Key:      ");
		text = new JTextArea(1, 15);
		JPanel panel2 = new JPanel();
		panel2.add(display);
		panel2.add(text);
		panel2.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridy = displayPanel.getComponentCount();
		gbc2.anchor = GridBagConstraints.WEST;
		displayPanel.add(panel2, gbc2);
		
		block1 = new JTextArea(10,30);
		block1.setEditable(false);
		block1.setLineWrap(true);
		block1.setWrapStyleWord(true);
		block = new JScrollPane(block1);
		JPanel panel3 = new JPanel();
		panel3.add(block);
		panel3.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.gridy = displayPanel.getComponentCount();
		gbc3.anchor = GridBagConstraints.SOUTH;
		displayPanel.add(panel3,gbc3);
		
		
		JPanel panel4 = new JPanel();
		search = new JButton("Search");
		panel4.add(search);
		panel4.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints gbc4 = new GridBagConstraints();
		gbc4.gridy = displayPanel.getComponentCount();
		gbc4.anchor = GridBagConstraints.SOUTH;
		displayPanel.add(panel4,gbc4);
		
		add(displayPanel, BorderLayout.CENTER);
		pack();
		
	}
	
	
	public void registerListener(InfoController controller)
	{
		search.addActionListener(controller);
	}
	
	
	public String getTypeOfSelection()
	{
		return (String)box.getSelectedItem();
	}
	
	public int getPrimaryKey()
	{
		try{
			return Integer.valueOf(text.getText());
		}
		catch(NumberFormatException e)
		{
			return 0;
		}
		
	}
	
	public void updateChild(ChildInfo info)
	{
		block1.setText("lol");
		
		System.out.println("lol");
		repaint();
	}
	
	public void updateHelper(HelperInfo info)
	{
		block1.setText("lol22");
		repaint();
	}
}
