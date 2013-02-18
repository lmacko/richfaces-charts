package sk.lukasmacko.richfaces.chart.component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.richfaces.cdk.annotations.*;



@JsfComponent(
        type = "sk.lukasmacko.richfaces.chartcomponent.Legend",
        family = "sk.lukasmacko.Chart",
        tag = @Tag(name="legend"))
abstract public class AbstractLegend extends javax.faces.component.UIComponentBase {
     
    
     @Attribute(defaultValue="PositionType.right",suggestedValue="PositionType.left,PositionType.top,PositionType.bottom")
     public abstract PositionType getPosition();
     
     @Attribute(defaultValue = "PlacementType.inside")
     public abstract PlacementType getPlacement();
     
     public static enum PlacementType {
        inside,
        outside
     }
     public static enum PositionType{
         top,left,right,bottom
     }
     
     public static Map<PositionType,String> positionMap;
     static {
        Map <PositionType,String>tmp = new HashMap<>();
        tmp.put(PositionType.top, "n");
        tmp.put(PositionType.left, "w");
        tmp.put(PositionType.bottom, "s");
        tmp.put(PositionType.right, "e");
        positionMap=Collections.unmodifiableMap(tmp);
    }
}
