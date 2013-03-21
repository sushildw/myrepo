
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SsDriver extends Configured implements Tool {

  @Override
  public int run(String[] args) throws Exception {

    if (args.length != 2) {
      System.out.printf(
          "Usage: %s [generic options] <input dir> <output dir>\n", getClass()
              .getSimpleName());
      ToolRunner.printGenericCommandUsage(System.out);
      return -1;
    }

    Job job = new Job(getConf());
    job.setJarByClass(SsDriver.class);
    job.setJobName(this.getClass().getName());

    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    
    job.setMapperClass(MapClass.class);
    job.setReducerClass(Reduce.class);
    
    // group and partition by the first int in the pair
    job.setPartitionerClass(FirstPartitioner.class);
    job.setGroupingComparatorClass(FirstGroupingComparator.class);

    // the map output is IntPair, IntWritable
    job.setMapOutputKeyClass(IntPair.class);
    job.setMapOutputValueClass(IntWritable.class);

    // the reduce output is Text, IntWritable
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    if (job.waitForCompletion(true)) {
      return 0;
    }
    return 1;
  }

  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(new SsDriver(), args);
    System.exit(exitCode);
  }
}
