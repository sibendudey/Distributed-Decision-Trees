package dey.mappers;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AttributeExtractorMapper extends Mapper<Object, Text, DoubleWritable, DoubleWritable> {
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        final String [] values = value.toString().split(",");
        for (int i = 0 ; i < values.length - 1 ; i++)   {
            context.write(new DoubleWritable(i), new DoubleWritable(Double.valueOf(values[i])));
        }
    }
}
