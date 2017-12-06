import java.util.*;

// array utility functions for quicksort
public class ArrayUtils {
  // sorts the arraylist using the given comparator
  <T> void quicksort(ArrayList<T> arr, IComparator<T> comp) {
    quicksortHelp(arr, comp, 0, arr.size());
  }
  
  //EFFECT: sorts the source array according to comp, in the range of indices [loIdx, hiIdx)
  <T> void quicksortHelp(ArrayList<T> source, IComparator<T> comp,
                               int loIdx, int hiIdx) {
    // Step 0: check for completion
    if (loIdx >= hiIdx) {
      return; // There are no items to sort
    }
    // Step 1: select pivot
    T pivot = source.get(loIdx);
    // Step 2: partition items to lower or upper portions of the temp list
    int pivotIdx = partition(source, comp, loIdx, hiIdx, pivot);
    // Step 3: sort both halves of the list
    quicksortHelp(source, comp, loIdx, pivotIdx);
    quicksortHelp(source, comp, pivotIdx + 1, hiIdx);
  }
  
  <T> int partition(ArrayList<T> source, IComparator<T> comp,
      int loIdx, int hiIdx, T pivot) {
    int curLo = loIdx;
    int curHi = hiIdx - 1;
    while (curLo < curHi) {
      // Advance curLo until we find a too-big value (or overshoot the end of the list)
      while (curLo < hiIdx && comp.compare(source.get(curLo), pivot) <= 0) {
        curLo = curLo + 1;
      }
      // Advance curHi until we find a too-small value (or undershoot the start of the list)
      while (curHi >= loIdx && comp.compare(source.get(curHi), pivot) > 0) {
        curHi = curHi - 1;
      }
      if (curLo < curHi) {
        swap(source, curLo, curHi);
      }
    }
    swap(source, loIdx, curHi); // place the pivot in the remaining spot
    return curHi;
  }
  
  // swaps two items in the source
  <T> void swap(ArrayList<T> source, int index1, int index2) {
    T temp = source.get(index1);
    source.set(index1, source.get(index2));
    source.set(index2, temp);
  }
}