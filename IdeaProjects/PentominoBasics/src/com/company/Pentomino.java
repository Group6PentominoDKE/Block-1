package com.company;

/**
 A single pentomino object
 */

public class Pentomino {

    private int[][][] positioningInSpace;
    private char type;

    /**
     Constructor
     @param positioningInSpace the position of the pentomino in the space
     @param c the type the pentomino represents
     */
    public Pentomino(int[][][] positioningInSpace, char c){
        this.positioningInSpace = positioningInSpace;
        this.type = c;
    }

    /**
     * Return LetterMatrix passed to the constructor.
     * @return LetterMatrix
     */
    public int[][][] getPositioningInSpace(){
        return this.positioningInSpace;
    }

    /**
     * Return type passed to the constructor.
     * @return Character */
    public char getType(){
        return this.type;
    }

    /**
     Method that determines which index in the array is considered the starting point for placing the pentomino on the board.
     @return the column index of the first cell in the first row of the matrix that is 1 i.e. not empty
     */
    public int xIndexOfStartPoint(){
        int index=0;
        for (int i = 0; i < this.positioningInSpace.length; i++) {
            if(this.positioningInSpace[i][0][0] == 1) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     Method that determines which index in the array is considered the starting point for placing the pentomino on the board.
     @return the column index of the first cell in the first row of the matrix that is 1 i.e. not empty
     */
    public int yIndexOfStartPoint(){
        int index=0;
        for (int i = 0; i < this.positioningInSpace[0].length; i++) {
            if(this.positioningInSpace[0][i][0] == 1) {
                index = i;
                break;
            }
        }
        return index;
    }
    /**
     Method that determines which index in the array is considered the starting point for placing the pentomino on the board.
     @return the column index of the first cell in the first row of the matrix that is 1 i.e. not empty
     */
    public int zIndexOfStartPoint(){
        int index=0;
        for (int i = 0; i < this.positioningInSpace[0][0].length; i++) {
            if(this.positioningInSpace[0][0][i] == 1) {
                index = i;
                break;
            }
        }
        return index;
    }

}
