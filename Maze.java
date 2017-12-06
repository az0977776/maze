// ASSIGNMENT 10
// wang, aaron
// awang9
// novak, luke
// nluken

import java.util.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;


// representation of the maze
public class Maze extends World {
  
  // dimensions of the maze
  int width;
  int height;
  // holds all the cells in the maze
  ArrayList<ArrayList<Cell>> cells;
  // holds all the edges "walls"
  ArrayList<Edge> edges;
  // holds all the "non walls"
  ArrayList<Edge> path;
  // random
  Random rand = new Random();
  // worldScene
  WorldScene defaultWorld;
  // default cell size
  int cellSize;
  // weight comparator for the kruskal algorithm
  WeightComp weightComp;
  // the array utilities
  ArrayUtils arrayUtils;
  // HashMap with all of the cells in order
  HashMap<String, String> cellHashMap;
  // initializes the util class
  ArrayUtils utils;
  // search type, "d" = depth first search, "b" = breadth first search
  String searchType;
  // determines whether to display the visited cells
  boolean showVisited;


  // starting cell
  Cell start;
  // target cell
  Cell target;
  // contains mapping from cell to its edges
  HashMap<Cell, Edge> cameFromEdge;
  // the worklist
  ArrayList<Cell> worklist;
  // cells that have been processed
  ArrayList<Cell> processed;
  // next cell to work on
  Cell next;
  // has the maze been completed
  boolean completed;
  // is the search being controlled by the player?
  boolean playerControl;
  // the player (used when giving human control)
  Player player;
  
  Maze(int width, int height, String s) {
    this.width = width;
    this.height = height;
    cellSize = 640 / Math.min(width, height);
    
    cells = new ArrayList<ArrayList<Cell>>();
    edges = new ArrayList<Edge>();
    path = new ArrayList<Edge>();
    defaultWorld = new WorldScene(width * cellSize, height * cellSize);
    weightComp = new WeightComp();
    arrayUtils = new ArrayUtils();
    cellHashMap = new HashMap<String, String>();
    playerControl = true;
    player = new Player(cellSize);
    showVisited = true;
    
    generateCells();
    setLinks();
    generateEdges();
    generateHashMap();
    kruskalImplements();
    
    searchType = s;
    start = cells.get(0).get(0);
    target = cells.get(width - 1).get(height - 1);
    cameFromEdge = new HashMap<Cell,Edge>();
    worklist = new ArrayList<Cell>();
    processed = new ArrayList<Cell>();
    completed = false;
    worklist.add(0,start);
  }
  
  Maze(int width, int height) {
    this.width = width;
    this.height = height;
    cellSize = 640 / Math.min(width, height);
    
    cells = new ArrayList<ArrayList<Cell>>();
    edges = new ArrayList<Edge>();
    path = new ArrayList<Edge>();
    defaultWorld = new WorldScene(width * cellSize, height * cellSize);
    weightComp = new WeightComp();
    arrayUtils = new ArrayUtils();
    cellHashMap = new HashMap<String, String>();
    playerControl = true;
    player = new Player(cellSize);
    showVisited = true;
    
    generateCells();
    setLinks();
    generateEdges();
    generateHashMap();
    kruskalImplements();

    searchType = "dfs";
    start = cells.get(0).get(0);
    target = cells.get(width - 1).get(height - 1);
    cameFromEdge = new HashMap<Cell,Edge>();
    worklist = new ArrayList<Cell>();
    processed = new ArrayList<Cell>();
    worklist.add(0,start);
    completed = false;
  }
  
  void remake(String s) {  
    cells = new ArrayList<ArrayList<Cell>>();
    edges = new ArrayList<Edge>();
    path = new ArrayList<Edge>();
    
    defaultWorld = new WorldScene(width * cellSize, height * cellSize);
    cellHashMap = new HashMap<String, String>();
    player = new Player(cellSize);
    

    generateCells();
    setLinks();
    generateEdges();
    generateHashMap();
    kruskalImplements();

    if (!playerControl) {
      searchType = s;
    }
    start = cells.get(0).get(0);
    target = cells.get(width - 1).get(height - 1);
    cameFromEdge = new HashMap<Cell,Edge>();
    worklist = new ArrayList<Cell>();
    processed = new ArrayList<Cell>();
    worklist.add(0,start);
    completed = false;
    
  }
  
