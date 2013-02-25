package sk.lukasmacko.richfaces.chart.component.model;

import java.util.HashMap;
import java.util.Map;
import org.richfaces.json.JSONArray;

/**
 *
 * @author Macko
 */
public class LineChartModel implements ChartModel{

    private Map<Double, Double> data;
    
    
    public Map<Double, Double> getData() {
        return data;
    }

    public void setData(Map<Double, Double> data) {
        this.data = data;
    }

    public LineChartModel() {
        data = new HashMap<Double,Double>();
    }

    public void add(double x, double y) {
        this.data.put(x, y);
    }

    @Override
    public JSONArray toJsonCollection() {
        JSONArray collection;

        collection = new JSONArray();
        
        for (Map.Entry<Double, Double> entry : data.entrySet()) {
            JSONArray point = new JSONArray();
            point.put(entry.getKey());
            point.put(entry.getValue());
            collection.put(point);
        }

        return collection;
    }

    @Override
    public ChartType getChartType() {
       return ChartType.line;
    }
    
}
