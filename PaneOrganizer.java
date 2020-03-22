package Tetris;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/*
 * This is my top level object. It instantiates the game, adds the game's center and score panes,
 * and creates a border pane to add these panes to. It also creates a Vbox as a bottom pane
 * that contains a quit button and instructions.
 */
public class PaneOrganizer {
	private BorderPane _borderPane;

	/*
	 * This creates the borderpane and adds the other panes to it. It also
	 * instantiates the game.
	 */
	public PaneOrganizer() {
		_borderPane = new BorderPane();
		_borderPane.setStyle("-fx-background-color: BLACK");
		_borderPane.setBottom(this.createBottomPane());
		Game game = new Game();
		_borderPane.setCenter(game.getCenterPane());
		_borderPane.setTop(game.getScorePane());

	}

	public BorderPane getRoot() {
		return _borderPane;
	}

	/*
	 * This creates a vbox for the bottom pane that contains the instructions
	 * and a quit button.
	 */
	private VBox createBottomPane() {
		VBox bottomPane = new VBox();
		bottomPane.setStyle("-fx-background-color: WHITE");
		bottomPane.setPrefSize(Constants.BOTTOMPANE_WIDTH,
				Constants.BOTTOMPANE_HEIGHT);
		bottomPane.setSpacing(Constants.VBOX_SPACING);
		bottomPane.setFocusTraversable(false);
		Label instructions = new Label(
				"Press the left and right arrow keys to move the piece. Press up"
						+ " to rotate the piece.");
		instructions.setFont(new Font("Arial", 11));
		bottomPane.getChildren().addAll(instructions, this.createQuitButton());
		bottomPane.setAlignment(Pos.CENTER);
		return bottomPane;
	}

	// The quit button responds to clicks and allows the user to quit the game.
	private Button createQuitButton() {
		Button quitButton = new Button("Quit Game");
		quitButton.setFocusTraversable(false);
		quitButton.setOnAction(new ClickHandler());
		return quitButton;
	}

	/*
	 * This private inner class responds to action events and calls
	 * platform.exit() to quit the game
	 */
	private class ClickHandler implements EventHandler<ActionEvent> {

		public ClickHandler() {

		}

		@Override
		public void handle(ActionEvent e) {
			Platform.exit();
		}
	}
}
