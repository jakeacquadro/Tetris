README Tetris

HANDIN:
This is my final handin for Tetris.

DESIGN CHOICES:
I wrote Tetris with the following classes: App, PaneOrganizer, Game,
Piece, Constants.

App has the start method which starts the application and instantiates the
top-level object, a PaneOrganizer.

PaneOrganizer instantiates the Game, BorderPane, and BottomPane, and has the
methods getRoot which gets the root BorderPane, createBottomPane which
creates the bottomPane and createQuitButton which creates a button that quits
the game and is part of the bottomPane which is a Vbox. It also has an event
handler called ClickHandler which responds to mouse clicking. It adds the
CenterPane and ScorePane from Game into the BorderPane.

Game controls the game logic and animates the game. Pieces are instantiated
within the game. It has the methods getCenterPane and getScorePane to get
these two panes for PaneOrganizer, the method setupScorePane to create the
scorePane and its label, updateScoreLabel to keep track of the score in the
scorePane, createBorder to fill in the rectangles that make up the border,
setUpTimeline to create a timeline and a keyframe, updateSpeed which changes
the speed depending on the score of the game, colorsChange to change the colors
of the border when the game reaches a certain score, gameOver which checks
to see if the conditions for ending the game are met, then ends the game
if they are, and clearLines to remove fully filled lines and shift everything above
them downward. It has two private inner classes, KeyHandler which deals with
left, right, down, and rotating of the pieces, as well as P for pausing the game
and SPACE to move pieces to the bottom instantly, and TimeHandler to shift the
pieces down after every keyframe, clear lines, end the game, and update the
difficulty of the game (speed).

Piece is what the player moves around. It consists of four rectangles that are
randomly generated to be in different positions relative to each other. Its
constructor randomly generates their positions and assigns colors depending
on the piece type, then adds them to the game. It has the method rotate which
spins the piece around so it can be placed in a different orientation. It also
has the methods checkRotateValidity, checkRightValidity, checkLeftValidity,
and checkDownValidity to make sure these moves can all be made without
overlapping with other squares in the array. setXLoc and setYloc shift the
piece by the inputted argument. addSquares adds the squares in the piece to
the array and is called when it can no longer move down.

Constants contains all of the constants used throughout the program.

KNOWN BUGS:
The red long piece will sometimes stack on top of other pieces at the top when
the game ends.
