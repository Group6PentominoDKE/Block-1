public class DynamicProgramming {
	
	private double[][][][] pointsArray;
	private Filled[][][][] filledArray;
	final private int[] DIMENSIONSOFFINALBOX = {8,5,33};
	
	public DynamicProgramming(char[] inputLetters){
		
		Pentomino[] allRotations = PentominoFactory.createLetters(inputLetters);
		
        for (int i = 0; i < allRotations.length; i++) {
            if(allRotations[i].getType() == 'a' || allRotations[i].getType() == 'l')
            {
            	allRotations[i].setValue(3);
            }
            else if(allRotations[i].getType() == 'b' || allRotations[i].getType() == 'p')
            {
            	allRotations[i].setValue(4);
            }
            else if(allRotations[i].getType() == 'c' || allRotations[i].getType() == 't')
            {
            	allRotations[i].setValue(5);
            }
        }
		
		pointsArray = new double[2][DIMENSIONSOFFINALBOX[2]][DIMENSIONSOFFINALBOX[1]][DIMENSIONSOFFINALBOX[0]];
		filledArray = new Filled[2][DIMENSIONSOFFINALBOX[2]][DIMENSIONSOFFINALBOX[1]][DIMENSIONSOFFINALBOX[0]];
		intializeLevel(pointsArray[0], filledArray[0]);
		intializeLevel(pointsArray[1], filledArray[1]);
		
		for (int i = 0; i < allRotations.length ; i++) {
			for (int z = 0; z< DIMENSIONSOFFINALBOX[2]; z++)
				for (int y = 0; y< DIMENSIONSOFFINALBOX[1]; y++)
					for (int x = 0; x< DIMENSIONSOFFINALBOX[0]; x++) {
						char[][][] currentBox = filledArray[1][z][y][x].getFilled();
						if (boxFits(allRotations[i].getCharRepresentation(),currentBox)) {
							if (i == 7) {
								int h = 1;
								
							}							
							char[][][] firstBox = allRotations[i].getCharRepresentation();
							int[] startCoords = {0,0,currentBox.length-firstBox.length};
							placeSmallerArrayInArray(currentBox, firstBox, startCoords);							
							double points = fillBox(currentBox,1, allRotations[i]);							
							if (pointsArray[0][z][y][x] <= points) {
								pointsArray[1][z][y][x] = points;
							} else {
								pointsArray[1][z][y][x] = pointsArray[0][z][y][x];
								filledArray[1][z][y][x] = new Filled(filledArray[0][z][y][x].getFilled());
							}

						} else {
							pointsArray[1][z][y][x] = pointsArray[0][z][y][x];
							filledArray[1][z][y][x] = new Filled(filledArray[0][z][y][x].getFilled());
						}
					}
			copyLevel();
			intializeLevel(pointsArray[1], filledArray[1]);
		}
	}
	
	public double getMaxPoints(){
		int z = pointsArray[0].length-1;
		int y = pointsArray[0][0].length-1;
		int x = pointsArray[0][0][0].length-1;
		return pointsArray[0][z][y][x];
	}
	
	public char[][][] getMaxFilled(){
		int z = filledArray[0].length-1;
		int y = filledArray[0][0].length-1;
		int x = filledArray[0][0][0].length-1;
		return filledArray[0][z][y][x].getFilled();
	}
	
	private void intializeLevel(double[][][] points, Filled[][][] filled){
		for (int z = 0; z< DIMENSIONSOFFINALBOX[2]; z++)
			for (int y = 0; y< DIMENSIONSOFFINALBOX[1]; y++)
				for (int x = 0; x < DIMENSIONSOFFINALBOX[0]; x++) {	
						points[z][y][x] = 0;
						filled[z][y][x] = new Filled(x,y,z);
				}
	}
	
	private void copyLevel(){
		for (int z = 0; z < DIMENSIONSOFFINALBOX[2]; z++)
			for (int y = 0; y< DIMENSIONSOFFINALBOX[1]; y++)
				for (int x = 0; x < DIMENSIONSOFFINALBOX[0]; x++) {
					pointsArray[0][z][y][x] = pointsArray[1][z][y][x];
					char[][][] original = filledArray[1][z][y][x].getFilled();
					char[][][] copy = new char[original.length][original[0].length][original[0][0].length];
					for (int i = 0; i < original.length; i++)
						for (int j = 0; j < original[0].length; j++) {
							System.arraycopy(original[i][j], 0, copy[i][j] , 0, original[0][0].length);
						}
					filledArray[0][z][y][x] = new Filled(copy);
				}
	}
	
	
	private boolean boxFits(char[][][] smallBox, char[][][] bigBox) {
		if (smallBox.length > bigBox.length)
			return false;
		if (smallBox[0].length > bigBox[0].length)
			return false;
		if (smallBox[0][0].length > bigBox[0][0].length)
			return false;
		return true;
	}
	
	private String[][] findDimensions(char[][][] inBox){
		int height = inBox.length;
		int length = inBox[0].length;
		int width = inBox[0][0].length;
		String[][] tempReturnStringArray = new String[1000][2]; // start coordinates, dimensions
		int counter = 0;
		for (int z = height -1; z >= 0; z--)
			for (int y = 0; y < length; y++)
				for (int x = 0; x < width; x++){
					if (inBox[z][y][x] == 'E'){ 
						String[] possibleAddition = new String[2];
						possibleAddition[0] = x+"x"+y+"x"+z;
						possibleAddition[1] =  findEmptyRectangle(x, y, z, inBox);
						counter = addToArray(counter, tempReturnStringArray, possibleAddition);
					}
				}
		
		String[][] finalReturnStringArray = new String[counter][2];
		for (int i = 0; i < counter; i++) {
			System.arraycopy(tempReturnStringArray[i], 0, finalReturnStringArray[i], 0, 2);
		}		
		return finalReturnStringArray;
	}
	
	private String findEmptyRectangle(int x, int y, int z, char[][][] inBox){
		int tempX = 0;
		int tempY = 0;
		int tempZ = 0;

		do {
			tempX++;
		} while(x+tempX < inBox[0][0].length && inBox[z][y][x+tempX] == 'E');
		
		do {
			tempY++;
		} while(y+tempY < inBox[0].length && inBox[z][y+tempY][x] == 'E');
		
		do {
			tempZ++;
		} while(z+tempZ < inBox.length && inBox[z+tempZ][y][x] == 'E');
		
		for (int z1 = z; z1 < z+tempZ-1; z1++)
			for (int y1 = y; y1 < y+tempY-1; y1++)
				for (int x1 = x; x1 < x+tempX-1; x1++) {
					
					if (x1+1 < inBox[0][0].length && z1 < inBox.length && y1 < inBox[0].length && inBox[z1][y1][x1+1] != 'E'){
						tempX = x1;
					}	


					if (y1+1 < inBox[0].length && x1+1 < inBox[0][0].length && z1 < inBox.length &&inBox[z1][y1+1][x1] != 'E'){
						tempY = y1;
					}
					
					if (z1+1 < inBox.length && y1+1 < inBox[0].length && x1+1 < inBox[0][0].length && inBox[z1+1][y1][x1] != 'E'){
						tempZ = z1;
					}						
				}
		
		return tempX+"x"+tempY+"x"+tempZ;
		
	} 
	
	private int addToArray(int counter, String[][] array, String[] potentialAddition) {
		if (counter > 0){
			int[] potAdd = splitStringToInt(potentialAddition[1]);
			boolean addOrStop = true;
			for (int i = 0; i < counter; i++) {
				int[] comp1 = splitStringToInt(array[i][1]);
				if (isBiggerBox(comp1,potAdd)){
					for (int j = 0; j < 2; j++) {
						array[i][j] = potentialAddition[j];
					}
					addOrStop = false;
					break;
				} else if (isSmallerBox(comp1,potAdd)){
					addOrStop = false;
					break;
				}
			}
			if (addOrStop) {
				for (int j = 0; j < 2; j++) {
					array[counter][j] = potentialAddition[j];
				}
				counter++;
			}
		} else {
			for (int j = 0; j < 2; j++) {
				array[counter][j] = potentialAddition[j];
			}
			counter++;
		}
		return counter;
	}
	
	private boolean isBiggerBox(int[]comp1, int[] comp2) {
		if (comp1[0] == comp2[0]) {
			if (comp1[1] < comp2[1] && comp1[2] <= comp2[2]){
				return true;
			} else {
				if (comp1[2] < comp2[2] && comp1[1] == comp2[1]) {
					return true;
				}
			}
		} else if (comp1[1] == comp2[1]) {
			if (comp1[0] < comp2[0] && comp1[2] <= comp2[2]){
				return true;
			} else {
				if (comp1[2] < comp2[2] && comp1[0] == comp2[0]) {
					return true;
				}
			}
		} else if (comp1[2] == comp2[2]) {
			if (comp1[0] < comp2[0] && comp1[1] <= comp2[1]){
				return true;
			} else {
				if (comp1[1] < comp2[1] && comp1[0] == comp2[0]) {
					return true;
				}
			}
		} 
		
		return false;
	}
	
	private boolean isSmallerBox(int[]comp1, int[] comp2) {
		if (comp1[0] == comp2[0]) {
			if (comp1[1] > comp2[1] && comp1[2] >= comp2[2]){
				return true;
			} else {
				if (comp1[2] > comp2[2] && comp1[1] == comp2[1]) {
					return true;
				}
			}
		} else if (comp1[1] == comp2[1]) {
			if (comp1[0] > comp2[0] && comp1[2] >= comp2[2]){
				return true;
			} else {
				if (comp1[2] > comp2[2] && comp1[0] == comp2[0]) {
					return true;
				}
			}
		} else if (comp1[2] == comp2[2]) {
			if (comp1[0] > comp2[0] && comp1[1] >= comp2[1]){
				return true;
			} else {
				if (comp1[1] > comp2[1] && comp1[0] == comp2[0]) {
					return true;
				}
			}
		} 
		
		return false;
	}
	
	private double[] findMaxPoints(String[][] coOrds, double[] max, int currentLevel){
		//Position , Points 
		if (coOrds.length == 0) {
			double[] ret = {0,0};
			return ret;
		}
		if (coOrds.length == 1) {
			int[] coOrd = splitStringToInt(coOrds[0][1]);
			double points = pointsArray[currentLevel][coOrd[2]-1][coOrd[1]-1][coOrd[0]-1];
			if (points > max[1]) {
				max[0] = 0;
				max[1] = points;
			}
			return max;
		} else {
			int[] coOrd = splitStringToInt(coOrds[coOrds.length-1][1]);
			if (coOrd[2]-1 >= 0 && coOrd[1]-1 >= 0 && coOrd[0]-1 >= 0){
				double coOrdPoints = pointsArray[currentLevel][coOrd[2]-1][coOrd[1]-1][coOrd[0]-1];
				if (coOrdPoints > max[1]) {
					max[0] = coOrds.length-1;
					max[1] = coOrdPoints;
				}
			}
			String[][] newCoOrds = new String[coOrds.length-1][2];
			System.arraycopy(coOrds, 0, newCoOrds, 0, coOrds.length-1);
			return findMaxPoints(newCoOrds, max, currentLevel);
		}	
	}
	
	private int[] splitStringToInt(String coOrds) {
		String[] stringCoOrd = coOrds.split("x");
		int[] intCoOrd = new int[3];
		for (int i = 0; i < 3; i++ ) {
			intCoOrd[i] = Integer.parseInt(stringCoOrd[i]);
		}
		return intCoOrd;
	}
	
	private void placeSmallerArrayInArray(char[][][] bigArray, char[][][] smallArray, int[] startCoords) {
		int height = smallArray.length;
		int length = smallArray[0].length;
		int width = smallArray[0][0].length;

		for (int z = startCoords[2]; z < startCoords[2]+height; z++)
			for (int y = startCoords[1]; y < startCoords[1]+length; y++) {
				System.arraycopy(smallArray[z-startCoords[2]][y-startCoords[1]], 0, bigArray[z][y], startCoords[0], width);
			}
	}
	
	private double fillBox(char[][][] box, int currentLevel, Pentomino currentPent ) {
		String[][] newDimensions = findDimensions(box);		
		if (newDimensions.length > 0) {
			double[] max = {0,0};
			double[] maxPoints = findMaxPoints(newDimensions,max,currentLevel);
			if (max[1] == 0.0) {
				return currentPent.getValue();
			} else {
				int[] intDimension = splitStringToInt(newDimensions[(int) maxPoints[0]][1]);
				char[][][] maxSmallBox = filledArray[currentLevel][intDimension[2]-1][intDimension[1]-1][intDimension[0]-1].getFilled();
				int[] intStartCoords = splitStringToInt(newDimensions[(int) maxPoints[0]][0]);
				placeSmallerArrayInArray(box,maxSmallBox,intStartCoords);
				return maxPoints[1] + fillBox(box,currentLevel, currentPent);
			}			
		}else {
			return currentPent.getValue();
		}
	}
	
	private void printMatrix(char[][][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				for (int k =  0; k < matrix[0][0].length; k++) {
					System.out.print(matrix[i][j][k]);
				}
				System.out.println();
			}
			System.out.println();
		}			
	}


}
