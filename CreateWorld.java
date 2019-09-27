import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import java.util.ArrayList;

// This class creates the Game World
class CreateWorld {
private Camera camera = new PerspectiveCamera(true);
private ArrayList<Box> walls = new ArrayList<>(); // A list of all the walls in the game

    ArrayList<Box> CreateWalls(){
        Box tempWall = WallOne();
        Box WallTwo = WallTwo();
        Box WallThree = WallThree();
        Box WallFour = WallFour();
        Box WallFive = WallFive();
        Box WallSix = WallSix();
        Box WallSeven = WallSeven();

        walls.add(tempWall); /* Add each Wall to a List So that collisions can be checked for every one */
        walls.add(WallTwo);
        walls.add(WallThree);
        walls.add(WallFour);
        walls.add(WallFive);
        walls.add(WallSix);
        walls.add((WallSeven));

        return walls;
    }

    AmbientLight addAbientLight(){
        return new AmbientLight(Color.GRAY);
    }

    PointLight addPointLight(){
        PointLight FirstLight = new PointLight((Color.WHITE));
        FirstLight.setTranslateZ((-9000));
        return FirstLight;
    }

    Camera InitialiseCamera(){
        camera.getTransforms().addAll(new Translate(0, 0, -30), new Rotate(90, Rotate.X_AXIS));
        camera.setFarClip(5500.0); // Cannot see objects further than this
        camera.setNearClip(0); // Cannot see objects closer than this
        camera.relocate(camera.getLayoutX(), camera.getLayoutY() + 200); // ???

        return camera;
    }

    Box CreatePlatform(){
        PhongMaterial material = new PhongMaterial(Color.SLATEGRAY);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("./Rock.png")));
        Box box = new Box(20000, 7000, 0);
        box.setMaterial(material);
        return box;
    }

    private Box WallOne(){
        Box tempWall = new Box(60, 7800, 200); //Width,height,depth
        PhongMaterial material = new PhongMaterial(Color.SLATEGRAY);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("./Rock.png")));
        tempWall.setMaterial(material);
        tempWall.getTransforms().add(new Translate(9300, 0, -92));
        return  tempWall;
    }

    private Box WallTwo(){
        Box tempWall = new Box(20000, 200, 200);
        PhongMaterial material = new PhongMaterial(Color.SLATEGRAY);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("./Rock.png")));
        tempWall.setMaterial(material);
        tempWall.getTransforms().add(new Translate(20, 3500, -92));
        return  tempWall;
    }

    private Box WallThree(){
        Box tempWall = new Box(20000, 1000, 200);
        PhongMaterial material = new PhongMaterial(Color.SLATEGRAY);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("./Rock.png")));
        tempWall.setMaterial(material);
        tempWall.getTransforms().add(new Translate(20, -4000, -92));
        return  tempWall;
    }

    private Box WallFour(){
        Box tempWall = new Box(60, 7800, 200);
        PhongMaterial material = new PhongMaterial(Color.SLATEGRAY);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("./Rock.png")));
        tempWall.setMaterial(material);
        tempWall.getTransforms().add(new Translate(-8990, 0, -92));
        return  tempWall;
    }

    private Box WallFive(){
        Box tempWall = new Box(60, 2700, 200);
        PhongMaterial material = new PhongMaterial(Color.SLATEGRAY);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("./Rock.png")));
        tempWall.setMaterial(material);
        tempWall.getTransforms().add(new Translate(-2000, 2000, -92));
        return  tempWall;
    }

    private Box WallSix(){
        Box WallSix = new Box(60, 2700, 200);
        PhongMaterial material = new PhongMaterial(Color.SLATEGRAY);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("./Rock.png")));
        WallSix.setMaterial(material);
        WallSix.getTransforms().add(new Translate(-1990, -2000, -92));
        return  WallSix;
    }

    private Box WallSeven(){
        Box tempWall = new Box(60, 2700, 200);
        PhongMaterial material = new PhongMaterial(Color.SLATEGRAY);
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("./Rock.png")));
        tempWall.setMaterial(material);
        tempWall.getTransforms().add(new Translate(4000, 0, -92));
        return  tempWall;
    }

    void run(){
        TestCreateWorld();
    }

    private void TestCreateWorld(){
        CreateWorld world = new CreateWorld();
        ArrayList<Box> TestList = world.CreateWalls();

        assert(!TestList.isEmpty());
        assert(TestList.get(1).getHeight() == 200);
        assert(TestList.get(1).getWidth() == 20000);
        assert(TestList.size() == 7);
        assert(TestList.get(6).getWidth() == 60);
        assert(TestList.get(6).getHeight() == 2700);
        assert(TestList.get(3).getWidth() != 0);
    }
}