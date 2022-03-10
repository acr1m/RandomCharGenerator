module generator.random.letters.randomlettergenerator {
	requires javafx.controls;
	requires javafx.fxml;
	
	
	exports generator.random.letters;
	opens generator.random.letters to javafx.fxml;
}