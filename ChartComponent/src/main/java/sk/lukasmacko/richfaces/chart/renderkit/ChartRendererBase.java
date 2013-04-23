package sk.lukasmacko.richfaces.chart.renderkit;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitContext;
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
import sk.lukasmacko.richfaces.chart.component.AxisAttributes;
import sk.lukasmacko.richfaces.chart.component.event.DataClickEvent;
import sk.lukasmacko.richfaces.chart.component.event.DragStopEvent;
import sk.lukasmacko.richfaces.chart.component.model.ChartDataModel;
import sk.lukasmacko.richfaces.chart.component.model.RawJSONString;

/**
 *
 * @author Macko
 */
public abstract class ChartRendererBase extends RendererBase {

    private JSONObject options;
    private JSONArray data;
    private JSONArray seriesOptions;
    private JSONObject axisOptions;
    private ChartDataModel.ChartType chartType;
    private Class classType;
    /**
     * Following properties identifier HTTP request parameters
     *
     */
    private static final String X_VALUE = "x";
    private static final String Y_VALUE = "y";
    private static final String POINT_INDEX = "pointIndex";
    private static final String SERIES_INDEX = "seriesIndex";
    private static final String EVENT_TYPE = "eventType";
    private static final String DATA_CLICK_TYPE = "dataClick";
    private static final String DRAG_STOP_TYPE = "dragStop";
    /**
     * Stores category names for bar and pie chart
     */
    private List<String> keys;

    /**
     * Method adds key-value pair to object.
     *
     * @param obj
     * @param key
     * @param value
     * @return
     * @throws IOException if put to JSONObject fails
     */
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
    private void setChartType(ChartDataModel model) {

        if (chartType == ChartDataModel.ChartType.unknown) {
            classType = model.getKeyType();
            chartType = model.getChartType();
        } else if (chartType != model.getChartType()) {
            throw new IllegalStateException("Use of the same charType parameter in ChartDataModels required.");
        } else if (classType != model.getKeyType()) {
            throw new IllegalStateException("Use of the same type parameter in ChartDataModels required.");
        }
    }

    /**
     *
     * @param context
     * @param component
     * @throws IOException
     */
    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        super.encodeBegin(context, component);


        chartType = ChartDataModel.ChartType.unknown;
        keys = null;
        options = new JSONObject();

        //chart options
        addAttribute(options, "title", ((AbstractChart) component).getTitle());

        //series properties
        seriesOptions = new JSONArray();
        addAttribute(options, "series", seriesOptions);

        data = new JSONArray();

