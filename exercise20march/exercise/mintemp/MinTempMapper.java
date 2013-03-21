


import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class MinTempMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
	  
	  	String dataRow = value.toString();
	  	
        
	  	String[] rowArr = dataRow.split( ",\\s*" );
	  	if(null != rowArr && rowArr.length > 0){
	  		if(null != rowArr[1] && null != rowArr[2] && "TMIN".equals(rowArr[2])){
	  			String month = rowArr[1].substring(4, 6);
	  			if(null != rowArr[3] && rowArr[3].length() > 0){
	  				int temp =0;
	  				temp = Integer.parseInt(rowArr[3]);
	  				context.write(new Text(month), new IntWritable(temp));
	  			}
	  		}
	  	}


  }
}


