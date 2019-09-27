import javafx.scene.Camera;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import java.util.Iterator;

class Utils {

    static void setNewPosition(Box box, double x, double y, double z){
        box.setTranslateX(x);
        box.setTranslateY(y);
        box.setTranslateZ(z);
    }

    static void RotateCamera(Shape3D sphere, double angle, Camera camera){
        cleanTransforms(camera); // Gives the camera a new position each time, rather than continually adding to the stack of numbers
        Rotate rotation = new Rotate( angle,
                sphere.getLayoutX(), sphere.getLayoutY(), sphere.getTranslateZ(),
                Rotate.Y_AXIS );

        camera.getTransforms().addAll( rotation );
    }

    static void cleanTransforms(Camera camera){
        for(Iterator<Transform> it = camera.getTransforms().iterator(); it.hasNext(); ) {
            Transform transform = it.next();
            if(transform.getClass() == Rotate.class){
                Rotate r = (Rotate)transform ;
                if(r.getAxis().getY() > 0){ // Remove all the 'Y' transforms from the camera each time you rotate it (i.e. set a fresh position each time and don't stack numbers)
                    it.remove();
                }
            }
        }
    }

    static boolean isColiding(CustomObject o1, Shape3D o2){ // Checks collision between two objects
        return o1.getBox().intersects(o1.getBox().sceneToLocal(o2.localToScene(o2.getBoundsInLocal())));
    }

}