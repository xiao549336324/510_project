package models;

public class AccountModel {

	@SuppressWarnings("unused")
	private ClientModel customer;
	private int id;
	@SuppressWarnings("unused")
	private int cid;
	private int tid;
	private double balance;
	
	
	public AccountModel() {
		
	}
    public AccountModel(int cid, double balance) {
 
		this.cid = cid;
 		this.balance = balance;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
 	public void setCid(int cid) {
		this.cid = cid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}
	
	public int getTid() {
		return tid;
	}
	 
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
  
}