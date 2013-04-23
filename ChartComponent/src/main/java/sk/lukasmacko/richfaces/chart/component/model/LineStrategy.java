package sk.lukasmacko.richfaces.chart.component.model;

import java.util.Iterator;
import java.util.Map;
import org.richfaces.json.JSONArray;

/**
 * Class implements Line chart specific behavior.
 * @author Lukas Macko
 */
public class LineStrategy<T> implements ChartStrategy<T> {

    /**
     * The method adds a key-value pair into model's data.
     * @param key
     * @param value
     * @param model 
     */
    @Override
    public void add(T key, Number value, ChartDataModel model) {
       model.data.put(key, value);
    }

    /**
     * The method creates JSON with following form [[x1,y1],[x2,y2],...]
     * @param model
     * @return 
     */
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
