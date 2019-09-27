import javafx.scene.shape.Box;

public class Player {
    private CustomObject customPlayer;
    private int PlayerHealth;
    private Box Player;
    private int Width, Height, Depth;

    Player(int PlayerWidth, int PlayerHeight, int PlayerDepth, int PlayerHealth){
        Width = PlayerWidth;
        Height = PlayerHeight;
        Depth = PlayerDepth;
        this.PlayerHealth = PlayerHealth;
        CreatePlayer();
    }

    void CreatePlayer(){
        Player = new Box(Width, Height, Depth);
        Player.setTranslateZ(92.0 - 10.0/2.0);
        Player.setTranslateX(-3000); // Start position at the edge of the World
    }

    CustomObject GetCustomPlayer(){
        customPlayer = new CustomObject(Player);
        return customPlayer;
    }

    Box GetPlayer(){
        return Player;
    }

    void setHealth(){
       PlayerHealth -= 1;
   }

    int getHealth(){
       return PlayerHealth;
   }
}


