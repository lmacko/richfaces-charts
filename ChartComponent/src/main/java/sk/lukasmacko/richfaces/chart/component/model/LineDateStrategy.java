/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.lukasmacko.richfaces.chart.component.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.richfaces.json.JSONArray;

/**
 *
 * @author Macko
 */
public class LineDateStrategy implements ChartStrategy<Date> {
    private SimpleDateFormat formatter;
    
    public LineDateStrategy(){
        formatter = new SimpleDateFormat("yyyy-MM-dd h:mm a",Locale.US);
    }
    
    @Override
    public void add(Date key, Number value, ChartDataModel model) {
       model.data.put(key, value);
    }

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
