package Player;

import java.io.IOException;
import java.io.PrintWriter;

import Clients.HangmanThread;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class StartGuesserScene extends SceneBasic{

	private UserInput name = new UserInput("Name:"); //a textfield for user to import there name
	private ButtonBar bar = new ButtonBar(); //a button bar to add buttons to 
	private Label warning = new Label("Make Sure Chooser sends hints and word to server before start");
	
	public StartGuesserScene() {
		super("Guesser");
		
		bar.addButton("Start", e -> start()); //button to start the game
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(400, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.add(name, 0, 0);
		gridPane.add(bar, 0, 1);
		gridPane.add(warning, 0, 2);
		gridPane.setAlignment(Pos.TOP_CENTER);
		root.getChildren().addAll(gridPane);
		
	}
	
	//allows the guesser to send their name to the server
	public void start() {
		try {
			PrintWriter outgoing = new PrintWriter(SceneManager.getSocket().getOutputStream());
			outgoing.println(name.getText());
			outgoing.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
		SceneManager.setScene(SceneManager.SceneType.guesser);
	}

}
