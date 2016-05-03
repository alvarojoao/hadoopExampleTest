import java.io.IOException;import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class PeakHourOfDayJob {
	public static class TokenizerMapper
	extends Mapper<Object, Text, Text, IntWritable>{
		private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();
		public void map(Object key, Text value, Context context
				) throws IOException, InterruptedException {
			
			String line = value.toString();
			String startDateString = line.split(",")[4];
		    DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
		    DateFormat dfNew = new SimpleDateFormat("HH zzz");
		    Date date;
		    try {
		    	date = df.parse(startDateString);
		        String newDateString = dfNew.format(date);
				word.set(newDateString);
				context.write(word, one);
		    } catch (ParseException e) {
		        e.printStackTrace();
		    }
		}
	}

	
	public static class IntSumReducer
	extends Reducer<Text,IntWritable,Text,IntWritable> {
		private IntWritable result = new IntWritable(1);
		Text hourMax = new Text();
		public void reduce(Text key, Iterable<IntWritable> values,
				Context context
				) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
                sum += val.get();
            }
			if (result.get()<sum){
				hourMax = key;
				result.set(sum);
			}
		}
		// only display the character which has the largest value
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
		    context.write(hourMax, result);
		}
	}

	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		
		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "DistinctUsersPerCountryJob");

		job.setJarByClass(PeakHourOfDayJob.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path("hdfs://127.0.0.1:9000/in"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://127.0.0.1:9000/PeakHourOfDayJob"));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		job.submit();
	}
}
