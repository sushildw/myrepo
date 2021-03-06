
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.StringTokenizer;

public class MaxTempMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	enum Temperature{
		MAX_COUNT,
		MIN_COUNT
	}
  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
	  
	  	String dataRow = value.toString();
	  	
	  	// through string tokenizer
	  	
    	/*String[] strArr = new String[10];
        StringTokenizer dataTokenizer = new StringTokenizer(dataRow, "\t");
        int count = 0;
        while(dataTokenizer.hasMoreTokens()){
            strArr[count] = dataTokenizer.nextToken();
            count++;
        }*/
        
	  	String[] rowArr = dataRow.split( ",\\s*" );
	  	if(null != rowArr && rowArr.length > 0){
	  		if(null != rowArr[1] && null != rowArr[2] && "TMAX".equals(rowArr[2])){
	  			if(null != rowArr[3] && rowArr[3].length() > 0){
	  				int temp =0;
	  				temp = Integer.parseInt(rowArr[3]);
	  				context.write(new Text("Max Temp: "), new IntWritable(temp));
	  			}
	  			context.getCounter(Temperature.MAX_COUNT).increment(1);
	  		} else if("TMIN".equals(rowArr[2])){
	  			context.getCounter(Temperature.MIN_COUNT).increment(1);
	  		}
	  	}


  }
}


