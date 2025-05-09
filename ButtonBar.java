package Player;

//Created by WILL CORBETT, this program is used to add buttons easier and less time consuming
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ButtonBar extends HBox {

	public ButtonBar() {
		super.setAlignment(Pos.CENTER);
	}

	// This method allows the api to create buttons easier
	public void addButton(String text, EventHandler<ActionEvent> func) {
		Button button = new Button(text);
		button.setOnAction(func);
		super.getChildren().addAll(button);
	}
}
