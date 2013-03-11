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
        data= new HashMap<String,Double>();
    }
    
    public PieChartModel(Map<String, Double> data) {
        this.data = data;
    }
    
    public void add(String key, double value) {
        if(value<0){
            throw new IllegalArgumentException("Value must be greater than zero.");
        }
        this.data.put(key, value);
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

    @Override
    public ChartType getChartType() {
       return ChartType.pie;
    }
    
    
    
}
