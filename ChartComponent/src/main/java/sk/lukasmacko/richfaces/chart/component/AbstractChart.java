package sk.lukasmacko.richfaces.chart.component;

import org.richfaces.cdk.annotations.*;


@JsfComponent(
        type = "sk.lukasmacko.richfaces.chart.component.Chart",
family = "sk.lukasmacko.Chart",
renderer =
@JsfRenderer(type = "sk.lukasmacko.chartRenderer"),
tag =
@Tag(name = "chart"))
abstract public class AbstractChart extends javax.faces.component.UIComponentBase {

    @Attribute
    public abstract String getTitle();
    
    @Attribute
    public abstract String getStyleClass();
    
   
}
