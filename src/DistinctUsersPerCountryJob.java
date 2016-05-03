import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class DistinctUsersPerCountryJob {
	public static class TokenizerMapper
	extends Mapper<Object, Text, Text, IntWritable>{
		private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();
        private Text userId = new Text();
		public void map(Object key, Text value, Context context
				) throws IOException, InterruptedException {
			
			String[] comps = value.toString().split(",");
			String userIdString = comps[0];
			String coutrinString = comps[1];
			
			word.set(coutrinString+","+userIdString);
			context.write(word, one);
		}
	}

	public static class IntSumCombiner
	extends Reducer<Text,IntWritable,Text,IntWritable> {
		private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

		public void reduce(Text key, Iterable<IntWritable> values,
				Context context
				) throws IOException, InterruptedException {
			String countryString =key.toString().split(",")[0];
			word.set(countryString);
			context.write(word, one);
		}
	}
	public static class IntSumReducer
	extends Reducer<Text,IntWritable,Text,IntWritable> {
		private IntWritable result = new IntWritable(1);

		public void reduce(Text key, Iterable<IntWritable> values,
				Context context
				) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
                sum += val.get();
            }
			result.set(sum);
			context.write(key, result);
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "DistinctUsersPerCountryJob");

		job.setJarByClass(DistinctUsersPerCountryJob.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumCombiner.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path("hdfs://127.0.0.1:9000/in"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://127.0.0.1:9000/DistinctUsersPerCountryJob"));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		job.submit();
	}
}
