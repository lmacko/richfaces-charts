package sk.lukasmacko.richfaces.chart.component.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.richfaces.json.JSONArray;

/**
 * Base class providing data for chart component.
 * It is designed to support different chart types
 * @author LukasMacko
 */
public abstract class ChartDataModel<T> {

    
    /**
     * data source for chart 
     */
    Map<T, Number> data;
    
    /**
     * List of keys in data map of this instance.
     */
    List<T> keys; 
    
    /**
     * List of keys which will select points output JSON,
     * if a chart has more than one series.
     */
    List<T> outputKeys;
    
    /**
     * Chart type based behavior
     * <ul>
     *   <li>to add new item(chart point) into data</li>
     *   <li>output data into proper JSON format</li>
     * </ul>
     */
    protected ChartStrategy strategy;
    
    
    private ChartType chartType;

    /**
     * Initialization of properties and strategy selection
     * @param type 
     */
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
            case unknown:
                throw new IllegalArgumentException("Chart type not specified");
        }
   }

    /**
     * Method delegates point adding to strategy.
     * @param key
     * @param value 
     */
    public void add(T key, Number value) {
        strategy.add(key, value, this);
    }

    /**
     * Method delegates conversion of data to JSON representation.
     * At first, it checks whether T type(class generic type) used
     * is supported by ChartType,if not UnsupportedOperationException
     * is thrown.
     * @return JSON collection 
     */
    public JSONArray toJson() {
        if(!isAllowedForChartType(chartType)){
            throw new UnsupportedOperationException("Unsupported key type "+getKeyType()+" for"+chartType);
        }
        return strategy.toJSON(this);
    }

    /**
     * Method checks if T type can be used with ChartType
     * @param type
     * @return 
     */
    public boolean isAllowedForChartType(ChartType type) {
        switch (type) {
            case line:
                return this.getKeyType() == Number.class || this.getKeyType() == Date.class ? true : false;
            case bar:
                return this.getKeyType() == Number.class || this.getKeyType() == String.class ? true : false;
            case pie:
                return this.getKeyType() == String.class ? true :false;
            case unknown:
        }

        return false;
    }

   
    public enum ChartType {
        line, bar, pie, unknown
    }

   
    
    /**
     * Concrete subclass overrides this method
     * determine its type.
     * @return T.class 
     */
    public abstract Class getKeyType();

    /** Class property getters/setters **/
    
    public Map<T, Number> getData() {
        return data;
    }

    public ChartType getChartType() {
        return chartType;
    }
    
    public List<T> getKeys() {
        return keys;
    }

    public List<T> getOutputKeys() {
        return outputKeys;
    }

    public void setOutputKeys(List<T> outputKeys) {
        this.outputKeys = outputKeys;
    }
    
    /** End of getters/setters **/
    
}
