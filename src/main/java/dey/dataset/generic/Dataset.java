package dey.dataset.generic;

import dey.dataset.pojos.tuple.RowTuple;

import java.util.List;

public interface Dataset<U, V> {
    List<RowTuple<U, V>> rowTuples();
}
