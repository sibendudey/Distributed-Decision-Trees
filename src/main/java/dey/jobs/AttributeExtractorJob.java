package dey.jobs;

import dey.mappers.AttributeExtractorMapper;
import dey.reducers.AttributeExtractorReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AttributeExtractorJob extends Configured implements Tool {

    private static Logger logger = LoggerFactory.getLogger(AttributeExtractorJob.class);

    @Override
    public int run(String[] strings) throws Exception {
        return kMeansJobEvaluation("./MapReduce/input/spambase.txt", "./MapReduce/input/attribute_split/");
    }

    private int kMeansJobEvaluation(String inputPath, String outputPath) throws
            ClassNotFoundException,
            InterruptedException, IOException {

        final Configuration conf = getConf();
        final Job attributeSplitExtractor = Job.getInstance(conf, "AttributeSplitExtractor");
        attributeSplitExtractor.setJarByClass(AttributeExtractorJob.class);
        final Configuration jobConf = attributeSplitExtractor.getConfiguration();
        jobConf.set("mapreduce.output.textoutputformat.separator", "\t");
        attributeSplitExtractor.setInputFormatClass(NLineInputFormat.class);
        attributeSplitExtractor.setMapperClass(AttributeExtractorMapper.class);
        attributeSplitExtractor.setReducerClass(AttributeExtractorReducer.class);
        attributeSplitExtractor.setOutputKeyClass(DoubleWritable.class);
        attributeSplitExtractor.setOutputValueClass(DoubleWritable.class);
        NLineInputFormat.addInputPath(attributeSplitExtractor, new Path(inputPath));
        attributeSplitExtractor.getConfiguration().setInt("mapreduce.input.lineinputformat.linespermap", 1000);
        FileOutputFormat.setOutputPath(attributeSplitExtractor, new Path(outputPath));
        return attributeSplitExtractor.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) {
        try {
            long time = System.currentTimeMillis();
            ToolRunner.run(new AttributeExtractorJob(), args);
            long time2 = System.currentTimeMillis();
            logger.info(String.format("Time taken in secs: %d", (time2 - time) / 1000));
        } catch (final Exception e) {
            logger.error("", e);
        }
    }
}
