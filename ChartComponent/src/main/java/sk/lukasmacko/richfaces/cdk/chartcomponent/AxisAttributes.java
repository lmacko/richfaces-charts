/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.lukasmacko.richfaces.cdk.chartcomponent;

import org.richfaces.cdk.annotations.Attribute;

/**
 *
 * @author Macko
 */
public interface AxisAttributes {
    
    @Attribute
    String getFormat();
    
    @Attribute
    String getLabel();
    
    @Attribute
    Double getMin();
    
    @Attribute
    Double getMax();
    
    @Attribute
    Integer getTickRotation();
    
}
