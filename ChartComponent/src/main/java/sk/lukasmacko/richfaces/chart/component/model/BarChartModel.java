package sk.lukasmacko.richfaces.chart.component.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.richfaces.json.JSONArray;

/**
 *
 * @author Macko
 */
public class BarChartModel implements ChartModel {

    private Map<String, Double> data;
    private Set<String> outputKeys;

    public BarChartModel() {
        data = new HashMap<>();
        outputKeys = null;
    }

    public Map<String, Double> getData() {
        return data;
    }

    public void setData(Map<String, Double> data) {
        this.data = data;
    }

    public Set<String> getKeys() {
        return data.keySet();
    }

    public void setOutputKeys(Set<String> keys) {
        this.outputKeys = keys;
    }

    public void add(String key, double value) {
        data.put(key, value);
    }

    @Override
    public JSONArray toJsonCollection() {
        JSONArray collection = new JSONArray();

        if (outputKeys == null) {
            for (Map.Entry<String, Double> e : data.entrySet()) {
                collection.put(e.getValue());
            }
        } else {
            for (String key : outputKeys) {
                if (data.get(key) != null) {
                    collection.put(data.get(key));
                } else {
                    collection.put(0);
                }
            }
        }
        return collection;
    }

    @Override
    public ChartType getChartType() {
        return ChartType.bar;
    }
}
