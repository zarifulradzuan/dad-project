package view;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import controller.TransactionController;
import model.Transaction;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;

public class TransactionPanel extends JPanel {
	private JTable transactionTable;
	private String id;
	private DefaultTableModel transactionTableModel;
	/**
	 * Create the panel.
	 */
	@SuppressWarnings("serial")
	public TransactionPanel(String id) {
		this.id = id;
		setLayout(new MigLayout("", "[381.00,grow][108.00,grow]", "[grow][]"));
		
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(tableScrollPane, "cell 0 0,grow");
		
		transactionTable = new JTable(){
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==0)
					return false;
				else
					return true;
			}
		};
		transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		transactionTable.setFillsViewportHeight(true);
		transactionTable.setColumnSelectionAllowed(false);
		tableScrollPane.setViewportView(transactionTable);
		
		JPanel sidePanel = new JPanel();
		add(sidePanel, "cell 1 0,grow");
		sidePanel.setLayout(new MigLayout("", "[]", "[][][][][][][][][][]"));
		
		JButton btnAddTransaction = new JButton("Add Transaction");
		sidePanel.add(btnAddTransaction, "cell 0 0,growx");
		
		JButton btnRemoveTransaction = new JButton("Remove Transaction");
		sidePanel.add(btnRemoveTransaction, "cell 0 1");
		
		JLabel lblAccountBalanceTo = new JLabel("Account balance to date:");
		sidePanel.add(lblAccountBalanceTo, "cell 0 8");
		
		JLabel lblBalance = new JLabel("balance");
		sidePanel.add(lblBalance, "cell 0 9");
		
		transactionTableModel = new DefaultTableModel();
		transactionTableModel.addColumn("ID");
		transactionTableModel.addColumn("Title");
		transactionTableModel.addColumn("Description");
		transactionTableModel.addColumn("Amount");
		transactionTableModel.addColumn("Date");
		transactionTableModel.addColumn("Type");
		try {
			for(Object[] a : TransactionController.getTransaction(this.id)) {
				transactionTableModel.addRow(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transactionTable.setModel(transactionTableModel);
		transactionTable.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				Transaction transactionUpdated = new Transaction(
				transactionTable.getModel().getValueAt(e.getFirstRow(), 0).toString(),
				transactionTable.getModel().getValueAt(e.getFirstRow(), 1).toString(),
				transactionTable.getModel().getValueAt(e.getFirstRow(), 2).toString(),
				Double.parseDouble(transactionTable.getModel().getValueAt(e.getFirstRow(), 3).toString()),
				transactionTable.getModel().getValueAt(e.getFirstRow(), 4).toString(),
				transactionTable.getModel().getValueAt(e.getFirstRow(), 5).toString());
				try {
					TransactionController.updateTransaction(transactionUpdated, id);
				} catch (SQLException e1) {
					//transactionTable.removeAll();
					
					e1.printStackTrace();
				}
				
			}
		});
		JComboBox<String> typeOfTransaction = new JComboBox<String>();
		typeOfTransaction.addItem("Debit");
		typeOfTransaction.addItem("Credit");	
		transactionTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(typeOfTransaction));
	}
	
	private void getTransactions() {
		
	}
}

