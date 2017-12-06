import java.awt.Color;
import java.util.*;

import javalib.impworld.World;
import javalib.worldimages.*;
import tester.*;

public class ExampleMaze {
  
  void testGen(Tester t) {
    Maze m = new Maze(5,5);
    for (Edge e : m.edges) {
      // checks that the maze generation created
      // all the edges with weights within
      // a certain range of numbers
      t.checkNumRange(e.weight, 0, 1);
    }
    t.checkExpect(m.showVisited, true);
    t.checkExpect(m.playerControl, true);
    t.checkExpect(m.searchType, "dfs");
    t.checkExpect(m.start, m.cells.get(0).get(0));
    t.checkExpect(m.target, m.cells.get(m.width - 1).get(m.height - 1));
    t.checkExpect(m.completed, false);
    t.checkExpect(m.worklist.size(), 1);
    
    // testing the remake function that regenerates the maze with 
    // the same height and width
    m.remake("dfs");
    for (Edge e : m.edges) {
      // checks that the remake function created
      // add the edges with weights within
      // a certain range of numbers
      t.checkNumRange(e.weight, 0, 1);
    }
    t.checkExpect(m.showVisited, true);
    t.checkExpect(m.playerControl, true);
    t.checkExpect(m.searchType, "dfs");
    t.checkExpect(m.start, m.cells.get(0).get(0));
    t.checkExpect(m.target, m.cells.get(m.width - 1).get(m.height - 1));
    t.checkExpect(m.completed, false);
    t.checkExpect(m.worklist.size(), 1);
  }
 
  // checks the generation of the cells
  // and makes sure that the size of the cells
  // 2d array is correct
  void testGenerateCells(Tester t) {
    Maze m = new Maze(5,5);
    t.checkExpect(m.cells.size(), m.width);
    t.checkExpect(m.cells.get(0).size(), m.height);
    
    Maze o = new Maze(10,20);
    t.checkExpect(o.cells.size(), o.width);
    t.checkExpect(o.cells.get(0).size(), o.height);
    
    Maze p = new Maze(20,10);
    t.checkExpect(p.cells.size(), p.width);
    t.checkExpect(p.cells.get(0).size(), p.height);
  }
  
  // checks the size of the edges array
  // to make sure that the number of edges is
  // correct
  void testEdges(Tester t) {
    Maze m = new Maze(15,15);
    t.checkExpect(m.edges.size(), (m.width - 1) * (m.height - 1));
    
    Maze o = new Maze(15,20);
    t.checkExpect(o.edges.size(), (o.width - 1) * (o.height - 1));
    
    Maze p = new Maze(20,15);
    t.checkExpect(p.edges.size(), (p.width - 1) * (p.height - 1));
  }
  
  
  // testing the kruskal algortithm
  void testKruskalImplements(Tester t) {
    Maze m = new Maze(3,3);
    t.checkExpect(m.edges.size(), (m.width - 1) * (m.height - 1));
    m = new Maze(13,5);
    t.checkExpect(m.edges.size(), (m.width - 1) * (m.height - 1));
    m = new Maze(33,103);
    t.checkExpect(m.edges.size(), (m.width - 1) * (m.height - 1));
  }
  
  // testing the FindRepresentatives method
  void testFindRepresentative(Tester t) {
    Maze m = new Maze(2,2);
    m.cellHashMap = new HashMap<String, String>();
    m.cellHashMap.put("A", "A");
    m.cellHashMap.put("B", "A");
    m.cellHashMap.put("C", "B");
    t.checkExpect(m.findRepresentative("C"), "A");
    t.checkExpect(m.findRepresentative("B"), "A");
    t.checkExpect(m.findRepresentative("A"), "A");
  }

  // testing the Union method
  void testUnion(Tester t) {
    Maze m = new Maze(2,2);
    m.cellHashMap = new HashMap<String, String>();
    m.cellHashMap.put("A", "A");
    m.cellHashMap.put("B", "A");
    m.cellHashMap.put("C", "B");
    m.union("B", "C");
    t.checkExpect(m.cellHashMap.get("B"), "A");
  }
  
  // tests the quicksort function and the comparator
  void testQuickSort(Tester t) {
    // declares and initializes the WeightComp class
    // that implements the IComparator
    IComparator<Edge> w = new WeightComp();
    // declares and initializes the ArrayUtils that
    // contains the quicksort function
    ArrayUtils a = new ArrayUtils();
    ArrayList<Edge> arr = new ArrayList<Edge>(Arrays
        .asList(new Edge(null,null,13.5,0),
            new Edge(null,null,66.1,0),
            new Edge(null,null,442.5,0),
            new Edge(null,null,4.2,0),
            new Edge(null,null,6.2,0)));
    a.quicksort(arr, w);
    t.checkExpect(arr.get(0).weight, 4.2);
    t.checkExpect(arr.get(1).weight, 6.2);
    t.checkExpect(arr.get(2).weight, 13.5);
    t.checkExpect(arr.get(3).weight, 66.1);
    t.checkExpect(arr.get(4).weight, 442.5);
  }
  
