package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import controller.LoginController;
import model.User;
import net.miginfocom.swing.MigLayout;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	private User user;

	public Login() {
		user = new User();
		setBounds(100, 100, 450, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[][][][][grow][][][][grow][][][][]", "[][][][][][][][]"));
		
		JLabel lblLabReservationSystem = new JLabel(".:: FINANCIAL MANAGER ::.");
		getContentPane().add(lblLabReservationSystem, "cell 6 1 2 1");
		
		JLabel lblUsername = new JLabel("USERNAME :");
		getContentPane().add(lblUsername, "cell 6 3,alignx trailing");
		
		JTextField fieldUsername = new JTextField();
		getContentPane().add(fieldUsername, "cell 7 3,growx");
		fieldUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("PASSWORD :");
		getContentPane().add(lblPassword, "cell 6 4,alignx trailing");
		
		JPasswordField fieldPassword = new JPasswordField();
		getContentPane().add(fieldPassword, "cell 7 4,growx");
		
		JButton btnLogin = new JButton("LOGIN");
		LoginController loginController = new LoginController(user);
		getContentPane().add(btnLogin, "cell 9 7");
		
		JLabel lblIncorrect = new JLabel("Incorrect username or password entered");
		lblIncorrect.setVisible(false);
		getContentPane().add(lblIncorrect, "cell 7 9");
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(loginController.doLogin(fieldUsername.getText(), fieldPassword.getPassword())) {
						new MainPage(loginController.getUser());
						dispose();
					}
					else
						lblIncorrect.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

}
