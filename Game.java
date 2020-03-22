package Tetris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

/*
 * Game controls the game logic and animation. It creates the centerPane, the scorePane,
 * a timeline, and new pieces. It has methods to create these panes, update the score,
 * update the speed of pieces, change their colors after a certain score is reached, set
 * up the timeline, and end the game. It has two private inner classes, KeyHandler and TimeHandler
 * which respond to key presses and action events respectively. TimeHandler animates the game
 * and updates positions and score over time.
 */
public class Game {

	private Pane _centerPane;
	private Rectangle[][] _board;
	private Piece _piece;
	private Timeline _timeline;
	private int _pauseTracker;
	private int _gameOverTracker;
	private int _score;
	private HBox _scorePane;
	private Label _scoreLabel;
	private int _colorChangerTracker;

	/*
	 * The constructor creates the board which is an array, calls createBorder
	 * and sets up the timeline. It also creates the centerPane and adds a new
	 * piece to it. It initializes all of the variables I use for tracking to
	 * zero.
	 */
	public Game() {
		_board = new Rectangle[Constants.CENTERPANE_WIDTH
				/ Constants.SQUARE_SIZE][Constants.CENTERPANE_HEIGHT
				/ Constants.SQUARE_SIZE];
		_centerPane = new Pane();
		this.createBorder();
		this.setupTimeline();
		_centerPane.setPrefSize(Constants.CENTERPANE_WIDTH,
				Constants.CENTERPANE_HEIGHT);
		_centerPane.setFocusTraversable(true);
		_centerPane.addEventHandler(KeyEvent.KEY_PRESSED, new KeyHandler());
		this.setupScorePane();
		_piece = new Piece(_centerPane);
		_pauseTracker = 0;
		_gameOverTracker = 0;
		_score = 0;
		_colorChangerTracker = 0;
	}

	// These two methods are used by PaneOrganizer so it can add the panes
	public Pane getCenterPane() {
		return _centerPane;
	}

	public Pane getScorePane() {
		return _scorePane;
	}

	/*
	 * This method creates a small pane that is added to the top of the screen
	 * and displays the score.
	 */
	private void setupScorePane() {
		_scorePane = new HBox();
		_scorePane.setPrefSize(Constants.SCOREPANE_WIDTH,
				Constants.SCOREPANE_HEIGHT);
		_scoreLabel = new Label("Score = 0");
		_scoreLabel.setTextFill(Color.WHITE);
		_scorePane.getChildren().add(_scoreLabel);
		_scorePane.setAlignment(Pos.CENTER);
	}

	/*
	 * Updates the score by accessing the instance variable score and converting
	 * it to a string
	 */
	private void updateScoreLabel() {
		String string = Integer.toString(_score);
		_scoreLabel.setText("Score = " + string);
	}

	/*
	 * This creates the rectangles that make up the border, adding them both
	 * visually and logically to the array
	 */
	private void createBorder() {
		for (int i = 0; i < _board.length; i++) {
			for (int j = 0; j < _board[0].length; j++) {
				if (i >= 1 && i <= 11) {
					if (j <= 1 || j >= 22) {
						Rectangle square = new Rectangle(Constants.SQUARE_SIZE,
								Constants.SQUARE_SIZE);
						square.setFill(Color.GRAY);
						square.setStroke(Color.BLACK);
						square.setX(i * Constants.SQUARE_SIZE);
						square.setY(j * Constants.SQUARE_SIZE);
						_centerPane.getChildren().add(square);
						_board[i][j] = square;
					}
				}
				if (i <= 1 || i >= 12) {
					Rectangle square = new Rectangle(Constants.SQUARE_SIZE,
							Constants.SQUARE_SIZE);
					square.setFill(Color.GRAY);
					square.setStroke(Color.BLACK);
					square.setX(i * Constants.SQUARE_SIZE);
					square.setY(j * Constants.SQUARE_SIZE);
					_centerPane.getChildren().add(square);
					_board[i][j] = square;
				}

			}
		}
	}

	// Standard timeline setup used for all animations
	private void setupTimeline() {
		KeyFrame kf = new KeyFrame(Duration.seconds(Constants.SPEED_INITIAL),
				new TimeHandler());
		_timeline = new Timeline(kf);
		_timeline.setCycleCount(Animation.INDEFINITE);
		_timeline.play();
	}

	/*
	 * This method updates the speed of the game. I call it when certain scores
	 * are reached to make the game harder
	 */
	private void updateSpeed(double speed) {
		_timeline.stop();
		KeyFrame kf = new KeyFrame(Duration.seconds(speed), new TimeHandler());
		_timeline = new Timeline(kf);
		_timeline.setCycleCount(Animation.INDEFINITE);
		_timeline.play();
	}

	/*
	 * This method changes the colors once the score reaches 200. It alternates
	 * the border squares between white and gray. It uses an instance variable
	 * _colorChangerTracker and checks if it is even or odd, then updates the
	 * colors depending on that.
	 */
	private void colorsChange() {
		if (_score >= 200) {
			for (int i = 0; i < _board.length; i++) {
				for (int j = 0; j < _board[0].length; j++) {
					if (j <= 1 || j >= 22) {

						if (_colorChangerTracker % 2 == 0) {
							_board[i][j].setFill(Color.GRAY);
						} else
							_board[i][j].setFill(Color.WHITE);
					}
					if (i <= 1 || i >= 12) {

						if (_colorChangerTracker % 2 == 0) {
							_board[i][j].setFill(Color.GRAY);
						} else
							_board[i][j].setFill(Color.WHITE);

					}
				}
			}
		}
	}

	/*
	 * This private inner class controls the animation.
	 */
	private class TimeHandler implements EventHandler<ActionEvent> {
		public TimeHandler() {
		}

