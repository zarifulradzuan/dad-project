package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.TransactionController;
import model.Transaction;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AddTransaction extends JFrame {

	private JPanel contentPane;
	private JTextField txtTitle;
	private JTextField txtDetails;
	private JTextField txtAmount;
	//private SpringLayout springLayout;
	public AddTransaction(String idUser) {
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 298, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(24, 48, 72, 22);
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblTitle);
		
		JLabel lblDetails = new JLabel("Description:");
		lblDetails.setBounds(24, 73, 72, 22);
		lblDetails.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblDetails);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(24, 98, 72, 22);
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblDate);
		
		JLabel lblAmount = new JLabel("Amount:");
		lblAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAmount.setBounds(24, 123, 72, 22);
		contentPane.add(lblAmount);
		
		
		JLabel lblType = new JLabel("Type:");
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblType.setBounds(24, 148, 72, 22);
		contentPane.add(lblType);
		
		JComboBox<String> typeOfTransaction = new JComboBox<String>();
		typeOfTransaction.addItem("Debit");
		typeOfTransaction.addItem("Credit");
		typeOfTransaction.setBounds(109, 148, 131, 22);
		contentPane.add(typeOfTransaction);
		
		txtTitle = new JTextField();
		txtTitle.setBounds(109, 49, 131, 22);
		contentPane.add(txtTitle);
		txtTitle.setColumns(10);
		
		txtDetails = new JTextField();
		txtDetails.setBounds(109, 74, 131, 22);
		contentPane.add(txtDetails);
		txtDetails.setColumns(10);
		

		JSpinner dateInput = new JSpinner();
		dateInput.setModel(new SpinnerDateModel(new java.util.Date(), null,null, Calendar.DATE));
		dateInput.setEditor(new JSpinner.DateEditor(dateInput,"yyyy-MM-dd"));
		dateInput.setBounds(109, 98, 134, 22);
		contentPane.add(dateInput);
		
		txtAmount = new JTextField();
		txtAmount.setBounds(109, 124, 131, 22);
		contentPane.add(txtAmount);
		txtAmount.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread threadAdd = new Thread() {
					public void run() {
						Transaction newTransaction = new Transaction(txtTitle.getText(),
								txtDetails.getText(), 
								Double.parseDouble(txtAmount.getText()), 
								dateInput.getValue().toString(), 
								typeOfTransaction.getSelectedItem().toString());
						try {
							TransactionController.addTransaction(newTransaction, idUser);
							JOptionPane.showMessageDialog(null, "Succcessfully added");
							dispose();
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, "Failed to add");
							e.printStackTrace();
						}
					}
				};
				threadAdd.start();
			}
		});
		btnAdd.setBounds(106, 193, 89, 22);
		contentPane.add(btnAdd);
	}
}
