package Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

//Created by Will Corbett, this program is used to make cerating GridPanes easier
public class OutputTable extends GridPane {

	private int row = 1; // a counter for the row of data we are on
	private final int FONT_SIZE = 20;//font size for the label

	public OutputTable(String... headers) {

        super.setAlignment(Pos.CENTER);
		for (int i = 0; i < headers.length; i++) {
			Label label = new Label(headers[i]);
			label.setFont(new Font(FONT_SIZE));
			super.add(label, i, 0);
		}
	}

	// this method adds a row to the gridpane with the label
	public void addword(String text) {

			Label label = new Label(text);
			label.setFont(new Font(FONT_SIZE));
			super.add(label, 0, row);
		
	}
	
	//this method clears the row instance variable
	public void clearData() {
		row = 1;
	}
}
