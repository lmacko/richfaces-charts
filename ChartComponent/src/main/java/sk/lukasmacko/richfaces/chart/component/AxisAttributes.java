package sk.lukasmacko.richfaces.chart.component;

import org.richfaces.cdk.annotations.Attribute;

/**
 * 
 * @author Macko
 */
public interface AxisAttributes {
    
    @Attribute
    public abstract String getFormat();
    
    @Attribute
    public abstract String getLabel();
    
    @Attribute
    public abstract Double getMin();
    
    @Attribute
    public abstract Double getMax();
    
    @Attribute
    public abstract Double getPad();
    
    @Attribute
    public abstract Integer getTickRotation();
    
}
