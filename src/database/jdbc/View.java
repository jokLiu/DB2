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

public class View extends JFrame {

	private JComboBox<String> box;
	private JTextField text;
	private JTextArea block1;
	private JScrollPane block;
	private JButton search;
	private Connection conn;
	public static final String childInfo = "Child Information";
	public static final String helperInfo = "Helper Information";

	public View(Connection conn) {
		super("Information");

		this.conn = conn;

		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new GridBagLayout());
		// add(displayPanelNorth);

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
		text = new JTextField(15);
		JPanel panel2 = new JPanel();
		panel2.add(display);
		panel2.add(text);
		panel2.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridy = displayPanel.getComponentCount();
		gbc2.anchor = GridBagConstraints.WEST;
		displayPanel.add(panel2, gbc2);

		block1 = new JTextArea(20, 50);
		block1.setEditable(false);
		block1.setLineWrap(true);
		block1.setWrapStyleWord(true);
		block1.setPreferredSize(new Dimension(300, 300));
		block = new JScrollPane(block1);
		JPanel panel3 = new JPanel();
		panel3.add(block);
		panel3.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.gridy = displayPanel.getComponentCount();
		gbc3.anchor = GridBagConstraints.SOUTH;
		displayPanel.add(panel3, gbc3);

		JPanel panel4 = new JPanel();
		search = new JButton("Search");
		JButton exit = new JButton("Exit");
		exit.addActionListener(e -> {
			try {
				conn.close();
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(new JFrame(), "Failed to close the connection!", "Error",
						JOptionPane.WARNING_MESSAGE);
				ex.printStackTrace();
			} finally {
				System.exit(1);
			}

		});

		panel4.add(search);
		panel4.add(exit);
		panel4.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints gbc4 = new GridBagConstraints();
		gbc4.gridy = displayPanel.getComponentCount();
		gbc4.anchor = GridBagConstraints.SOUTH;
		displayPanel.add(panel4, gbc4);

		add(displayPanel, BorderLayout.CENTER);
		pack();

	}

	public void registerListener(InfoController controller) {
		search.addActionListener(controller);

	}

	public String getTypeOfSelection() {
		return String.valueOf(box.getSelectedItem());
	}

	public int getPrimaryKey() {
		try {
			return Integer.valueOf(text.getText());
		} catch (NumberFormatException e) {
			return 0;
		}

	}

	public void updateChild(ChildInfo info) {

		ArrayList<GiftInfo> gifts = info.getGifts();

		JPanel mainPanel = new JPanel(new GridLayout(gifts.size() + 5, 2));

		addComponent(mainPanel, new JTextField("Child ID: "));

		addComponent(mainPanel, new JTextField(String.valueOf(info.getCid())));

		addChildToPanel(mainPanel, info);

		block.setViewportView(mainPanel);
		repaint();
	}

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

	private void addComponent(JPanel mainPanel, JTextField field) {
		field.setEnabled(false);
		field.setDisabledTextColor(Color.BLACK);
		mainPanel.add(field);
	}

	public void updateHelper(HelperInfo info) {

		JPanel mainPanel = new JPanel(new GridLayout(countSize(info), 2));

		addComponent(mainPanel, new JTextField("Helper's ID: "));

		addComponent(mainPanel, new JTextField(String.valueOf(info.getSlhid())));

		addComponent(mainPanel, new JTextField("Helper's name: "));

		addComponent(mainPanel, new JTextField(info.getName()));

		for (ChildInfo ch : info.getChildInfo()) {
			addChildToPanel(mainPanel, ch);
		}

		block.setViewportView(mainPanel);
		repaint();
	}

	private int countSize(HelperInfo info) {
		int count = 2;
		for (ChildInfo ch : info.getChildInfo()) {
			count += ch.getGifts().size();
			count += 4;
		}
		return count;

	}

}
