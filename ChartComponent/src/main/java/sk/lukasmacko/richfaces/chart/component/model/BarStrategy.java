package sk.lukasmacko.richfaces.chart.component.model;

import java.util.Iterator;
import java.util.Map;
import org.richfaces.json.JSONArray;

/**
 *
 * @author Macko
 */
public class BarStrategy<T> implements ChartStrategy<T> {

    @Override
    public void add(T key, Number value, ChartDataModel model) {
        model.data.put(key, value);
        model.keys.add(key);
    }

    @Override
    public JSONArray toJSON(ChartDataModel model) {
        JSONArray collection = new JSONArray();

        if (model.getKeyType() == Number.class) {
            Iterator it = model.data.entrySet().iterator();
            while (it.hasNext()) {
                JSONArray point = new JSONArray();
                Map.Entry entry = (Map.Entry) it.next();
                point.put(entry.getKey());
                point.put(entry.getValue());
                collection.put(point);
            }
        } else {
            if (model.outputKeys == null) {
                Iterator it = model.data.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    collection.put(model.data.get(entry.getKey()));
                }
            } else {

                Iterator it = model.outputKeys.iterator();
                while (it.hasNext()) {
                    Object key = it.next();
                    if (model.data.get(key) != null) {
                        collection.put(model.data.get(key));
                    } else {
                        collection.put(0);
                    }
                }
            }
        }
        return collection;

    }
}
