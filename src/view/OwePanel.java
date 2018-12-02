package view;

import javax.swing.JPanel;
import javax.swing.AbstractCellEditor;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import org.json.JSONException;

import controller.FriendController;
import controller.OweController;
import controller.TransactionController;
import javafx.scene.control.ComboBox;
import model.Friend;
import model.Transaction;

import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class OwePanel extends JPanel {
	private DefaultTableModel  oweTableModel;
	private DefaultTableModel  owedTableModel;
	private ArrayList<Friend> friends;
	private String idUser;
	private JTable owedTable;
	private JTable oweTable;
	private JComboBox<String> friendsList;
	private boolean refreshing = false;
	/**
	 * Create the panel.
	 */
	public OwePanel(String idUser) {
		this.idUser = idUser;
		friends = new ArrayList<Friend>();
		setLayout(null);

		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(0, 0, 430, 320);
		add(leftPanel);
		leftPanel.setLayout(null);
		OwePanel owePanel = this;
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		//tabbedPane.addTab("Owe", );
		tabbedPane.setBounds(0, 0, 430, 273);
		leftPanel.add(tabbedPane);

		JScrollPane oweScrollPane = new JScrollPane();
		JScrollPane owedScrollPane = new JScrollPane();
		tabbedPane.addTab("Owe", null, oweScrollPane, null);
		tabbedPane.addTab("Owed", null, owedScrollPane, null);

		owedTable = new JTable(){
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==0)
					return false;
				else
					return true;
			}
		};
		owedScrollPane.setViewportView(owedTable);
		owedTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				oweTable.clearSelection();
			}
		});

		oweTable = new JTable(){
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==0)
					return false;
				else
					return true;
			}
		};
		oweTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				owedTable.clearSelection();
			}
		});
		oweScrollPane.setViewportView(oweTable);
		oweTableModel = new DefaultTableModel();
		oweTableModel.addColumn("ID");
		oweTableModel.addColumn("Lender");
		oweTableModel.addColumn("Detail");
		oweTableModel.addColumn("Status");
		oweTableModel.addColumn("Amount");
		oweTableModel.addColumn("Date");

		owedTableModel = new DefaultTableModel();
		owedTableModel.addColumn("ID");
		owedTableModel.addColumn("Borrower");
		owedTableModel.addColumn("Detail");
		owedTableModel.addColumn("Status");
		owedTableModel.addColumn("Amount");
		owedTableModel.addColumn("Date");
		oweTable.setModel(oweTableModel);
		owedTable.setModel(owedTableModel);

		friendsList = new JComboBox<String>();
		refreshFriends();
		JComboBox<String> statusList = new JComboBox<String>();
		statusList.addItem("Paid");
		statusList.addItem("Unpaid");
		oweTable.getColumnModel().getColumn(5).setCellEditor(new DatePicker());
		owedTable.getColumnModel().getColumn(5).setCellEditor(new DatePicker());
		oweTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(statusList));
		owedTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(statusList));
		oweTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(friendsList));
		owedTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(friendsList));
		owedTable.getTableHeader().setReorderingAllowed(false);
		oweTable.getTableHeader().setReorderingAllowed(false);
		oweTable.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				Thread threadUpdate = new Thread() {
					public void run() {
						String idOtherParty = "";
						try {
							for(Object[] a: FriendController.getFriends(idUser)) {
								if(		a[1].equals(oweTable.getModel().getValueAt(e.getFirstRow(), 1).toString()) ||
										a[2].equals(oweTable.getModel().getValueAt(e.getFirstRow(), 1).toString())) {
									idOtherParty=a[0].toString();
									System.out.println(idOtherParty);
								}
							}
							OweController.updateRecord(
									oweTable.getModel().getValueAt(e.getFirstRow(), 0).toString(),
									idOtherParty,
									oweTable.getModel().getValueAt(e.getFirstRow(), 2).toString(),
									oweTable.getModel().getValueAt(e.getFirstRow(), 3).toString(),
									Double.parseDouble(oweTable.getModel().getValueAt(e.getFirstRow(), 4).toString()),
									oweTable.getModel().getValueAt(e.getFirstRow(), 5).toString(),
									"updateOwe");
						}catch(JSONException e) {
							JOptionPane.showMessageDialog(null, "Unable to update record", "Error", JOptionPane.WARNING_MESSAGE);
							e.printStackTrace();
						}
						refreshTable();
					}
				};
				if(!refreshing)
					threadUpdate.start();
			}
		});
		owedTable.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				Thread threadUpdate = new Thread() {
					public void run() {
						String idOtherParty = "";
						try {
							for(Object[] a: FriendController.getFriends(idUser)) {
								System.out.println(a[1]+" "+owedTable.getModel().getValueAt(e.getFirstRow(), 1).toString());
								if(		a[1].equals(owedTable.getModel().getValueAt(e.getFirstRow(), 1).toString()) ||
										a[2].equals(owedTable.getModel().getValueAt(e.getFirstRow(), 1).toString())) {
									idOtherParty=a[0].toString();
									System.out.println(idOtherParty);
								}
							}
							OweController.updateRecord(
									owedTable.getModel().getValueAt(e.getFirstRow(), 0).toString(),
									idOtherParty,
									owedTable.getModel().getValueAt(e.getFirstRow(), 2).toString(),
									owedTable.getModel().getValueAt(e.getFirstRow(), 3).toString(),
									Double.parseDouble(owedTable.getModel().getValueAt(e.getFirstRow(), 4).toString()),
									owedTable.getModel().getValueAt(e.getFirstRow(), 5).toString(),
									"updateOwed");
						}catch(JSONException e) {
							JOptionPane.showMessageDialog(null, "Unable to update record", "Error", JOptionPane.WARNING_MESSAGE);
							e.printStackTrace();
						}
						refreshTable();
					}
				};
				if(!refreshing)
					threadUpdate.start();
			}
		});
		refreshTable();
		JPanel rightPanel = new JPanel();
		rightPanel.setBounds(435, 31, 187, 261);
		add(rightPanel);
		rightPanel.setLayout(new MigLayout("", "[196.00px]", "[25px][25px][25px][23px][][][][][]"));

		JButton btnAddOwed = new JButton("Add owed");
		rightPanel.add(btnAddOwed, "cell 0 1,grow");
		btnAddOwed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AddOwed(idUser, owePanel);
			}
		});


		JButton btnAddOwe = new JButton("Add owe");
		rightPanel.add(btnAddOwe, "cell 0 0,grow");

		JButton btnRemove = new JButton("Remove record");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(oweTable.getSelectedRow()!=-1) {
					int selectedRow = oweTable.getSelectedRow();
					String listId = oweTable.getValueAt(selectedRow, 0).toString();
					try {
						OweController.deleteRecord(listId);
						refreshTable();
						JOptionPane.showMessageDialog(null, "Deleted record", null, JOptionPane.INFORMATION_MESSAGE);
					} catch (JSONException e1) {
						JOptionPane.showMessageDialog(null, "Unable to delete record", "Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
				else if(owedTable.getSelectedRow()!=-1) {
					int selectedRow = owedTable.getSelectedRow();
					String listId = owedTable.getValueAt(selectedRow, 0).toString();
					try {
						OweController.deleteRecord(listId);
						JOptionPane.showMessageDialog(null, "Deleted record", null, JOptionPane.INFORMATION_MESSAGE);
					} catch (JSONException e1) {
						JOptionPane.showMessageDialog(null, "Unable to delete record", "Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}

				}
				else
					JOptionPane.showMessageDialog(null, "No record selected", "Error", JOptionPane.ERROR_MESSAGE);
				//make a method and send argument listId to delete in database
			}
		});
		rightPanel.add(btnRemove, "cell 0 2,grow");

		JButton btnFriends = new JButton("Friends");
		rightPanel.add(btnFriends, "cell 0 8,growx,aligny top");
		btnFriends.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FriendManager(idUser, owePanel);
			}
		});
		btnAddOwe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AddOwe(idUser,owePanel);
			}
		});

	}

	public void refreshFriends() {
		friendsList.removeAllItems();
		try {
			for(Object[] a: FriendController.getFriends(idUser)) {
				friends.add(new Friend(Integer.parseInt(a[0].toString()),a[1].toString(),a[2].toString()));
				if(a[1].equals("Friend not yet registered"))
					friendsList.addItem(a[2].toString());
				else
					friendsList.addItem(a[1].toString());
			}
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "Error getting friends", "Error", JOptionPane.ERROR_MESSAGE);
			e2.printStackTrace();
		}
	}
	public void refreshTable() {
		refreshing=true;
		oweTableModel.setRowCount(0);
		owedTableModel.setRowCount(0);
		try {
			for(Object[] a: OweController.getRecord(idUser, "getOwe")) 
				for(Friend friend:friends) 
					if(String.valueOf(friend.getId()).equals(a[1].toString())) 
						if(friend.getUsername().equals("Friend not yet registered"))
							oweTableModel.addRow(new Object[] {a[0],friend.getEmail(),a[2],a[3],a[4],a[5]});
						else 
							oweTableModel.addRow(new Object[] {a[0],friend.getUsername(),a[2],a[3],a[4],a[5]});

			for(Object[] a: OweController.getRecord(idUser, "getOwed")) 
				for(Friend friend:friends) 
					if(String.valueOf(friend.getId()).equals(a[1].toString())) 
						if(friend.getUsername().equals("Friend not yet registered"))
							owedTableModel.addRow(new Object[] {a[0],friend.getEmail(),a[2],a[3],a[4],a[5]});
						else 
							owedTableModel.addRow(new Object[] {a[0],friend.getUsername(),a[2],a[3],a[4],a[5]});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refreshing=false;
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

