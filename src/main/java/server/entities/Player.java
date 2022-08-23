package server.entities;

public class Player {
    public Integer id = 0;
    public String spriteName = "res/sprites/entities/player/player1.png";
    public Integer x = 32;
    public Integer y = 32;
    public String player_name = "";
    public Player(Integer id){
        this.id = id;
    }
}
