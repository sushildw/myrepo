
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.Mapper;

public class PreprocessorMapper extends MapReduceBase implements
    Mapper<LongWritable, Text, IntWritable, Text> {
	
  enum parse{
	  MAL_PARSE_COUNT;
  }

  private Text word = new Text();
	
	
  @Override
  public void map(LongWritable key, Text value,
      OutputCollector<IntWritable,Text> output, Reporter reporter)
      throws IOException {

    String line = value.toString();
	String[] tokens = line.split("t");
	if( tokens == null || tokens.length != 2 ){
		System.err.print("Problem with input line: "+line+"n");
		return;
	}
	int nbOccurences = 0;
	try{
		nbOccurences = Integer.parseInt(tokens[1].trim());
	}catch(NumberFormatException nfe){
		System.out.println(nfe.getMessage());
		nbOccurences =0;
		reporter.getCounter(parse.MAL_PARSE_COUNT).increment(1);
	}
	
	word.set(tokens[0]);
	output.collect(new IntWritable(nbOccurences),word );
    
  }
  
  
  
}
