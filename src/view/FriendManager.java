package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;

import controller.FriendController;
import controller.TransactionController;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FriendManager extends JFrame {

	private JPanel contentPane;
	private JTable friendTable;
	private DefaultTableModel friendTableModel;
	private String idUser;
	public FriendManager(String id) {
		FriendManager fManager = this;
		this.setVisible(true);
		idUser = id;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][grow]", "[grow]"));
		
		JScrollPane friendScrollPane = new JScrollPane();
		contentPane.add(friendScrollPane, "cell 0 0,grow");
		
		friendTable = new JTable(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		friendScrollPane.setViewportView(friendTable);
		
		
		friendTableModel = new DefaultTableModel();
		friendTableModel.addColumn("ID");
		friendTableModel.addColumn("Username");
		friendTableModel.addColumn("Email");
		friendTable.setModel(friendTableModel);
		friendTable.getColumnModel().getColumn(0).setPreferredWidth(10);
		friendTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		friendTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		refreshTable();
		
		JPanel sidePanel = new JPanel();
		contentPane.add(sidePanel, "cell 1 0,grow");
		sidePanel.setLayout(new MigLayout("", "[]", "[]"));
		
		JButton btnAddFriend = new JButton("Add Friend");
		btnAddFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddNewFriend(idUser, fManager);
			}
		});
		sidePanel.add(btnAddFriend, "cell 0 0");
	}
	
	public void refreshTable() {
		friendTableModel.setRowCount(0);
		try {
			for(Object[] a : FriendController.getFriends(idUser))
				friendTableModel.addRow(a);
		} catch (JSONException e) {
			JOptionPane.showMessageDialog(null, "Unable to get friends", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}
