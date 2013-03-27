
package sk.lukasmacko.richfaces.chart.component.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.richfaces.json.JSONArray;

/**
 *
 * @author Macko
 */
public abstract class ChartDataModel<T> {

    
    
    Map<T, Number> data;
    List<T> keys; 
    List<T> outputKeys;
    
    protected ChartStrategy strategy;
    private ChartType chartType;

    public ChartDataModel(ChartType type) {
        
        this.chartType = type;
        data = new HashMap<T, Number> ();
        keys = new ArrayList<T>();
        switch (chartType) {
            case line:
                strategy = new LineStrategy<T>();
                break;
            case bar:
                strategy = new BarStrategy<T>();
                break;
            case pie:
                strategy = new PieStrategy<T>();
                break;
            case donut:
            case unknown:
                throw new IllegalArgumentException("Chart type not specified");
        }
   }

    public void add(T key, Number value) {
        strategy.add(key, value, this);
    }

    public JSONArray toJson() {
        if(!isAllowedForChartType(chartType)){
            throw new UnsupportedOperationException("Unsupported key type "+getKeyType()+" for"+chartType);
        }
        return strategy.toJSON(this);
    }

    public boolean isAllowedForChartType(ChartType type) {
        switch (type) {
            case line:
                return this.getKeyType() == Number.class || this.getKeyType() == Date.class ? true : false;
            case bar:
                return this.getKeyType() == Number.class || this.getKeyType() == String.class ? true : false;
            case pie:
                return this.getKeyType() == String.class ? true :false;
            case donut:
            case unknown:
        }

        return false;
    }

   
    public enum ChartType {
        line, bar, pie, donut, unknown
    }

    public Map<T, Number> getData() {
        return data;
    }

    public ChartType getChartType() {
        return chartType;
    }
    
    public abstract Class getKeyType();

    public List<T> getKeys() {
        return keys;
    }

    public List<T> getOutputKeys() {
        return outputKeys;
    }

    public void setOutputKeys(List<T> outputKeys) {
        this.outputKeys = outputKeys;
    }
    
}
