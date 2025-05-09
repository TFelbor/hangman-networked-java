package Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Clients.HangmanThread;
import javafx.scene.control.Label;

public class ChooserScene extends SceneBasic {

	
	public ChooserScene() {
		super("Chooser");
		Label done = new Label(" you are all done see if your opponet wins");
		root.getChildren().addAll(done);
		
	}
	
	
	
}
