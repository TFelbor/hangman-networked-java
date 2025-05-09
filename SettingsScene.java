package Player;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class SettingsScene extends SceneBasic {

	private ButtonBar bar = new ButtonBar(); // a button bar to add buttons
	private UserInput guessCount = new UserInput("# of Guesses "); // a textfeild to change the number of hints
	private UserInput portNumber = new UserInput("Server PortNumber: "); // a textfield to change port number of server
	private UserInput hostName = new UserInput("Server HostName: "); // a textfield to change the hostname of the server

	public SettingsScene() {
		super("Settings");
		bar.addButton("Apply New Settings",
				e -> writeXML(guessCount.getText(), portNumber.getText(), hostName.getText())); // apply button to
																								// connnect to server
		bar.addButton("Cancel", e ->	SceneManager.setScene(SceneManager.SceneType.start)); //to go back to start
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(400, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.add(guessCount, 0, 0);
		gridPane.add(portNumber, 0, 1);
		gridPane.add(hostName, 0, 2);
		gridPane.add(bar, 0, 3);
		gridPane.setAlignment(Pos.TOP_CENTER);
		root.getChildren().addAll(gridPane);
	}

	// a method to update the settings in the xml file
	public void writeXML(String guessCount, String portNumber, String hostName) {
			
			
				PrintWriter print;
				try {
					print = new PrintWriter("Project6.txt");
					print.println("<GAME>");
					print.println("<Guesses>");
					print.println(guessCount);
					print.println("</Guesses>");
					print.println("<SERVER>");
					print.println("<PortNumber>");
					print.println(portNumber);
					print.println("</PortNumber>");
					print.println("<HostName>");
					print.println(hostName);
					print.println("</HostName>");
					print.println("</SERVER>");
					print.println("</GAME>");
					print.close();
					SceneManager.setScene(SceneManager.SceneType.start);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
					
	}

}
