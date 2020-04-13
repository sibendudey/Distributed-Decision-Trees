package dey.mappers;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class TupleNormalizerMapper extends Mapper<Object, Text, NullWritable, Text> {
    private Logger logger = LoggerFactory.getLogger(TupleNormalizerMapper.class);
    private List<double[]> minMaxValues = new ArrayList<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        URI[] uris = context.getCacheFiles();
        FileSystem fileSystem = FileSystem.get(context.getConfiguration());
        boolean DEBUG_MODE = context.getConfiguration().getBoolean("DEBUG_MODE", true);
        // uncomment for S3
        if (!DEBUG_MODE) {
            try {
                fileSystem = FileSystem.get(new URI("s3://sibendu-project/"), context.getConfiguration());
            } catch (URISyntaxException e) {
                logger.error(e.getMessage());
            }
        }

        for (URI uri : uris) {
            Path path = new Path(uri.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileSystem.open(path)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\\s+");
                minMaxValues.add(new double[] {Double.valueOf(split[1]), Double.valueOf(split[2])});
            }
        }
    }

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] split = value.toString().split(",");
        StringBuilder normalizedTuple = new StringBuilder();
        for (int i = 0 ; i < split.length - 1 ; i++)    {
            double nonNormalizedValue = Double.parseDouble(split[i]);
            normalizedTuple.append(normalizedValue(nonNormalizedValue, i)).append(",");
        }
        normalizedTuple.append(split[split.length - 1]);
        context.write(NullWritable.get(), new Text(normalizedTuple.toString()));
    }

    private double normalizedValue(double nonNormalizedValue, int index) {
        double normalizedValue = 0;
        double max = minMaxValues.get(index)[1];
        double min = minMaxValues.get(index)[0];
        if (Math.abs(max - min) != 0)   {
            normalizedValue =  (nonNormalizedValue - min) / (max - min);
            if (normalizedValue > 1.0)
                throw new RuntimeException("illegal normalized value");
        }
        return normalizedValue;
    }
}
