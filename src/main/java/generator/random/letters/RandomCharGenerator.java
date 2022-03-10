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
 * @author Kevin A Boykin
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
		
		Scene scene = new Scene(vboxRoot, 320, 240);
		stage.setTitle("Random Letter Set Generator");
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * This method handles generation of the results. <br>
	 * Process outline:<br>
	 * &#9;- Grabs text from text-fields and converts them to integers<br>
	 * &#9;- Clears the output label (text-box)<br>
	 * &#9;- Generates random numbers using the method generateNumbers()<br>
	 * &#9;- Converts the numbers to letters if the checkbox is selected <br>
	 * @param rangeInput     The range of possible numbers.
	 * @param lengthInput    The length, or amount of numbers generated, for the set.
	 * @param setsInput      The number of sets to generate.
	 * @param shiftInput     The amount to shift the base, or lowest value, of the range. <br>&#9;<i>A shift value of 5, for a range of 3, would generate numbers [5-7].</i>
	 * @param cboxConvertToLetters The checkbox control for determining whether to convert the generated numbers to letters.
	 * @param lblResult            The text label to output the generated number/letter result.
	 */
	private void generateResults(TextField rangeInput,
								 TextField lengthInput,
								 TextField setsInput,
								 TextField shiftInput,
								 CheckBox cboxConvertToLetters,
								 Label lblResult) {
		
		//grabs the text from the input field and casts as Integer
		try {
			int range = Integer.parseInt(rangeInput.getText());
			int length = Integer.parseInt(lengthInput.getText());
			int sets = Integer.parseInt(setsInput.getText());
			int shift = Integer.parseInt(shiftInput.getText());
			
			if (range == 0 || length == 0) {
				throw new Exception("Values should be higher than zero.");
			} else if (sets == 0) {
				throw new Exception("Values should be higher than zero.");
			}
			
			// clear the text result area in order to rebuild it
			lblResult.setText("");
			
			for (int i = 0; i < sets; i++) {
				ArrayList<String> generatedString = generateNumbers(range, length, shift);
				
				if (cboxConvertToLetters.isSelected()) {
					
					// convert each index of String array to Char, shift, and replace.
					for (int j = 0; j < generatedString.size(); j++) {
						int v = Integer.parseInt(generatedString.get(j));
						char c = (char) (v + 65
//								+ Integer.parseInt(shiftInput.getText())
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
	
	/**
	 * Generates random numbers and returns an ArrayList<String> to be optionally converted into character/letters.
	 * @param range  parsed integer from TextField rangeInput
	 * @param amount parsed integer from TextField amountInput
	 * @param shift  parsed integer from TextField shiftInput
	 * @return list of numbers as ArraryList<String>
	 */
	private ArrayList<String> generateNumbers(int range, int amount, int shift) {
		
		ArrayList<String> list = new ArrayList<>();
		
		for (int i = 0; i < amount; i++) {
			list.add(String.valueOf(new Random().nextInt(shift, shift + range)));
		}
		
		System.out.println("list = " + list);
		return list;
	}
}