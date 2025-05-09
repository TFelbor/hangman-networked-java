//Created by Will Corbett, this scene is a scene that allows the chooser to choose 3 hints and say thir name
package Player;

import java.io.IOException;
import java.io.PrintWriter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class StartChooserScene extends SceneBasic{

	private UserInput name = new UserInput("Name: ");//a textfield for user to import there name
	private UserInput hint1 = new UserInput("Hint 1: "); //a textfield for user to import there hint
	private UserInput hint2 = new UserInput("Hint 2: "); //a textfield for user to import there hint
	private UserInput hint3 = new UserInput("Hint 3: "); //a textfield for user to import there hint
	private UserInput word = new UserInput("Word: "); //a textfield for user to import there word
	private ButtonBar bar = new ButtonBar(); //a bar to add buttons
	
	public StartChooserScene() {
		super("Chooser");
		
		bar.addButton("Start", e -> start()); //button to start the game
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(400, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.add(name, 0, 0);
		gridPane.add(hint1, 0, 1);
		gridPane.add(hint2, 0, 2);
		gridPane.add(hint3, 0, 3);
		gridPane.add(word, 0, 4);
		gridPane.add(bar, 0, 5);
		gridPane.setAlignment(Pos.TOP_CENTER);
		root.getChildren().addAll(gridPane);
	}
	
	//this method sends the hints of the chooser and their name to the server
	public void start() {
		try {
			PrintWriter outgoing = new PrintWriter(SceneManager.getSocket().getOutputStream());
			outgoing.println(name.getText());
			outgoing.println(hint1.getText());
			outgoing.println(hint2.getText());
			outgoing.println(hint3.getText());
			outgoing.println(word.getText());
			outgoing.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SceneManager.setScene(SceneManager.SceneType.chooser);
	}

}
