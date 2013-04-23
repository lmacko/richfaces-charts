package sk.lukasmacko.richfaces.chart.component.model;

import org.richfaces.json.JSONArray;

/**
 *
 * @author Lukas Macko
 */
public interface ChartStrategy<T> {
    
     public void add(T key,Number value, ChartDataModel model);
    
     public JSONArray toJSON(ChartDataModel model);
}
