package controller;

import java.io.IOException;

import configuration.SugarCalculation;
import configuration.StaticValues;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Activity {
	
	private String actEat = "Eat Food";
	private String actExercise = "Exercise";
	private SugarCalculation bslCalculation;
	
	@FXML Simulation insulinSimulatorController;
	@FXML TextField currentBSLTextId;
	@FXML TextField carboId;
	@FXML ChoiceBox<String> cboxActivity;
	@FXML Label lblIPCarbo;
	@FXML Label lblExerciseNote;
	
	@FXML
    void initialize() {	
		cboxActivity.getItems().clear();
		cboxActivity.getItems().add(actEat);
		cboxActivity.getItems().add(actExercise);
		cboxActivity.setValue(actEat);
		lblExerciseNote.setVisible(false);
		
		cboxActivity.setOnAction(e -> getChoice());
		currentBSLTextId.setText(Double.toString(StaticValues.CurrentBSL));
	}
	
	private void getChoice() {
		String selectedValue = cboxActivity.getValue();
		if(selectedValue == actExercise) {
			carboId.setVisible(false);
			lblExerciseNote.setVisible(true);
			lblIPCarbo.setVisible(false);
		}
		else {
			carboId.setVisible(true);
			lblIPCarbo.setVisible(true);
			lblExerciseNote.setVisible(false);
		}
		
	}

	public void configurationOkClick(ActionEvent event) throws IOException {
		double carbo = 0;
		String selectedValue = cboxActivity.getValue();
		
		if(selectedValue == actExercise) {
			carbo -= 30;
			Simulation.addMessage("Amount of Carbohydrates lost due to exercise : " + carbo + " g");
		}
		else {
			carbo = Double.parseDouble(carboId.getText());
			Simulation.addMessage("Amount of Carbohydrates taken : " + carbo + " g");
		}
		
		StaticValues.CarbohydrateIntake = carbo;
		
		double bsl = SugarCalculation.getInstance().bslAfterActivity(carbo, 0);
		System.out.println("Blood Sugar Level increased to " + bsl + " mg/dl");
		
		setInsulinControllerDefaultValue();
		
		StaticValues.PreviousBSL = StaticValues.CurrentBSL;
		StaticValues.CurrentBSL = bsl;
		StaticValues.TempBSL = bsl;
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.close();
	}
	
	private void setInsulinControllerDefaultValue() {
		Simulation.checkForBsl = true;
		Simulation.showHighBslMsg = true;
		Simulation.increasingCarboCounter = 1;
		Simulation.autoInjectionStarted = Simulation.isAutoMode ? false: true;
		Simulation.inManualIdealDecrease = true;
		Simulation.inManualInject = Simulation.isAutoMode ? false : true;
	}
	
	public void configurationCancelClick(ActionEvent event) throws IOException {
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.close();
	}
}
