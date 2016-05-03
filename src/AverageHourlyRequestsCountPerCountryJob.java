import java.io.IOException;import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

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

import sun.net.www.content.text.plain;



public class AverageHourlyRequestsCountPerCountryJob {
	public static class TokenizerMapper
	extends Mapper<Object, Text, Text, Text>{
        private Text word = new Text();
        private Text hour = new Text();
		public void map(Object key, Text value, Context context
				) throws IOException, InterruptedException {
			
			String line = value.toString();
			String countryString = line.split(",")[1];
			String startDateString = line.split(",")[4];
		    DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
		    DateFormat dfNew = new SimpleDateFormat("HH");
		    Date date;
		    try {
		    	date = df.parse(startDateString);
		        String newDateString = dfNew.format(date);
				word.set(countryString);
				hour.set(newDateString);
				context.write(word, hour);
		    } catch (ParseException e) {
		        e.printStackTrace();
		    }
		   
		}
	}

	public static class IntSumReducer
	extends Reducer<Text,Text,Text,LongWritable> {
        private LongWritable result = new LongWritable(1);

		public void reduce(Text key, Iterable<Text> values,
				Context context
				) throws IOException, InterruptedException {
			Map<String, Integer> mappOMap = new HashMap<>();
			
			for (Text val : values) {
				String valueString  = val.toString();
				if(!mappOMap.containsKey(valueString)){
					mappOMap.put(valueString, 1);
				}else{
					int valAtual  = mappOMap.get(valueString)+1;
					mappOMap.replace(valueString, valAtual+1);
				} 
            }
			int total = 0;
			for(Integer vInteger:mappOMap.values()){
				total+=vInteger;
			}
			long val = total/mappOMap.size();
			
			result.set(val);
			context.write(key, result);
		}
	}

	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		
		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "MostActiveUserPerCountry");

		job.setJarByClass(AverageHourlyRequestsCountPerCountryJob.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path("hdfs://127.0.0.1:9000/in"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://127.0.0.1:9000/AverageHourlyRequestsCountPerCountryJob"));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		job.submit();
	}
}
