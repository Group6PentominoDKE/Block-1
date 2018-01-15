import javafx.scene.shape.Box;
import javafx.scene.paint.*;



public class BoxTester {

  private int[][][] array;
  private int[] coordList = new int[15];
  private int squareCounter;
  private Box box;

  public BoxTester (int[][][] inArray) {

    array = inArray;
  }

  public int[][][] search (int i, int j, int k, int id) {
    int TEMPL = 0;
    int TEMPH = 0;
    int TEMPW = 0;
    for(int a=i; a+i<array.length && array[a][j][k] == id; a++) {
      TEMPL++;
    }
    for(int b=j; b+j<array[0].length && array[i][b][k] == id; b++) {
      TEMPH++;
    }
    for(int c=k; c+k<array[0][0].length && array[i][j][c] == id; c++) {
      TEMPW++;
    }
    build(TEMPL, TEMPH, TEMPW, i, j, k);
    for(int a=i; a-i < TEMPL; a++) {
      for(int b=j; b-j < TEMPH; b++) {
        for(int c=k; c-k < TEMPW; c++) {
          array[a][b][c] = 0;
        }
      }
    }
    return array;
  }
  public void build(int x, int y, int z, int i, int j, int k) {
    PhongMaterial material = new PhongMaterial(new Color(Math.random(), Math.random(), Math.random(),1.0));
    box.setTranslateX(i*50);
    box.setTranslateY(j*50);
    box.setTranslateZ(k*50);
    box = new Box(x*50, y*50, z*50);
  }
  public Box getBox() {
    return box;
  }
}
