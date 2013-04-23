package sk.lukasmacko.richfaces.chart.component.model;

import java.util.Iterator;
import java.util.Map;
import org.richfaces.json.JSONArray;

/**
 * Class implements Bar chart specific behavior.
 * @author Lukas Macko
 */
public class BarStrategy<T> implements ChartStrategy<T> {

    /**
     * The method add key-value pair into model's data. It 
     * also push key into list holding keys. 
     * @param key
     * @param value
     * @param model 
     */
    @Override
    public void add(T key, Number value, ChartDataModel model) {
        model.data.put(key, value);
        model.keys.add(key);
    }

    /**
     * The method creates JSON.
     * <p>If independent variable(x-value) is Number 
     * then JSON has following form [[x1,y1],[x2,y2],...].</p>
     * <p>If independent variable(x-value) is String then [x1,x2,...] y-values
     * are passed in options. The output values and their order are
     * determined by the following conditions.
     * <ul>
     * <li>If chart plots only one series then output 
     * JSON contains all points in the same order as they were added.</li>
     * <li>If there are more then one series in a chart,
     * first series selects the independent variable(keys) which output
     * JSON will contain. Missing keys in other series are added with zero value. 
     * </li>
     * </ul>
     * </p>
     * 
     * 
     * @param model
     * @return 
     */
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
                        //add 0 value at missing key index
                        collection.put(0);
                    }
                }
            }
        }
        return collection;

    }
}
