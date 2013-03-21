

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class AvgWordLengthMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
	  
	  	String dataRow = value.toString();
	  	
	  	for (String word : dataRow.split("\\W+")) {
	        if (word.length() > 0) {
	          context.write(new Text(String.valueOf(word.charAt(0))), new IntWritable(word.length()));
	        }
	      }
	 
  }
}



