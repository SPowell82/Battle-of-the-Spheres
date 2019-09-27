import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;
import java.util.ArrayList;

public class Enemy{
    private boolean Reached = false;
    private int FireRate = 0;
    private Sphere enemy;

    Enemy(int EnemySize, int XPos, int YPos, int ZPos){
        CreateEnemy(EnemySize, XPos, YPos,ZPos);
    }

    private Sphere CreateEnemy(int size, int XPos, int YPos, int ZPos){
        enemy = new Sphere(size);
        enemy.getTransforms().add(new Translate(XPos,YPos,ZPos));
        PhongMaterial material = new PhongMaterial(Color.DARKRED);
        enemy.setMaterial(material);

        return enemy;
    }

    Sphere GetEnemy(){
        return enemy;
    }

    boolean MoveEnemy(){

        double x = enemy.getTranslateY();
        if(enemy.getTranslateY() > -4200 && !Reached){
            enemy.setTranslateY(x - 1);
        }
        else if(enemy.getTranslateY() >= -4200){
            Reached = true;
            enemy.setTranslateY(x + 1);
            if(enemy.getTranslateY() >= 3900.0){
                Reached = false;
            }
        }

        return false;
    }

    ArrayList<Sphere> EnemyAttack(Box player, ArrayList<Sphere> EnemyBullets, Group stage){
        double PlayerX = player.getTranslateX();
        double EnemyX = enemy.getTranslateX();
        double EnemyY = enemy.getTranslateY();

        FireRate += 1;
        if(PlayerX >= -2200 && PlayerX <= 3300){
            if(FireRate > 600 | FireRate == 0) {
                Sphere sphere = new Sphere(50);
                sphere.setMaterial(new PhongMaterial(Color.SILVER));
                sphere.setTranslateX(EnemyX);
                sphere.setTranslateY(EnemyY);
                sphere.setTranslateZ(-300);
                stage.getChildren().add(sphere);
                EnemyBullets.add(sphere);
                FireRate = 0;
                return EnemyBullets;
            }
        }

        return EnemyBullets;
    }

    boolean MoveEnemyBullets(Box player, ArrayList<Sphere> EnemyBullets,CustomObject customPlayer){
        if(EnemyBullets.isEmpty()){
            return false;
        }

        double angle = Math.atan2(player.getTranslateX() - enemy.getTranslateX(),
                player.getTranslateY()- enemy.getTranslateY());

        for(int i = 0; i<EnemyBullets.size(); i++){
            EnemyBullets.get(i).setTranslateX(EnemyBullets.get(i).getTranslateX() - 15 * Math.cos((angle+Math.toRadians(90))));
            EnemyBullets.get(i).setTranslateY(EnemyBullets.get(i).getTranslateY() + 15 * Math.sin((angle+Math.toRadians(90))));
            EnemyBullets.get(i).setTranslateZ(EnemyBullets.get(i).getTranslateZ() + 1);

            if(Utils.isColiding(customPlayer, EnemyBullets.get(i))){
                return true;
            }
        }

        return false;
    }

    ArrayList CheckDistance(Group stage, ArrayList<Sphere> EnemyBullets){


        for(int i = 0; i<EnemyBullets.size(); i++){
            if(EnemyBullets.get(i).getTranslateX() > 10000 || EnemyBullets.get(i).getTranslateY() > 10000){
                stage.getChildren().remove(EnemyBullets.get(i));
                EnemyBullets.remove(i);
            }else if(EnemyBullets.get(i).getTranslateX() < -10000 || EnemyBullets.get(i).getTranslateY() < -10000){
                stage.getChildren().remove(EnemyBullets.get(i));
                EnemyBullets.remove(i);
            }
        }

        return EnemyBullets;
    }

    void run(){
        TestEnemy();
    }

    void TestEnemy(){
        Box player = new Box();
        Sphere sphere = new Sphere();
        ArrayList<Sphere> List = new ArrayList<>();
        CustomObject customplayer = new CustomObject(player);
        double x = sphere.getTranslateX();
        Group stage = new Group();

        List.add(sphere);
        MoveEnemyBullets(player,List,customplayer);
        assert(List.get(0).getTranslateX() != x);
        assert(List.get(0).getTranslateY() > 0);
        assert(MoveEnemyBullets(player,List,customplayer) == false);
        assert(List.size() == 1);
        List = CheckDistance(stage,List);
        assert(List.size() == 1);
        assert(!List.isEmpty());
    }
}
