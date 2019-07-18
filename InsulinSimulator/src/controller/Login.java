package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login {
	@FXML TextField txtUserName;
	@FXML TextField txtPassword;
	@FXML Label lblErrorMsg;
	@FXML Simulation insulinSimulatorController;

	@FXML
    void initialize() {	
	}
	
	public void loginClick(ActionEvent event) throws IOException {
		String userName = txtUserName.getText();
		String password = txtPassword.getText();
		
		if(userName.equals("Doctor") && password.equals("abc")) {
			Simulation.isAutoMode = false;
			Simulation.inManualInject = false;
			
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.close();
		}
		else {
			lblErrorMsg.setText("Login Failed.");
		}
	}
	
	public void btnCancelClick(ActionEvent event) {
		Simulation.isAutoMode = true;
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.close();
	}
}
