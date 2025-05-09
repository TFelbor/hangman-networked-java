package Player;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


//Created by Will Corbett
//This method is a GUI scene for the start scene, it allows the user to play as the chooser or guesser

public class StartScene extends SceneBasic {

	private ButtonBar bar = new ButtonBar(); // button bar for adding buttons
	private Label errorMessage = new Label(); // a label for a potential error message

	StartScene() {
	
		super("Start");
		Label message = new Label("Login Menu");
		message.setFont(new Font(50));	
		if (SceneManager.getSocket() == null) {
			errorMessage.setText("Error Trying to connect to the Server");
		}
		bar.addButton("Chooser", e -> chooser()); // button to play as chooser
		bar.addButton("Guesser", e -> guesser()); // button to play as guesser
		bar.addButton("Settings", e -> settings()); //a button that takes to settings scene
		bar.addButton("Connect", e -> SceneManager.setScene(SceneManager.SceneType.connect)); //a button that takes to settings scene
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(400, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.add(bar, 0, 2);
		errorMessage.setTextFill(Color.RED);
		gridPane.add(errorMessage, 0, 3);
		gridPane.setAlignment(Pos.TOP_CENTER);
		root.getChildren().addAll(gridPane);
	}
	
	//a method to send the client to the chooser start screen
	public void chooser() {
		if (SceneManager.getSocket() == null) {
			errorMessage.setText("Error Trying to connect to the Server");
		}
		else {
			SceneManager.setScene(SceneManager.SceneType.startChooser);
		}
	}
	
	//a method to send the client to the guesser start screen
	public void guesser() {
		if (SceneManager.getSocket() == null) {
			errorMessage.setText("Error Trying to connect to the Server");
		}
		else {
			SceneManager.setScene(SceneManager.SceneType.startGuesser);
		}
	}

	//a method to send the client to the settings start screen
	public void settings() {
		if (SceneManager.getSocket() == null) {
			errorMessage.setText("Error Trying to connect to the Server");
		}
		else {
			SceneManager.setScene(SceneManager.SceneType.settings);
		}
	}
	// override to create a error message
		@Override
		public Scene getScene() {
			errorMessage.setText("");
			return super.getScene();
		}

}
