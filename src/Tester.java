import javax.swing.JFrame;

public class Tester {

	public static void main(String[] args) {
		
		View view = new View();
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setSize(400, 300);
		view.pack();
		view.setVisible(true);

	}

}
