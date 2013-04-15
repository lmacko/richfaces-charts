package sk.lukasmacko.richfaces.chart.component;

import org.richfaces.cdk.annotations.*;
/**
 * The &lt;lm:yaxis&lt; tag 
 * @author Lukas Macko
 */
@JsfComponent(
        type = "sk.lukasmacko.richfaces.chart.component.Yaxis",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="yaxis"))
abstract public class AbstractYaxis extends javax.faces.component.UIComponentBase implements AxisAttributes{

}

