package sk.lukasmacko.richfaces.chart.component;

import javax.el.MethodExpression;
import org.richfaces.cdk.annotations.*;
import sk.lukasmacko.richfaces.chart.component.event.DataClickEvent;
import sk.lukasmacko.richfaces.chart.component.event.DataClickListener;

@JsfComponent(
        type = "sk.lukasmacko.richfaces.chart.component.Chart",
family = "sk.lukasmacko.Chart",
renderer =
@JsfRenderer(type = "sk.lukasmacko.chartRenderer"),
tag =
@Tag(name = "chart"),
fires = {@Event(value = DataClickEvent.class, listener = DataClickListener.class)})
abstract public class AbstractChart extends javax.faces.component.UIComponentBase {

    @Attribute
    public abstract String getTitle();

    @Attribute
    public abstract String getStyleClass();
    
    @Attribute(events= @EventName("dataclick"))
    public abstract String getOndataclick();
    
    @Attribute(events= @EventName("mouseover"))
    public abstract String getOnmouseover();
    
    
    
    @Attribute(signature = @Signature(parameters = DataClickEvent.class))
    public abstract MethodExpression getDataClickListener();
    
    
}