  // tests the generateName function for cells
  void testGenerateName(Tester t) {
    Cell c1 = new Cell(0,0,0);
    t.checkExpect(c1.generateName(), "0 0");
    
    Cell c2 = new Cell(15,16,0);
    t.checkExpect(c2.generateName(), "15 16");
    
    Cell c3 = new Cell(26,13,0);
    t.checkExpect(c3.generateName(), "26 13");
  }
  
  // tests getEdge
  void testGetEdge(Tester t) {
    Maze m = new Maze(5,5);
    Edge e = m.getEdge(m.cells.get(0).get(0),m.cells.get(1).get(0));
    Edge e2 = m.getEdge(m.cells.get(0).get(0),m.cells.get(0).get(1));
    if (e != null) {
      if (e.c1 == m.cells.get(0).get(0)) {
        t.checkExpect(e.c2, m.cells.get(1).get(0));
      }
    }
    if (e2 != null) {
      if (e2.c2 == m.cells.get(0).get(0)) {
        t.checkExpect(e2.c1, m.cells.get(0).get(1));
      }
    }
  }
  
  // test otherEnd
  void testOtherEnd(Tester t) {
    Maze m = new Maze(5,5);
    Cell c1 = new Cell(0,0,1);
    Cell c2 = new Cell(0,1,1);
    Cell c3 = new Cell(1,1,1);
    Edge e1 = new Edge(c1, c2, 3, 1);
    Edge e2 = new Edge(c2, c3, 4, 1);
    t.checkExpect(m.otherEnd(c1, e1), c2);
    t.checkExpect(m.otherEnd(c2, e1), c1);
    t.checkExpect(m.otherEnd(c2, e2), c3);
    t.checkExpect(m.otherEnd(c3, e2), c2);
  }
  
  // test setLinks
  void testSetLinks(Tester t) {
    Maze m = new Maze(5,5);
    // setLink called in the constructor of Maze
    t.checkExpect(m.cells.get(0).get(0).top, null);
    t.checkExpect(m.cells.get(0).get(0).right, m.cells.get(1).get(0));
    t.checkExpect(m.cells.get(0).get(0).bottom, m.cells.get(0).get(1));
    t.checkExpect(m.cells.get(0).get(0).left, null);
  
    t.checkExpect(m.cells.get(4).get(4).top, m.cells.get(4).get(3));
    t.checkExpect(m.cells.get(4).get(4).right, null);
    t.checkExpect(m.cells.get(4).get(4).bottom, null);
    t.checkExpect(m.cells.get(4).get(4).left, m.cells.get(3).get(4));
    
    t.checkExpect(m.cells.get(2).get(2).top, m.cells.get(2).get(1));
    t.checkExpect(m.cells.get(2).get(2).right, m.cells.get(3).get(2));
    t.checkExpect(m.cells.get(2).get(2).bottom, m.cells.get(2).get(3));
    t.checkExpect(m.cells.get(2).get(2).left, m.cells.get(1).get(2));
  }
  

  // test generateHashMap
  void testGenerateHashMap(Tester t) {
    Maze m = new Maze(10,10);
    // the size of the cell hashmap should be equal to the number of cells in the maze
    // in this case 10 by 10 maze is equal to 100 cells
    t.checkExpect(m.cellHashMap.size(), 100);
    // the cellhashmap should contain keys for every single cell in the maze
    for (ArrayList<Cell> a: m.cells) {
      for (Cell c: a) {
        t.checkExpect(m.cellHashMap.containsKey(c.generateName()), true);
      }
    }    
  }

  // Testing the player constructor
  Player p = new Player(10);
  
  void testPlayerConstructor(Tester t) {
    t.checkExpect(p.x, 0);
    t.checkExpect(p.y, 0);
    t.checkExpect(p.size, 10);
  }
  
  // Testing the player display
  void testPlayerDisplay(Tester t) {
    t.checkExpect(p.display(), new RectangleImage(8, 8, OutlineMode.SOLID, Color.ORANGE));
  }
  
  // Testing the makeUnvisited method for cells
  Cell c = new Cell(0, 0, 10);
  
  void testMakeUnvisited(Tester t) {
    t.checkExpect(c.visited, false);
    c.visited = true;
    t.checkExpect(c.visited, true);
    c.makeUnvisited();
    t.checkExpect(c.visited, false);
  }
  
