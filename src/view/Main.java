package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main {

	private JFrame frame;
	/**
	 * Launch the applicatzza	ion.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new Login();
		frame.setVisible(true);
	}

}
