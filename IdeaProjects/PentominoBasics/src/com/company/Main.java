package com.company;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static Pentomino[] pentominoes;
    private static char[][][] cargoSpace;//globally defining the cargoSpace
    private static int solutionsCount;
    private static int emptyCells;
    private static int width;
    private static int height;
    private static int length;
    private static boolean solutionFound;
    private static boolean countingNeeded =false;


    public static void main(String[] args) {

        //Start input
        Scanner sc = new Scanner(System.in);
        System.out.println("Width, height and length of the cargoSpace: ");
        String boardSize = sc.nextLine();
        String[] dimension = boardSize.split(" ");

        while (!dimensionValidation(dimension)) {
            System.out.println("Width and height need to be integers. Input 2 integers");
            boardSize = sc.nextLine();
            dimension = boardSize.split(" ");
        }
        width =  Integer.parseInt(dimension[0]);
        height =  Integer.parseInt(dimension[1]);

        boolean rotateBoard =false;
        if(width > height ) { // if the width is bigger the program is slower, that is why we can swap the height and width
            int temp = width;
            width = height;
            height = temp;
            rotateBoard =true;
        }
        System.out.println("Write letters without spaces:  ");
        String input = sc.nextLine();

        char[] inputArray = input.toLowerCase().toCharArray();

        while(!pentominoesValidation(inputArray)) {
            System.out.println("Invalid input. Try again:");

            input = sc.nextLine();
            inputArray = input.toCharArray();
        }

        System.out.println("Do you want to see just one solution or count all the solutions?");
        System.out.println("type '1' for one solution");
        System.out.println("type 'count' for counting (this could take around 15 seconds in worst cases)");
        String command = sc.nextLine();
        if(Objects.equals(command, "count"))
        {
            countingNeeded = true;
        }

        //End input

        cargoSpace = new char[height][width][length];
        for (int l = 0; l < height; l++) {
            for (int j = 0; j < width; j++) {
                for (int i = 0; i < length; i++) {
                    cargoSpace[l][j][i] = ' ';
                }
            }
        }

        pentominoes = PentominoFactory.createLetters(inputArray);
        int [][][] shape;
        for(Pentomino pent: pentominoes)
        {
            System.out.println("new shape");
            shape = pent.getPositioningInSpace();
            for (int i = 0; i < shape.length; i++) {
                System.out.println("new plane");
                for (int j = 0; j < shape[0].length; j++) {
                    for (int k = 0; k < shape[0][0].length; k++) {
                        System.out.print(shape[i][j][k] + " ");
                    }
                    System.out.println();
                }
            }
        }

        long startTime = System.nanoTime();

        //searchSolution(0 ,0, 0);

        long endTime = System.nanoTime();

        System.out.println("Answer found in " +(endTime- startTime)/1000000 + " milliseconds");

        if(countingNeeded) {
            System.out.println("Count of solutions: " + solutionsCount / 4);
            return;
        }

        if(solutionFound)
        {
            /*if(rotateBoard)
            {
                cargoSpace = rotateBoard(cargoSpace);
                height = cargoSpace.length;
                width = cargoSpace[0].length;
            }*/

            //Display.disp(cargoSpace);
        }
        else {
            System.out.println("No possible solution");
        }


    }

    /**
     * Essential method of the program containing most of the logic. It is recursive and it iterates through all of the pentominoes
     * If it finds fitting pentomino it goes into recursion and when there are no more possible pentominoes it goes out of the recursion and backtracks
     * @param x coordinate of x axis
     * @param y coordinate of y axis
     */
    private static void searchSolution(int x, int y, int z) {

        for (Pentomino currentPentomino : pentominoes) {

            boolean currentFits = isCurrentFits(x, y, z, currentPentomino);

            if (currentFits) {
                putPentominoOnTheBoard(x, y, z, currentPentomino);

                /*if (impossibleCase()) {
                    removePentomino(x, y, z , currentPentomino);
                    continue;
                }*/

                boolean freeCellFound = false;


                for (int l = 0; l < height; l++) { //checking for free cell
                    for (int j = 0; j < width; j++) {
                        for (int i = 0; i < length; i++) {

                            if (cargoSpace[l][j][i] == ' ') {

                                freeCellFound = true;
                                searchSolution(l, j, i);/// go into the recursion

                                if (solutionFound && !countingNeeded) //if the condition is true the program will go out of all recursions
                                {
                                    return;
                                }
                                break;/// when the method executes, the recursion has stopped and we should stop iterating the cargoSpace
                            }
                        }
                        if (freeCellFound) break;
                    }
                }

                if (!freeCellFound) {
                    if (countingNeeded) {
                        for (int l = 0; l < height; l++) {
                            for (int j = 0; j < width; j++) {
                                System.out.print(cargoSpace[l][j] + " ");
                            }
                            System.out.println();
                        }
                        System.out.println();
                        solutionsCount++;
                    } else {
                        solutionFound = true;
                        return;
                    }
                }

                removePentomino(x, y,z, currentPentomino); //backtracking

            }

        }
    }

    /**
     * The method gets the shape of the current pentomino, puts it on the cargoSpace and assign the current cell as filled
     * The method also makes the position of the pentomino in boolean array true, so that it cannot be used again
     * @param x coordinate of x axis
     * @param y coordinate of y axis
     * @param pentomino the current pentomino
     */
    private static void putPentominoOnTheBoard(int x, int y, int z, Pentomino pentomino) {
        int xPosition;
        int yPosition;
        int zPosition;

        int planes = pentomino.getPositioningInSpace().length;
        int rows = pentomino.getPositioningInSpace()[0].length;
        int cols = pentomino.getPositioningInSpace()[0][0].length;


        int startX = pentomino.xIndexOfStartPoint();
        int startY = pentomino.yIndexOfStartPoint();
        int startZ = pentomino.zIndexOfStartPoint();

        for (int i = 0; i < planes; i++) {
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    if (pentomino.getPositioningInSpace()[i][j][k] == 1) {
                        xPosition = x + i - startX;
                        yPosition = y + j - startY;
                        zPosition = z + k - startZ;///check if working
                        cargoSpace[zPosition][xPosition][yPosition] = pentomino.getType();
                    }
                }

            }
        }
    }

    /**
     * The method gets the shape of the current pentomino and only checks if it could fit on the cargoSpace.
     * @param x coordinate of x axis
     * @param y coordinate of y axis
     * @param pentomino the current pentomino
     */
    private static boolean isCurrentFits(int x, int y, int z, Pentomino pentomino) {
        int xPosition;
        int yPosition;
        int zPosition = 0;
        boolean currentFits = true;

        int planes = pentomino.getPositioningInSpace().length;
        int rows = pentomino.getPositioningInSpace()[0].length;
        int cols = pentomino.getPositioningInSpace()[0][0].length;

        int startX = pentomino.xIndexOfStartPoint();
        int startY = pentomino.yIndexOfStartPoint();
        int startZ = pentomino.zIndexOfStartPoint();

        for (int k = 0; k < planes; k++) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (pentomino.getPositioningInSpace()[k][i][j] == 1) {
                        xPosition = x + i - startX;
                        yPosition = y + j - startY;
                        zPosition = z + k - startZ;///check if working
                        if (zPosition < 0 || xPosition < 0 || yPosition < 0 || xPosition >= height || yPosition >= width || zPosition >= length ||
                                cargoSpace[xPosition][yPosition][zPosition] != ' ') {
                            currentFits = false;
                            break;
                        }
                    }
                }
            }
        }

        return currentFits;
    }

    /**
     * The method gets the shape of the current pentomino, removes it from the cargoSpace and assign the current cell as empty(not filled)
     * The method also makes the position of the pentomino in boolean array false, so that it can be used again
     * @param x coordinate of x axis
     * @param y coordinate of y axis
     * @param pentomino the current pentomino
     */
    private static void removePentomino(int x, int y, int z, Pentomino pentomino) {
        int xPosition;
        int yPosition;
        int zPosition;

        int planes = pentomino.getPositioningInSpace().length;
        int rows = pentomino.getPositioningInSpace()[0].length;
        int cols = pentomino.getPositioningInSpace()[0][0].length;

        int startX = pentomino.xIndexOfStartPoint();
        int startY = pentomino.yIndexOfStartPoint();
        int startZ = pentomino.zIndexOfStartPoint();

        for (int i = 0; i < planes; i++) {
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    if(pentomino.getPositioningInSpace()[i][j][k] == 1) {
                        xPosition = x + i - startX;
                        yPosition = y + j - startY;
                        zPosition = z + k - startZ;///check if working
                        cargoSpace[xPosition][yPosition][zPosition] = ' ';
                    }
                }

            }
        }
    }

    /**
     * The method is recursive and counts all isolated empty cells in one region
     * @param x coordinate of x axis
     * @param y coordinate of y axis
     */
    /*private static void countEmptyCells(int x, int y) {

        if (y >= 0 && x < height && x >= 0 && y < width && cargoSpace[x][y] == ' '){
            cargoSpace[x][y] = 'a';
            emptyCells++;

        }
        else{

            return;
        }
        countEmptyCells(x,y-1);
        countEmptyCells(x+1,y);
        countEmptyCells(x,y+1);
        countEmptyCells(x-1,y);
    }*/

    /**
     * The method checks the most left cells and searches for an empty one. When found, it counts how many isolated cells are there
     *
     */
    /*private static boolean impossibleCase() {

        for (int l = 0; l < width; l++) { //checking for free cell
            for (int j = 0; j < height; j++) {
                if (cargoSpace[j][l] == ' '){
                    emptyCells =0;
                    countEmptyCells(j, l);

                    for (int k = 0; k < height; k++) { //backtracking
                        for (int m = 0; m < width; m++) {
                            if (cargoSpace[k][m] == 'a')
                                cargoSpace[k][m] = ' ';
                        }
                    }

                    if(emptyCells % 5 != 0) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }
*/
    /**
     * Tests whether valid dimension inputs were inputted
     * @param dimension
     * @return Whether the input was valid
     */

    private static boolean dimensionValidation(String[] dimension) {
        try {
            width =  Integer.parseInt(dimension[0]);
            height =  Integer.parseInt(dimension[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether an incorrect symbol has been typed
     * @param input array of pentomino characters
     * @return false if there is an incorrect letter
     */
    private static boolean pentominoesValidation(char[] input) {
        for (int i = 0; i < input.length; i++) {//if the input is not a pentomino letter
            if (input[i] != 'u' && input[i] != 'z' && input[i] != 'y' && input[i] != 'n' && input[i] != 'i' && input[i] != 'f' &&
                    input[i] != 't' && input[i] != 'x' && input[i] != 'w' && input[i] != 'l' && input[i] != 'p' && input[i] != 'v') {
                return false;
            }
            if (input[i] == ' ') { //If space was inputted
                return false;
            }
        }
        return true;
    }


    /**
     * Rotates a char matrix98888888
     * @param matrix the original matrix
     * @return char matrix of original matrix that has been rotated
     */
    private static char[][] rotateBoard(char[][] matrix){//probably it is not useful anymore
        int matLen = matrix.length;
        int matWid = matrix[0].length;
        char[][] newMatrix = new char[matWid][matLen];
        for (int i = 0; i < matLen; i++){
            for (int j = 0; j < matWid; j++){
                newMatrix[j][matLen-1-i] = matrix[i][j];
            }
        }
        return newMatrix;
    }
}
