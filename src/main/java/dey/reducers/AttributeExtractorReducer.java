package dey.reducers;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class AttributeExtractorReducer extends Reducer<DoubleWritable, DoubleWritable, Text, Text> {
    // TODO Remove hardcoding.
    private static final int intervals = 4;
    @Override
    protected void reduce(DoubleWritable key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
        double means = 0.0D;
        int count = 0;
        Iterator<DoubleWritable> iterator = values.iterator();
        while (iterator.hasNext())  {
            means += iterator.next().get();
            count++;
        }

        means /= count;

        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < 2 * intervals; i++)  {
            sb.append((i + 1.0) / intervals * means);
        }

        context.write(new Text("" + key.get()), new Text(sb.toString()));
    }
}
