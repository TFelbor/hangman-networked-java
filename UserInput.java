package Player;

//Created by Will Corbett, this program is used to help with adding labels and textfield in redundent situations

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class UserInput extends HBox {

	private Label label = new Label(); // label that we are puttting in the hbox
	protected TextField textField = new TextField(); // textField that we are adding to the hbox

	public UserInput(String text) {
		this.label.setText(text);
		label.setMinWidth(90);
		super.getChildren().addAll(label, textField);
	}

	// A method to get the text from the textfield
	public String getText() {
		return this.textField.getText();
	}
}
