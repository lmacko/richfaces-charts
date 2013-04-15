package sk.lukasmacko.richfaces.chart.component;

import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import org.richfaces.cdk.annotations.*;
import sk.lukasmacko.richfaces.chart.component.event.DataClickEvent;
import sk.lukasmacko.richfaces.chart.component.event.DataClickListener;
import sk.lukasmacko.richfaces.chart.component.event.DragStopEvent;

/**
 * <p>The &lt;lm:chart&gt; support different chart types.
 * This tag sets title, css class, javascript event handlers
 * and server-side listeners. Use nested tags  &lt;lm:legend&gt;,
 * &lt;lm:cursor&gt;, &lt;lm:xaxis&gt;, &lt;lm:yaxis&gt; for chart customization.
 * Use &lt;lm:series&gt; to pass data</p>
 * @author Lukas Macko
 */
@JsfComponent(
        type = "sk.lukasmacko.richfaces.chart.component.Chart",
        family = "sk.lukasmacko.Chart",
        renderer = @JsfRenderer(type = "sk.lukasmacko.chartRenderer"),
        tag = @Tag(name = "chart",handler = "sk.lukasmacko.richfaces.facelets.html.ChartTagHandler",
            generate = true,
            type = TagType.Facelets),
        fires = {
            @Event(value = DataClickEvent.class, listener = DataClickListener.class)}
)
abstract public class AbstractChart extends javax.faces.component.UIComponentBase{

    /**
     * Text shown above the chart 
     */
    @Attribute
    public abstract String getTitle();

    /**
     * assign css class  
     */
    @Attribute
    public abstract String getStyleClass();

    /**
     * Javascript handler for dataclick event.
     * An event object is passed to handler.
     * 
     * event.data.seriesIndex
     * event.data.pointIndex
     * event.data.x
     * event.data.y
     *  
     */
    @Attribute(events =
    @EventName("dataclick"))
    public abstract String getOndataclick();

    /**
     * Javascript handler for highlight event.
     * An event object is passed to handler.
     * 
     * event.data.seriesIndex
     * event.data.pointIndex
     * event.data.x
     * event.data.y
     *  
     */
    @Attribute(events =
    @EventName("highlight"))
    public abstract String getOnhighlight();
    
    /**
     * Javascript handler for unhilight event.
     *  
     */
    @Attribute(events =
    @EventName("unhighlight"))
    public abstract String getOnunhighlight();
    
    /**
     * Javascript handler for dragstop event.
     * An event object is passed to handler.
     * 
     * event.data.seriesIndex
     * event.data.pointIndex
     * event.data.x
     * event.data.y
     *  
     */
    @Attribute(events =
    @EventName("dragstop"))
    public abstract String getOndragstop();
    
    /**
     * Javascript handler for dragstart event.
     * An event object is passed to handler.
     * 
     * event.data.seriesIndex
     * event.data.pointIndex
     * event.data.x
     * event.data.y
     *  
     */
    @Attribute(events =
    @EventName("dragstart"))
    public abstract String getOndragstart();
    
    /**
     * Javascript handler for pointmove event.
     * An event object is passed to handler.
     * 
     * event.data.seriesIndex
     * event.data.pointIndex
     * event.data.x
     * event.data.y
     *  
     */
    @Attribute(events =
    @EventName("pointmove"))
    public abstract String getOnpointmove();
    

    /**
     * Server-side listener for dataclick event.
     * Method which accepts one parameter - DataClickEvent
     * 
     */
    @Attribute(signature =
    @Signature(parameters = DataClickEvent.class))
    public abstract MethodExpression getDataClickListener();
    
    
    /**
     * Server-side listener for dragstop event.
     * Method which accepts one parameter - DragStopEvent
     * 
     */
    @Attribute(signature =
    @Signature(parameters = DragStopEvent.class))
    public abstract MethodExpression getDragStopListener();
        
    /**
     * The method broadcast events to listeners
     */
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
        else if(event instanceof DragStopEvent){
            FacesContext facesContext= getFacesContext();
            MethodExpression expression = getDragStopListener();
            if(expression != null){
                expression.invoke(facesContext.getELContext(), new Object[]{event});
            }
            super.broadcast(event);
        }
        else{
            super.broadcast(event);
        }
    }

}
