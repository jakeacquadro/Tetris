package Tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The app class starts the game and instantiates the paneOrganizer. It also
 * creates and sets the scene.
 */

public class App extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Tetris");
		stage.show();
		PaneOrganizer organizer = new PaneOrganizer();
		Scene scene = new Scene(organizer.getRoot());
		stage.setScene(scene);
	}

	/*
	 * Here is the mainline! No need to change this.
	 */
	public static void main(String[] argv) {
		// launch is a method inherited from Application
		launch(argv);
	}
}
