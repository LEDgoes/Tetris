Tetris
======

Tetris for LEDgoes, written in Java

This game was designed to work using 4 LEDgoes displays connected vertically, with the identifiers of 0, 16, 32, and 48 in that order.

This program is designed to be run as a java application. When run, a window will open on your computer and the game of Tetris will begin on the LEDgoes display.
If you would like to have the Tetris game displayed in the window on your computer as well, uncomment the lines:
//board.draw(g);
//drawCurrentPiece(g);
in the draw method within the Tetris controller class.

The game is dependent on the RXTX library, which is used to communicate to the LEDgoes display through a USB port.
To change the port that you are using, change the name of the port that you are connecting to inside the TetrisController class.





