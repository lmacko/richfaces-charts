package sk.lukasmacko.richfaces.chart.component.model;

import java.util.Iterator;
import java.util.Map;
import org.richfaces.json.JSONArray;

/**
 * Class implements Pie chart specific behavior.
 * @author Macko
 */
public class PieStrategy<T> implements ChartStrategy<T> {

    /**
     * The method adds key-value pair into model's data.
     * Value must be greater than zero.
     * @param key
     * @param value
     * @param model 
     */
    @Override
    public void add(T key, Number value, ChartDataModel model) {
        if (value.doubleValue() > 0) {
            model.data.put(key, value);
            model.keys.add(key);
        }
        else{
            throw new IllegalArgumentException("Value must be greater than zero.");
        }
    }

    /**
     * The method creates output JSON with a following form
     * [[x1,y1],[x2,y2],...]. If there are more than one series 
     * in a chart output values are determined in the same way as
     * in @see"bar strategy".
     * @param model
     * @return 
     */
    @Override
    public JSONArray toJSON(ChartDataModel model) {
        JSONArray collection = new JSONArray();

        if (model.outputKeys == null) {
            Iterator it = model.data.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                JSONArray point = new JSONArray();
                point.put(entry.getKey());
                point.put(entry.getValue());
                collection.put(point);
            }
        } else {
            Iterator it = model.outputKeys.iterator();
            while (it.hasNext()) {
                Object key = it.next();
                JSONArray point = new JSONArray();
                if (model.data.get(key) != null) {
                    point.put(key);
                    point.put(model.data.get(key));
                } else {
                    point.put(key);
                    point.put(0);
                }
                collection.put(point);
            }
        }
        return collection;
    }
}
