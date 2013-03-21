



import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AvgWordLengthReducer extends Reducer<Text, IntWritable, Text, DoubleWritable> {

  @Override
  public void reduce(Text key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException {
	  
	      
    int sum= 0;
    double count =0.0;
    
    for (IntWritable value : values) {
    		 sum =+ value.get();
    	     count++;
    }
    context.write(key, new DoubleWritable(sum/count));

  }
}
