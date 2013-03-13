package sk.lukasmacko.richfaces.chart.component;

import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
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
        fires = {
    @Event(value = DataClickEvent.class, listener = DataClickListener.class)}
)
abstract public class AbstractChart extends javax.faces.component.UIComponentBase {

    @Attribute
    public abstract String getTitle();

    @Attribute
    public abstract String getStyleClass();

    @Attribute(events =
    @EventName("dataclick"))
    public abstract String getOndataclick();

    @Attribute(events =
    @EventName("highlight"))
    public abstract String getOnhighlight();
    
    @Attribute(events =
    @EventName("unhighlight"))
    public abstract String getOnunhighlight();
    
    @Attribute(events =
    @EventName("dragstop"))
    public abstract String getOndragstop();
    
    @Attribute(events =
    @EventName("dragstart"))
    public abstract String getOndragstart();
    
    
    @Attribute(events =
    @EventName("pointmove"))
    public abstract String getOnpointmove();
    

    @Attribute(signature =
    @Signature(parameters = DataClickEvent.class))
    public abstract MethodExpression getDataClickListener();
    
    @Override
    public void broadcast(FacesEvent event) throws AbortProcessingException {
        
           
        if (event instanceof DataClickEvent) {
            FacesContext facesContext = getFacesContext();
            MethodExpression expression = getDataClickListener();
            if (expression != null) {
                expression.invoke(facesContext.getELContext(), new Object[]{event});
            }
            super.broadcast(event);
         
        }
        else{
            super.broadcast(event);
        }
    }
    
    

}
