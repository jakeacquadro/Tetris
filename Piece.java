package Tetris;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*
 * Piece is the composite shape that the user moves around the screen. It randomly generates
 * a piece then add that piece to the pane in the argument. It has methods to set the x and
 * y locations of the pieces, rotate the pieces, check move validity in three directions,
 * and add the squares in the piece to an array. I chose to make one piece class rather than
 * separate classes for each piece.
 */
public class Piece {
	private Rectangle _square1;
	private Rectangle _square2;
	private Rectangle _square3;
	private Rectangle _square4;
	private int _x1;
	private int _y1;
	private int _x2;
	private int _y2;
	private int _x3;
	private int _y3;
	private int _x4;
	private int _y4;
	private String _shape;

	/*
	 * The constructor randomly generates a different piece. It instantiates
	 * four rectangles of the same size then depending on the random value, it
	 * provides a different piece. Each different piece has a different
	 * arrangement of the four squares and a different color. I use the instance
	 * variable _shape because it makes the code easier to read so that you can
	 * see which shape is being created but more importantly, so that the rotate
	 * method knows what piece is being rorated so that it doesn't rotate the
	 * "o block".
	 */
	public Piece(Pane pane) {
		_x1 = Constants.X1_INITIAL;
		_y1 = Constants.SQUARE_SIZE * 2;
		_square1 = new Rectangle(Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
		_square2 = new Rectangle(Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
		_square3 = new Rectangle(Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
		_square4 = new Rectangle(Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
		_square1.setStroke(Color.BLACK);
		_square2.setStroke(Color.BLACK);
		_square3.setStroke(Color.BLACK);
		_square4.setStroke(Color.BLACK);
		int rand = (int) (Math.random() * 7);
		switch (rand) {
		case 0:
			_x2 = _x1 + Constants.SQUARE_SIZE;
			_y2 = _y1;
			_x3 = _x1 - Constants.SQUARE_SIZE;
			_y3 = _y1;
			_x4 = _x1;
			_y4 = _y1 + Constants.SQUARE_SIZE;
			_square1.setFill(Color.GREEN);
			_square2.setFill(Color.GREEN);
			_square3.setFill(Color.GREEN);
			_square4.setFill(Color.GREEN);
			_shape = "tblock";
			break;

		case 1:
			_x2 = _x1;
			_x3 = _x1;
			_x4 = _x1;
			_y2 = _y1 + Constants.SQUARE_SIZE;
			_y3 = _y2 + Constants.SQUARE_SIZE;
			_y4 = _y3 + Constants.SQUARE_SIZE;
			_square1.setFill(Color.RED);
			_square2.setFill(Color.RED);
			_square3.setFill(Color.RED);
			_square4.setFill(Color.RED);
			_shape = "iblock";
			break;
		case 2:
			_x2 = _x1;
			_x3 = _x1 + Constants.SQUARE_SIZE;
			_x4 = _x3;
			_y2 = _y1 + Constants.SQUARE_SIZE;
			_y3 = _y1;
			_y4 = _y2;
			_square1.setFill(Color.CHARTREUSE);
			_square2.setFill(Color.CHARTREUSE);
			_square3.setFill(Color.CHARTREUSE);
			_square4.setFill(Color.CHARTREUSE);
			_shape = "oblock";
			break;
		case 3:
			_x2 = _x1;
			_y2 = _y1 + Constants.SQUARE_SIZE;
			_x3 = _x1 + Constants.SQUARE_SIZE;
			_y3 = _y1 + Constants.SQUARE_SIZE;
			_x4 = _x1 - Constants.SQUARE_SIZE;
			_y4 = _y1;
			_square1.setFill(Color.BLUE);
			_square2.setFill(Color.BLUE);
			_square3.setFill(Color.BLUE);
			_square4.setFill(Color.BLUE);
			_shape = "zblock";
			break;
		case 4:
			_x2 = _x1;
			_y2 = _y1 + Constants.SQUARE_SIZE;
			_x3 = _x1 - Constants.SQUARE_SIZE;
			_y3 = _y1 + Constants.SQUARE_SIZE;
			_x4 = _x1 + Constants.SQUARE_SIZE;
			_y4 = _y1;
			_square1.setFill(Color.YELLOW);
			_square2.setFill(Color.YELLOW);
			_square3.setFill(Color.YELLOW);
			_square4.setFill(Color.YELLOW);
			_shape = "sblock";
			break;
		case 5:
			_x2 = _x1;
			_x3 = _x1;
			_x4 = _x1 + Constants.SQUARE_SIZE;
			_y2 = _y1 + Constants.SQUARE_SIZE;
			_y3 = _y2 + Constants.SQUARE_SIZE;
			_y4 = _y1;
			_square1.setFill(Color.ORANGE);
			_square2.setFill(Color.ORANGE);
			_square3.setFill(Color.ORANGE);
			_square4.setFill(Color.ORANGE);
			_shape = "rblock";
			break;
		case 6:
			_x2 = _x1;
			_x3 = _x1;
			_x4 = _x1 - Constants.SQUARE_SIZE;
			_y2 = _y1 + Constants.SQUARE_SIZE;
			_y3 = _y2 + Constants.SQUARE_SIZE;
			_y4 = _y1;
			_square1.setFill(Color.PURPLE);
			_square2.setFill(Color.PURPLE);
			_square3.setFill(Color.PURPLE);
			_square4.setFill(Color.PURPLE);
			_shape = "lblock";
			break;
		}
		_square1.setX(_x1);
		_square1.setY(_y1);
		_square2.setX(_x2);
		_square2.setY(_y2);
		_square3.setX(_x3);
		_square3.setY(_y3);
		_square4.setX(_x4);
		_square4.setY(_y4);
		pane.getChildren().addAll(_square1, _square2, _square3, _square4);
	}

	/*
	 * This method calculates the position of all the squares rotated around
	 * each point then updates their positions to these calculated positions.
	 */
	public void rotate() {
		// this first line makes sure it doesn't rotate the o block.
		if (_shape != "oblock") {

			/*
			 * I have all of these local variables because I don't want their
			 * value to update until this method is called and I want them to be
			 * constant throughout the method
			 */
			int oldx1 = _x1;
			int oldy1 = _y1;
			int oldx2 = _x2;
			int oldy2 = _y2;
			int oldx3 = _x3;
			int oldy3 = _y3;
			int oldx4 = _x4;
			int oldy4 = _y4;
			/*
			 * Instead of nesting the value of newx and newy in the setter
			 * methods, I use local variables because I want to update _x and _y
			 * below for each square
			 */
			int newx1 = _x1 - _y1 + oldy1;
			int newy1 = _y1 + _x1 - oldx1;
			_square1.setX(newx1);
			_square1.setY(newy1);
			int newx2 = _x1 - _y1 + oldy2;
			int newy2 = _y1 + _x1 - oldx2;
			_square2.setX(newx2);
			_square2.setY(newy2);
			int newx3 = _x1 - _y1 + oldy3;
			int newy3 = _y1 + _x1 - oldx3;
			_square3.setX(newx3);
			_square3.setY(newy3);
			int newx4 = _x1 - _y1 + oldy4;
			int newy4 = _y1 + _x1 - oldx4;
			_square4.setX(newx4);
			_square4.setY(newy4);
			_x1 = newx1;
			_y1 = newy1;
			_x2 = newx2;
			_y2 = newy2;
			_x3 = newx3;
			_y3 = newy3;
			_x4 = newx4;
			_y4 = newy4;
		}
	}

	/*
	 * This checks if rotation is possible by checking the indices of each of
	 * the new x and y locations of the squares. If the piece can rotate, it
	 * will return true. If not, it will return false.
	 */
	public Boolean checkRotateValidity(Rectangle[][] board) {
		int oldx1 = _x1;
		int oldy1 = _y1;
		int oldx2 = _x2;
		int oldy2 = _y2;
		int oldx3 = _x3;
		int oldy3 = _y3;
		int oldx4 = _x4;
		int oldy4 = _y4;
		int newx1 = _x1 - _y1 + oldy1;
		int newy1 = _y1 + _x1 - oldx1;
		int newx2 = _x1 - _y1 + oldy2;
		int newy2 = _y1 + _x1 - oldx2;
		int newx3 = _x1 - _y1 + oldy3;
		int newy3 = _y1 + _x1 - oldx3;
		int newx4 = _x1 - _y1 + oldy4;
		int newy4 = _y1 + _x1 - oldx4;
		if (board[newx1 / Constants.SQUARE_SIZE][newy1 / Constants.SQUARE_SIZE] == null
				&& board[newx2 / Constants.SQUARE_SIZE][newy2
						/ Constants.SQUARE_SIZE] == null
				&& board[newx3 / Constants.SQUARE_SIZE][newy3
						/ Constants.SQUARE_SIZE] == null
				&& board[newx4 / Constants.SQUARE_SIZE][newy4
						/ Constants.SQUARE_SIZE] == null) {
			return true;
		} else
			return false;
	}

	/*
	 * Checks downward movement validity by making sure the spot below each of
	 * the squares is not occupied by another square. It returns a boolean for
	 * other methods in the game class to use.
	 */
	public Boolean checkDownValidity(Rectangle[][] board) {
		if (board[_x1 / Constants.SQUARE_SIZE][_y1 / Constants.SQUARE_SIZE + 1] == null
				&& board[_x2 / Constants.SQUARE_SIZE][_y2
						/ Constants.SQUARE_SIZE + 1] == null
				&& board[_x3 / Constants.SQUARE_SIZE][_y3
						/ Constants.SQUARE_SIZE + 1] == null
				&& board[_x4 / Constants.SQUARE_SIZE][_y4
						/ Constants.SQUARE_SIZE + 1] == null) {
			return true;
		} else
			return false;
	}

	/*
	 * Checks right movement validity by making sure the spot to the right of
	 * each of the squares is not occupied by another square. It returns a
	 * boolean for other methods in the game class to use.
	 */
	public Boolean checkRightValidity(Rectangle[][] board) {
		if (board[_x1 / Constants.SQUARE_SIZE + 1][_y1 / Constants.SQUARE_SIZE] == null
				&& board[_x2 / Constants.SQUARE_SIZE + 1][_y2
						/ Constants.SQUARE_SIZE] == null
				&& board[_x3 / Constants.SQUARE_SIZE + 1][_y3
						/ Constants.SQUARE_SIZE] == null
				&& board[_x4 / Constants.SQUARE_SIZE + 1][_y4
						/ Constants.SQUARE_SIZE] == null) {
			return true;
		} else
			return false;
	}

	/*
	 * Checks left movement validity by making sure the spot to the left of each
	 * of the squares is not occupied by another square
	 */
	public Boolean checkLeftValidity(Rectangle[][] board) {
		if (board[_x1 / Constants.SQUARE_SIZE - 1][_y1 / Constants.SQUARE_SIZE] == null
				&& board[_x2 / Constants.SQUARE_SIZE - 1][_y2
						/ Constants.SQUARE_SIZE] == null
				&& board[_x3 / Constants.SQUARE_SIZE - 1][_y3
						/ Constants.SQUARE_SIZE] == null
				&& board[_x4 / Constants.SQUARE_SIZE - 1][_y4
						/ Constants.SQUARE_SIZE] == null) {
			return true;
		} else
			return false;
	}

	/*
	 * The setXLoc and setYLoc methods move the squares by the amount entered as
	 * an argument. They update the _x and _y instance variable for location
	 * then set the position to these.
	 */
	public void setXLoc(int x) {
		_x1 = _x1 + x;
		_x2 = _x2 + x;
		_x3 = _x3 + x;
		_x4 = _x4 + x;
		_square1.setX(_x1);
		_square2.setX(_x2);
		_square3.setX(_x3);
		_square4.setX(_x4);
	}

	public void setYLoc(int y) {
		_y1 = _y1 + y;
		_y2 = _y2 + y;
		_y3 = _y3 + y;
		_y4 = _y4 + y;
		_square1.setY(_y1);
		_square2.setY(_y2);
		_square3.setY(_y3);
		_square4.setY(_y4);
	}

	/*
	 * This adds the squares to the array by dividing their locations by the
	 * size of the square
	 */
	public void addSquares(Rectangle[][] board, Pane pane) {
		board[_x1 / Constants.SQUARE_SIZE][_y1 / Constants.SQUARE_SIZE] = _square1;
		board[_x2 / Constants.SQUARE_SIZE][_y2 / Constants.SQUARE_SIZE] = _square2;
		board[_x3 / Constants.SQUARE_SIZE][_y3 / Constants.SQUARE_SIZE] = _square3;
		board[_x4 / Constants.SQUARE_SIZE][_y4 / Constants.SQUARE_SIZE] = _square4;
	}

}
