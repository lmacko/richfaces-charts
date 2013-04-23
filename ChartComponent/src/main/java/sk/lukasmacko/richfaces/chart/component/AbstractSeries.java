package sk.lukasmacko.richfaces.chart.component;

import org.richfaces.cdk.annotations.*;
import sk.lukasmacko.richfaces.chart.component.model.ChartDataModel;


/**
 * Tag &lt;lm;series&gt; specifies chart data. Use value attribute
 * or nested &lt;lm:point&gt; tags to pass data.
 * 
 * @author Lukas Macko
 */
@JsfComponent(
        type = "sk.lukasmacko.richfaces.chart.component.Series",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="series"))
abstract public class AbstractSeries extends javax.faces.component.UIComponentBase {
    
    /**
     * 
     */
    @Attribute
    public abstract String getLabel();
    
    @Attribute(required=true,suggestedValue="ChartDataModel.ChartType.line,ChartDataModel.ChartType.bar,ChartDataModel.ChartType.pie")
    public abstract ChartDataModel.ChartType getType();
    
    /**
     * Data passed into chart. If attribute is null, nested &lt;lm:point&gt;
     * tags are expected.
     */
    @Attribute
    public abstract ChartDataModel getValue();
    
    /**
     * Color of series
     */
    @Attribute
    public abstract String getColor();
    
    
    
    /**
     * 
     *  
     */
    @Attribute(defaultValue="MarkerType.filledCircle",suggestedValue="MarkerType.circle,MarkerType.diamond,"
            + "MarkerType.square,MarkerType.x,MarkerType.plus,MarkerType.dash,"
            + "MarkerType.filledDiamond,MarkerType.filledCircle,MarkerType.filledSquare")
    public abstract MarkerType getMarker();
    
    /**
     * 
     * 
     */
    @Attribute(defaultValue="true")
    public abstract boolean isMarkerVisible();
    
    /**
     * Attribute enables to drag point of this series.
     */
    @Attribute(defaultValue="false")
    public abstract boolean isDragable();
    
    /**
     * Show linear trendline.
     */
    @Attribute(defaultValue="false")
    public abstract boolean isTrendlineVisible();
    
    /**
     * Restrict point dragging. Allowed values <b>x</b>,<b>y</b><b>none</b>.
     */
    @Attribute(defaultValue="ConstraintType.none",suggestedValue="ConstraintType.x,ConstraintType.y,ConstraintType.none") 
    public abstract ConstraintType getDragableConstraint();
    
    
    
    public enum MarkerType{
        diamond, circle, square, x, plus, dash, filledDiamond, filledCircle, filledSquare
    }
    
    public enum ConstraintType{
        x,y,none
    }
    
}
