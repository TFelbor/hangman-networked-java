package Player;
//This program is part of the clients GUI, it will request an input for a port number and a host name
//It will then attempt to connect to the server and if successful return to the login screen
//This is created by Will Corbett
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class ConnectScene extends SceneBasic {
	private UserInput host = new UserInput("Host:"); //A label and textfield for the host name
	private UserInput port = new UserInput("Port:"); //A label and textfield for the port number
	private ButtonBar bar = new ButtonBar(); //A button bar to add buttons too
	private Label errorMessage = new Label(); //ErrorMessage if there is an error


	public ConnectScene() {
		// Creating Grid Pane
		super("Connect");
		bar.addButton("Apply", e -> apply(host.getText(), port.getText())); //apply button to connnect to server
		bar.addButton("Cancel", e -> SceneManager.setScene(SceneManager.SceneType.start));
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(400, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.add(host, 0, 0);
		gridPane.add(port, 0, 1);
		gridPane.add(bar, 0, 2);
		errorMessage.setTextFill(Color.RED);
		gridPane.add(errorMessage, 1, 3);
		gridPane.setAlignment(Pos.TOP_CENTER);
		root.getChildren().addAll(gridPane);

	}

	// a method to connect to the server
	public void apply(String hostNum, String portNum) {
		if (hostNum.equals("localhost") && portNum.equals("32007")) {
			try {
				SceneManager.setSocket(new Socket(hostNum, Integer.parseInt(portNum)));
				
			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (UnknownHostException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} 
			SceneManager.setScene(SceneManager.SceneType.start);
		} else
			errorMessage.setText("Port Number or Host Name Does Not Match");

	}

	
	// to override if there is an error message
	@Override
	public Scene getScene() {
		errorMessage.setText("");
		return super.getScene();
	}

}
