package model;

public class Transaction {
	private String idTransaction;
	private String title;
	private String description;
	private double amount;
	private String date;
	private String status;
	
	public Transaction(String idTransaction, String title, String description, double amount, String date, String status) {
		this.setAmount(amount);
		this.setDate(date);
		this.setTitle(title);
		this.setStatus(status);
		this.setDescription(description);
		this.setIdTransaction(idTransaction);
	}
	
	public Transaction(String title, String description, double amount, String date, String status) {
		this.setAmount(amount);
		this.setDate(date);
		this.setTitle(title);
		this.setStatus(status);
		this.setDescription(description);
	}

	public String getIdTransaction() {
		return idTransaction;
	}

	public void setIdTransaction(String idTransaction) {
		this.idTransaction = idTransaction;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
