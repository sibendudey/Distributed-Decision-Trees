package dey.dataset.pojos.tuple;

import dey.dataset.pojos.label.Label;

import java.util.List;

public class RowTupleImpl implements RowTuple<Double, Double> {
    private final List<Double> data;
    private final Label<Double> label;

    public RowTupleImpl(List<Double> data, Label<Double> label)    {
        this.data = data;
        this.label = label;
    }
    @Override
    public List<Double> data() {
        return data;
    }

    @Override
    public Label<Double> label() {
        return label;
    }
}
