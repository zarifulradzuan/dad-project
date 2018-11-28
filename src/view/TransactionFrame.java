package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import controller.TransactionController;

import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.sql.SQLException;

import net.miginfocom.swing.MigLayout;

public class TransactionFrame extends JFrame {
	private JTable transactionTable;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public TransactionFrame(String id) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 559, 321);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel centerPanel = new JPanel();
		getContentPane().add(centerPanel, BorderLayout.WEST);
		
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
		getContentPane().add(eastPanel, BorderLayout.EAST);
		eastPanel.setLayout(new MigLayout("", "[]", "[][]"));
		
		JButton btnAddTransaction = new JButton("Add Transaction");
		eastPanel.add(btnAddTransaction, "cell 0 0,growx");
		
		JButton btnRemoveTransaction = new JButton("Remove Transaction");
		eastPanel.add(btnRemoveTransaction, "cell 0 1,growx");

	}

}