  // Testing the makeAllCellsUnvisited method
  void testResetMaze(Tester t) {
    Maze m = new Maze(2, 2, "player");
    m.cells.get(1).get(1).visited = true;
    m.resetMaze();
    t.checkExpect(m.cells.get(1).get(1).visited, false);
    m.worklist.add(c);
    m.resetMaze();
    ArrayList<Cell> n = new ArrayList<Cell>();
    n.add(m.start);
    t.checkExpect(m.worklist, n);
  }
  
  // Testing onKeyEvent
  void testOnKeyEvent(Tester t) {
    
  }
  
  // Test BFS and DFS
  void testSearches(Tester t) {
    Maze m = new Maze(10,10);
    // the worklist should be auto generated by the constructor with the starting cell
    t.checkExpect(m.worklist.size() > 0, true);
    t.checkExpect(m.worklist.get(0), m.start);
    // the size of the array of all the processed cells should be equal to 0
    t.checkExpect(m.processed.size(), 0);
    m.dfs();
    // the worklist's first element should have been processed at the end of one 
    // iteration of the DFS
    t.checkExpect(m.worklist.get(0).equals(m.start), false);
    // the initial cell/ starting cell should have been processed
    t.checkExpect(m.processed.size(), 1);
    t.checkExpect(m.processed.get(0), m.start);
    // the starting cell should have been mutated to being visited
    t.checkExpect(m.start.visited, true);
    // running DFS a few times
    m.dfs();
    m.dfs();
    m.dfs();
    // the size of the worklist is larger than 0
    t.checkExpect(m.worklist.size() > 0, true);
    // four cells tested so far
    t.checkExpect(m.processed.size(), 4);    
    
    Maze m2 = new Maze(10,10);
    // the worklist should be auto generated by the constructor with the starting cell
    t.checkExpect(m2.worklist.size() > 0, true);
    t.checkExpect(m2.worklist.get(0), m2.start);
    // the size of the array of all the processed cells should be equal to 0
    t.checkExpect(m2.processed.size(), 0);
    m2.bfs();
    // the worklist's first element should have been processed at the end of one 
    // iteration of the DFS
    t.checkExpect(m2.worklist.get(0).equals(m2.start), false);
    // the initial cell/ starting cell should have been processed
    t.checkExpect(m2.processed.size(), 1);
    t.checkExpect(m2.processed.get(0), m2.start);
    // the starting cell should have been mutated to being visited
    t.checkExpect(m2.start.visited, true);
    
  }
  
  // test getCurrentCell
  void testGetCurrentCell(Tester t) {
    Maze m = new Maze(10,10);
    // the player should be starting on the starting cell
    t.checkExpect(m.player.x, m.start.x);
    t.checkExpect(m.player.y, m.start.y);
    // the current cell of the player is the starting cell
    t.checkExpect(m.getCurrentCell(), m.start);
    // moving the player to the right by one cell
    m.player.x++;
    // moving the player to down by one cell
    m.player.y++;
    // the new get current cell is the cell to the bottom right of the starting cell
    t.checkExpect(m.getCurrentCell(), m.start.right.bottom);
  }
 
  // test cell display function
  void testCellDisplay(Tester t) {
    Cell c1 = new Cell(1,1,3);
    Cell c2 = new Cell(1,1,3);
    Cell c3 = new Cell(1,1,3);
    
    c1.truePath = true;
    c2.visited = true;
    
    // if the cell is on the truepath then it will always be visible after completion
    t.checkExpect(c1.display(false), new RectangleImage(3, 3, OutlineMode.SOLID,
        new Color(220, 220, 60)));
    t.checkExpect(c1.display(true), new RectangleImage(3, 3, OutlineMode.SOLID,
        new Color(220, 220, 60)));
    
    // if the cell is visited and not on the truepath then it only shows when the display
    // function is passed true as a parameter
    t.checkExpect(c2.display(false), new RectangleImage(3, 3, OutlineMode.SOLID,
        Color.LIGHT_GRAY));
    t.checkExpect(c2.display(true), new RectangleImage(3, 3, OutlineMode.SOLID,
        new Color(170, 170, 240)));
    
    // if the cell is not visited and not on the true path then it is light gray
    t.checkExpect(c3.display(false), new RectangleImage(3, 3, OutlineMode.SOLID,
        Color.LIGHT_GRAY));
    t.checkExpect(c3.display(true), new RectangleImage(3, 3, OutlineMode.SOLID,
        Color.LIGHT_GRAY));
    
  }
  
  // tests the rendering of the Maze
  void testBigBang(Tester t) {
    Maze m = new Maze(50, 50,"dfs");
    World w = m;
    w.bigBang(m.width * m.cellSize + 8, m.height * m.cellSize + 8,0.00001);
  }
  
  
}


