package view;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class OwePanel extends JPanel {
	private DefaultTableModel  oweTableModel;
	private String idUser;
	private JTable oweOwedList;

	/**
	 * Create the panel.
	 */
	public OwePanel(String idUser) {
		this.idUser = idUser;
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 13, 370, 272);
		add(panel);
		panel.setLayout(null);
		
		oweOwedList = new JTable();
		oweOwedList.setBounds(363, 262, -712, -262);
		panel.add(oweOwedList);
		oweOwedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(409, 13, 134, 114);
		add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnAddOwed = new JButton("Add Owed");
		btnAddOwed.setBounds(0, 37, 134, 25);
		panel_2.add(btnAddOwed);
		btnAddOwed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AddOwed(idUser);
			}
		});
		
	
			JButton btnAddOwe = new JButton("Add owe");
			btnAddOwe.setBounds(0, 0, 134, 25);
			panel_2.add(btnAddOwe);
			
			JButton btnRemove = new JButton("Remove");
			btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = oweOwedList.getSelectedRow();
					int listId = (int) oweOwedList.getValueAt(selectedRow, 0);
					
					//make a method and send argument listId to delete in database
				}
			});
			btnRemove.setBounds(0, 76, 134, 25);
			panel_2.add(btnRemove);
			
			JButton btnFriends = new JButton("Friends");
			btnFriends.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new FriendManager(idUser);
				}
			});
			btnFriends.setBounds(454, 134, 89, 23);
			add(btnFriends);
			btnAddOwe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new AddOwe(idUser);
				}
			});

	}
	
	public void setTable() {
		oweTableModel = new DefaultTableModel();
		Object [] coloumName = {"Id", "Borrower", "Lender", "Amount", "Date" };
		
		//make a method to return multidimensional data and iterate throgn rowData,
		//every 1st iteration complete add data into table.
		Object[] rowData = {1,"admin","abu", 200, "20/6/2018"};
		oweTableModel.addColumn(coloumName);
		oweTableModel.addRow(rowData);
		
		
		oweOwedList.setModel(oweTableModel);
		

	}
}

