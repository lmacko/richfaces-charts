package sk.lukasmacko.richfaces.chart.component.model;

import java.util.Iterator;
import java.util.Map;
import org.richfaces.json.JSONArray;

/**
 *
 * @author Macko
 */
public class LineStrategy<T> implements ChartStrategy<T> {

    @Override
    public void add(T key, Number value, ChartDataModel model) {
       model.data.put(key, value);
    }

    @Override
    public JSONArray toJSON(ChartDataModel model) {
        JSONArray collection;

        collection = new JSONArray();
        for (Iterator it = model.data.entrySet().iterator(); it.hasNext();) {
            JSONArray point = new JSONArray();
            Map.Entry entry = (Map.Entry) it.next();
            point.put(entry.getKey());
            point.put(entry.getValue());
            collection.put(point);
        }

        return collection;
    }
    
    
}
