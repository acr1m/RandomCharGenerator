package generator.random.letters;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


/**
 * This class creates a Label gives to heads-up to the fxml loader to make this method (available for targeting in the fxml file using the
 *
 * <Label fx:id="welcomeText"/>
 * <Button text="Hello!" onAction="d#onHelloButtonClick"/>
 */
public class HelloController {
	@FXML
	private Label welcomeText;
	
	@FXML
	protected void onHelloButtonClick() {
		welcomeText.setText("Welcome to JavaFX Application!");
	}
}