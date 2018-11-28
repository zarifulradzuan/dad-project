package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.TransactionController;
import model.Transaction;
import net.miginfocom.swing.MigLayout;


import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class TransactionPanel extends JPanel {
	private JTable transactionTable;
	private String id;
	private DefaultTableModel transactionTableModel;
	private boolean refreshing = false;
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
							refreshTable(this, (DefaultTableModel) transactionTable.getModel());
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
	
	private void refreshTable(Thread thread, DefaultTableModel tableModel) {
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
		
	}
	
	public class DateLabelFormatter extends AbstractFormatter{
		private String datePattern = "yyyy-MM-dd";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if(value!=null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}
			return null;
		}
		
	}
	
	public class DatePicker extends AbstractCellEditor implements TableCellEditor, ActionListener {
		UtilDateModel model = new UtilDateModel();
		
		@Override
		public Object getCellEditorValue() {
			String date = (model.getYear()+"-"+model.getMonth()+"-"+model.getDay());
			return date;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			java.util.Date date = null;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(e.toString());
			} catch (ParseException e1) {
				// 
				e1.printStackTrace();
			}
			model.setDate(date.getYear(),date.getMonth(),date.getDay());
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			Properties p = new Properties();
			p.put("text.today","Today");
			p.put("text.month","Month");
			p.put("text.year","Year");
			JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
			JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
			
			return datePicker;
		}
		
	}
	
	
	
}

