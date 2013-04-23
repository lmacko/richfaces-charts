package sk.lukasmacko.richfaces.chart.component;

import org.richfaces.cdk.annotations.Attribute;

/**
 * 
 * @author Lukas Macko
 */
public interface AxisAttributes {
    
    /**
     * 
     * Format for axis ticks
     */
    @Attribute
    public abstract String getFormat();
    
    /*
     * Axis description
     */
    @Attribute
    public abstract String getLabel();
    
    /**
     * Minimum value of the axis
     */
    @Attribute
    public abstract Double getMin();
    
    /**
     * Maximum value of the axis
     */
    @Attribute
    public abstract Double getMax();
    
    /*
     * A factor multiplied by the data range
     * on the axis to determine min and max. 
     */
    @Attribute
    public abstract Double getPad();
    
    /**
     * Axis ticks are rotated by attribute value(clockwise).
     */
    @Attribute
    public abstract Integer getTickRotation();
    
}
