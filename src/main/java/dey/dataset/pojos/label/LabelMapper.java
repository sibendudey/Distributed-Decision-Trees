package dey.dataset.pojos.label;

import java.util.List;

public class LabelMapper {
    public static double[] convertToPrimitiveDouble(List<Label<Double>> labels)   {
        double[] doubleLabels = new double[labels.size()];
        for (int i = 0 ; i < labels.size() ; i++)
            doubleLabels[i] = labels.get(i).label();

        return doubleLabels;
    }
}
