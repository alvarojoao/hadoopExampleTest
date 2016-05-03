import java.io.IOException;import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class MostActiveUserPerCountry {
	public static class TokenizerMapper
	extends Mapper<Object, Text, Text, Text>{
        private Text word = new Text();
        private Text userId = new Text();
		public void map(Object key, Text value, Context context
				) throws IOException, InterruptedException {
			
			String line = value.toString();
			String user = line.split(",")[0];
			String country = line.split(",")[1];
			word.set(country);
			userId.set(user);
			context.write(word, userId);
		   
		}
	}

	public static class IntSumReducer
	extends Reducer<Text,Text,Text,Text> {
		private IntWritable result = new IntWritable(1);
        private Text resultSecond = new Text();

		public void reduce(Text key, Iterable<Text> values,
				Context context
				) throws IOException, InterruptedException {
			int max = 0;
			int count = 0;
			Map<String, Integer> mappOMap = new HashMap<>();
			String maxUserString = "";
			
			for (Text val : values) {count++;
				String valueString  = val.toString();
				if(!mappOMap.containsKey(valueString)){
					mappOMap.put(valueString, 1);
				}else{
					int valAtual  = mappOMap.get(valueString)+1;
					if (max<valAtual){
						max = mappOMap.get(valueString);
						maxUserString = valueString;
					}
					mappOMap.replace(valueString, valAtual+1);
				} 
            }
			resultSecond.set(maxUserString);
			result.set(count);
			context.write(key, resultSecond);
		}
	}

	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		
		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "MostActiveUserPerCountry");

		job.setJarByClass(MostActiveUserPerCountry.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path("hdfs://127.0.0.1:9000/in"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://127.0.0.1:9000/MostActiveUserPerCountry"));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		job.submit();
	}
}
