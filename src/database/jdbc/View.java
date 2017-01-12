package database.jdbc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// TODO: Auto-generated Javadoc
/**
 * The Class View.
 */
public class View extends JFrame {

	/** The box for selecting type of report. */
	private JComboBox<String> box;

	/** The text field to insert the id. */
	private JTextField text;

	/** The scroll block to be updated after selection */
	private JScrollPane block;

	/** The search button */
	private JButton search;

	/** The Constant childInfo. */
	public static final String childInfo = "Child's Information";

	/** The Constant helperInfo. */
	public static final String helperInfo = "Helper's Information";

	/**
	 * Instantiates a new view.
	 *
	 * @param conn
	 *            the connection to close after exit button is pressed
	 */
	public View(Connection conn) {
		
		// calls the constructor of the super class
		super("Information");

		//main panel for this frame
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new GridBagLayout());

		//new JLabel
		JLabel searchLabel = new JLabel("Select Type of Search:");
		
		//adding all the types to the selection box
		box = new JComboBox<>();
		box.addItem(childInfo);
		box.addItem(helperInfo);

		//adding the label and the selection box to the main panel
		JPanel panel1 = new JPanel();
		panel1.add(searchLabel);
		panel1.add(box);
		panel1.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = displayPanel.getComponentCount();
		constraints.anchor = GridBagConstraints.WEST;
		displayPanel.add(panel1, constraints);

		//new JLabel 
		JLabel display = new JLabel("Insert Primary Key:      ");
		
		//field for inputing the id
		text = new JTextField(15);
		
		//adding the label and the text field for the id to the main panel
		JPanel panel2 = new JPanel();
		panel2.add(display);
		panel2.add(text);
		panel2.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridy = displayPanel.getComponentCount();
		constraints2.anchor = GridBagConstraints.WEST;
		displayPanel.add(panel2, constraints2);

		
		//the main text block for the information to display
		JTextArea block1 = new JTextArea(20, 45);
		block1.setEditable(false);
		block1.setLineWrap(true);
		block1.setWrapStyleWord(true);
		block1.setPreferredSize(new Dimension(300, 300));
		
		//creating the scroll pane (if there are a lot data to display)
		block = new JScrollPane(block1);
		
		//adding the 
		JPanel panel3 = new JPanel();
		panel3.add(block);
		panel3.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints constraints3= new GridBagConstraints();
		constraints3.gridy = displayPanel.getComponentCount();
		constraints.anchor = GridBagConstraints.SOUTH;
		displayPanel.add(panel3, constraints3);

		//creating a buttons
		JPanel panel4 = new JPanel();
		
		//search button for searching information
		search = new JButton("Search");
		
		//exit button for closing the connection and closing the system
		JButton exit = new JButton("Exit");

		//adding the listener to the exit button
		exit.addActionListener(e -> {
			try {
				//closing the connection
				conn.close();
				System.out.println("Connection closed");
			} catch (SQLException ex) {
				//if fails we display the error window message
				JOptionPane.showMessageDialog(new JFrame(), "Failed to close the connection!", "Error",
						JOptionPane.WARNING_MESSAGE);
			} finally {
				//and exit in all the cases
				System.exit(1);
			}

		});

		//adding the buttons to the main panel
		panel4.add(search);
		panel4.add(exit);
		panel4.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints gbc4 = new GridBagConstraints();
		gbc4.gridy = displayPanel.getComponentCount();
		gbc4.anchor = GridBagConstraints.SOUTH;
		displayPanel.add(panel4, gbc4);

