package sk.lukasmacko.richfaces.chart.component;

import org.richfaces.cdk.annotations.*;
import sk.lukasmacko.richfaces.chart.component.model.ChartDataModel;



@JsfComponent(
        type = "sk.lukasmacko.richfaces.chart.component.Series",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="series"))
abstract public class AbstractSeries extends javax.faces.component.UIComponentBase {
    @Attribute
    public abstract String getLabel();
    
    @Attribute(required=true,suggestedValue="ChartDataModel.ChartType.line,ChartDataModel.ChartType.bar,ChartDataModel.ChartType.pie")
    public abstract ChartDataModel.ChartType getType();
    
    @Attribute()
    public abstract ChartDataModel getValue();
        
    @Attribute
    public abstract String getColor();
    
    @Attribute(defaultValue="MarkerType.filledCircle",suggestedValue="MarkerType.circle,MarkerType.diamond,"
            + "MarkerType.square,MarkerType.x,MarkerType.plus,MarkerType.dash,"
            + "MarkerType.filledDiamond,MarkerType.filledCircle,MarkerType.filledSquare")
    public abstract MarkerType getMarker();
    
    @Attribute(defaultValue="true")
    public abstract boolean isMarkerVisible();
    
    @Attribute(defaultValue="false")
    public abstract boolean isDragable();
    
    @Attribute(defaultValue="false")
    public abstract boolean isTrendlineVisible();
    
    @Attribute(defaultValue="ConstraintType.none",suggestedValue="ConstraintType.x,ConstraintType.y,ConstraintType.none") 
    public abstract ConstraintType getDragableConstraint();
    
    
    
    public enum MarkerType{
        diamond, circle, square, x, plus, dash, filledDiamond, filledCircle, filledSquare
    }
    
    public enum ConstraintType{
        x,y,none
    }
    
}
