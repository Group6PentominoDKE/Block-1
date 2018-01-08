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
import javafx.scene.shape.DrawMode;

public class Truck extends Application {

    private static final int WINDOW_LENGTH = 1500;
    private static final int WINDOW_HEIGHT = 700;
    private static final int CameraViewDistance = 700;
    private static final int TruckLength = 1650;
    private static final int TruckWidth = 250;
    private static final int TruckHeight = 400;
    private static final int RotateAngle = 5;

    public void start(Stage stage) {
        Box truck = new Box(TruckLength, TruckWidth, TruckHeight);
        Group root = new Group(truck);
        Scene scene = new Scene(root, WINDOW_LENGTH, WINDOW_HEIGHT, true);
        PhongMaterial material = new PhongMaterial(Color.BLACK);
        truck.setDrawMode(DrawMode.LINE);
        truck.setMaterial(material);
        truck.setTranslateX(WINDOW_LENGTH/2);
        truck.setTranslateY(WINDOW_HEIGHT/2);
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
