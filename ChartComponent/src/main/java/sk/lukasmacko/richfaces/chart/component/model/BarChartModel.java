package sk.lukasmacko.richfaces.chart.component.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.richfaces.json.JSONArray;

/**
 *
 * @author Macko
 */
public class BarChartModel implements ChartModel {

    private Map<String, Double> data;
    private List<String> keys; 
    private List<String> outputKeys;

    public BarChartModel() {
        data = new HashMap<>();
        keys = new ArrayList<>();
        outputKeys = null;
    }

    public Map<String, Double> getData() {
        return data;
    }

    public void setData(Map<String, Double> data) {
        this.data = data;
        this.keys = new ArrayList<>();
        keys.addAll(data.keySet());
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setOutputKeys(List<String> outKeys) {
        this.outputKeys = outKeys;
    }
    
    public List<String> getOutputKeys(){
        return this.outputKeys;
    }

    public void add(String key, double value) {
        data.put(key, value);
        this.keys.add(key);
    }

    @Override
    public JSONArray toJsonCollection() {
        JSONArray collection = new JSONArray();

        if (outputKeys == null) {
            for (String k : keys) {
                collection.put(data.get(k));
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
