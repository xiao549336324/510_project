package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import dao.DBConnect;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import models.ClientModel;

public class AdminController implements Initializable {
	
	@FXML
	private Pane pane1;
	@FXML
	private Pane pane2;
	@FXML
	private Pane pane3;
	@FXML
	private Pane pane4;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtAddress;	
	@FXML
	private TextField txtUpdateID;	
	@FXML
	private TextField txtUpdateBalance;	
	@FXML
	private TextField txtDelID;	
	@FXML
	private Label labelUpdate;
	@FXML
	private Label labelAdd;
	@FXML
	private Label labelDel;
	@FXML
	private TableView<ClientModel> tblAccounts;
	@FXML
	private TableColumn<ClientModel, String> tid;
	@FXML
	private TableColumn<ClientModel, String> cid;
	@FXML
	private TableColumn<ClientModel, String> balance;
	
	// Declare DB objects
	DBConnect conn = null;

	public AdminController() {
		conn = new DBConnect();
	}

	public void viewAccounts() throws IOException{
		pane4.setVisible(true);
		pane3.setVisible(false);
		pane2.setVisible(false);
		pane1.setVisible(false);
		ClientModel cm = new ClientModel();
		
		tblAccounts.getItems().setAll(cm.getAllAccounts()); // load table data from ClientModel List
		tblAccounts.setVisible(true); // set table view to visible if not
		
		System.out.println(cm.getClientInfo());
	}

	public void updateRec() {
		pane4.setVisible(false);
		pane3.setVisible(false);
		pane2.setVisible(false);
		pane1.setVisible(true);

	}

	public void deleteRec() {
		pane4.setVisible(false);
		pane3.setVisible(false);
		pane2.setVisible(true);
		pane1.setVisible(false);
	}

	public void addBankRec() {
		pane4.setVisible(false);
		pane3.setVisible(true);
		pane2.setVisible(false);
		pane1.setVisible(false);

	}

	public void submitBank() {
		String sql = null;
		sql = "insert into yangxiaoxiao_bank(name,address) values ('" + txtName.getText() + "','" + txtAddress.getText()
				+ "')";
		try (Statement stmt = conn.getConnection().createStatement()){
			System.out.println("Inserting records into the table...");
			stmt.executeUpdate(sql);
			System.out.println("Bank Record created");
		} catch (SQLException se) {
			se.printStackTrace();
		}
		labelAdd.setText("Bank Added!");
	}

	public void submitUpdate() {
		String sql = null;

		sql = "update yangxiaoxiao_accounts set balance="  + txtUpdateBalance.getText()+ " WHERE tid = " + txtUpdateID.getText();

		try (Statement stmt = conn.getConnection().createStatement()){
			// Execute a query
			System.out.println("Update Submit button pressed");
			stmt.executeUpdate(sql);
			System.out.println("balance record updated");
		} catch (SQLException se) {
			se.printStackTrace();
		}		
		labelUpdate.setText("Record Updated!");
	}

	public void submitDelete() {
		String sql = null;

		sql = "DELETE FROM yangxiaoxiao_accounts WHERE tid = "  + txtDelID.getText();

		try (Statement stmt = conn.getConnection().createStatement()){

			System.out.println("Delelte Submit button pressed");
			stmt.executeUpdate(sql);
			System.out.println("balance record deleted");
		} catch (SQLException se) {
			se.printStackTrace();
		}		
		labelDel.setText("Record Deleted!");

	}
	
	public void initialize(URL location, ResourceBundle resources) {
		tid.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("tid"));
		cid.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("cid"));
		balance.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("balance"));

		// auto adjust width of columns depending on their content
		tblAccounts.setColumnResizePolicy((param) -> true);
		Platform.runLater(() -> customResize(tblAccounts));

		tblAccounts.setVisible(false); // set invisible initially
	}
	
    public void customResize(TableView<?> view) {

        AtomicLong width = new AtomicLong();
        view.getColumns().forEach(col -> {
            width.addAndGet((long) col.getWidth());
        });
        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            view.getColumns().forEach(col -> {
                col.setPrefWidth(col.getWidth()+((tableWidth-width.get())/view.getColumns().size()));
            });
        }
    }
  
}
