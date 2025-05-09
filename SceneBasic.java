package Player;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class SceneBasic {

	private Scene scene; //a gui scene
	protected VBox root = new VBox(); //a VBox for the Gui Scenes

	public SceneBasic(String title) {
		Label message = new Label(title);
        message.setFont(new Font(40));
        root.getChildren().addAll(message);
        root.setAlignment(Pos.TOP_CENTER);
        scene = new Scene(root, 450, 250);
	}
	
	public Scene getScene() {
		return this.scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	//adds a button to the VBOX
		public void addButton(String text, EventHandler<ActionEvent> func) {
			Button button = new Button(text);
			button.setMinWidth(200);
			button.setOnAction(func);
			root.getChildren().addAll(button);
			
		}

		
		
}
