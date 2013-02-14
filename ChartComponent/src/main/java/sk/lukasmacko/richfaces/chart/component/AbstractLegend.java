package sk.lukasmacko.richfaces.chart.component;

import org.richfaces.cdk.annotations.*;



@JsfComponent(
        type = "sk.lukasmacko.richfaces.chartcomponent.Legend",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="legend"))
abstract public class AbstractLegend extends javax.faces.component.UIComponentBase {
     
    
     @Attribute
     public abstract String getPosition();
     
     @Attribute(defaultValue = "PlacementType.inside")
     public abstract PlacementType getPlacement();
     
     public static enum PlacementType {
        inside,
        outside
    }
}
