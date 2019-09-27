import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.text.Text;
import javafx.scene.SubScene;

public class Controller extends Application {

  private final int WIDTH = 1920;
  private final int HEIGHT = 1080;
  private double angle = 0;
  private Box platform;
  private Enemy enemyOne;
  private Input input;
  private Player player;
  private Bullets bulletobj;
  private double distance = 2.5;
  private Text text = new Text();
  private Group stage = new Group();
  private CustomObject customPlayer;
  private CreateWorld world = new CreateWorld();
  private AnchorPane globalRoot = new AnchorPane();
  private ArrayList<Box> walls = new ArrayList<>(); // A list of all the walls in the game
  private ArrayList<Sphere> bullets = new ArrayList<>();
  private Camera camera = new PerspectiveCamera(true);
  private ArrayList<Sphere> MovingBullets = new ArrayList<>();
  private ArrayList<Sphere> EnemyBullets = new ArrayList<>();
  private HashMap<String, Boolean> currentlyActiveKeys = new HashMap<>();
  private SubScene scene = new SubScene(stage, WIDTH, HEIGHT, false, SceneAntialiasing.BALANCED);

  @Override
  public void start(Stage GameWorld) {
    CreateCharacters();
    BuildWorld();
    BuildWalls();
    BuildScene();
    globalRoot.getChildren().add(this.scene);
    Scene scene = new Scene(globalRoot, WIDTH, HEIGHT, false);
    computeCurrentlyPressedKeys(scene);

    /* GAME LOOP */
    new AnimationTimer() {
      public void handle(long currentNanoTime) {
        for (int i = 0; i < walls.size(); i++) { /* Check Collision with all the walls  */
          TakeInput(i); /* Takes input from the keyboard and initiates a response  */
          ControlBullets(); /* If the player has shot a bullet, it moves across the stage */
          CheckEnemies(); /* Moves both the enemy and their bullets */
        }
        camera.relocate(PlayerX(), PlayerY());/* Relocate/change actual position of camera */
        customPlayer.UpdatePlayerBox(player.GetPlayer(), angle);
      }
    }.start();

    TestAll();
    InitiateText(player.getHealth());
    GameWorld.setTitle("Battle of the Spheres");
    GameWorld.setScene(scene);
    GameWorld.show();
  }

  private void CreateCharacters(){
    platform = world.CreatePlatform();
    player = new Player(10, 10, 10, 50); /* Player: width,height,depth,health*/
    customPlayer = player.GetCustomPlayer();
    enemyOne = new Enemy(60,0,0,-300);
    input = new Input();
    bulletobj = new Bullets();
  }

  private void BuildWorld() {
    stage.getChildren().add(world.addPointLight());
    stage.getChildren().add(world.addAbientLight());
    stage.getChildren().add(platform);
    stage.getChildren().add(player.GetPlayer());
    stage.getChildren().add(enemyOne.GetEnemy());
    stage.getChildren().add(customPlayer.getBox());
    camera = world.InitialiseCamera();
  }

  private void BuildWalls() {

    walls = world.CreateWalls();
    for(int i = 0; i<walls.size(); i++){
      stage.getChildren().add(walls.get(i));
    }

  }

  private void BuildScene() {
    scene.setFill(Color.SLATEGRAY);
    scene.setCamera(camera);
  }

  private void computeCurrentlyPressedKeys(Scene scene){ // Stores the keys that the user is currently pressing inside a hashmap, and removes those that they release
    scene.setOnKeyPressed(event -> {
      String codeString = event.getCode().toString(); // Place the key pressed in a string
      if (!currentlyActiveKeys.containsKey(codeString)) { // If the key you are pressing is not inside the hashmap
        currentlyActiveKeys.put(codeString, true);  // Place the key you are pressing inside the hashmap
      }
    });
    scene.setOnKeyReleased(event ->
            currentlyActiveKeys.remove(event.getCode().toString())  // Remove the key you released from the hashmap
    );
  }

  private void TakeInput(int i){
    angle = input.CalculateAngle(angle,currentlyActiveKeys, player.GetPlayer(), camera);
    input.handleInput(player.GetPlayer(), customPlayer, walls.get(i), angle,distance, bullets, currentlyActiveKeys);
  }

  private void ControlBullets(){
    if(!bullets.isEmpty()){ /* The player has pressed 'SPACE' to shoot */
      MovingBullets = bulletobj.CheckBullets(PlayerX(), PlayerY(),stage, MovingBullets, bullets.get(0));
      bullets = new ArrayList<>();
    }
    MovingBullets = bulletobj.MoveBullets(MovingBullets, angle, stage);
  }

  private void CheckEnemies(){
    enemyOne.MoveEnemy();
    EnemyBullets = enemyOne.EnemyAttack(player.GetPlayer(), EnemyBullets, stage);
    if(enemyOne.MoveEnemyBullets(player.GetPlayer(), EnemyBullets, customPlayer)){
      player.setHealth();
      UpdateText(player.getHealth());
      if(PlayerDefeat()){
        System.exit(0);
      }
    }
    EnemyBullets = enemyOne.CheckDistance(stage, EnemyBullets);
  }

  private boolean PlayerDefeat(){
    if(player.getHealth() <= 0){
      return true;
    }
    return false;
  }

  private void InitiateText(int Health){ /* This code is adapted from code found on StackOverflow  */
    text.setText("Health: " + Health);
    text.setStyle("-fx-font-size: 20;");
    text.setCache(true);
    text.setFill(Color.YELLOW);
    text.setTranslateY(50);
    globalRoot.getChildren().add(text);
  }

  private void UpdateText(int Health){
    globalRoot.getChildren().remove(text);
    InitiateText(Health);
  }

  private double PlayerX(){
    return player.GetPlayer().getTranslateX();
  }

  private double PlayerY(){
    return player.GetPlayer().getTranslateY();
  }

  public static void main(String[] args) {
    Application.launch(args);
  }


     /* INITIATES TESTS WITHIN CLASSES */
  private void TestAll(){
    input.run();
    bulletobj.run();
    world.run();
    enemyOne.run();
    TestPlayer();
  }

  private void TestPlayer(){
    Player player = new Player(10,10,10,100);

    assert(player.getHealth() == 100);
    assert(player.getHealth() != 0);
    assert(player.GetPlayer().getHeight() == 10);
    assert(player.GetPlayer().getWidth() == 10);
    player.setHealth();
    assert(player.getHealth() != 100);
    assert(player.getHealth() == 99);
    player.setHealth();
    assert(player.getHealth() != 99);
    assert(player.getHealth() == 98);
  }

}