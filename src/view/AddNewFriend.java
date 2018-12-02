package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;

import controller.FriendController;
import controller.UserController;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AddNewFriend extends JFrame {

	private JPanel contentPane;
	private JTextField txtEmail;
	private int existanceCheck=0;

	public AddNewFriend(String idUser, FriendManager fManager) {
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 309, 154);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][grow]", "[][][][][][56.00,grow]"));
		
		JPanel emailFormPanel = new JPanel();
		contentPane.add(emailFormPanel, "cell 1 1,grow");
		emailFormPanel.setLayout(new MigLayout("", "[][139.00]", "[]"));
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(existanceCheck==1) {
					try {
						UserController.newPhantom(txtEmail.getText());
						FriendController.addFriend(idUser, UserController.getByEmail(txtEmail.getText()).getUserID());
						JOptionPane.showMessageDialog(null, "Added friend");
						fManager.refreshTable();
						fManager.getOwePanel().refreshFriends();
						dispose();
					} catch (JSONException e1) {
						JOptionPane.showMessageDialog(null, "Unable to add friend", "Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
				else if(existanceCheck==0 || existanceCheck==2) {
					try {
						FriendController.addFriend(idUser, UserController.getByEmail(txtEmail.getText()).getUserID());
						JOptionPane.showMessageDialog(null, "Added friend");
						fManager.refreshTable();
						fManager.getOwePanel().refreshFriends();
						dispose();
					} catch (JSONException e1) {
						JOptionPane.showMessageDialog(null, "Unable to add friend", "Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
			}
		});
		btnAdd.setEnabled(false);
		contentPane.add(btnAdd, "cell 1 3");
		
		JLabel lblFriendEmail = new JLabel("Friend's email:");
		emailFormPanel.add(lblFriendEmail, "cell 0 0");
		
		txtEmail = new JTextField();
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Thread checkValid = new Thread() {
					public void run() {
						try {
							existanceCheck = UserController.checkValid("email",txtEmail.getText());
							System.out.println(existanceCheck);
							btnAdd.setEnabled(true);
							for(Object[] a :FriendController.getFriends(idUser)) {
								if(txtEmail.getText().equals(a[2])) {
									btnAdd.setEnabled(false);
									break;
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				};
				checkValid.start();
			}
		});
		emailFormPanel.add(txtEmail, "flowx,cell 1 0");
		txtEmail.setColumns(10);
		
		JLabel lblCheck = new JLabel("\u2713");
		emailFormPanel.add(lblCheck, "cell 1 0");
		lblCheck.setVisible(false);
		
		
	}

}
