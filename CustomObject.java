import javafx.scene.shape.Box;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

class CustomObject {
    private Box box;
    private double width;
    private double height;
    private double depth;

    CustomObject(Box shape){
        width = shape.getWidth();
        height = shape.getHeight();
        depth = shape.getHeight();
        box = new Box(width, height, depth);
        box.setMaterial(new PhongMaterial(new Color(1,0,0, 0)));
        UpdatePlayerBox(shape,0);
    }

    void UpdatePlayerBox(Box shape, double angle){

        Utils.setNewPosition(box, shape.getTranslateX(), shape.getTranslateY(), shape.getTranslateZ());
        shape.setRotationAxis(new Point3D(0,0,1));
        shape.setRotate(angle);
    }

    Box getBox() {
        return box;
    }

}