package generator.random.letters;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This application utilizes JavaFX to create a GUI for generating random characters (numbers or letters) with text-fields for customization of output.
 */
public class RandomCharGenerator extends Application {
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) throws IOException {
		
		Label lblRange = new Label("Range of Values");
		Label lblLength = new Label("Length of Set");
		Label lblSets = new Label("Number of Sets");
		Label lblShift = new Label("Range Shift Amount");
		Tooltip tooltipShift = new Tooltip("This will shift the base of the output");
		
		TextField txtfldRangeInput = new TextField();
		TextField txtfldAmountInput = new TextField();
		TextField txtfldSetsInput = new TextField();
		TextField txtfldShiftInput = new TextField();
		txtfldRangeInput.setPromptText("Input a positive integer...");
		txtfldAmountInput.setPromptText("Input a positive integer...");
		txtfldSetsInput.setPromptText("Input a positive integer...");
		txtfldShiftInput.setPromptText("Input a positive integer...");
		
		Tooltip.install(lblShift, tooltipShift);
		Tooltip.install(txtfldShiftInput, tooltipShift);
		
		HBox hboxRange = new HBox(10, lblRange, txtfldRangeInput);
		HBox hboxAmount = new HBox(10, lblLength, txtfldAmountInput);
		HBox hboxSets = new HBox(10, lblSets, txtfldSetsInput);
		HBox hboxShift = new HBox(10, lblShift, txtfldShiftInput);
		
		Button btnRun = new Button("Generate");
		CheckBox cboxConvertToLetters = new CheckBox("Convert to Letters");
		HBox hboxButtons = new HBox(10, cboxConvertToLetters, btnRun);
		
		Label lblTitle = new Label("Random Letter Set Generator");
		Label lblResult = new Label("Results will show here...");
		lblResult.setWrapText(true);
		
		VBox vboxRoot = new VBox(10, lblTitle,
				hboxRange, hboxAmount, hboxSets, hboxShift,
				hboxButtons, lblResult);
		
		vboxRoot.setPadding(new Insets(10));
		vboxRoot.setAlignment(Pos.CENTER);
		hboxAmount.setAlignment(Pos.CENTER_RIGHT);
		hboxRange.setAlignment(Pos.CENTER_RIGHT);
		hboxSets.setAlignment(Pos.CENTER_RIGHT);
		hboxShift.setAlignment(Pos.CENTER_RIGHT);
		hboxButtons.setAlignment(Pos.CENTER);
		btnRun.setAlignment(Pos.CENTER);
		
		txtfldAmountInput.setOnKeyPressed(event -> {
			// if Enter key is pressed
			if (event.getText().compareToIgnoreCase("Enter") == 0) {
				generateResults(txtfldRangeInput,
						txtfldAmountInput,
						txtfldSetsInput,
						txtfldShiftInput,
						cboxConvertToLetters,
						lblResult);
			}
		});
		
		btnRun.setOnAction(event -> {
			generateResults(txtfldRangeInput,
					txtfldAmountInput,
					txtfldSetsInput,
					txtfldShiftInput,
					cboxConvertToLetters,
					lblResult);
		});
		
//		FXMLLoader fxmlLoader = new FXMLLoader(RandomCharGenerator.class.getResource("hello-view.fxml"));
//		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		
		Scene scene = new Scene(vboxRoot, 320, 240);
		stage.setTitle("Random Letter Set Generator");
		stage.setScene(scene);
		stage.show();
	}
	
	private void generateResults(TextField txtfldRangeInput,
								 TextField txtfldAmountInput,
								 TextField txtfldSetsInput,
								 TextField txtfldShiftInput,
								 CheckBox cboxConvertToLetters,
								 Label lblResult) {
		
		//grabs the text from the input field and casts as Integer
		try {
			int range = Integer.parseInt(txtfldRangeInput.getText());
			int amount = Integer.parseInt(txtfldAmountInput.getText());
			int sets = Integer.parseInt(txtfldSetsInput.getText());
			int shift = Integer.parseInt(txtfldShiftInput.getText());
			
			if (range == 0 || amount == 0) {
				throw new Exception("Values should be higher than zero.");
			} else if (sets == 0) {
				throw new Exception("Values should be higher than zero.");
			}
			
			// clear the text result area in order to rebuild it
			lblResult.setText("");
			
			for (int i = 0; i < sets; i++) {
				ArrayList<String> generatedString = generateNumbers(range, amount, shift);
				
				if (cboxConvertToLetters.isSelected()) {
					
					// convert each index of String array to Char, shift, and replace.
					for (int j = 0; j < generatedString.size(); j++) {
						int v = Integer.parseInt(generatedString.get(j));
						char c = (char) (v + 65
//								+ Integer.parseInt(txtfldShiftInput.getText())
						);
						generatedString.set(j, String.valueOf(c));
					}
				}
				
				// concatenate the new string onto the existing text.
				lblResult.setText(
						lblResult.getText() + "\n" +
								generatedString);
			}
			
		} catch (Exception e) {
//			System.out.println(e.getMessage());
			lblResult.setText(e.getMessage());
		}
		
	}
	
	private ArrayList<String> generateNumbers(int range, int amount, int shift) {
		
		ArrayList<String> list = new ArrayList<>();
		
		for (int i = 0; i < amount; i++) {
			list.add(String.valueOf(new Random().nextInt(shift, shift + range)));
		}
		
		System.out.println("list = " + list);
		return list;
	}
}