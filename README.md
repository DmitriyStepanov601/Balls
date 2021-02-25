# Balls

This application is an interpretation of the famous game Balls.
Each turn of the game, 3 balls of different colors appear randomly on the playing field. The player must collect lines of 5 balls of the same color during the game. The more such lines, the more points the player will get in the game. In the upper-left corner of the window are the number of points scored by the user, while at the very bottom at the beginning of the game is a line about the beginning of the game, which disappears during the game and appears only at the beginning of a new game.

The game takes place on a square field of 10 by 10 cells and is a series of moves. Each turn, the computer first puts five balls of random colors in random cells. Next, the player makes a move when he can move any ball to another free cell, but there must be a non-diagonal path of free cells between the start and end cells. If after moving it turns out that five balls of the same color are collected in a line horizontally, vertically or diagonally, then all such balls disappear and the player is given the opportunity to make another movement of the ball. If the line does not line up after moving, then the turn ends, and a new one begins with the appearance of new balls. If a line is collected when new balls appear, it disappears, the player gets points, but no additional movement is given. The game continues until the entire field is filled with balls and the player can not make a move.

The goal of the game is to get the maximum number of points. The score is arranged in such a way that if you remove more than 5 balls in one move, the player gets significantly more points.

![balls](https://user-images.githubusercontent.com/61186198/109145541-a817ab80-7773-11eb-9c70-cbd3e929f73a.gif)

