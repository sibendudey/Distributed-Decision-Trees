package dey.dataset.pojos.tuple;

import dey.dataset.pojos.label.Label;

import java.util.List;

public interface RowTuple<U, V> {
    List<U> data();
    Label<V> label();
}
