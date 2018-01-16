import javafx.scene.Group;
import javafx.scene.shape.Box;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.*;




public class CargoStacker {

private Group pent;
private Group cargoSpace;
private int length;
private int height;
private int width;
private int[][][] array;
private PentominoTester testerPent;
private BoxTester testerBox;
private boolean pentMode;

public CargoStacker (Group space, int[][][] input, boolean pento) {
  array = input;
  cargoSpace = space;
  testerPent = new PentominoTester(input);
  testerBox = new BoxTester(input);
  pentMode = pento;
}

public Group stack () {
  pent = new Group();
  for(int i=0; i<array.length; i++) {
    for(int j=0; j<array[0].length; j++) {
      for(int k=0; k<array[0][0].length; k++) {
        if(array[i][j][k] != 0) {
          if(pentMode == true) {
          array = testerPent.search(i,j,k,array[i][j][k]);
          cargoSpace.getChildren().add(testerPent.getPent());
        } else {
          array = testerBox.search(i,j,k,array[i][j][k]);
          cargoSpace.getChildren().add(testerBox.getBox());
          }
        }
      }
    }
  }
  return cargoSpace;
}
}
