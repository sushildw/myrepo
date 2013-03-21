import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.WritableComparator;


public class FirstGroupingComparator implements RawComparator<IntPair> {
	
    @Override
    public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
      return WritableComparator.compareBytes(b1, s1, Integer.SIZE/8, 
                                             b2, s2, Integer.SIZE/8);
    }

    @Override
    public int compare(IntPair o1, IntPair o2) {
      int l = o1.getFirst();
      int r = o2.getFirst();
      return l == r ? 0 : (l < r ? -1 : 1);
    }

}
