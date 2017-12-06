import java.util.*;

import javalib.impworld.World;
import tester.*;

public class ExampleMaze {
  
  void dtestGen(Tester t) {
    Maze m = new Maze(5,5);
    for (Edge e : m.edges) {
      // checks that the maze generation created
      // all the edges with weights within
      // a certain range of numbers
      t.checkNumRange(e.weight, 0, 1);
    }
    // testing the remake function that regenerates the maze with 
    // the same height and width
    m.remake("eggtart");
    for (Edge e : m.edges) {
      // checks that the remake function created
      // add the edges with weights within
      // a certain range of numbers
      t.checkNumRange(e.weight, 0, 1);
    }
  }
 
  // checks the generation of the cells
  // and makes sure that the size of the cells
  // 2d array is correct
  void dtestGenerateCells(Tester t) {
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
  void dtestEdges(Tester t) {
    Maze m = new Maze(15,15);
    t.checkExpect(m.edges.size(), (m.width - 1) * (m.height - 1));
    
    Maze o = new Maze(15,20);
    t.checkExpect(o.edges.size(), (o.width - 1) * (o.height - 1));
    
    Maze p = new Maze(20,15);
    t.checkExpect(p.edges.size(), (p.width - 1) * (p.height - 1));
  }
  
  
  // testing the kruskal algortithm
  void dtestKruskalImplements(Tester t) {
    Maze m = new Maze(3,3);
    t.checkExpect(m.edges.size(), (m.width - 1) * (m.height - 1));
    m = new Maze(13,5);
    t.checkExpect(m.edges.size(), (m.width - 1) * (m.height - 1));
    m = new Maze(33,103);
    t.checkExpect(m.edges.size(), (m.width - 1) * (m.height - 1));
  }
  
  // testing the FindRepresentatives method
  void dtestFindRepresentative(Tester t) {
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
  void dtestUnion(Tester t) {
    Maze m = new Maze(2,2);
    m.cellHashMap = new HashMap<String, String>();
    m.cellHashMap.put("A", "A");
    m.cellHashMap.put("B", "A");
    m.cellHashMap.put("C", "B");
    m.union("B", "C");
    t.checkExpect(m.cellHashMap.get("B"), "A");
  }
  
  // tests the quicksort function and the comparator
  void dtestQuickSort(Tester t) {
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
  void dtestGenerateName(Tester t) {
    Cell c1 = new Cell(0,0,0);
    t.checkExpect(c1.generateName(), "0 0");
    
    Cell c2 = new Cell(15,16,0);
    t.checkExpect(c2.generateName(), "15 16");
    
    Cell c3 = new Cell(26,13,0);
    t.checkExpect(c3.generateName(), "26 13");
  }
  
  // tests getEdge
  void dtestGetEdge(Tester t) {
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
  void dtestOtherEnd(Tester t) {
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
  void dtestSetLinks(Tester t) {
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
  
  // TODO Test player class and methods
  Player p = new Player(10);
  
  // TODO Test onKeyEvent
  
  // TODO Test BFS and DFS
  void testSearches(Tester t) {
    Maze m = new Maze(10,10, "player");
    
  }

  // TODO Test Generate Player Image
  
  // TODO test getCurrentCell
  
  // TODO test cell display function
  
  
  // tests the rendering of the Maze
  void dtestBigBang(Tester t) {
    Maze m = new Maze(10, 10,"player");
    World w = m;
    w.bigBang(m.width * m.cellSize + 8, m.height * m.cellSize + 8,0.01);
  }
  
  
}


