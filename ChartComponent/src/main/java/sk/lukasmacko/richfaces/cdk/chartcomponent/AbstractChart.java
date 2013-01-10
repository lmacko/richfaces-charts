package sk.lukasmacko.richfaces.cdk.chartcomponent;

import org.richfaces.cdk.annotations.*;

/**
 * A trivial hello world component
 */

@JsfComponent(
        type = "sk.lukasmacko.richfaces.chartcomponent.Chart",
        family = "sk.lukasmacko.text",
        renderer = @JsfRenderer(type = "sk.lukasmacko.chart"),
        tag = @Tag(name="chart"))
abstract public class AbstractChart extends javax.faces.component.UIComponentBase {

}
