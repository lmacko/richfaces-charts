package sk.lukasmacko.richfaces.chart.component.model;

import java.util.Iterator;
import java.util.Map;
import org.richfaces.json.JSONArray;

/**
 *
 * @author Macko
 */
public class PieStrategy<T> implements ChartStrategy<T> {

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

    @Override
    public JSONArray toJSON(ChartDataModel model) {
        JSONArray collection = new JSONArray();

        if (model.outputKeys == null) {
            Iterator it = model.data.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                collection.put(model.data.get(entry.getKey()));
                collection.put(model.data.get(entry.getValue()));
            }
        } else {
            Iterator it = model.outputKeys.iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if (model.data.get(key) != null) {
                    collection.put(key);
                    collection.put(model.data.get(key));
                } else {
                    collection.put(key);
                    collection.put(0);
                }
            }
        }
        return collection;
    }
}
