import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.Box;
import javafx.scene.paint.*;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Truck extends Application {

    private static final int WINDOW_LENGTH = 1500;
    private static final int WINDOW_HEIGHT = 700;
    private static final int CameraViewDistance = 700;
    private static final int RotateAngle = 5;
    private static int[][][] array;
    private static boolean pentMode;

    public static void insertArray(int[][][] input, boolean pent) {
      array = input;
      pentMode = pent;
    }
    public void start(Stage stage) {
        Group root = new Group();
        CargoStacker cargo = new CargoStacker(root, array, pentMode);
        cargo.stack();
        Scene scene = new Scene(root, WINDOW_LENGTH, WINDOW_HEIGHT, true);
        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setTranslateZ(-CameraViewDistance);
        scene.setCamera(camera);
        EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
           public void handle(KeyEvent e) {

             if (e.getCode() == KeyCode.UP) {
               camera.getTransforms().add(new Rotate(RotateAngle,WINDOW_LENGTH/2,WINDOW_HEIGHT/2,CameraViewDistance,Rotate.X_AXIS));
             }
             if (e.getCode() == KeyCode.DOWN) {
               camera.getTransforms().add(new Rotate(-RotateAngle,WINDOW_LENGTH/2,WINDOW_HEIGHT/2,CameraViewDistance,Rotate.X_AXIS));
             }
             if (e.getCode() == KeyCode.LEFT) {
               camera.getTransforms().add(new Rotate(-RotateAngle,WINDOW_LENGTH/2,WINDOW_HEIGHT/2,CameraViewDistance,Rotate.Y_AXIS));
             }
             if (e.getCode() == KeyCode.RIGHT) {
               camera.getTransforms().add(new Rotate(RotateAngle,WINDOW_LENGTH/2,WINDOW_HEIGHT/2,CameraViewDistance,Rotate.Y_AXIS));
             }
             if (e.getCode() == KeyCode.N) {
               camera.getTransforms().add(new Rotate(RotateAngle,WINDOW_LENGTH/2,WINDOW_HEIGHT/2,CameraViewDistance,Rotate.Z_AXIS));
             }
             if (e.getCode() == KeyCode.M) {
               camera.getTransforms().add(new Rotate(-RotateAngle,WINDOW_LENGTH/2,WINDOW_HEIGHT/2,CameraViewDistance,Rotate.Z_AXIS));
           }
         }
        };
        scene.addEventHandler(KeyEvent.KEY_PRESSED, handler);
        stage.setTitle("Truck");
        stage.setScene(scene);
        stage.show();

}
}
