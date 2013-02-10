package sk.lukasmacko.richfaces.chart.component;

import org.richfaces.cdk.annotations.*;
import sk.lukasmacko.richfaces.chart.component.model.ChartModel;
import sk.lukasmacko.richfaces.chart.component.model.LineChartModel;



@JsfComponent(
        type = "sk.lukasmacko.richfaces.chartcomponent.Series",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="series"))
abstract public class AbstractSeries extends javax.faces.component.UIComponentBase {
    @Attribute
    public abstract String getLabel();
    
    @Attribute(required=true)
    public abstract SeriesType getType();
    
    @Attribute(required=true)
    public abstract ChartModel getValue();
        
    @Attribute
    public abstract String getColor();
    
    @Attribute
    public abstract String getMarker();
    
    @Attribute
    public abstract boolean isMarkerVisible();
    
    @Attribute
    public abstract boolean isDragable();
    
    @Attribute 
    public abstract String getDragableConstraint();
    
    public enum SeriesType{
        line,bar,pie
    }
    
}
