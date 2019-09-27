import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.HashMap;

class Input{
    private int fireRate = 0;

     double CalculateAngle(double angle, HashMap Keys, Box box, Camera camera){
         TesthandleInput();
        if (Keys.containsKey(KeyCode.ESCAPE.toString())) {
            System.exit(0);
        }

        if (Keys.containsKey(KeyCode.A.toString())) {
            angle -= 0.09;
        }

        if (Keys.containsKey(KeyCode.D.toString())) {
            angle += 0.09;
        }

        Utils.RotateCamera(box, angle, camera);
        return angle;
    }

     void handleInput(Box box, CustomObject player, Box wall, double angle, double distance, ArrayList bullets, HashMap Keys) {

        double y_c = box.getTranslateY();
        double x_c = box.getTranslateX();

        int sign = 1;
        if(Utils.isColiding(player, wall)){
            sign = -100;
        }

        if (Keys.containsKey(KeyCode.UP.toString())) {
            double x = x_c + Math.cos(Math.toRadians(angle - 90)) * distance*sign;
            double y = y_c + Math.sin(Math.toRadians(angle - 90)) * distance*sign;
            MoveBox(box,x,y);
        }

        if (Keys.containsKey(KeyCode.DOWN.toString())) {
            double x = x_c + Math.cos(Math.toRadians(angle - 90)) * -distance*sign;
            double y = y_c + Math.sin(Math.toRadians(angle - 90)) * -distance*sign;
            MoveBox(box,x,y);
        }

        if (Keys.containsKey(KeyCode.LEFT.toString())) {
            double x = x_c + Math.cos(Math.toRadians(angle - 180)) * distance*sign;
            double y = y_c + Math.sin(Math.toRadians(angle - 180)) * distance*sign;
            MoveBox(box,x,y);
        }

        if (Keys.containsKey(KeyCode.RIGHT.toString())) {
            double x = x_c + Math.cos(Math.toRadians(angle - 0)) * distance*sign;
            double y = y_c + Math.sin(Math.toRadians(angle - 0)) * distance*sign;
            MoveBox(box,x,y);
        }

        fireRate += 1;
        if (Keys.containsKey(KeyCode.SPACE.toString())) {
            if (fireRate > 550 | fireRate == 0) {
                Sphere sphere = new Sphere(20);
                bullets.add(sphere);
                fireRate = 0;
            }
        }
    }

    private void MoveBox(Box box, double xPosition, double yPosition){
        box.setTranslateX(xPosition);
        box.setTranslateY(yPosition);
    }

    void run(){
         TesthandleInput();
         TestCalculateAngle();
    }

    private void TestCalculateAngle(){
         HashMap<String, Boolean> Keys = new HashMap<>();
         Camera camera = new PerspectiveCamera(true);
         Box TestBox = new Box();

         Keys.put("A", true);
         assert((CalculateAngle(10, Keys, TestBox, camera)) == 9.91);
         assert((CalculateAngle(10, Keys, TestBox, camera)) != 10.0);
         assert((CalculateAngle(100, Keys, TestBox, camera)) == 99.91);
         assert((CalculateAngle(0, Keys, TestBox, camera)) != 0.09);
         assert((CalculateAngle(13, Keys, TestBox, camera)) != 9.91);
         assert((CalculateAngle(1, Keys, TestBox, camera)) == 0.91);

         Keys.remove("A");
         Keys.put("D", true);
         assert((CalculateAngle(20,Keys,TestBox,camera)) == 20.09);
         assert((CalculateAngle(10, Keys, TestBox, camera)) == 10.09);
         assert((CalculateAngle(10, Keys, TestBox, camera)) != 10.0);
         assert((CalculateAngle(100, Keys, TestBox, camera)) == 100.09);
         assert((CalculateAngle(0, Keys, TestBox, camera)) != 1.09);
         assert((CalculateAngle(13, Keys, TestBox, camera)) != 12.91);
         assert((CalculateAngle(1, Keys, TestBox, camera)) == 1.09);
    }

    private void TesthandleInput(){
        HashMap<String, Boolean> Keys = new HashMap<>();
        Box TestBox = new Box();
        Box TestWall = new Box(10,10,10);
        ArrayList<Sphere>  List = new ArrayList<>();
        CustomObject TestObj = new CustomObject(TestBox);
        TestBox.setTranslateZ(-300);
        TestBox.setTranslateY(30);
        TestBox.setTranslateX(20);
        double test = TestBox.getTranslateX();
        double test2 = TestBox.getTranslateY();

        Keys.put(KeyCode.DOWN.toString(),true);
        handleInput(TestBox,TestObj,TestWall, 10,12,List,Keys);

        assert(TestBox.getTranslateX()) != test;
        assert(TestBox.getTranslateY() != test2);
    }
}
