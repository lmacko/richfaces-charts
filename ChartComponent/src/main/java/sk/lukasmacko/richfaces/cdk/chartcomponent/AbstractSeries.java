package sk.lukasmacko.richfaces.cdk.chartcomponent;

import org.richfaces.cdk.annotations.*;

/**
 * A trivial hello world component
 */

@JsfComponent(
        type = "sk.lukasmacko.richfaces.chartcomponent.Series",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="series"))
abstract public class AbstractSeries extends javax.faces.component.UIComponentBase {
    @Attribute
    public abstract String getLabel();
    
    @Attribute
    public abstract String getType();
    
    @Attribute
    public abstract Object getValue();
    
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
    
    
}
