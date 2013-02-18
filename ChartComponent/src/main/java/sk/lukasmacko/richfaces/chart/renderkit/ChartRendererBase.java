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
import sk.lukasmacko.richfaces.chart.component.model.BarChartModel;
import sk.lukasmacko.richfaces.chart.component.model.ChartModel;
import sk.lukasmacko.richfaces.chart.component.model.LineChartModel;
import sk.lukasmacko.richfaces.chart.component.model.PieChartModel;
import sk.lukasmacko.richfaces.chart.component.model.RawJSONString;

/**
 *
 * @author Macko
 */
public abstract class ChartRendererBase extends RendererBase {

    private JSONObject options;
    
    private JSONArray data;
    private ChartModel.ChartType chartType;
    
    /**
     * Stores category names for bar and pie chart
     */
    private List<String> keys;

    public static JSONObject addAttribute(JSONObject obj, String key, Object value) {
        try {
            obj.put(key, value);
        } catch (JSONException ex) {
            Logger.getLogger(ChartRendererBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }

    /**
     * Method sets the type of processed chart. It checks if combination of
     * series type is allowed.
     *
     * @throws IllegalStateException if unsupported combination used
     *
     * @param type -processed series type
     */
    private void setChartType(ChartModel.ChartType type) {

        if (chartType == ChartModel.ChartType.unknown) {
            chartType = type;
        } else if (chartType != type) {
            throw new IllegalStateException("Combination of " + chartType + " and " + type + " is not supported");
        }
    }

    /**
     * Process nested tags and writes javascript function to initialize a chart.
     *
     * @param context
     * @param component
     * @throws IOException
     */
    protected void initChart(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        AbstractChart chart = (AbstractChart) component;
        chartType = ChartModel.ChartType.unknown;
        keys = null;
        options = new JSONObject();

        //chart options
        addAttribute(options, "title", component.getAttributes().get("title"));

        //series properties
        JSONArray seriesOptions = new JSONArray();
        addAttribute(options, "series", seriesOptions);

        data = new JSONArray();

        //axis properties
        JSONObject axisOptions = new JSONObject();
        addAttribute(options, "axes", axisOptions);


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
                JSONObject cursorOpt = processCursor(ch);
                addAttribute(options, "cursor", cursorOpt);
            } else if (ch instanceof AbstractXaxis) {
                JSONObject xaxisOpt = processAxis(ch);
                addAttribute(axisOptions, "xaxis", xaxisOpt);
            } else if (ch instanceof AbstractYaxis) {
                JSONObject yaxisOpt = processAxis(ch);
                addAttribute(axisOptions, "yaxis", yaxisOpt);
            }
        }

        
        //bar chart - category labels(ticks) must be part of xaxis options
        if (chartType == ChartModel.ChartType.bar) {

            if (axisOptions.has("xaxis")) {
                try {
                    ((JSONObject) axisOptions.get("xaxis")).put("ticks", keys);
                } catch (JSONException e) {
                }
            } else {
                JSONObject xaxisOpt = new JSONObject();

                
                JSONArray ticksJSON = new JSONArray();
                for (String s : keys) {
                    ticksJSON.put(s);
                }

                addAttribute(xaxisOpt, "ticks", ticksJSON);
                addAttribute(xaxisOpt, "renderer", new RawJSONString("$.jqplot.CategoryAxisRenderer"));
                addAttribute(axisOptions, "xaxis", xaxisOpt);
            }
        }

        //output javascript intialization
        writer.write("new RichFaces.ui.Chart(\"" + chart.getClientId() + "\",");
        writer.write(options.toString());
        writer.write("," + data.toString() + ");");

    }

    protected JSONObject processLegend(UIComponent legend) {
        JSONObject legendOpt = new JSONObject();

        addAttribute(legendOpt, "show", true);
        addAttribute(legendOpt, "placement", legend.getAttributes().get("placement"));
        addAttribute(legendOpt, "location", AbstractLegend.positionMap.get((AbstractLegend.PositionType) legend.getAttributes().get("position")));
        return legendOpt;
    }

    protected JSONObject processSeries(UIComponent series) {
        JSONObject seriesOpt = new JSONObject();
        JSONObject rendererOpt = new JSONObject();

        ChartModel model = (ChartModel) series.getAttributes().get("value");

        //process type dependent attributes
        switch ((AbstractSeries.SeriesType) series.getAttributes().get("type")) {
            case bar:
                if (!(model instanceof BarChartModel)) {
                    throw new UnsupportedOperationException("Bar chart requieres BarChartModel.");
                }
                //addAttribute(seriesOpt, "renderer", "bar");
                addAttribute(seriesOpt, "renderer", new RawJSONString("$.jqplot.BarRenderer"));
                addAttribute(rendererOpt,"fillToZero", true);
                addAttribute(seriesOpt, "rendererOptions", rendererOpt);
                
                //first series determine output categories
                if (keys == null) {
                    BarChartModel barmodel;
                    barmodel = (BarChartModel) series.getAttributes().get("value");
                    if(barmodel.getOutputKeys()!=null){
                        keys= barmodel.getOutputKeys();
                    }
                    else{
                        keys = barmodel.getKeys();
                    }
                } else {
                    
                    ((BarChartModel) model).setOutputKeys(keys);
                }

                break;
            case line:
                if (!(model instanceof LineChartModel)) {
                    throw new UnsupportedOperationException("Line chart requieres LineChartModel.");
                }
                //marker properties
                JSONObject markerOpt = new JSONObject();
                addAttribute(markerOpt, "style", series.getAttributes().get("marker"));
                addAttribute(seriesOpt, "markerOptions", markerOpt);
                addAttribute(seriesOpt, "showMarker", series.getAttributes().get("showMarker"));

                break;
            case pie:
                if (!(model instanceof PieChartModel)) {
                    throw new UnsupportedOperationException("Pie chart requieres PieChartModel.");
                }
                //property render will be overwriten in javascript richfaces.chart.js
                addAttribute(seriesOpt, "renderer", new RawJSONString("$.jqplot.PieRenderer"));
                addAttribute(rendererOpt, "showDataLabels", true);
                addAttribute(seriesOpt, "rendererOptions", rendererOpt);

                break;

        }

        setChartType(model.getChartType());
        data.put(model.toJsonCollection());


        //attributes for all chart types
        addAttribute(seriesOpt, "label", series.getAttributes().get("label"));
        addAttribute(seriesOpt, "color", series.getAttributes().get("color"));
        addAttribute(seriesOpt, "isDragable", series.getAttributes().get("dragable"));

        JSONObject trendlineOpt = new JSONObject();
        addAttribute(trendlineOpt, "show", series.getAttributes().get("trendlineVisible"));
        addAttribute(seriesOpt, "trendline", trendlineOpt);
        
        return seriesOpt;


    }

    protected JSONObject processAxis(UIComponent axis) {
        JSONObject axisOpt = new JSONObject();
        addAttribute(axisOpt, "min", axis.getAttributes().get("min"));
        addAttribute(axisOpt, "max", axis.getAttributes().get("max"));
        addAttribute(axisOpt, "label", axis.getAttributes().get("label"));

        //TODO format and tick rotation

        return axisOpt;
    }

    protected JSONObject processCursor(UIComponent cursor) {
        JSONObject cursorOpt = new JSONObject();
        addAttribute(cursorOpt, "zoom", cursor.getAttributes().get("zoomEn"));
        addAttribute(cursorOpt, "constrainZoomTo", cursor.getAttributes().get("constraintZoom"));
        addAttribute(cursorOpt, "show", true);
        return cursorOpt;
    }
}
