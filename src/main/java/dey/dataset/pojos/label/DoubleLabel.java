package dey.dataset.pojos.label;

import java.io.Serializable;

public class DoubleLabel implements Label<Double>, Serializable {
    private Double label;
    public DoubleLabel(Double label) {
        this.label = label;
    }
    @Override
    public Double label() {
        return label;
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DoubleLabel
                && ((DoubleLabel) obj).label.equals(this.label);
    }
}