		//adding the main panel to the frame
		add(displayPanel, BorderLayout.CENTER);
		pack();

	}

	/**
	 * Register search button listener listener.
	 *
	 * @param controller
	 *            the controller
	 */
	public void registerListener(InfoController controller) {
		search.addActionListener(controller);

	}

	/**
	 * Gets the type of selection.
	 * Either helper or child
	 *
	 * @return the type of selection
	 */
	public String getTypeOfSelection() {
		return String.valueOf(box.getSelectedItem());
	}

	/**
	 * Gets the primary key which was input by the user
	 *
	 * @return the primary key
	 */
	public int getPrimaryKey() {
		try {
			return Integer.valueOf(text.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Input a correct ID!", "Error", JOptionPane.WARNING_MESSAGE);
			return -1;
		}

	}

	/**
	 * Update child.
	 * updates the text field by the information about a specific child.
	 *
	 * @param info
	 *            the info
	 */
	public void updateChild(ChildInfo info) {

		if(info.getCid() == 0)  printError();
		else{
			ArrayList<GiftInfo> gifts = info.getGifts();
	
			JPanel mainPanel = new JPanel(new GridLayout(gifts.size() + 5, 2));
	
			addComponent(mainPanel, new JTextField("Child ID: "));
	
			addComponent(mainPanel, new JTextField(String.valueOf(info.getCid())));
	
			addChildToPanel(mainPanel, info);
	
			block.setViewportView(mainPanel);
			repaint();
		}
	}
	
	
	/**
	 * Prints the error message.
	 */
	private void printError()
	{
		JOptionPane.showMessageDialog(new JFrame(), "Input a correct ID!", "Error", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Adds all the information about a particular child to the panel
	 *
	 * @param mainPanel
	 *            the main panel
	 * @param info
	 *            the information about the child
	 */
	private void addChildToPanel(JPanel mainPanel, ChildInfo info) {
		addComponent(mainPanel, new JTextField("Child's name: "));

		addComponent(mainPanel, new JTextField(info.getName()));

		addComponent(mainPanel, new JTextField("Child's address: "));

		addComponent(mainPanel, new JTextField(info.getAddress()));

		addComponent(mainPanel, (new JTextField("The set of presents to ")));

		addComponent(mainPanel, new JTextField(info.getName()));

		addComponent(mainPanel, new JTextField("Gift's ID "));

		addComponent(mainPanel, new JTextField("Description"));

		ArrayList<GiftInfo> gifts = info.getGifts();

		for (GiftInfo g : gifts) {
			addComponent(mainPanel, new JTextField(String.valueOf(g.getGid())));
			addComponent(mainPanel, new JTextField(g.getDesc()));
		}
	}

	/**
	 * Adds the component to the panel
	 * and sets disabled and colour
	 *
	 * @param mainPanel
	 *            the main panel
	 * @param field
	 *            the field
	 */
	private void addComponent(JPanel mainPanel, JTextField field) {
		field.setEnabled(false);
		field.setDisabledTextColor(Color.BLACK);
		mainPanel.add(field);
	}

	/**
	 * Update helper.
	 * updates the text field by the information about a specific helper;
	 *
	 * @param info
	 *            the info
	 */
	public void updateHelper(HelperInfo info) {
		

		if(info.getSlhid() == 0)  printError();
			else{
			JPanel mainPanel = new JPanel(new GridLayout(countSize(info), 2));
	
			addComponent(mainPanel, new JTextField("Helper's ID: "));
	
			addComponent(mainPanel, new JTextField(String.valueOf(info.getSlhid())));
	
			addComponent(mainPanel, new JTextField("Helper's name: "));
	
			addComponent(mainPanel, new JTextField(info.getName()));
	
			for (ChildInfo ch : info.getChildInfo()) {
				addComponent(mainPanel, new JTextField());
				addComponent(mainPanel, new JTextField());
				addChildToPanel(mainPanel, ch);
			}
	
			block.setViewportView(mainPanel);
			repaint();
		}
	}

	/**
	 * Count the size of the panel which will be necessary to display the helper
	 *
	 * @param info
	 *            the info
	 * @return the int
	 */
	private int countSize(HelperInfo info) {
		int count = 2;
		for (ChildInfo ch : info.getChildInfo()) {
			count += ch.getGifts().size();
			count += 5;
		}
		return count;

	}

}
