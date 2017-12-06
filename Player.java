import java.awt.Color;

import javalib.worldimages.*;

// to represent a player in the maze game

public class Player {
  int x;
  int y;
  int size;
  
  Player(int size) {
    this.x = 0;
    this.y = 0;
    this.size = size;
  }
  
  // to display the player
  WorldImage display() {
    return new RectangleImage(this.size - 2, this.size - 2, OutlineMode.SOLID, Color.ORANGE);
  }
}