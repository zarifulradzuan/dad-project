package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import controller.TransactionController;
import controller.UserController;
import model.Transaction;
import net.miginfocom.swing.MigLayout;


import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class TransactionPanel extends JPanel {
	private JTable transactionTable;
	private String id;
	private DefaultTableModel transactionTableModel;
	private boolean refreshing = false;
	JLabel lblBalance = new JLabel();
	private JPanel panel;
	/**
	 * Create the panel.
	 */
	public TransactionPanel(String id) {
		this.id = id;
		setLayout(new MigLayout("", "[381.00,grow][108.00,grow]", "[grow][]"));
		
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
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
		
		
		transactionTable.getTableHeader().setReorderingAllowed(false);
		transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		transactionTable.setFillsViewportHeight(true);
		transactionTable.setColumnSelectionAllowed(false);
		tableScrollPane.setViewportView(transactionTable);
		
		JPanel sidePanel = new JPanel();
		add(sidePanel, "cell 1 0,grow");
		sidePanel.setLayout(new MigLayout("", "[grow]", "[][][][][][][][grow][][]"));
		
		JButton btnAddTransaction = new JButton("Add Transaction");
		btnAddTransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddTransaction(id);
			}
		});
		sidePanel.add(btnAddTransaction, "cell 0 0,growx");
		
		JButton btnRemoveTransaction = new JButton("Remove Transaction");
		btnRemoveTransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		sidePanel.add(btnRemoveTransaction, "cell 0 1,growx");
		
		JButton btnRefreshTransaction = new JButton("Refresh Transaction");
		btnRefreshTransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable((DefaultTableModel) transactionTable.getModel());
			}
		});
		sidePanel.add(btnRefreshTransaction, "cell 0 2,growx");
		
		panel = new JPanel();
		sidePanel.add(panel, "cell 0 9,grow");
		panel.setLayout(new MigLayout("", "[]", "[]"));
		
		JLabel lblAccountBalanceTo = new JLabel("Account balance to date: RM");
		panel.add(lblAccountBalanceTo, "flowx,cell 0 0");
		
		transactionTableModel = new DefaultTableModel();
		transactionTableModel.addColumn("ID");
		transactionTableModel.addColumn("Title");
		transactionTableModel.addColumn("Description");
		transactionTableModel.addColumn("Amount");
		transactionTableModel.addColumn("Date");
		transactionTableModel.addColumn("Type");
		
		
		
		refreshTable(transactionTableModel);
		transactionTable.setModel(transactionTableModel);
		transactionTable.getColumnModel().getColumn(0).setPreferredWidth(27);
		transactionTable.getColumnModel().getColumn(1).setPreferredWidth(300);
		transactionTable.getColumnModel().getColumn(2).setPreferredWidth(300);
		transactionTable.getColumnModel().getColumn(3).setPreferredWidth(300);
		transactionTable.getColumnModel().getColumn(4).setPreferredWidth(300);
		transactionTable.getColumnModel().getColumn(5).setPreferredWidth(300);
		transactionTable.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				Thread threadUpdateTransaction = new Thread() {
					public void run() {
						Transaction transactionUpdated = new Transaction(
						transactionTable.getModel().getValueAt(e.getFirstRow(), 0).toString(),
						transactionTable.getModel().getValueAt(e.getFirstRow(), 1).toString(),
						transactionTable.getModel().getValueAt(e.getFirstRow(), 2).toString(),
						Double.parseDouble(transactionTable.getModel().getValueAt(e.getFirstRow(), 3).toString()),
						transactionTable.getModel().getValueAt(e.getFirstRow(), 4).toString(),
						transactionTable.getModel().getValueAt(e.getFirstRow(), 5).toString());
						try {
							TransactionController.updateTransaction(transactionUpdated, id);
							refreshTable((DefaultTableModel) transactionTable.getModel());
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					}
				};
				if(!refreshing)
					threadUpdateTransaction.start();
			}
		});
		JComboBox<String> typeOfTransaction = new JComboBox<String>();
		
		typeOfTransaction.addItem("Debit");
		typeOfTransaction.addItem("Credit");
		
		transactionTable.getColumnModel().getColumn(4).setCellEditor(new DatePicker());
		transactionTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(typeOfTransaction));
	}
	
	private void refreshTable(DefaultTableModel tableModel) {
		refreshing = true;
		tableModel.setRowCount(0);
		try {
			for(Object[] a : TransactionController.getTransaction(this.id)) {
				tableModel.addRow(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refreshing = false;
		try {
			panel.add(lblBalance, "cell 0 0");
			lblBalance.setText(String.valueOf(UserController.getBalance(id)));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Trouble getting balance");
			e.printStackTrace();
		}
		
	}
	
	public class DatePicker extends AbstractCellEditor implements TableCellEditor, ActionListener {
		JSpinner dateInput;
		@Override
		public Object getCellEditorValue() {
			return new SimpleDateFormat("yyyy-MM-dd").format(dateInput.getValue());		
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = null;
			try {
				date = (dateFormatter.parse(value.toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dateInput = new JSpinner();
			dateInput.setModel(new SpinnerDateModel(new java.util.Date(), null,null, Calendar.DATE));
			dateInput.setEditor(new JSpinner.DateEditor(dateInput,"yyyy-MM-dd"));
			dateInput.setValue(date);
			return dateInput;
		}
		
	}
	
	
	
}

