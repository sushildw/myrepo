
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.SequenceFileInputFormat;
import org.apache.hadoop.mapred.lib.InputSampler;
import org.apache.hadoop.mapred.lib.TotalOrderPartitioner;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SortExample extends Configured implements Tool {

  @Override
  public int run(String[] args) throws Exception {

    String input, output;
    if(args.length == 2) {
      input = args[0];
      output = args[1];
    } else {
      input = "your-input-dir";
      output = "your-output-dir";
    }

    JobConf conf = new JobConf(getConf(), SortExample.class);
    conf.setJobName(this.getClass().getName());

    FileInputFormat.setInputPaths(conf, new Path(input));
    FileOutputFormat.setOutputPath(conf, new Path(output));


    conf.setInputFormat(SequenceFileInputFormat.class);
    conf.setOutputKeyClass(IntWritable.class);
    conf.setOutputValueClass(Text.class);
    
    conf.setNumReduceTasks(2);
    //conf.setPartitionerClass(MyPartitioner.class);
    
    // added for total order partitioner
    InputSampler.Sampler<IntWritable, Text> sampler =
    		new InputSampler.RandomSampler<IntWritable, Text>(0.1, 100);
    TotalOrderPartitioner.setPartitionFile(conf, new Path("hdfs://localhost/user/training/in/fortotalsort"));
    InputSampler.writePartitionFile(conf, sampler);
    conf.setPartitionerClass(TotalOrderPartitioner.class);
    
    //TotalOrderPartitioner.setPartitionFile(conf,null);
    
    JobClient.runJob(conf);
    return 0;
   
  }

  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(new SortExample(), args);
    System.exit(exitCode);
  }
}
