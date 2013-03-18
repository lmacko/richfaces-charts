/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.lukasmacko.richfaces.chart.component;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import org.richfaces.cdk.annotations.Attribute;
import org.richfaces.cdk.annotations.JsfComponent;
import org.richfaces.cdk.annotations.Tag;

/**
 *
 * @author Macko
 */
@JsfComponent(
        type="sk.lukasmacko.richfaces.chart.component.Point",
        family="sk.lukasmacko.Chart",
        tag =@Tag(name="point"))
public abstract class AbstractPoint extends UIComponentBase{
    
    @Attribute(required=true)
    public abstract double getX();
    
    @Attribute(required=true)
    public abstract Number getY();
}
