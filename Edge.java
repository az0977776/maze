import java.awt.Color;

import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

// representation of edges that connect the Cells
public class Edge {
  
  // the cells that the Edge connects
  Cell c1;
  Cell c2;
  
  // default cell size
  int cellSize;
  
  // weight of this Edge
  double weight;
  
  Edge(Cell c1, Cell c2, double weight, int cellSize) {
    this.c1 = c1;
    this.c2 = c2;
    this.weight = weight;
    this.cellSize = cellSize;
  }
  
  //display that creates a World Image of this Edge
  WorldImage display() {
    if (c1.x - c2.x == 0) {
      return new RectangleImage(cellSize + 3, 3, OutlineMode.SOLID,
          Color.GRAY);
    }
    else {
      return new RectangleImage(3, cellSize + 3, OutlineMode.SOLID,
          Color.GRAY);
    }
  }
}
