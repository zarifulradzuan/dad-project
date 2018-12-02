package view;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;

import controller.FriendController;
import controller.OweController;
import controller.UserController;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddOwe extends JFrame {

	private JPanel contentPane;
	private UserController user = new UserController();
	private ArrayList<String> friends = new ArrayList<String>();
	private DefaultTableModel friendTableModel;
	private JTable friendTable;
	private JScrollPane scrollPane;
	private JTextField lenderTxtField;
	private JTextField AmountTxtField;
	private String idUser;
	private JLabel lblDetail;
	private JTextField txtDetail;


	/**
	 * Create the frame.
	 * @param owePanel 
	 */
	public AddOwe(String idUser, OwePanel owePanel) {
		this.idUser = idUser;
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
				lenderTxtField.setText(selectedName);
			}
		});
		friendTableModel = new DefaultTableModel();
        friendTable.setModel(friendTableModel);
        refreshFriends();
        
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 227);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(friendTable);
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int selectedRow = friendTable.getSelectedRow();
				int selectedColumn = friendTable.getSelectedColumn();
				String selectedName = friendTable.getValueAt(selectedRow, selectedColumn).toString();
				lenderTxtField.setText(selectedName);
			}
		});
		scrollPane.setBounds(282, 13, 138, 164);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 23, 235, 154);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblLenderName = new JLabel("Lender Name");
		lblLenderName.setBounds(0, 3, 83, 16);
		panel.add(lblLenderName);
		
		JLabel lblAmount = new JLabel("Amount (RM)");
		lblAmount.setBounds(0, 32, 83, 19);
		panel.add(lblAmount);
		
		lenderTxtField = new JTextField();
		lenderTxtField.setBounds(107, 0, 116, 22);
		panel.add(lenderTxtField);
		lenderTxtField.setEditable(false);
		lenderTxtField.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setEnabled(false);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String idOtherParty="";
					for(Object[] a:FriendController.getFriends(idUser)) {
						if(		a[1].equals(lenderTxtField.getText()) ||
								a[2].equals(lenderTxtField.getText())) {
							idOtherParty=a[0].toString();
						}
					}
					OweController.addRecord(idUser, idOtherParty, AmountTxtField.getText(),txtDetail.getText());
					owePanel.refreshTable();
					dispose();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		btnSubmit.setBounds(107, 118, 116, 25);
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
		
		lblDetail = new JLabel("Detail");
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
			System.out.println("getting friends"+idUser);
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
