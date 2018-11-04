# machine_learning-minimax

Description: Implementation of the minimax algorithm to learn the game Othello, with multiple different evaluation functions

This project implements multiple AIs to learn to play the game Othello. The central algorithm is minimax with alpha beta
pruning, which traverses a game tree to predict successful moves. We implement alpha beta pruning to increase efficiency by
eliminating poor moves in the game tree. We experiment with different evaluation functions to best traverse the game tree,
and thus build the smartest AI. DumAI.java is the simplest evaluation function, which choses a move to explore at random.
The following classes implement smarter eval functions:

OthelloAI2
OthelloAI2a
OthelloAI2c
OthelloAI2d
OthelloAI2e
OthelloAI2f

To pay: The program takes 3 arguments...2 AIs and an even number for the size of the board. The first argument can
be replaced by the word 'human' for self play.

For example: $ java Othello human DumAI 8

*you must click the GUI to advance the AI moves
