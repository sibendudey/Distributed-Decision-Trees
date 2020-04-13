package dey.dataset.mutable;


import dey.dataset.generic.Dataset;
import dey.dataset.pojos.label.Label;

import java.util.List;

public interface MutableMatrixSet<U, V> extends Dataset<U, V> {
    void addTuple(List<U> dataPoint, Label<V> label);
}
