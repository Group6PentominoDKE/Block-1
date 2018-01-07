package com.company;

import java.util.Arrays;

/**
 Creates an array of pentomino objects
 */

public class PentominoFactory {

    /**
     *	Constructor
     *	Creates an array of pentomino objects each with a unique matrix
     *	@param inputLetters A char array of the pentominoes that should be represented
     *	@return An array of unique pentomino objects
     */
    public static Pentomino[] createLetters(char[] inputLetters){

        int[][][] baseRotation; //Original form of letter
        int count = 0; // Counter for all unique orientations of the input pentominoes

        Pentomino[] AllVar = new Pentomino[163];
        Pentomino[] currentPentomino = new Pentomino[8];// array for all rotations and flips of the pentomino

        for (int i = 0; i < inputLetters.length; i++){//for every char
            baseRotation = createPemtomino(inputLetters[i]);
            int currentCounter = 0;

            for (int j = 0; j < 4; j++){
                if (isAllDifferent(baseRotation,currentPentomino)){
                    currentPentomino[currentCounter] = new Pentomino(baseRotation,inputLetters[i]);
                    currentCounter++;
                }
                baseRotation = rotateXy(baseRotation);
            }

            if(isDifferent(flipXyPlane(baseRotation),baseRotation)){

                baseRotation = flipXyPlane(baseRotation);

                for (int j = 0; j < 4; j++){
                    if (isAllDifferent(baseRotation,currentPentomino)){
                        currentPentomino[currentCounter] = new Pentomino(baseRotation,inputLetters[i]);
                        currentCounter++;
                    }
                    baseRotation = rotateXy(baseRotation);
                }
            }

            for (int k = 0; k < currentCounter; k++){
                AllVar[count] = currentPentomino[k];
                currentPentomino[k] = null;
                count++;
            }
        }
        return Arrays.copyOf(AllVar, count);
    }


    /**
     *	Creates a matrix that corresponds to which pentomino it should represent
     *	@param letter the char that the matrix must represent
     *	@return A integer matrix that represents the Char input where 1 represents a filled block and 0 represents an empty block
     */
    private static int[][][] createPemtomino(char letter){
        int[][][] result;
        if (letter == 'p')
            result =  new int[][][]{{{1,1},{1,1},{1,0}}};//P
        else if (letter == 't')
            result =  new int[][][]{{{1,1,1},{0,1,0},{0,1,0}}};//T
        /*else if (letter == 'a')
            result =  new int[][][]{{1,1,0},{0,1,0},{0,1,1}};//Z
        else if (letter == 'b')
            result =  new int[][][]{{1,0,1},{1,1,1}};//U
        else if (letter == 'c')
            result =  new int[][][]{{1,1,0,0},{0,1,1,1}};//N
        */
        else if(letter == 'l')
            result =  new int[][][]{{{0,0,0,1},{1,1,1,1}}};//L
        else result = null;

        return result;
    }

    /**
     *	Creates a mirror image of an inputted int matrix
     *	@param shape The original shape
     *	@return A mirror image of the of the front plane, mirrored on a vertical axis
     */
    private static int[][][] flipXyPlane(int[][][] shape){

        int height = shape[0].length;
        int width = shape[0][0].length;
        int[][][] newShape = new int[1][height][width];

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                newShape[0][i][width-j-1] = shape[0][i][j];
            }
        }


        return newShape;
    }
    private static int[][][] flipXzPlane(int[][][] shape){

        int height = shape[0].length;
        int planes = shape.length;
        int[][][] newShape = new int[planes][height][1];

        for (int i = 0; i < planes; i++){
            for (int j = 0; j < height; j++){
                newShape[i][height-j-1][0] = shape[i][j][0];
            }
        }


        return newShape;
    }
    private static int[][][] flipZyPlane(int[][][] shape){

        int planes = shape.length;
        int width = shape[0][0].length;
        int[][][] newShape = new int[planes][1][width];

        for (int i = 0; i < planes; i++){
            for (int j = 0; j < width; j++){
                newShape[i][0][width-j-1] = shape[i][0][j];
            }
        }


        return newShape;
    }

    /**
     *	Creates a copy of an imputted int matrix rotated 90degrees clockwise
     *	@params matrix The original matrix
     *	@return A matrix that is rotated 90 degrees clockwise from the original matrix
     */
    private static int[][][] rotateXy(int[][][] shape){
        int height = shape[0].length;
        int width = shape[0][0].length;
        int[][][] newShape = new int[1][width][height];
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                newShape[0][j][height-1-i] = shape[0][i][j];
            }
        }
        return newShape;
    }

    private static int[][][] rotateZy(int[][][] shape){
        int planes = shape.length;
        int width = shape[0][0].length;
        int[][][] newShape = new int[width][1][planes];
        for (int i = 0; i < planes; i++){
            for (int j = 0; j < width; j++){
                newShape[j][0][planes-1-i] = shape[i][0][j];
            }
        }
        return newShape;
    }
    private static int[][][] rotateXz(int[][][] shape){
        int height = shape[0].length;
        int planes = shape.length;
        int[][][] newShape = new int[height][planes][0];
        for (int i = 0; i < planes; i++){
            for (int j = 0; j < height; j++){
                newShape[j][planes-1-i][0] = shape[i][j][0];
            }
        }
        return newShape;
    }

    /**
     *	Tests whether an inputted matrix is different to every pentomino in an inputted array of pentominoes
     *	@params Mat The matrix that is being tested
     *	@params Arr An array of pentominoes
     *	@return Whether the inputted matrix is different from all the other pentominoes
     */
    private static boolean isAllDifferent(int[][][] Mat, Pentomino[] Arr){
        int i = 0;
        while (Arr[i] != null){
            if (isDifferent(Mat,Arr[i].getPositioningInSpace()) == false)
                return false;
            i++;
        }
        return true;
    }


    /**
     *	Tests whether an inputted matrix is different to another inputted matrix
     *	@params Mat1 The first matrix that is being tested
     *	@params Mat2 The second matrix that is being tested
     *	@return Whether the two inputted arrays are different
     */
    private static boolean isDifferent(int[][][] Mat1, int[][][] Mat2){
        if (Mat1[0].length != Mat2[0].length || Mat1[0][0].length != Mat2[0][0].length)
            return true;
        else {
            for (int i = 0; i < Mat1[0].length; i++)
                for (int j = 0; j < Mat1[0][0].length; j++){
                    if (Mat1[0][i][j] != Mat2[0][i][j])
                        return true;
                }
        }
        return false;
    }
}
