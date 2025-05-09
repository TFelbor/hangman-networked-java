package Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class HintsScene extends SceneBasic{

	ArrayList<String> hints;
	PrintWriter outgoing;
	BufferedReader incoming;
	
	
	public HintsScene() {
		super("Hints");
		getHints();
		
		if ( ! hints.equals(null)) {
		for (int i = 0; i < 5; i++) {
			addHint(hints.get(i));
		}
		}
	}

	
	// this method gets the hints from the server after sending the string, "SEND_HINTS"
	// the method receives the 5 hints and keeps them hidden from the player, until he redeems them
	public void getHints() {
		
		try {
			outgoing = new PrintWriter(SceneManager.getSocket().getOutputStream());
			incoming = new BufferedReader(new InputStreamReader(SceneManager.getSocket().getInputStream()));
			while (true) {
				outgoing.println("SEND_HINTS");
				String feed = incoming.readLine();
				if (feed.equals("Sending... HINTS")) {
					hints = new ArrayList<String>();
					hints.add(incoming.readLine());
					hints.add(incoming.readLine());
					hints.add(incoming.readLine());
					hints.add(incoming.readLine());
					hints.add(incoming.readLine());
					break;
				}
				else outgoing.println("Error receiving data. Try again...");
			}
			for (int i = 0; i < 5; i++) {
				if ( ! hints.get(0).equals(null) ) addHint(hints.get(i));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	// adds a box with a button to redeem the hint in Hints Scene
			public void addHint(String text) {
				
				VBox b = new VBox();
				HBox h = new HBox();
				
				Label instruction = new Label("Click the button below to show a hint");			
				Label hint = new Label(text);
				hint.setTextFill(Color.WHITE);
				
				Button btn = new Button();
				btn.setOnAction(e ->  {
					hint.setTextFill(Color.BLACK);
					instruction.setTextFill(Color.WHITE);
				});
				
				b.getChildren().add(instruction);
				h.getChildren().addAll(btn, hint);
				b.getChildren().add(h);
				
				h.setAlignment(Pos.CENTER);
				b.setAlignment(Pos.CENTER);
				
				root.getChildren().add(h);
			}
}
