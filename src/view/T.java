package view;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.TransactionController;
import net.miginfocom.swing.MigLayout;

public class T extends JPanel {
	private JTable transactionTable;
	/**
	 * Create the panel.
	 */
	public T(String id) {
		setBounds(100, 100, 559, 321);
		setLayout(new BorderLayout(0, 0));
		
		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.WEST);
		
		DefaultTableModel transactionTableModel = new DefaultTableModel();
		transactionTableModel.addColumn("ID");
		transactionTableModel.addColumn("Title");
		transactionTableModel.addColumn("Description");
		transactionTableModel.addColumn("Amount");
		transactionTableModel.addColumn("Date");
		transactionTableModel.addColumn("Status");
		try {
			for(Object[] a : TransactionController.getTransaction(id))
				transactionTableModel.addRow(a);
		} catch (SQLException e) {
			System.out.println("Error getting transactions");
			e.printStackTrace();
		}
		transactionTable = new JTable();
		
		centerPanel.add(transactionTable);
		
		JPanel eastPanel = new JPanel();
		add(eastPanel, BorderLayout.EAST);
		eastPanel.setLayout(new MigLayout("", "[]", "[][]"));
		
		JButton btnAddTransaction = new JButton("Add Transaction");
		eastPanel.add(btnAddTransaction, "cell 0 0,growx");
		
		JButton btnRemoveTransaction = new JButton("Remove Transaction");
		eastPanel.add(btnRemoveTransaction, "cell 0 1,growx");
	}

}
