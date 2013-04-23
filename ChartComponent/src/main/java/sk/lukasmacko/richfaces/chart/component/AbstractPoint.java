package sk.lukasmacko.richfaces.chart.component;

import javax.faces.component.UIComponentBase;
import org.richfaces.cdk.annotations.Attribute;
import org.richfaces.cdk.annotations.JsfComponent;
import org.richfaces.cdk.annotations.Tag;

/**
 * The &lt;lm:point&gt; tag pass data to &lt;lm:series&gt;.
 * Example of usage:
 * &lt;lm:chart&gt;<br>
 * &nbsp; &lt;lm:series type="line"&gt<br>
 * &nbsp;&nbsp; &lt;a4j:repeat value="#{bean.items}" var="item"&gt;<br>
 * &nbsp;&nbsp;&nbsp; &lt;lm:point x=#{item.foo} y={item.bar} /&gt;<br>
 * &nbsp;&nbsp; &lt;/a4:repeat&gt;<br>
 * &nbsp; &lt;/lm:series&gt;<br>
 * &lt;/lm:chart&gt;<br>
 * @author Lukas Macko
 */
@JsfComponent(
        type="sk.lukasmacko.richfaces.chart.component.Point",
        family="sk.lukasmacko.Chart",
        tag =@Tag(name="point"))
public abstract class AbstractPoint extends UIComponentBase{
    
    /**
     * 
     * 
     */
    @Attribute(required=true)
    public abstract Object getX();
    
    /**
     * 
     * 
     */
    @Attribute(required=true)
    public abstract Number getY();
}
