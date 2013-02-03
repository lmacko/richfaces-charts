package sk.lukasmacko.richfaces.cdk.chartcomponent;

import org.richfaces.cdk.annotations.*;

/**
 * A trivial hello world component
 */

@JsfComponent(
        type = "sk.lukasmacko.richfaces.chartcomponent.Yaxis",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="yaxis"))
abstract public class AbstractYaxis extends javax.faces.component.UIComponentBase implements AxisAttributes{

}

