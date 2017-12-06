// to compare the weights of two edges
public class WeightComp implements IComparator<Edge> {

  // compares the weight of two edges
  @Override
  public double compare(Edge t1, Edge t2) {
    return t1.weight - t2.weight;
  } 
}