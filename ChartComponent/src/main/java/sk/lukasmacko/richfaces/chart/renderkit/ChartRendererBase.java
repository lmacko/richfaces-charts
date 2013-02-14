package sk.lukasmacko.richfaces.chart.renderkit;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;
import org.richfaces.renderkit.RendererBase;
import sk.lukasmacko.richfaces.chart.component.AbstractChart;
import sk.lukasmacko.richfaces.chart.component.AbstractCursor;
import sk.lukasmacko.richfaces.chart.component.AbstractLegend;
import sk.lukasmacko.richfaces.chart.component.AbstractSeries;
import sk.lukasmacko.richfaces.chart.component.AbstractXaxis;
import sk.lukasmacko.richfaces.chart.component.AbstractYaxis;
import sk.lukasmacko.richfaces.chart.component.model.ChartModel;

/**
 *
 * @author Macko
 */
public abstract class ChartRendererBase extends RendererBase {

    private JSONObject options;
    private JSONArray data;

    public static JSONObject addAttribute(JSONObject obj, String key, Object value) {
        try {
            obj.put(key, value);
        } catch (JSONException ex) {
            Logger.getLogger(ChartRendererBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }

    protected void initChart(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        AbstractChart chart = (AbstractChart) component;

        options = new JSONObject();
        
        
        //series properties
        JSONArray seriesOptions = new JSONArray();
        addAttribute(options, "series", seriesOptions);
                
        
        data = new JSONArray();

        //chart options
        addAttribute(options, "title", component.getAttributes().get("title"));


        
        List<UIComponent> children = chart.getChildren();
        //process children tags
        for (UIComponent ch : children) {
            if (ch instanceof AbstractLegend) {
                JSONObject legendOpt = processLegend(ch);
                addAttribute(options, "legend", legendOpt);
            } else if (ch instanceof AbstractSeries) {
                JSONObject opts = processSeries(ch);
                seriesOptions.put(opts);
                
            } else if (ch instanceof AbstractCursor) {
                
            } else if (ch instanceof AbstractXaxis) {
                
            } else if (ch instanceof AbstractYaxis) {
                
            }
        }
        
        
        //output javascript intialization
        writer.write("new RichFaces.ui.Chart(\""+chart.getClientId()+"\",");
        writer.write(options.toString());
        writer.write("," + data.toString()+");");

    }

    protected JSONObject processLegend(UIComponent legend) {
        JSONObject legendOpt = new JSONObject();

        addAttribute(legendOpt, "show", true);
        addAttribute(legendOpt, "placement", legend.getAttributes().get("placement"));
        addAttribute(legendOpt, "position", legend.getAttributes().get("position"));
        return legendOpt;
    }

    protected JSONObject processSeries(UIComponent series) {
        
           ChartModel model = (ChartModel) series.getAttributes().get("value");
           data.put(model.toJsonCollection());
           
           JSONObject seriesOpt = new JSONObject();
           addAttribute(seriesOpt, "label", series.getAttributes().get("label"));
           addAttribute(seriesOpt, "showMarker", series.getAttributes().get("showMarker"));
           
           //marker properties
           JSONObject markerOpt = new JSONObject();
           addAttribute(markerOpt, "style", series.getAttributes().get("marker"));
           addAttribute(seriesOpt, "markerOptions", markerOpt);
           
           addAttribute(seriesOpt, "color", series.getAttributes().get("color"));
           //TODO dragable
           return seriesOpt;
           
        
    }
}
