import java.awt.Color;

import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

// representation of a single cell in the maze
public class Cell {

  // location of the cell
  int x;
  int y;
  
  // default cell size
  int cellSize;
  
  // connection to the neighbors
  Cell top;
  Cell right;
  Cell bottom;
  Cell left;
  
  // has this cell been visited
  boolean visited;
  
  // is this cell on the true path
  boolean truePath;
  
  Cell(int x, int y, int cellSize, boolean visited, boolean truePath) {
    this.x = x;
    this.y = y;
    this.cellSize = cellSize;
    this.visited = visited;
    this.truePath = truePath;
  }
  
  Cell(int x, int y, int cellSize) {
    this.x = x;
    this.y = y;
    this.cellSize = cellSize;
  }
  
  // display that creates a World Image of this cell
  WorldImage display(boolean showVisited) {
    if (this.truePath) {
      return new RectangleImage(cellSize, cellSize, OutlineMode.SOLID,
          new Color(220, 220, 60));
    }
    if (this.visited && showVisited) {
      return new RectangleImage(cellSize, cellSize, OutlineMode.SOLID,
          new Color(170, 170, 240));
    }
    return new RectangleImage(cellSize, cellSize, OutlineMode.SOLID,
        Color.LIGHT_GRAY);
  }
  
  // generates a string key for a hashmap
  // format: X coordinate, followed by a space, followed by the y coord
  String generateName() {
    return this.x + " " + this.y;
  }
}
