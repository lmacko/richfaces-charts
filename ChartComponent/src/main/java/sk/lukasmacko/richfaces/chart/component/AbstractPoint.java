package sk.lukasmacko.richfaces.chart.component;

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
    public abstract Object getX();
    
    @Attribute(required=true)
    public abstract Number getY();
}