        //axis properties
        axisOptions = new JSONObject();
        addAttribute(options, "axes", axisOptions);

    }

    /**
     * Process nested tags
     *
     * @param context
     * @param component
     * @throws IOException
     */
    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        super.encodeChildren(context, component);
        AbstractChart chart = (AbstractChart) component;
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
        if (chartType == ChartDataModel.ChartType.bar && classType != Number.class) {

            if (axisOptions.has("xaxis")) {

                try {
                    ((JSONObject) axisOptions.get("xaxis")).put("ticks", keys);
                } catch (JSONException e) {
                    throw new IOException("An error occured during processing options" + e);
                }
            } else {
                JSONObject xaxisOpt = new JSONObject();


                JSONArray ticksJSON = new JSONArray();

                for (String key : keys) {
                    ticksJSON.put(key);
                }



                addAttribute(xaxisOpt, "ticks", ticksJSON);
                addAttribute(xaxisOpt, "renderer", new RawJSONString("$.jqplot.CategoryAxisRenderer"));
                addAttribute(axisOptions, "xaxis", xaxisOpt);
            }
        } else if (chartType == ChartDataModel.ChartType.pie) {
            //if there are more than one series in pie chart
            //change rednerer to donut
            if (seriesOptions.length() > 1) {
                for (int i = 0; i < seriesOptions.length(); i++) {
                    try {
                        JSONObject s = (JSONObject) seriesOptions.get(i);
                        s.remove("renderer");
                        addAttribute(s, "renderer", new RawJSONString("$.jqplot.DonutRenderer"));
                        ((JSONObject)s.get("rendererOptions")).put("sliceMargin", 3);
                    } catch (JSONException ex) {
                        throw new IOException("");
                    }
                }
            }

        }
        if (classType == Date.class) {
            if (axisOptions.has("xaxis")) {

                try {
                    ((JSONObject) axisOptions.get("xaxis")).put("renderer", new RawJSONString("$.jqplot.DateAxisRenderer"));
                } catch (JSONException e) {
                    throw new IOException("An error occured during processing options" + e);
                }
            } else {
                JSONObject xaxisOpt = new JSONObject();
                addAttribute(xaxisOpt, "renderer", new RawJSONString("$.jqplot.DateAxisRenderer"));
                addAttribute(axisOptions, "xaxis", xaxisOpt);
            }
        }
        addAttribute(options, "chartType", chartType);
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    /**
     * Process legend tag and its attributes
     *
     * @param legend
     * @return
     * @throws IOException
     */
    protected JSONObject processLegend(UIComponent legend) throws IOException {
        AbstractLegend l = (AbstractLegend) legend;
        JSONObject legendOpt = new JSONObject();

        addAttribute(legendOpt, "show", l.isVisible());
        addAttribute(legendOpt, "placement", l.getPlacement());
        addAttribute(legendOpt, "location", AbstractLegend.positionMap.get((AbstractLegend.PositionType) l.getPosition()));
        return legendOpt;
    }

    /**
     * Process series tag and its attributes
     *
     * @param series
     * @return
     * @throws IOException
     */
    protected JSONObject processSeries(UIComponent series) throws IOException {
        JSONObject seriesOpt = new JSONObject();
        JSONObject rendererOpt = new JSONObject();
        JSONObject dragableOpt = new JSONObject();

        AbstractSeries s = (AbstractSeries) series;

        ChartDataModel model = (ChartDataModel) s.getValue();

        if (model == null) {
            VisitPointCallback callback = new VisitPointCallback(s.getType());
            series.visitTree(VisitContext.createVisitContext(
                    FacesContext.getCurrentInstance()),
                    callback);
            if (callback.getModel() != null && !callback.getModel().getData().isEmpty()) {
                model = callback.getModel();
            }
        }

        if (model == null) {
            throw new IllegalStateException("Series has no data");
        }

        setChartType(model);



        //process type dependent attributes
        switch (s.getType()) {
            case bar:
                if (model.getChartType() != ChartDataModel.ChartType.bar) {
                    throw new UnsupportedOperationException("Bar chart requieres proper chartType in ChartDataModel.");
                }

                addAttribute(seriesOpt, "renderer", new RawJSONString("$.jqplot.BarRenderer"));
                addAttribute(rendererOpt, "fillToZero", true);
                addAttribute(seriesOpt, "rendererOptions", rendererOpt);

                //first series determine output categories
                if (keys == null) {
                    if (model.getOutputKeys() != null) {
                        keys = model.getOutputKeys();
                    } else {
                        keys = model.getKeys();
                    }
                } else {
                    model.setOutputKeys(keys);
                }

                break;
            case line:
                if (model.getChartType() != ChartDataModel.ChartType.line) {
                    throw new UnsupportedOperationException("ChartDataModel requieres proper chartType.");
                }
                //marker properties
                JSONObject markerOpt = new JSONObject();
                addAttribute(markerOpt, "style", s.getMarker());
                addAttribute(seriesOpt, "markerOptions", markerOpt);
                addAttribute(seriesOpt, "showMarker", s.isMarkerVisible());
                addAttribute(seriesOpt, "highlightMouseOver", true);
                break;
            case pie:
                if (model.getChartType() != ChartDataModel.ChartType.pie) {
                    throw new UnsupportedOperationException("Pie chart requieres PieChartModel.");
                }
                addAttribute(seriesOpt, "renderer", new RawJSONString("$.jqplot.PieRenderer"));
                addAttribute(rendererOpt, "showDataLabels", true);
                addAttribute(seriesOpt, "rendererOptions", rendererOpt);

                break;

        }

        data.put(model.toJson());

        //attributes for all chart types
        addAttribute(seriesOpt, "label", s.getLabel());
        addAttribute(seriesOpt, "color", s.getColor());
        addAttribute(seriesOpt, "isDragable", s.isDragable());
        addAttribute(dragableOpt, "constrainTo", s.getDragableConstraint());
        addAttribute(dragableOpt, "color", new RawJSONString("undefined"));
        addAttribute(seriesOpt, "dragable", dragableOpt);

        JSONObject trendlineOpt = new JSONObject();
        addAttribute(trendlineOpt, "show", s.isTrendlineVisible());
        addAttribute(seriesOpt, "trendline", trendlineOpt);

        return seriesOpt;


    }

    /**
     * Process xaxis and yaxis tag
     *
     * @param axis
     * @return
     * @throws IOException
     */
    protected JSONObject processAxis(UIComponent ax) throws IOException {
        AxisAttributes axis = (AxisAttributes) ax;
        JSONObject axisOpt = new JSONObject();
        addAttribute(axisOpt, "min", axis.getMin());
        addAttribute(axisOpt, "max", axis.getMax());
        addAttribute(axisOpt, "pad", axis.getPad());
        addAttribute(axisOpt, "label", axis.getPad());
        JSONObject tickOpt = new JSONObject();
        if (axis.getTickRotation() != null) {
            addAttribute(axisOpt, "tickRenderer", new RawJSONString("$.jqplot.CanvasAxisTickRenderer"));
            addAttribute(tickOpt, "angle", axis.getTickRotation());
        }
        addAttribute(tickOpt, "formatString", axis.getFormat());
        addAttribute(axisOpt, "tickOptions", tickOpt);

        return axisOpt;
    }

    /**
     * Process cursor tag
     *
     * @param cursor
     * @return
     * @throws IOException
     */
    protected JSONObject processCursor(UIComponent cursor) throws IOException {
        AbstractCursor c = (AbstractCursor) cursor;
        JSONObject cursorOpt = new JSONObject();
        addAttribute(cursorOpt, "style", c.getCursorStyle());
        addAttribute(cursorOpt, "showTooltip", c.isTooltipVisible());
        addAttribute(cursorOpt, "followMouse", true);
        addAttribute(cursorOpt, "zoom", c.isZoomEn());
        addAttribute(cursorOpt, "constrainZoomTo", c.getConstraintZoom());
        addAttribute(cursorOpt, "show", true);
        return cursorOpt;
    }

    /**
     * Method process request parameters and creates events.
     *
     * @param context
     * @param component
     */
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
                } else if (DRAG_STOP_TYPE.equals(eventTypeParam)) {
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
     * Creates function for server-side event processing. Function is passed to
     * client javascript. It creates HTTP request to server when event occurs.
     *
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

        return new JSFunctionDefinition("event", EVENT_TYPE, SERIES_INDEX, POINT_INDEX,
                X_VALUE, Y_VALUE).addToBody(ajaxFunction);
    }

    /**
     * Method creates unique identifier for request parameter.
     *
     * @param component
     * @param attribute
     * @return
     */
    protected String getFieldId(UIComponent component, String attribute) {
        return component.getClientId() + "-" + attribute;
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
    /**
     * Create js var name for chart
     */
    public String getJsVar(UIComponent component,FacesContext context){
        return component.getClientId().replace(":", "_");
    }
}
