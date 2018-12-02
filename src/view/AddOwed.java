package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;

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

public class AddOwed extends JFrame {

	private JPanel contentPane;
	private ArrayList<String> userList;
	private DefaultTableModel userListModel = new DefaultTableModel();
	private final JTable userTableList = new JTable();
	private JScrollPane scrollPane;
	private JTextField borrowerTxtField;
	private JTextField AmountTxtField;
	private OweController oweController = new OweController();
	private String owner;


	/**
	 * Create the frame.
	 */
	public AddOwed(String owner) {
		this.owner = owner;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 179);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int selectedRow = userTableList.getSelectedRow();
				int selectedColumn = userTableList.getSelectedColumn();
				String selectedName = userTableList.getValueAt(selectedRow, selectedColumn).toString();
				borrowerTxtField.setText(selectedName);
				
			}
		});
		scrollPane.setBounds(282, 13, 138, 106);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 23, 223, 89);
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
		
		AmountTxtField = new JTextField();
		AmountTxtField.setBounds(107, 29, 116, 22);
		panel.add(AmountTxtField);
		AmountTxtField.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(owner);
				oweController.addOwe(owner.toString(), AmountTxtField.getText().toString(), borrowerTxtField.getText() );
				
			}
		});
		btnSubmit.setBounds(107, 64, 116, 25);
		panel.add(btnSubmit);
		
		setUserList();
		setVisible(true);
		
	}
	
	
	public void setUserList() {
		try {
			userList =UserController.getAllUsers();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Vector<Object> coloumName = new Vector<>();
		coloumName.add("name");
		
	
		// Data of the table
        Vector<Object> vector = new Vector<Object>();
        for (int i = 0; i < userList.size(); i++) {
        	vector.add(userList.get(i));
        }

        userListModel.addColumn(coloumName);
        userListModel.addRow(vector);
        
        
        scrollPane.setViewportView(userTableList);
        userTableList.setModel(userListModel);
        
	}}
