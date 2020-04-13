package dey.dataset.generic;

import dey.dataset.pojos.label.Label;
import dey.dataset.pojos.tuple.RowTupleImpl;
import dey.dataset.pojos.tuple.RowTuple;

import java.util.ArrayList;
import java.util.List;

public class DatasetImpl implements Dataset<Double, Double> {
    protected List<RowTuple<Double, Double>> rowTuples;
    public DatasetImpl(List<List<Double>> matrix, List<Label<Double>> labels)  {
        int dataSize = matrix.size();
        int labelSize = labels.size();
        rowTuples = new ArrayList<>();

        if (dataSize != labelSize)
            throw new IllegalArgumentException("The size of data much match with number of labels");

        for (int i = 0; i < dataSize; i++)  {
            rowTuples.add(new RowTupleImpl(matrix.get(i), labels.get(i)));
        }
    }

    @Override
    public List<RowTuple<Double, Double>> rowTuples() {
        return rowTuples;
    }
}