		@Override
		public void handle(ActionEvent e) {
			/*
			 * These "if" conditions ensure that the piece can move down before
			 * the game moves them down. If it cannot, the game adds them to the
			 * array and spawns a new piece
			 */
			if (_piece.checkDownValidity(_board) == true) {
				_piece.setYLoc(Constants.SQUARE_SIZE);
			} else if (_piece.checkDownValidity(_board) == false) {
				_piece.addSquares(_board, _centerPane);
				this.gameOver();
				/*
				 * this only spawns a new piece if the game is not over.
				 * Otherwise the game will spawn a new piece when the game has
				 * ended
				 */
				if (_gameOverTracker == 0) {
					_piece = new Piece(_centerPane);
				}

			}
			Game.this.clearLines();
			Game.this.updateScoreLabel();
			/*
			 * This adds two levels of difficulty. If the score reaches 100 it
			 * speeds up then again at 200.
			 */
			if (_score == 100) {
				Game.this.updateSpeed(Constants.SPEED_1);
			}
			if (_score == 200) {
				Game.this.updateSpeed(Constants.SPEED_2);
			}
			Game.this.colorsChange();
			/*
			 * The colorChangeTracker incrementing each keyframe is what allows
			 * the board to change colors when the score is over 200.
			 */
			_colorChangerTracker += 1;

		}

		/*
		 * this method checks to see if any pieces are above the tile third from
		 * the top. If any are, it displays a game over message, stops the
		 * timeline, and prevents pieces from moving due to key input.
		 */
		private void gameOver() {
			for (int i = 2; i < _board.length - 2; i++) {
				if (_board[i][3] != null) {
					Label label = new Label("Game over!");
					label.setFont(new Font("Arial", 50));
					_centerPane.getChildren().add(label);
					label.toFront();
					label.relocate(80, 240);
					label.setTextFill(Color.RED);
					// by making _pauseTracker odd and stopping the timeline,
					// the user cannot interact with the piece
					_pauseTracker = 3;
					_timeline.stop();
					// this instance variable prevents the game from spawning
					// another piece when the game ends
					_gameOverTracker = 1;
				}
			}
		}
	}

	/*
	 * clearLines checks if lines are full then clears the full lines. Then, it
	 * shifts all squares in the array above this line logically and visually
	 * down by 1.
	 */
	private void clearLines() {
		for (int clearColumn = 2; clearColumn < _board[0].length - 2; clearColumn++) {
			if (_board[2][clearColumn] != null
					&& _board[3][clearColumn] != null
					&& _board[4][clearColumn] != null
					&& _board[5][clearColumn] != null
					&& _board[6][clearColumn] != null
					&& _board[7][clearColumn] != null
					&& _board[8][clearColumn] != null
					&& _board[9][clearColumn] != null
					&& _board[10][clearColumn] != null
					&& _board[11][clearColumn] != null) {
				// updates score by 10 every time a row is cleared
				_score += 10;
				for (int row = 2; row < _board.length - 2; row++) {
					// these two lines remove the squares visually and
					// logicallys
					_centerPane.getChildren().remove(_board[row][clearColumn]);
					_board[row][clearColumn] = null;
					for (int column = clearColumn - 1; column > 2; column--) {
						// this visually moves blocks down
						if (_board[row][column] != null) {
							_board[row][column].setY(column
									* Constants.SQUARE_SIZE
									+ Constants.SQUARE_SIZE);
						}
						// this line updates the indices
						_board[row][column + 1] = _board[row][column];
					}
				}

			}
		}

	}

	private class KeyHandler implements EventHandler<KeyEvent> {
		public KeyHandler() {
		}

		/*
		 * For left, right, and down movement, I call methods to check left and
		 * right move validity while for up I check rotate validity. I also have
		 * _pauseTracker to make sure that these are not called when the game is
		 * paused
		 */
		@Override
		public void handle(KeyEvent e) {
			switch (e.getCode()) {
			case RIGHT:
				if (_piece.checkRightValidity(_board) == true
						&& _pauseTracker % 2 == 0) {
					_piece.setXLoc(Constants.SQUARE_SIZE);
				}
				break;

			case LEFT:
				if (_piece.checkLeftValidity(_board) == true
						&& _pauseTracker % 2 == 0) {
					_piece.setXLoc(-Constants.SQUARE_SIZE);
				}
				break;
			case DOWN:
				if (_piece.checkDownValidity(_board) == true
						&& _pauseTracker % 2 == 0) {
					_piece.setYLoc(Constants.SQUARE_SIZE);
				}
				break;
			case UP:
				if (_piece.checkRotateValidity(_board) == true
						&& _pauseTracker % 2 == 0) {
					_piece.rotate();
				}
				break;
			case SPACE:
				if (_pauseTracker % 2 == 0) {
					// my while loop moves pieces down immediately
					while (_piece.checkDownValidity(_board) == true) {
						_piece.setYLoc(Constants.SQUARE_SIZE);
					}
					// this adds the squares to the array and makes a new piece
					_piece.addSquares(_board, _centerPane);
					_piece = new Piece(_centerPane);
				}
				break;
			case P:
				/*
				 * The pause button "P" tracks whether the game is paused via an
				 * instance variable that is updated every time p is pressed. It
				 * checks if it is even or odd, and pauses or plays the game
				 * depending on that value.
				 */
				if (_pauseTracker % 2 == 0) {
					_timeline.pause();
					_pauseTracker++;
				} else if (_pauseTracker % 2 != 0) {
					_timeline.play();
					_pauseTracker++;
				}
				break;
			default:
				break;

			}
			e.consume();
		}
	}
}