  // makes all of the cells unvisited without resetting the maze itself
  // also resets the worklist and the processed list and sets completed to false
  // used when player control is toggled
  void resetMaze() {
    for (ArrayList<Cell> cl: cells) {
      for (Cell c: cl) {
        c.makeUnvisited();
        c.truePath = false;
      }
    }
    worklist = new ArrayList<Cell>();
    worklist.add(0, start);
    processed = new ArrayList<Cell>();
    completed = false;
    
  }

  //generates all the Cells
  void generateCells() {
    for (int i = 0; i < width; i++) {
      cells.add(new ArrayList<Cell>());
      for (int j = 0; j < height; j++) {
        cells.get(i).add(new Cell(i,j, cellSize));
      }
    }
  }
  
  // sets the neighbors for all the Cells
  void setLinks() {
    Cell tempcell;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        //System.out.println(i + ", " + j);
        tempcell = cells.get(i).get(j);
        tempcell.left = (i == 0 
            ? null : cells.get(i - 1).get(j));
        tempcell.top = (j == 0 
            ? null : cells.get(i).get(j - 1));
        tempcell.right = (i == width - 1 
            ? null : cells.get(i + 1).get(j));
        tempcell.bottom = (j == height - 1 
            ? null : cells.get(i).get(j + 1)); 
      }
    }
  }
  
  // generates all the edges
  void generateEdges() {
    Cell temp;
    //System.out.println(cells.size() + " " + cells.get(0).size());
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        //System.out.println(i + ", " + j);
        temp = cells.get(i).get(j);
        //left to right connections
        if (i != cells.size() - 1) {
          edges.add(new Edge(temp, cells.get(i + 1).get(j), rand.nextDouble(), cellSize));         
        }
        // up to down connections
        if (j != cells.get(i).size() - 1) {
          edges.add(new Edge(temp, cells.get(i).get(j + 1), rand.nextDouble(), cellSize));         
        }
      }
    }
    for (Edge e: edges) {
      path.add(e);
    }
    //for (Edge e : edges) {
    // System.out.println("from " + e.c1.x + " " + e.c1.y + " to " + e.c2.x + " " + e.c2.y);
    //}
  }
  
  // generate cell Images
  void generateCellImages() {
    Cell c;
    WorldImage k;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        c = cells.get(i).get(j);
        if (i == 0 && j == 0) {
          k = new RectangleImage(cellSize, cellSize, OutlineMode.SOLID,
              new Color(40, 200, 50));
        }
        else if (i == width - 1 && j == height - 1) {
          k = new RectangleImage(cellSize, cellSize, OutlineMode.SOLID,
              new Color(240, 20, 70));
        }
        else {
          k = c.display(showVisited);
        }
        defaultWorld.placeImageXY(k,
            c.x * cellSize + cellSize / 2 + 5,
            c.y * cellSize + cellSize / 2 + 5);
      }
    }
  }
  
  // generate Edge Images
  void generateEdgeImages() {
    for (Edge e : edges) {
      defaultWorld.placeImageXY(e.display(),
          e.c1.x * cellSize + 5 + ((e.c1.x - e.c2.x == 0) ? cellSize / 2 : cellSize),
          e.c1.y * cellSize + 5 + ((e.c1.x - e.c2.x == 0) ? cellSize : cellSize / 2));
    }
  }
  
  // generates the image of the player at their current location
  void generatePlayerImage() {
    defaultWorld.placeImageXY(player.display(), 
        player.x * cellSize + cellSize / 2 + 5,
        player.y * cellSize + cellSize / 2 + 5);
  }
 
  // puts the cells into a hashmap
  void generateHashMap() {
    for (ArrayList<Cell> a: cells) {
      for (Cell c: a) {
        cellHashMap.put(c.generateName(), c.generateName());
      }
    }
  }
  
  // the main makeScene function
  public WorldScene makeScene() {
    defaultWorld = this.getEmptyScene();
    defaultWorld.placeImageXY(new RectangleImage(defaultWorld.width * cellSize,
        defaultWorld.height * cellSize, OutlineMode.SOLID, Color.GRAY),
        defaultWorld.width / 2 * cellSize, defaultWorld.height / 2 * cellSize);
    generateCellImages();
    generateEdgeImages();
    if (playerControl) {
      generatePlayerImage();
    }

    return defaultWorld;
  }
  
  // gets the current cell that the player is on
  Cell getCurrentCell() {
    return cells.get(player.x).get(player.y);
  }
  
  // on key handler
  public void onKeyEvent(String ke) {
    if (ke.equals("r")) {
      searchType = "bfs";
      playerControl = false;
      resetMaze();
      this.player = new Player(cellSize);
    }
    if (ke.equals("e")) {
      searchType = "dfs";
      playerControl = false;
      resetMaze();
      this.player = new Player(cellSize);
    }
    if (ke.equals("g")) {
      this.playerControl = true;
      remake(searchType.substring(0, 1));
    }
    if (ke.equals("p")) {
      this.playerControl = true;
      resetMaze();
      completed = false;
      this.player = new Player(cellSize); 
    }
    
    if (ke.equals("t")) {
      this.showVisited = !showVisited;
    }
    
    // player controls
    if (playerControl && !completed) {
      Cell currentCell = this.getCurrentCell();
      if (ke.equals("w") || ke.equals("up")) {
        if (currentCell.top != null 
            && path.contains(this.getEdge(currentCell, currentCell.top))) {
          if (!currentCell.top.visited) {
            cameFromEdge.put(currentCell.top, getEdge(currentCell, currentCell.top));
          }
          player.y = player.y - 1;
          currentCell.visited = true;
        }
      }
      
      if (ke.equals("a") || ke.equals("left")) {
        if (currentCell.left != null 
            && path.contains(this.getEdge(currentCell, currentCell.left))) {
          if (!currentCell.left.visited) {
            cameFromEdge.put(currentCell.left, getEdge(currentCell, currentCell.left));
          }
          player.x = player.x - 1;
          currentCell.visited = true;
        }
      }
      
      if (ke.equals("s") || ke.equals("down")) {
        if (currentCell.bottom != null 
            && path.contains(this.getEdge(currentCell, currentCell.bottom))) {
          if (!currentCell.bottom.visited) {
            cameFromEdge.put(currentCell.bottom, getEdge(currentCell, currentCell.bottom));
          }
          player.y = player.y + 1;
          currentCell.visited = true;
        }
        
      }
      
      if (ke.equals("d") || ke.equals("right")) {
        if (currentCell.right != null 
            && path.contains(this.getEdge(currentCell, currentCell.right))) {
          if (!currentCell.right.visited) {
            cameFromEdge.put(currentCell.right, getEdge(currentCell, currentCell.right));
          }
          player.x = player.x + 1;
          currentCell.visited = true;
        }
      }
    }
  }
  
  // on tick 
  public void onTick() {
    if (!playerControl) {
      if (searchType.equals("dfs")) {
        dfs();
      }
      else {
        bfs();
      }
    }
    if (player.x == target.x && player.y == target.y && !completed) {
      completed = true;
      this.reconstruct(cameFromEdge, getCurrentCell(), start);
    }
  }

  // the kruskal algorithm
  void kruskalImplements() {
    ArrayList<Edge> edgesInTree = new ArrayList<Edge>();
    // list is a list of edges, weight comp cmpares their weights
    arrayUtils.quicksort(edges, weightComp);
    //for (Edge e : edges) {
    //  System.out.println(e.weight);
    //}
    
    // main while loop
    int counter = 0;
    while (counter < edges.size()) {
      Edge pick = edges.get(counter);
      if (!findRepresentative(pick.c1.generateName())
          .equals(findRepresentative(pick.c2.generateName()))) {
        edgesInTree.add(pick);
        union(pick.c1.generateName(), pick.c2.generateName());
      }
      counter++;
    }
    for (int i = 0;
        i < edges.size();
        i++) {
      for (Edge f : edgesInTree) {
        if (edges.get(i).equals(f)) {
          edges.remove(i);
        }
      }
    }
    for (int i = 0; i < edges.size(); i++) {
      path.remove(edges.get(i));
    }
  }
  
  // finds the representive of the given string in the cell hashmap
  String findRepresentative(String s) {
    if (cellHashMap.get(s).equals(s)) {
      return s;
    }
    else {
      return findRepresentative(cellHashMap.get(s));
    }
  }
  
  // sets the representative of the second to the first
  void union(String s1, String s2) {
    cellHashMap.put(findRepresentative(s1), findRepresentative(s2));
  }
  
  // Depth-first search
  void dfs() {
    if (worklist.size() > 0 && !completed) {
      next = worklist.remove(worklist.size() - 1);
      next.visited = true;
      if (processed.contains(next)) {
        return;
      }
      if (next == target) {
        reconstruct(cameFromEdge, next, start);
        completed = true;
        return;
      }
      else {
        if (next.top != null && path.contains(getEdge(next, next.top)) 
            && !processed.contains(next.top)) {
          worklist.add(next.top);
          cameFromEdge.put(next.top, getEdge(next, next.top));
        }
        if (next.left != null && path.contains(getEdge(next, next.left))
            && !processed.contains(next.left)) {
          worklist.add(next.left);
          cameFromEdge.put(next.left, getEdge(next, next.left));
        }
        if (next.bottom != null && path.contains(getEdge(next, next.bottom))
            && !processed.contains(next.bottom)) {
          worklist.add(next.bottom);
          cameFromEdge.put(next.bottom, getEdge(next, next.bottom));
        }
        if (next.right != null && path.contains(getEdge(next, next.right)) 
            && !processed.contains(next.right)) {
          worklist.add(next.right);
          cameFromEdge.put(next.right, getEdge(next, next.right));
        }
      }
      processed.add(next);
    }
  }
  
  // Breadth-first search
  void bfs() {
    if (worklist.size() > 0 && !completed) {
      next = worklist.remove(0);
      next.visited = true;
      if (processed.contains(next)) {
        return;
      }
      if (next == target) {
        reconstruct(cameFromEdge, next, start);
        completed = true;
        return;
      }
      else {
        if (next.top != null && path.contains(getEdge(next, next.top)) 
            && !processed.contains(next.top)) {
          worklist.add(next.top);
          cameFromEdge.put(next.top, getEdge(next, next.top));
        }
        if (next.left != null && path.contains(getEdge(next, next.left))
            && !processed.contains(next.left)) {
          worklist.add(next.left);
          cameFromEdge.put(next.left, getEdge(next, next.left));
        }
        if (next.bottom != null && path.contains(getEdge(next, next.bottom))
            && !processed.contains(next.bottom)) {
          worklist.add(next.bottom);
          cameFromEdge.put(next.bottom, getEdge(next, next.bottom));
        }
        if (next.right != null && path.contains(getEdge(next, next.right)) 
            && !processed.contains(next.right)) {
          worklist.add(next.right);
          cameFromEdge.put(next.right, getEdge(next, next.right));
        }
      }
      processed.add(next);
    }
  }
  
  // gets the edge that connects Cell start and Cell end
  // returns null if it doesnt exist
  Edge getEdge(Cell start, Cell end) {
    for (Edge e : path) {
      if ((e.c1 == start && e.c2 == end) || (e.c1 == end && e.c2 == start)) {
        return e;
      }
    }
    return null;
  }
  
  // gets the cell at the other end of the edge when given a cell and an edge
  Cell otherEnd(Cell c, Edge e) {
    if (e.c1 == c) {
      return e.c2;
    }
    else {
      return e.c1;
    }
  }
  
  // sets the truePath 
  void reconstruct(HashMap<Cell,Edge> hm, Cell c, Cell start) {
    Cell prev;
    Edge k;
    k = hm.get(c);
    prev = otherEnd(c, k);
    
    while (prev != start) {      
      prev.truePath = true;
      k = hm.get(prev);
      prev = otherEnd(prev, k);
    }
  }
}
