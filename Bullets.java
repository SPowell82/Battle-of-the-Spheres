import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import java.util.ArrayList;

class Bullets{

    ArrayList<Sphere> CheckBullets(double XPos, double YPos, Group stage, ArrayList<Sphere> MovingBullets, Sphere bullet){

        Sphere sphere;
        sphere = bullet;
        sphere.setMaterial(new PhongMaterial(Color.DARKTURQUOISE));
        sphere.setTranslateX(XPos);
        sphere.setTranslateY(YPos);
        sphere.setTranslateZ(-30); // Above the Ground which is X = -92
        stage.getChildren().add(sphere);
        MovingBullets.add(sphere);

        return MovingBullets;
    }

    ArrayList<Sphere> MoveBullets(ArrayList<Sphere> MovingBullets, double angle, Group stage){
        double x,y,x_coord,y_coord;
        for(int k = 0; k<MovingBullets.size(); k++){ // Move the bullet forward from its current position
            x_coord = MovingBullets.get(k).getTranslateX();
            y_coord = MovingBullets.get(k).getTranslateY();
            x = x_coord + Math.cos(Math.toRadians(angle - 90)) * 10;
            y = y_coord + Math.sin(Math.toRadians(angle - 90)) * 10;
            MovingBullets.get(k).setTranslateX(x);
            MovingBullets.get(k).setTranslateY(y);
            MovingBullets = CheckDistance(k, MovingBullets, stage);
        }

        return MovingBullets;
    }

    private ArrayList CheckDistance(int k, ArrayList<Sphere> MovingBullets, Group stage){
        if(MovingBullets.get(k).getTranslateX() > 10000 || MovingBullets.get(k).getTranslateY() > 10000){
            stage.getChildren().remove(MovingBullets.get(k));
            MovingBullets.remove(k);
        }else if(MovingBullets.get(k).getTranslateX() < -10000 || MovingBullets.get(k).getTranslateY() < -10000){
            stage.getChildren().remove(MovingBullets.get(k));
            MovingBullets.remove(k);
        }
        return MovingBullets;
    }

    void run(){
        TestCheckBullets();
        TestMoveBullets();

    }

    private void TestCheckBullets(){
        double x = 50.0, y = 100.0;
        Group stage = new Group();
        ArrayList<Sphere> MovingBullets = new ArrayList<>();
        Sphere bullet = new Sphere();

        assert(stage.getChildren().isEmpty());
        assert(MovingBullets.isEmpty());
        MovingBullets = CheckBullets(x,y,stage,MovingBullets, bullet);
        assert(!MovingBullets.isEmpty());
        assert(MovingBullets.contains(bullet));
        assert(stage.getChildren().contains(bullet));
    }

    private void TestMoveBullets(){
        double angle = 50.0;
        Sphere sphere = new Sphere();
        Group stage = new Group();
        ArrayList<Sphere> MovingBullets = new ArrayList<>();
        sphere.setTranslateZ(-300);
        sphere.setTranslateY(30);
        sphere.setTranslateX(20);
        MovingBullets.add(sphere);
        double x = sphere.getTranslateX();

        assert(MovingBullets.get(0).getTranslateX() == 20);
        MoveBullets(MovingBullets,angle,stage);
        assert(MovingBullets.get(0).getTranslateX() != x);
        assert(MovingBullets.get(0).getTranslateX() > 20);
        assert(MovingBullets.contains(sphere));
    }
}
