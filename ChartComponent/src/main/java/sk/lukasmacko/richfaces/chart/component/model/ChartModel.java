
package sk.lukasmacko.richfaces.chart.component.model;

import org.richfaces.json.JSONArray;

/**
 *
 * @author Macko
 */
public interface ChartModel {
    public ChartType getChartType();
    
    public JSONArray toJsonCollection();
    
    public enum ChartType{
        line,linebar,bar,pie,donut,unknown
    }
}
