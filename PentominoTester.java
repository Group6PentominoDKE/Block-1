import javafx.scene.shape.Box;
import javafx.scene.Group;
import javafx.scene.paint.*;
/**
This Class builds the pentomino shaped parcels one by one and attaches them to the Object
*/
public class PentominoTester {

  private static int RES = 50;
  private int[][][] array;
  private int[] coordList = new int[15];
  private int pentCubeCounter = 0;
  private Group pentomino = new Group();
    /**
    Constructs the Object for a specific array
    */
    public PentominoTester (int[][][] inArray) {
      array = inArray;
    }
    /**
    Finds, records then clears all array cells of a single parcel
    @param i searched cell's coordinate in the array's first dimension
    @param j searched cell's coordinate in the array's second dimension
    @param k searched cell's coordinate in the array's third dimension
    @param ID number of the parcel
    @return gives back the array with the parcels array cells cleared
    */
    public int[][][] search (int i, int j, int k, int ID) {
      coordList = new int[15];
      pentCubeCounter = 0;
      for(int a=-3; a<4 && i+a<array.length; a++) {
        if(a+i>=0) {
          for(int b=-3; b<4 && j+b<array[0].length; b++) {
            if(b+j>=0) {
              for(int c=-3; c<4 && k+c<array[0][0].length; c++) {
                if(c+k>=0) {
                  if(array[a+i][b+j][c+k] == ID) {
                    coordList[pentCubeCounter*3] = a;
                    coordList[pentCubeCounter*3+1] = b;
                    coordList[pentCubeCounter*3+2] = c;
                    array[a+i][b+j][c+k] = 0;
                    pentCubeCounter++;
                  }
                }
              }
            }
          }
        }
      }
      build(i, j, k);
      return array;
    }
    /**
    Builds and attaches a 3D shape of a random color of the searched parcel to this Object,
    then moves it into its proper place in the cargo space
    @param i searched cell's coordinate in the array's first dimension
    @param j searched cell's coordinate in the array's second dimension
    @param k searched cell's coordinate in the array's third dimension
    */
    public void build(int i, int j, int k) {
      pentomino = new Group();
      Box[] listBoxes = new Box[5];
      PhongMaterial material = new PhongMaterial(new Color(Math.random(), Math.random(), Math.random(),1.0));
      for(int t =0; t<=4; t++) {
        listBoxes[t] = new Box(RES,RES,RES);
        listBoxes[t].setMaterial(material);
        listBoxes[t].setTranslateX(coordList[t*3]*RES);
        listBoxes[t].setTranslateY(coordList[t*3+1]*RES);
        listBoxes[t].setTranslateZ(coordList[t*3+2]*RES);
        pentomino.getChildren().add(listBoxes[t]);
      }
      pentomino.setTranslateX(i*RES);
      pentomino.setTranslateY(j*RES);
      pentomino.setTranslateZ(k*RES);
    }
    /**
    Returns the 3D parcel currently attached to this Object
    @return the pentomino parcel
    */
    public Group getPent() {
      return pentomino;
    }
}
