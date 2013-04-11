package sk.lukasmacko.richfaces.chart.component;

import org.richfaces.cdk.annotations.*;
/**
 * The &lt;lm:xaxis&lt; tag 
 * @author Lukas Macko
 */
@JsfComponent(
        type = "sk.lukasmacko.richfaces.chart.component.Xaxis",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="xaxis"))
abstract public class AbstractXaxis extends javax.faces.component.UIComponentBase implements AxisAttributes{

}
