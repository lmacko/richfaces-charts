/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.lukasmacko.richfaces.chart.component.model;

import java.util.HashMap;
import java.util.Map;
import org.richfaces.json.JSONArray;

/**
 *
 * @author Macko
 */
public class PieChartModel implements ChartModel{

    
    private Map<String,Double> data;
    
    public PieChartModel(){
        data= new HashMap<>();
    }
    
    public PieChartModel(Map<String, Double> data) {
        this.data = data;
    }
    
    public void add(String x, double y) {
        this.data.put(x, y);
    }
    
    @Override
    public JSONArray toJsonCollection() {
        JSONArray collection;

        collection = new JSONArray();
        
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            JSONArray point = new JSONArray();
              point.put(entry.getKey());
            point.put(entry.getValue());
          
            collection.put(point);
        }

        return collection;
    }
    
}
