package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.JSONException;

import controller.UserController;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Register {

	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JPasswordField pwdPasswordVerify;
	private JLabel lblPasswordVerify;
	private JLabel lblUsernameCheck;
	private JLabel lblPasswordCheck;
	private JLabel lblEmailCheck;
	private JButton btnRegister;
	private JTextField txtEmail;
	
	public Register() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("deprecation")
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 401, 240);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setBounds(43, 35, 103, 14);
		frame.getContentPane().add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Thread checkExisting = new Thread() {
					public void run() {
						try {
							if(UserController.checkValid("username",txtUsername.getText())) {
								lblUsernameCheck.setText("x");
								lblUsernameCheck.setForeground(Color.RED);
							}
							else {
								lblUsernameCheck.setText("\u2713");
								lblUsernameCheck.setForeground(Color.GREEN);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						checkRegisterValid();
					}
				};
				checkExisting.start();
				
			}
		});
		txtUsername.setBounds(156, 32, 108, 20);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(43, 66, 103, 14);
		frame.getContentPane().add(lblPassword);
		
		pwdPassword = new JPasswordField();
		pwdPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(pwdPassword.getText().equals(pwdPasswordVerify.getText())) {
					lblPasswordCheck.setText("\u2713");
					lblPasswordCheck.setForeground(Color.GREEN);
					checkRegisterValid();
				}
				else {
					lblPasswordCheck.setText("x");
					lblPasswordCheck.setForeground(Color.RED);
					checkRegisterValid();
				}
			}
		});
		pwdPassword.setBounds(156, 63, 108, 20);
		frame.getContentPane().add(pwdPassword);
		
		pwdPasswordVerify = new JPasswordField();
		pwdPasswordVerify.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(pwdPassword.getText().equals(pwdPasswordVerify.getText())) {
					lblPasswordCheck.setText("\u2713");
					lblPasswordCheck.setForeground(Color.GREEN);
					checkRegisterValid();
				}
				else {
					lblPasswordCheck.setText("x");
					lblPasswordCheck.setForeground(Color.RED);
					checkRegisterValid();
				}
			}
		});
		pwdPasswordVerify.setBounds(156, 94, 108, 20);
		frame.getContentPane().add(pwdPasswordVerify);
		
		lblPasswordVerify = new JLabel("Type pasword again:");
		lblPasswordVerify.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPasswordVerify.setBounds(10, 97, 136, 14);
		frame.getContentPane().add(lblPasswordVerify);
		
		lblUsernameCheck = new JLabel("");
		lblUsernameCheck.setForeground(Color.GREEN);
		lblUsernameCheck.setBounds(274, 35, 15, 14);
		frame.getContentPane().add(lblUsernameCheck);
		
		lblPasswordCheck = new JLabel();
		lblPasswordCheck.setBounds(274, 66, 46, 14);
		frame.getContentPane().add(lblPasswordCheck);
		
		lblEmailCheck = new JLabel();
		lblEmailCheck.setBounds(274, 126, 46, 14);
		frame.getContentPane().add(lblEmailCheck);
		
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread registerUser = new Thread() {
					public void run() {
						try {
							UserController.registerUser(txtUsername.getText(), pwdPassword.getText(), txtEmail.getText());
							JOptionPane.showMessageDialog(null, "Successfully registered.");
							frame.dispose();
						} catch (JSONException e) {
							JOptionPane.showMessageDialog(null, "Unable to register user.");
							e.printStackTrace();
						}
					}
				};
				registerUser.start();
			}
		});
		btnRegister.setEnabled(false);
		btnRegister.setBounds(156, 167, 89, 23);
		frame.getContentPane().add(btnRegister);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(100, 128, 46, 14);
		frame.getContentPane().add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Thread checkExisting = new Thread() {
					public void run() {
						try {
							if(UserController.checkValid("email",txtEmail.getText())) {
								lblEmailCheck.setText("x");
								lblEmailCheck.setForeground(Color.RED);
							}
							else {
								lblEmailCheck.setText("\u2713");
								lblEmailCheck.setForeground(Color.GREEN);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						checkRegisterValid();
					}
					
				};
				checkExisting.start();
			}
		});
		txtEmail.setBounds(156, 125, 108, 20);
		frame.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
	}
	
	void checkRegisterValid() {
		if(lblPasswordCheck.getForeground()==Color.GREEN && lblUsernameCheck.getForeground()==Color.GREEN && lblEmailCheck.getForeground()==Color.GREEN)
			btnRegister.setEnabled(true);
		else
			btnRegister.setEnabled(false);
	}
}
