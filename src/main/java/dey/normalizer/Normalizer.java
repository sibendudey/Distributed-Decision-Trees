package dey.normalizer;

import java.util.List;
import java.util.Set;

public interface Normalizer<U> {
    List<List<U>> normalize(List<List<U>> datapoints, Set<Integer> exclude);
}


