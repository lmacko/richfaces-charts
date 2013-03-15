package sk.lukasmacko.richfaces.chart.renderkit;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSReference;
import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;
import org.richfaces.renderkit.AjaxFunction;
import org.richfaces.renderkit.RendererBase;
import org.richfaces.renderkit.util.AjaxRendererUtils;
import sk.lukasmacko.richfaces.chart.component.AbstractChart;
import sk.lukasmacko.richfaces.chart.component.AbstractCursor;
import sk.lukasmacko.richfaces.chart.component.AbstractLegend;
import sk.lukasmacko.richfaces.chart.component.AbstractSeries;
import sk.lukasmacko.richfaces.chart.component.AbstractXaxis;
import sk.lukasmacko.richfaces.chart.component.AbstractYaxis;
import sk.lukasmacko.richfaces.chart.component.event.DataClickEvent;
import sk.lukasmacko.richfaces.chart.component.event.DragStopEvent;
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
    
    private static final String X_VALUE="x";
    private static final String Y_VALUE="y";
    private static final String POINT_INDEX="pointIndex";
    private static final String SERIES_INDEX="seriesIndex";
    private static final String EVENT_TYPE="eventType";
    
    private static final String DATA_CLICK_TYPE="dataClick";
    private static final String DRAG_STOP_TYPE="dragStop";
    /**
     * Stores category names for bar and pie chart
     */
    private List<String> keys;

    public static JSONObject addAttribute(JSONObject obj, String key, Object value) throws IOException {
        try {
            obj.put(key, value);
        } catch (JSONException ex) {
            throw new IOException("JSONObject put failed.");
        }
        return obj;
    }

    /**
     * Method sets the type of processed chart. It checks if combination of
     * series type is allowed.
     *
     * @throws IllegalStateException if unsupported combination occurs
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
     * Process nested tags
     *
     * @param context
     * @param component
     * @throws IOException
     */
    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        
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
        addAttribute(options, "chartType", chartType);

    }
    
    /**
     * Process legend tag and its attributes 
     * @param legend
     * @return
     * @throws IOException 
     */
    protected JSONObject processLegend(UIComponent legend) throws IOException {
        JSONObject legendOpt = new JSONObject();

        addAttribute(legendOpt, "show", true);
        addAttribute(legendOpt, "placement", legend.getAttributes().get("placement"));
        addAttribute(legendOpt, "location", AbstractLegend.positionMap.get((AbstractLegend.PositionType) legend.getAttributes().get("position")));
        return legendOpt;
    }

    /**
     * Process series tag and its attributes
     * @param series
     * @return
     * @throws IOException 
     */
    protected JSONObject processSeries(UIComponent series) throws IOException {
        JSONObject seriesOpt = new JSONObject();
        JSONObject rendererOpt = new JSONObject();
        JSONObject dragableOpt = new JSONObject();

        ChartModel model = (ChartModel) series.getAttributes().get("value");

        //process type dependent attributes
        switch ((AbstractSeries.SeriesType) series.getAttributes().get("type")) {
            case bar:
                if (!(model instanceof BarChartModel)) {
                    throw new UnsupportedOperationException("Bar chart requieres BarChartModel.");
                }

                addAttribute(seriesOpt, "renderer", new RawJSONString("$.jqplot.BarRenderer"));
                addAttribute(rendererOpt, "fillToZero", true);
                addAttribute(seriesOpt, "rendererOptions", rendererOpt);

                //first series determine output categories
                if (keys == null) {
                    BarChartModel barmodel;
                    barmodel = (BarChartModel) series.getAttributes().get("value");
                    if (barmodel.getOutputKeys() != null) {
                        keys = barmodel.getOutputKeys();
                    } else {
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
                addAttribute(seriesOpt, "highlightMouseOver", true);
                break;
            case pie:
                if (!(model instanceof PieChartModel)) {
                    throw new UnsupportedOperationException("Pie chart requieres PieChartModel.");
                }
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
        addAttribute(dragableOpt, "constrainTo", series.getAttributes().get("dragableConstraint"));
        addAttribute(dragableOpt, "color", new RawJSONString("undefined"));
        addAttribute(seriesOpt, "dragable", dragableOpt);

        JSONObject trendlineOpt = new JSONObject();
        addAttribute(trendlineOpt, "show", series.getAttributes().get("trendlineVisible"));
        addAttribute(seriesOpt, "trendline", trendlineOpt);

        return seriesOpt;


    }

    /**
     * Process xaxis and yaxis tag 
     * @param axis
     * @return
     * @throws IOException 
     */
    protected JSONObject processAxis(UIComponent axis) throws IOException {
        JSONObject axisOpt = new JSONObject();
        addAttribute(axisOpt, "min", axis.getAttributes().get("min"));
        addAttribute(axisOpt, "max", axis.getAttributes().get("max"));
        addAttribute(axisOpt, "label", axis.getAttributes().get("label"));
       //TODO format and tick rotation

        return axisOpt;
    }

    /**
     * Process cursor tag
     * @param cursor
     * @return
     * @throws IOException 
     */
    protected JSONObject processCursor(UIComponent cursor) throws IOException {
        JSONObject cursorOpt = new JSONObject();
        addAttribute(cursorOpt, "zoom", cursor.getAttributes().get("zoomEn"));
        addAttribute(cursorOpt, "constrainZoomTo", cursor.getAttributes().get("constraintZoom"));
        addAttribute(cursorOpt, "show", true);
        return cursorOpt;
    }
    
    /**
     * 
     * @return chart data 
     */
    public String getData() {
        return data.toString();
    }

    /**
     * 
     * @return 
     */
    public String getOptions() {
        return options.toString();
    }

    @Override
    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);

        if (!component.isRendered()) {
            return;
        }
        
        Map<String, String> requestParameterMap = context.getExternalContext().getRequestParameterMap();
        if (requestParameterMap.get(component.getClientId(context)) != null) {
            String yParam = requestParameterMap.get(getFieldId(component, X_VALUE));
            String xParam = requestParameterMap.get(getFieldId(component, Y_VALUE));
            String pointIndexParam = requestParameterMap.get(getFieldId(component, POINT_INDEX));
            String eventTypeParam = requestParameterMap.get(getFieldId(component, EVENT_TYPE));
            String seriesIndexParam = requestParameterMap.get(getFieldId(component, SERIES_INDEX));
            try {
                
                if (DATA_CLICK_TYPE.equals(eventTypeParam)) {
                    double y = Double.parseDouble(yParam);
                    int seriesIndex = Integer.parseInt(seriesIndexParam);
                    int pointIndex = Integer.parseInt(pointIndexParam);
                    String x = xParam;
                    new DataClickEvent(component, seriesIndex, pointIndex, x, y).queue();
                } 
                else if(DRAG_STOP_TYPE.equals(eventTypeParam)){
                    double y = Double.parseDouble(yParam);
                    int seriesIndex = Integer.parseInt(seriesIndexParam);
                    int pointIndex = Integer.parseInt(pointIndexParam);
                    String x = xParam;
                    new DragStopEvent(component, seriesIndex, pointIndex, x, y).queue();
                }
            } catch (NumberFormatException ex) {
                throw new FacesException("Cannot convert request parmeters", ex);
            }
        }
    }
    
    /**
     * Creates function for server-side event processing
     * @param context
     * @param component
     * @return 
     */
    protected Object createSubmitEventFunction(FacesContext context, UIComponent component) {
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(getFieldId(component, SERIES_INDEX), new JSReference(SERIES_INDEX));
        params.put(getFieldId(component, POINT_INDEX), new JSReference(POINT_INDEX));
        params.put(getFieldId(component, X_VALUE), new JSReference(X_VALUE));
        params.put(getFieldId(component, Y_VALUE), new JSReference(Y_VALUE));
        params.put(getFieldId(component, EVENT_TYPE), new JSReference(EVENT_TYPE));
        String clientId = component.getClientId();
        params.put(clientId, clientId);
        
        AjaxFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(context, component);
        ajaxFunction.getOptions().getParameters().putAll(params);
        //ajaxFunction.getOptions().set("complete", new JSReference(CALLBACK));
        
        
        return new JSFunctionDefinition("event", EVENT_TYPE, SERIES_INDEX, POINT_INDEX,
            X_VALUE, Y_VALUE).addToBody(ajaxFunction);
    }

    /**
     * 
     * @param context
     * @param component
     * @param attribute
     * @return 
     */
    protected String getFieldId(UIComponent component, String attribute) {
        return component.getClientId() + "-" + attribute;
    }
}
