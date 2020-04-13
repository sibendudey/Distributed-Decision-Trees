package dey.dataset.mutable;

import dey.dataset.generic.DatasetImpl;
import dey.dataset.pojos.label.Label;
import dey.dataset.pojos.tuple.RowTuple;
import dey.dataset.pojos.tuple.RowTupleImpl;

import java.util.List;

public class DoubleMutableMatrixSet extends DatasetImpl implements MutableMatrixSet<Double, Double> {

    public DoubleMutableMatrixSet(List<List<Double>> data, List<Label<Double>> label) {
        super(data, label);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (RowTuple<Double, Double> rowTuple : rowTuples) {
            int featureSize = rowTuple.data().size();
            for (int j = 0; j < featureSize; j++) {
                sb.append(rowTuple.data().get(j)).append(",");
            }

            sb.append(rowTuple.label()).append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public void addTuple(List<Double> dataPoint, Label<Double> label) {
        rowTuples.add(new RowTupleImpl(dataPoint, label));
    }
}
