package sk.lukasmacko.richfaces.chart.component.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.richfaces.json.JSONArray;

/**
 * Class implements Line chart with independent variable of Date type
 * specific behavior.
 * @author Lukas Macko
 */
public class LineDateStrategy implements ChartStrategy<Date> {
    /**
     * Formatter specify x-values format in output JSON.
     */
    private SimpleDateFormat formatter;
    
    public LineDateStrategy(){
        formatter = new SimpleDateFormat("yyyy-MM-dd h:mm a",Locale.US);
    }
    
    /**
     * The method adds key-value pair into model's data.
     * @param key
     * @param value
     * @param model 
     */
    @Override
    public void add(Date key, Number value, ChartDataModel model) {
       model.data.put(key, value);
    }

    /**
     * The method creates JSON with following form [[x1,y1],[x2,y2],...]
     * x-values are printed with formatter
     * @param model
     * @return 
     */
    @Override
    public JSONArray toJSON(ChartDataModel model) {
        JSONArray collection;

        collection = new JSONArray();
        for (Iterator it = model.data.entrySet().iterator(); it.hasNext();) {
            JSONArray point = new JSONArray();
            Map.Entry entry = (Map.Entry) it.next();
            point.put(formatter.format(entry.getKey()));
            point.put(entry.getValue());
            collection.put(point);
        }

        return collection;
    }
}
