package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;

import controller.FriendController;
import controller.OweController;
import controller.UserController;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddOwed extends JFrame {

	private JPanel contentPane;
	private ArrayList<String> friends = new ArrayList<String>();
	private DefaultTableModel friendTableModel;
	private JTable friendTable;
	private JScrollPane scrollPane;
	private JTextField borrowerTxtField;
	private JTextField AmountTxtField;
	private String idUser;
	private OwePanel owePanel;
	private JTextField txtDetail;

	/**
	 * Create the frame.
	 * @param owePanel 
	 */
	public AddOwed(String idUser, OwePanel owePanel) {
		this.idUser = idUser;
		this.owePanel = owePanel;
		friendTable = new JTable(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		friendTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = friendTable.getSelectedRow();
				int selectedColumn = friendTable.getSelectedColumn();
				String selectedName = friendTable.getValueAt(selectedRow, selectedColumn).toString();
				borrowerTxtField.setText(selectedName);
			}
		});
		friendTableModel = new DefaultTableModel();
        friendTable.setModel(friendTableModel);
        refreshFriends();
        
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(friendTable);
		scrollPane.setBounds(282, 13, 138, 106);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 23, 223, 142);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblLenderName = new JLabel("Borrower Name");
		lblLenderName.setBounds(0, 3, 83, 16);
		panel.add(lblLenderName);
		
		JLabel lblAmount = new JLabel("Amount (RM)");
		lblAmount.setBounds(0, 32, 83, 19);
		panel.add(lblAmount);
		
		borrowerTxtField = new JTextField();
		borrowerTxtField.setBounds(107, 0, 116, 22);
		panel.add(borrowerTxtField);
		borrowerTxtField.setEditable(false);
		borrowerTxtField.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setEnabled(false);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(idUser);
				try {
					String idOtherParty="";
					for(Object[] a:FriendController.getFriends(idUser)) {
						if(		a[1].equals(borrowerTxtField.getText()) ||
								a[2].equals(borrowerTxtField.getText())) {
							idOtherParty=a[0].toString();
						}
					}
					OweController.addRecord(idOtherParty, idUser, AmountTxtField.getText(), txtDetail.getText());
					dispose();
					owePanel.refreshTable();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnSubmit.setBounds(107, 106, 116, 25);
		panel.add(btnSubmit);
		
		AmountTxtField = new JTextField();
		AmountTxtField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
				Double.parseDouble(AmountTxtField.getText());
				btnSubmit.setEnabled(true);
				}catch(IllegalArgumentException e2) {
					btnSubmit.setEnabled(false);
				}
			}
		});
		AmountTxtField.setBounds(107, 29, 116, 22);
		panel.add(AmountTxtField);
		AmountTxtField.setColumns(10);
		
		JLabel lblDetail = new JLabel("Detail");
		lblDetail.setBounds(0, 62, 46, 14);
		panel.add(lblDetail);
		
		txtDetail = new JTextField();
		txtDetail.setBounds(107, 59, 116, 20);
		panel.add(txtDetail);
		txtDetail.setColumns(10);
		
		
		setVisible(true);
		
	}
	
	
	public void refreshFriends() {
		try {
			friendTableModel.addColumn("Friends");
			System.out.println("getting friends");
			for(Object[] a: FriendController.getFriends(idUser)) {
				if(a[1].equals("Friend not yet registered"))
					friendTableModel.addRow(new Object[] {a[2]});
				else
					friendTableModel.addRow(new Object[] {a[1]});
			}
			friendTable.setModel(friendTableModel);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
